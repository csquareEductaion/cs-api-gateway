package com.csquare.gateway.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Custom class for HttpUtil
 *
 * @copyright Copyright (c) CodeX. All Right Reserved.
 * @author CodeX
 */

public class HttpUtil {

    /**
     * @param sourceReq - the HttpServletRequest
     * @param sourceResp - the HttpServletResponse
     * @param forwardURL - the String
     */
    public void forward(HttpServletRequest sourceReq, HttpServletResponse sourceResp, String forwardURL) {

        HttpURLConnection proxyConn = null;
        try {
            final String method = sourceReq.getMethod();
            final boolean hasOutBody = method.equals("POST");

            String queryString = "";
            if (null != sourceReq.getQueryString()) {
                queryString = StringUtil.append("?", sourceReq.getQueryString());
            }

            forwardURL = StringUtil.append(forwardURL, queryString);

            URL url = new URL(forwardURL);
            proxyConn = (HttpURLConnection) url.openConnection();
            proxyConn.setRequestMethod(method);
            proxyConn.setUseCaches(false);
            proxyConn.setDoInput(true);
            proxyConn.setDoOutput(hasOutBody);

            copyRequestHeadersToProxy(sourceReq, proxyConn);

            // conn.setFollowRedirects(false); // throws AccessDenied exception

            proxyConn.connect();
            if (hasOutBody) {
                copyPostParamsToProxy(sourceReq, proxyConn);
            }

            copyResponseFromProxy(sourceResp, proxyConn);
        } catch (MalformedURLException e) {
            sourceResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            sourceResp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } finally {
            CommonUtil.disconnect(proxyConn);
        }
    }

    /**
     * @param sourceResp - the HttpServletResponse
     * @param proxyConn - the HttpURLConnection
     * @throws IOException - the IOException
     */
    private void copyResponseFromProxy(HttpServletResponse sourceResp, HttpURLConnection proxyConn) throws IOException {

        int responseCode = proxyConn.getResponseCode();
        sourceResp.setStatus(responseCode);
        copyResponseHeadersFromProxy(sourceResp, proxyConn);
        copyResponseBodyFromProxy(sourceResp, proxyConn);
    }

    /**
     * @param sourceResp - the HttpServletResponse
     * @param proxyConn - the HttpURLConnection
     * @throws IOException - the IOException
     */
    private void copyResponseBodyFromProxy(HttpServletResponse sourceResp, HttpURLConnection proxyConn) throws IOException {

        InputStream proxyConnIS = null;

        try {
            proxyConnIS = proxyConn.getInputStream();
        } catch (Exception ex) {
            proxyConnIS = proxyConn.getErrorStream();
        }

        OutputStream sourceOS = sourceResp.getOutputStream();
        try {
            int bytesRead;
            byte[] buffer = new byte[16 * 1024];
            while ((bytesRead = proxyConnIS.read(buffer)) != -1) {
                sourceOS.write(buffer, 0, bytesRead);
            }
        } finally {
            CommonUtil.flush(sourceOS);
            CommonUtil.close(sourceOS);
            CommonUtil.close(proxyConnIS);
        }
    }

    /**
     * @param sourceResp - the HttpServletResponse
     * @param proxyConn - the HttpURLConnection
     */
    private void copyResponseHeadersFromProxy(HttpServletResponse sourceResp, HttpURLConnection proxyConn) {

        Map<String, List<String>> proxyConnHeaders = proxyConn.getHeaderFields();
        Set<String> headerNames = proxyConnHeaders.keySet();
        for (String headerName : headerNames) {
            List<String> headerValues = proxyConnHeaders.get(headerName);
            sourceResp.setHeader(headerName, headerValues.get(0));
        }
    }

    /**
     * @param sourceReq - the HttpServletRequest
     * @param proxyConn - the HttpURLConnection
     * @throws IOException - the IOException
     */
    private void copyPostParamsToProxy(HttpServletRequest sourceReq, HttpURLConnection proxyConn) throws IOException {

        InputStream reqIS = null;
        OutputStream proxyConnOS = null;
        int bytesRead;
        try {
            reqIS = sourceReq.getInputStream();
            proxyConnOS = proxyConn.getOutputStream();
            final byte[] buffer = new byte[16 * 1024];
            while ((bytesRead = reqIS.read(buffer)) != -1) {
                proxyConnOS.write(buffer, 0, bytesRead);
            }
        } finally {
            CommonUtil.flush(proxyConnOS);
            CommonUtil.close(proxyConnOS);
        }
        return;
    }

    /**
     * @param sourceReq - the HttpServletRequest
     * @param proxyConn - the HttpURLConnection
     */
    private void copyRequestHeadersToProxy(HttpServletRequest sourceReq, HttpURLConnection proxyConn) {

        final Enumeration<String> headers = sourceReq.getHeaderNames();
        while (headers.hasMoreElements()) {
            final String header = headers.nextElement();
            proxyConn.addRequestProperty(header, sourceReq.getHeader(header));
        }
    }
}
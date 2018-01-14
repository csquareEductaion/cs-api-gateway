package com.csquare.gateway.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.xml.sax.SAXException;

import com.csquare.gateway.parser.ConfigParser;
import com.csquare.gateway.util.HttpUtil;
import com.csquare.gateway.util.StringUtil;


/**
 * Custom class for APIController
 *
 * @copyright Copyright (c) CodeX. All Right Reserved.
 * @author CodeX
 */

public class ApiGateWayServlet extends HttpServlet {

    private WebApplicationContext webApplicationContext;

    public ApiGateWayServlet(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    /** serialVersionUID */
    private static final long serialVersionUID = -5884034153719292456L;
    private static final Logger LOGGER = Logger.getLogger(ApiGateWayServlet.class);
    private final Map<String, String> urlPatternProxyMap = new HashMap<String, String>();

    @Override
    public void init(javax.servlet.ServletConfig servletConfig) throws ServletException {

        LOGGER.info("Calling FrontController.init()");
        super.init(servletConfig);

        initController();
        LOGGER.info("FrontController done parsing: ");
    }

    @Override
    public void doGet(HttpServletRequest sourceReq, HttpServletResponse sourceResp) {

        forwardRequest(sourceReq, sourceResp);
    }

    @Override
    public void doPost(HttpServletRequest sourceReq, HttpServletResponse sourceResp) {

        forwardRequest(sourceReq, sourceResp);
    }

    // -----------------------------------------------------------------------------------------------------
    /**
     * @param sourceReq - the HttpServletRequest
     * @param sourceResp - the HttpServletResponse
     */
    private void forwardRequest(HttpServletRequest sourceReq, HttpServletResponse sourceResp) {

        sourceResp.addHeader("Access-Control-Allow-Origin", "*");
        String contextPath = sourceReq.getContextPath();
        String forwardURI = sourceReq.getRequestURI().replace(contextPath, "");
        String forwardURL = getForwardURL(forwardURI);
        if (StringUtil.isEmpty(forwardURL)) {
            LOGGER.error(StringUtil.append("Unable to find Forward URL in mappings: ", forwardURI));
            sourceResp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        forwardURL = StringUtil.append(forwardURL, forwardURI);
        HttpUtil httpUtil = new HttpUtil();
        httpUtil.forward(sourceReq, sourceResp, forwardURL);
    }

    /**
     * 
     */
    private void initController() {

        ConfigParser configParser = new ConfigParser();
        try {
            configParser.parse();
        } catch (SAXException e) {
            LOGGER.error(e);
        } catch (ParserConfigurationException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (Exception e) {
            LOGGER.error(e);
        }

        Map<String, String> urlPatternProxyMap = configParser.getUrlPatternMap();
        this.urlPatternProxyMap.clear();
        if (null == urlPatternProxyMap) {
            return;
        }

        this.urlPatternProxyMap.putAll(urlPatternProxyMap);
    }

    /**
     * @param forwardURI - the String
     * @return forwardURL - the String
     */
    private String getForwardURL(String forwardURI) {

        int firstSlashIndex = forwardURI.indexOf("/");
        if (0 == firstSlashIndex) {
            forwardURI = forwardURI.substring(1);
        }

        String[] requestURISplit = forwardURI.split("/");
        String forwardURL = "";
        StringBuilder urlToSearch = new StringBuilder();
        for (String uri : requestURISplit) {

            urlToSearch.append(uri);
            if (urlPatternProxyMap.containsKey(urlToSearch.toString())) {
                forwardURL = urlPatternProxyMap.get(urlToSearch.toString());
                return forwardURL;
            }
            urlToSearch.append("/");
        }

        return forwardURL;
    }
}
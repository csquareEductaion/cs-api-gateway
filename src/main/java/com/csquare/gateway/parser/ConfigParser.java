package com.csquare.gateway.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.csquare.gateway.util.CommonUtil;
import com.csquare.gateway.util.SystemUtil;
import com.csquare.gateway.util.SystemUtil.SystemKey;


/**
 * Custom class for ConfigParser
 *
 * @copyright Copyright (c) CodeX. All Right Reserved.
 * @author CodeX
 */

public class ConfigParser extends DefaultHandler {

    private String proxyUrl = null;

    private final Map<String, String> urlPatternProxyMap = new HashMap<String, String>();

    /**
     * @throws SAXException
     *             - the SAXException
     * @throws ParserConfigurationException
     *             - the ParserConfigurationException
     * @throws IOException
     *             - the IOException
     */
    public void parse() throws SAXException, ParserConfigurationException, IOException {

        InputStream input = null;
        try {
            input = readXML();
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            // saxFactory.setSchema(schema);
            SAXParser saxParser = saxFactory.newSAXParser();
            saxParser.parse(input, this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            CommonUtil.close(input);
        }
        return;
    }

    private InputStream readXML() {

        InputStream input = null;
        try {
            String appConfigPath = SystemUtil.getEnv(SystemKey.CS_APPCONFIG_PATH);
            input = new FileInputStream(appConfigPath + "/api-gateway-config.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return input;
    }

    /**
     * Method to start element
     *
     * @param uri
     *            - The String
     * @param localName
     *            - The String
     * @param elementName
     *            - The String
     * @param attributes
     *            - The Attributes
     * @throws SAXException
     *             - The SAXException
     */
    @Override
    public void startElement(String uri, String localName, String elementName, Attributes attributes) throws SAXException {

        if ("proxy".equals(elementName)) {
            proxyUrl = attributes.getValue("forward-url");
        } else if ("url-pattern".equals(elementName)) {
            String urlPattern = attributes.getValue("value");
            urlPatternProxyMap.put(urlPattern, proxyUrl);
        }
    }

    @Override
    public void characters(char[] buffer, int start, int length) throws SAXException {

        // if (!rowBegin) {
        // return;
        // }
        //
        // text = new String(buffer, start, length);
    }

    /**
     * Method to end element
     *
     * @param uri
     *            - The String
     * @param localName
     *            - The String
     * @param elementName
     *            - The String
     * @throws SAXException
     *             - The SAXException
     */
    @Override
    public void endElement(String uri, String localName, String elementName) throws SAXException {

        // if (!rowBegin) {
        // return;
        // }

        // if ("proxy".equals(elementName)) {
        // rowBegin = false;
        // productList.add(productBean);
        // return;
        // }
        //
        // String value = sanitize(elementName, text);
        // String beanProperty = getBeanProperty(elementName);
        // setBeanPropertyValue(productBean, beanProperty, value);
    }

    /**
     * Method to get productList
     *
     * @return productList - The ArrayList<ProductBean>
     */
    public Map<String, String> getUrlPatternMap() {

        return urlPatternProxyMap;
    }

}
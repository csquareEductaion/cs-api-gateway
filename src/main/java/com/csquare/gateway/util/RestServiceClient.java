package com.csquare.gateway.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;


public enum RestServiceClient {

    INSTANCE;

    private final List<MediaType> mediaTypes;
    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;

    private RestServiceClient() {

        mediaTypes = Arrays.asList(MediaType.APPLICATION_JSON);
        mapper = new ObjectMapper();
        restTemplate = new RestTemplate();
        setErrorHandler(restTemplate);
    }

    @SuppressWarnings("unchecked")
    public <T> T getForObject(String url, Class<T> cls) throws RestClientException {

        T value = null;
        try {
            String result = getRequest(url);
            if (null == result) {
                return value;
            }

            if (String.class.isAssignableFrom(cls)) {
                value = (T) result;
                return value;
            }

            value = mapper.readValue(result, cls);
        } catch (JsonParseException e) {
            throw new RestClientException("", e);
        } catch (JsonMappingException e) {
            throw new RestClientException("", e);
        } catch (IOException e) {
            throw new RestClientException("", e);
        }

        return value;
    }

    public <T> List<T> getForListObject(String url, Class<T> cls) throws RestClientException {

        List<T> valueList = null;
        try {

            String result = getRequest(url);
            if (null == result) {
                return valueList;
            }

            valueList = mapper.readValue(result, TypeFactory.defaultInstance().constructCollectionType(List.class, cls));
        } catch (JsonParseException e) {
            throw new RestClientException("", e);
        } catch (JsonMappingException e) {
            throw new RestClientException("", e);
        } catch (IOException e) {
            throw new RestClientException("", e);
        }
        return valueList;
    }

    // ---------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public <T> T postForObject(String url, Object param, Class<T> cls) throws RestClientException {

        T value = null;
        try {
            String result = postRequest(url, param);
            if (null == result) {
                return value;
            }

            if (String.class.isAssignableFrom(cls)) {
                value = (T) result;
                return value;
            }

            value = mapper.readValue(result, cls);
        } catch (JsonParseException e) {
            throw new RestClientException("", e);
        } catch (JsonMappingException e) {
            throw new RestClientException("", e);
        } catch (IOException e) {
            throw new RestClientException("", e);
        }

        return value;
    }

    public <T> List<T> postForListObject(String url, Object param, Class<T> cls) throws RestClientException {

        List<T> valueList = null;
        try {
            String result = postRequest(url, param);
            if (null == result) {
                return valueList;
            }

            valueList = mapper.readValue(result, TypeFactory.defaultInstance().constructCollectionType(List.class, cls));
        } catch (JsonParseException e) {
            throw new RestClientException("", e);
        } catch (JsonMappingException e) {
            throw new RestClientException("", e);
        } catch (IOException e) {
            throw new RestClientException("", e);
        }
        return valueList;
    }

    // --------------------------------------------------------------------------------------

    /**
     * @param url
     * @param sessionToken
     * @param userName
     * @return
     * @throws RestClientException
     */
    private String getRequest(String url) throws RestClientException {

        String sessionToken = UUID.randomUUID().toString();
        String userName = UUID.randomUUID().toString();
        // RedisCacheUtil.INSTANCE.putObject("INTERNAL_SERVICE", sessionToken,
        // userName, 1 * 60);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(mediaTypes);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("INTERNAL_SERVICE", "YES");
        headers.add("sessionToken", sessionToken);
        headers.add("userName", userName);

        HttpEntity<?> entity = new HttpEntity<Object>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        responseEntity.getStatusCode();
        String result = responseEntity.getBody();
        return result;
    }

    /**
     * @param url
     * @param sessionToken
     * @param userName
     * @return
     * @throws RestClientException
     */
    private String postRequest(String url, Object param) throws RestClientException {

        String sessionToken = UUID.randomUUID().toString();
        String userName = UUID.randomUUID().toString();
        // RedisCacheUtil.INSTANCE.putObject("INTERNAL_SERVICE", sessionToken,
        // userName, 1 * 60);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(mediaTypes);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("INTERNAL_SERVICE", "YES");
        headers.add("sessionToken", sessionToken);
        headers.add("userName", userName);

        // MultiValueMap<String, String> headers = new
        // LinkedMultiValueMap<String, String>();
        // headers.add("INTERNAL_SERVICE", "YES");
        // headers.add("sessionToken", sessionToken);
        // headers.add("userName", userName);

        HttpEntity<?> entity = null;
        if (null == param) {
            entity = new HttpEntity<Object>(headers);
        } else {
            entity = new HttpEntity<Object>(param, headers);
        }

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        responseEntity.getStatusCode();
        String result = responseEntity.getBody();
        return result;
    }

    /**
     * Method to set Error Handler for RestTemplate
     *
     * @param restTemplate
     *            - The RestTemplate
     */
    private void setErrorHandler(RestTemplate restTemplate) {

        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {

            protected boolean hasError(HttpStatus statusCode) {

                if (statusCode.is2xxSuccessful()) {
                    return false;
                }
                return true;
            }
        });
    }
}

package com.gocarshare.authserver.Configurations;

import com.gocarshare.authserver.Exception.ExceptionResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class RestUtil {

    private static RestTemplate restTemplate;

    @PostConstruct
    public void init(){
        restTemplate = new RestTemplate();
    }


    public static <T> T get(String url, Class<T> responseType) {
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, responseType);
        return responseEntity.getBody();
    }

    public static <T> T post(String url, Object request, Class<T> responseType) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(request);
        ResponseEntity<T> responseEntity = null;
        try {
          responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
          System.out.println(responseEntity.getStatusCode().value());
          if (responseEntity.getStatusCode().value() != 200) {
            throw new ExceptionResponse(
              responseEntity.getStatusCode().value(),
              Objects.requireNonNull(responseEntity.getBody()).toString());
          }
          return  responseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
          assert responseEntity != null;
          String body = e.getResponseBodyAsString();
          int statusCode = e.getRawStatusCode();
          throw new ExceptionResponse(statusCode, body);
        }
    }

    public static <T> T put(String url, Object request, Class<T> responseType) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(request);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType);
        return responseEntity.getBody();
    }

    public static <T> T post(String url, String request, Class<T> responseType, String userName, String password) {

        RestTemplate builder = new RestTemplateBuilder()
                .basicAuthentication(userName, password)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<T> responseEntity = builder.exchange(url, HttpMethod.POST, requestEntity, responseType);
        return responseEntity.getBody();
    }

}

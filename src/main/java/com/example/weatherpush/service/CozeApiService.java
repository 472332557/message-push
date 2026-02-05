package com.example.weatherpush.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CozeApiService {

    private static final Logger logger = LoggerFactory.getLogger(CozeApiService.class);

    private static final String COZE_API_URL = "https://api.coze.cn/v1/workflow/stream_run";
    private static final String TOKEN = "pat_o2zmctmRKqtxOMBlVacJF07TMvvqGfzb90zt2JEfvVGxE7j3nChDHRJQxOVU2f71";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CozeApiService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String callCozeWorkflow(String city) {
        try {
            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(TOKEN);

            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("workflow_id", "7602641055988891686");

            Map<String, String> parameters = new HashMap<>();
            parameters.put("city", city);
            requestBody.put("parameters", parameters);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            logger.info("Calling Coze API with city: {}", city);

            // 发送POST请求
            ResponseEntity<String> response = restTemplate.postForEntity(
                    COZE_API_URL,
                    requestEntity,
                    String.class);

            logger.info("Coze API response status: {}", response.getStatusCode());
            logger.info("Coze API response body: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {
            logger.error("Error calling Coze API", e);
            return "Error: " + e.getMessage();
        }
    }
}
package com.example.weatherpush.service;

import com.example.weatherpush.config.CozeApiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Coze API 服务类
 * 作者：liangzc
 * 日期：2026 年 03 月 12 日
 */
@Service
public class CozeApiService {

    private static final Logger logger = LoggerFactory.getLogger(CozeApiService.class);

    @Autowired
    private CozeApiConfig cozeApiConfig;

    private final RestTemplate restTemplate;

    public CozeApiService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * 调用天气工作流
     * @param city 城市名称
     * @return API 响应结果
     */
    public String callCozeWorkflow(String city) {
        // 参数校验
        if (city == null || city.trim().isEmpty()) {
            logger.error("城市参数不能为空");
            throw new IllegalArgumentException("城市参数不能为空");
        }

        // 去除首尾空格
        city = city.trim();

        // 校验城市名称长度
        if (city.length() > 50) {
            logger.error("城市名称过长：{}", city.length());
            throw new IllegalArgumentException("城市名称不能超过 50 个字符");
        }

        // 校验是否包含特殊字符 (只允许中文、英文、空格和连字符)
        if (!city.matches("[\\u4e00-\\u9fa5a-zA-Z\\s-]+")) {
            logger.error("城市名称包含非法字符：{}", city);
            throw new IllegalArgumentException("城市名称只能包含中文、英文、空格和连字符");
        }

        // 构建请求参数
        Map<String, String> parameters = new HashMap<>();
        parameters.put("city", city);

        return callWorkflow(cozeApiConfig.getWeatherWorkflowId(), parameters, "天气");
    }

    /**
     * 调用新闻工作流
     * @return API 响应结果
     */
    public String callNewsWorkflow() {
        Map<String, Object> parameters = new HashMap<>();
        return callWorkflow(cozeApiConfig.getNewsWorkflowId(), parameters, "新闻");
    }

    /**
     * 通用的工作流调用方法
     * @param workflowId 工作流 ID
     * @param parameters 请求参数
     * @param workflowName 工作流名称 (用于日志)
     * @return API 响应结果
     */
    private String callWorkflow(String workflowId, Map<String, ?> parameters, String workflowName) {
        try {
            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(cozeApiConfig.getToken());

            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("workflow_id", workflowId);
            requestBody.put("parameters", parameters);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            logger.info("Calling Coze {} API, workflow_id: {}", workflowName, workflowId);

            // 发送 POST 请求
            ResponseEntity<String> response = restTemplate.postForEntity(
                    cozeApiConfig.getApiUrl(),
                    requestEntity,
                    String.class);

            logger.info("Coze {} API response status: {}, body: {}", 
                workflowName, response.getStatusCode(), response.getBody());

            return response.getBody();

        } catch (Exception e) {
            logger.error("Error calling Coze {} API", workflowName, e);
            return "Error: " + e.getMessage();
        }
    }
}
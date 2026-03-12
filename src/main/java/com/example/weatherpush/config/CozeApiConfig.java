package com.example.weatherpush.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * Coze API 配置类
 * 作者：liangzc
 * 日期：2026 年 03 月 12 日
 */
@Configuration
public class CozeApiConfig {

    /**
     * Coze API 基础 URL
     */
    @Value("${coze.api.url:https://api.coze.cn/v1/workflow/stream_run}")
    private String apiUrl;

    /**
     * Coze API 认证 Token（从环境变量 COZE_TOKEN 读取）
     */
    @Value("${coze.api.token}")
    private String token;

    /**
     * 天气工作流 ID（从环境变量 WEATHER_WORKFLOW_ID 读取）
     */
    @Value("${coze.api.workflow-id.weather}")
    private String weatherWorkflowId;

    /**
     * 新闻工作流 ID（从环境变量 NEWS_WORKFLOW_ID 读取）
     */
    @Value("${coze.api.workflow-id.news}")
    private String newsWorkflowId;

    /**
     * 验证配置是否完整
     */
    @PostConstruct
    public void validate() {
        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("COZE_TOKEN 未配置，请设置环境变量或配置文件");
        }
        if (weatherWorkflowId == null || weatherWorkflowId.isEmpty()) {
            throw new IllegalStateException("WEATHER_WORKFLOW_ID 未配置，请设置环境变量或配置文件");
        }
    }

    /**
     * 配置带有超时的 RestTemplate Bean
     * @return 配置好的 RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);  // 5 秒连接超时
        factory.setReadTimeout(30000);    // 30 秒读取超时
        return new RestTemplate(factory);
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getToken() {
        return token;
    }

    public String getWeatherWorkflowId() {
        return weatherWorkflowId;
    }

    public String getNewsWorkflowId() {
        return newsWorkflowId;
    }
}

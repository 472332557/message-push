package com.example.weatherpush.controller;

import com.example.weatherpush.service.CozeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 * 作者：liangzc
 * 日期：2026 年 03 月 12 日
 */

@RestController
@RequestMapping("/api")
public class TestController {

    private final CozeApiService cozeApiService;

    // 构造器注入
    @Autowired
    public TestController(CozeApiService cozeApiService) {
        this.cozeApiService = cozeApiService;
    }

    /**
     * 测试 Coze API 接口
     * @return Coze API 调用结果
     */
    @GetMapping("/test-coze")
    public String testCozeApi() {
        return cozeApiService.callCozeWorkflow("深圳");
    }

    /**
     * 健康检查接口
     * @return 应用运行状态
     */
    @GetMapping("/health")
    public String health() {
        return "Application is running!";
    }
}
package com.example.weatherpush.controller;

import com.example.weatherpush.service.CozeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 * <p>提供 Coze API 测试和健康检查接口</p>
 *
 * @author liangzc
 * @date 2026-03-13
 */

@RestController
@RequestMapping("/api")
public class TestController {

    private final CozeApiService cozeApiService;

    /**
     * 构造器注入 CozeApiService
     *
     * @param cozeApiService Coze API 服务实例
     */
    @Autowired
    public TestController(CozeApiService cozeApiService) {
        this.cozeApiService = cozeApiService;
    }

    /**
     * 测试 Coze 天气工作流 API
     * <p>调用深圳地区的天气工作流并返回结果</p>
     *
     * @return Coze API 调用返回的天气数据
     */
    @GetMapping("/test-coze")
    public String testCozeApi() {
        return cozeApiService.callCozeWorkflow("深圳");
    }

    /**
     * 应用健康检查接口
     * <p>用于监控服务运行状态</p>
     *
     * @return 应用运行状态信息
     */
    @GetMapping("/health")
    public String health() {
        return "Application is running!";
    }

    /**
     * 新闻推送接口
     * <p>调用 Coze 新闻工作流获取今日新闻资讯</p>
     *
     * @return Coze API 调用返回的新闻数据
     */
    @GetMapping("/news")
    public String newsPush() {
        return cozeApiService.callNewsWorkflow();
    }
}
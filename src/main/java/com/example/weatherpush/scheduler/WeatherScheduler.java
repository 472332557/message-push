package com.example.weatherpush.scheduler;

import com.example.weatherpush.service.CozeApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 天气定时任务调度器
 * 作者：liangzc
 * 日期：2026年02月04日
 */
@Component
public class WeatherScheduler {

    private static final Logger logger = LoggerFactory.getLogger(WeatherScheduler.class);

    @Autowired
    private CozeApiService cozeApiService;

    /**
     * 每天早上7:30执行的天气推送任务
     * cron表达式：0 30 7 * * ?
     * 秒 分 时 日 月 周
     */
    @Scheduled(cron = "0 30 7 * * ?")
    public void scheduledWeatherPush() {
        logger.info("开始执行定时天气推送任务...");

        try {
            // 调用Coze API获取深圳天气
            String result = cozeApiService.callCozeWorkflow("深圳");

            logger.info("定时任务Coze API调用结果: {}", result);

        } catch (Exception e) {
            logger.error("定时天气推送任务执行失败", e);
        }
    }

    /**
     * 每天早上7:35执行的测试任务
     * cron表达式：0 35 7 * * ?
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void scheduledTestTask() {
        logger.info("每天7:35执行的测试任务 - 当前时间: {}", System.currentTimeMillis());
    }
}
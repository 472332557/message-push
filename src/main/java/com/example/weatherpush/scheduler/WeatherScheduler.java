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
 * 日期：2026 年 02 月 04 日
 */
@Component
public class WeatherScheduler {

    private static final Logger logger = LoggerFactory.getLogger(WeatherScheduler.class);

    @Autowired
    private CozeApiService cozeApiService;

    /**
     * 每天早上 7:30 执行的天气推送任务
     */
    @Scheduled(cron = "${scheduler.cron.weather:0 30 7 * * ?}")
    public void scheduledWeatherPush() {
        logger.info("开始执行定时天气推送任务...");

        try {
            // 调用 Coze API 获取深圳天气
            String result = cozeApiService.callCozeWorkflow("深圳");

            // 检查结果是否成功
            if (result != null && result.startsWith("Error")) {
                logger.error("天气推送失败：{}", result);
            } else {
                logger.info("天气推送成功：{}", result);
            }

        } catch (Exception e) {
            logger.error("定时天气推送任务执行失败", e);
        }
    }

    /**
     * 每 10 分钟执行一次的测试任务
     */
    @Scheduled(cron = "${scheduler.cron.test:0 */10 * * * ?}")
    public void scheduledTestTask() {
        logger.info("每 10 分钟执行一次的测试任务 - 当前时间：{}", System.currentTimeMillis());
    }

    /**
     * 每天早上 8:00 执行的新闻推送任务
     */
    @Scheduled(cron = "${scheduler.cron.news:0 0 8 * * ?}")
    public void scheduledNewsPush() {
        logger.info("开始执行定时新闻推送任务...");

        try {
            // 调用 Coze API 获取新闻
            String result = cozeApiService.callNewsWorkflow();

            // 检查结果是否成功
            if (result != null && result.startsWith("Error")) {
                logger.error("新闻推送失败：{}", result);
            } else {
                logger.info("新闻推送成功：{}", result);
            }

        } catch (Exception e) {
            logger.error("定时新闻推送任务执行失败", e);
        }
    }
}

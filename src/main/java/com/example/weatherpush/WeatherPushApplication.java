package com.example.weatherpush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 天气推送应用启动类
 * 作者：liangzc
 * 日期：2026 年 03 月 12 日
 */

@SpringBootApplication
@EnableScheduling
public class WeatherPushApplication {

    /**
     * 应用启动入口方法
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(WeatherPushApplication.class, args);
    }

}
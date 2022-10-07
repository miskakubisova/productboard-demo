package com.productboard.productboarddemo.cron;

import com.productboard.productboarddemo.service.GithubApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This cron job is called every day at 6am to load actual language stats for GitHub repository.
 */
@Slf4j
@Component
@AllArgsConstructor
public class CronJob {
    private final GithubApiService githubApiService;

    @Scheduled(cron = "0 0 6 * * ?")
    public void scheduleFixedRateTask() {
        log.debug("action=calling_daily_job status=start");
        var languageStats = githubApiService.getLanguageStatsAndSaveHistory();
        log.debug("action=calling_daily_job status=end message=found {} language stats", languageStats.size());
    }
}

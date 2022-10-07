package com.productboard.productboarddemo.controller;

import com.productboard.productboarddemo.service.GithubApiService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * I used this controller only for testing purposes, to test the functionality before running cron job.
 */
@Data
@RestController
@AllArgsConstructor
public class GithubApiController {

    private final GithubApiService githubApiService;

    @GetMapping("language-stats")
    public Map<String, Double> getLanguageStats(){
        return githubApiService.getLanguageStatsAndSaveInstantly();
    }
}

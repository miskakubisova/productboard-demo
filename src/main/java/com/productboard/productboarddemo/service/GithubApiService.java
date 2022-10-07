package com.productboard.productboarddemo.service;

import com.productboard.productboarddemo.provider.GithubApiProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * This service might be useless in this simple scenario, but it is good practice keeping the layers apart.
 * Also, I didn't use interface for this service as I would normally do, but I found it useless this time.
 */
@Service
@AllArgsConstructor
public class GithubApiService {

    private final GithubApiProvider githubApiProvider;

    public Map<String, Double> getLanguageStatsAndSaveInstantly(){
        return githubApiProvider.getLanguageStatsAndSaveInstantly();
    }

    public Map<String, Double> getLanguageStatsAndSaveHistory(){
        return githubApiProvider.getLanguageStatsAndSaveHistory();
    }
}

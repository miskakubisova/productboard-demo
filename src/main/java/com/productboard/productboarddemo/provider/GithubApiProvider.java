package com.productboard.productboarddemo.provider;

import com.productboard.productboarddemo.config.GithubApiConfiguration;
import com.productboard.productboarddemo.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Simple provider that calls GitHub API using {@link RestTemplate}.
 * API url is read from configuration file
 * For repositories that didn't have language specified I introduced "Unknown" value
 */
@Slf4j
@Component
@AllArgsConstructor
public class GithubApiProvider {

    private static final String REPOSITORIES_URL = "/repos";
    private static final String QUERY = "type={repositoryType}&per_page={pageSize}";
    private static final String PAGE_PARAM = "pageSize";
    private static final String TYPE_PARAM = "repositoryType";
    private static final String PAGE_SIZE = "100";
    private static final String TYPE = "public";
    private final GithubApiConfiguration configuration;
    private final LanguageStatsRepository languageStatsRepository;
    private final LanguageStatsHistoryRepository languageStatsHistoryRepository;
    private RestTemplate restTemplate;

    public Map<String, Double> getLanguageStatsAndSaveInstantly() {
        var statsCount = getLanguageStatsResources();
        saveLanguageStatsInstantly(statsCount);
        return statsCount;
    }

    public Map<String, Double> getLanguageStatsAndSaveHistory() {
        var statsCount = getLanguageStatsResources();
        saveLanguageStatsHistory(statsCount);
        return statsCount;
    }

    private Map<String, Double> getLanguageStatsResources() {
        var params = Map.of(TYPE_PARAM, TYPE, PAGE_PARAM, PAGE_SIZE);
        var uri = UriComponentsBuilder
                .fromHttpUrl(configuration.getEndpointUrl() + REPOSITORIES_URL)
                .query(QUERY)
                .build(params);
        log.debug("action=call_github_api status=start");
        var response = Optional.ofNullable(restTemplate.getForObject(uri, GithubRepository[].class))
                .orElse(new GithubRepository[]{});
        log.debug("action=call_github_api status=end message=found {} results", response.length);
        return countStats(List.of(response));
    }

    private void saveLanguageStatsInstantly(Map<String, Double> githubRepositories) {
        githubRepositories.forEach((language, percentage) -> {
            if (languageStatsRepository.existsByLanguageNameIgnoreCase(language)) {
                log.debug("language stat for language {} already exists, updating percentage", language);
                var languageStats = languageStatsRepository.findByLanguageNameIgnoreCase(language);
                languageStats.setPercentage(percentage);
                languageStatsRepository.save(languageStats);
            } else {
                log.debug("language stat for language {} does not exist yey, creating new entry", language);
                languageStatsRepository
                        .save(LanguageStats.builder()
                                .languageName(language)
                                .percentage(percentage)
                                .build());
            }
        });
    }

    private void saveLanguageStatsHistory(Map<String, Double> githubRepositories) {
        // I know that saving whole object (even list of objects!) like this is not the best solution,
        // maybe I could store it as JSON, but I thought that it could be enough for this simple case
        languageStatsHistoryRepository.save(LanguageStatsHistory.builder().data(githubRepositories.toString()).build());
    }

    private Map<String, Double> countStats(List<GithubRepository> githubRepositories) {
        var size = githubRepositories.size();
        var groupedLanguages = StreamEx.of(githubRepositories)
                .peek(repo -> {
                    if (repo.getLanguage() == null) {
                        repo.setLanguage("Unknown");
                    }
                })
                .groupingBy(GithubRepository::getLanguage, Collectors.counting());

        return groupedLanguages.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        value -> new BigDecimal((double) value.getValue() / size).setScale(2, RoundingMode.HALF_UP).doubleValue()));
    }
}

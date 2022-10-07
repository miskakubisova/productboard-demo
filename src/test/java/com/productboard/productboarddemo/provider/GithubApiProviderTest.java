package com.productboard.productboarddemo.provider;

import com.productboard.productboarddemo.config.GithubApiConfiguration;
import com.productboard.productboarddemo.model.GithubRepository;
import com.productboard.productboarddemo.model.LanguageStats;
import com.productboard.productboarddemo.model.LanguageStatsHistoryRepository;
import com.productboard.productboarddemo.model.LanguageStatsRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GithubApiProviderTest {

    public static final String ENDPOINT_URL = "https://api.github.com/orgs/productboard";
    @Mock
    GithubApiConfiguration githubApiConfiguration;

    @Mock
    LanguageStatsRepository languageStatsRepository;

    @Mock
    LanguageStatsHistoryRepository languageStatsHistoryRepository;

    @Mock
    RestTemplate restTemplate;
    @InjectMocks

    private GithubApiProvider githubApiProvider;

    @Order(1)
    @Test
    void getLanguageStatsAndSaveInstantly_Insert() {
        var githubRepositories = getGithubRepositories();
        when(githubApiConfiguration.getEndpointUrl()).thenReturn(ENDPOINT_URL);
        when(restTemplate.getForObject(any(URI.class), any())).thenReturn(githubRepositories);
        when(languageStatsRepository.existsByLanguageNameIgnoreCase(anyString())).thenReturn(false);

        githubApiProvider.getLanguageStatsAndSaveInstantly();

        verify(languageStatsRepository, times(0)).findByLanguageNameIgnoreCase(any());
        verify(languageStatsRepository, times(3)).save(any());
        verify(languageStatsHistoryRepository, times(0)).save(any());
    }

    @Order(2)
    @Test
    void getLanguageStatsAndSaveInstantly_Update(){
        var githubRepositories = new GithubRepository[] {GithubRepository.builder().language("java").build()};
        when(githubApiConfiguration.getEndpointUrl()).thenReturn(ENDPOINT_URL);
        when(restTemplate.getForObject(any(URI.class), any())).thenReturn(githubRepositories);
        when(languageStatsRepository.existsByLanguageNameIgnoreCase(anyString())).thenReturn(true);
        when(languageStatsRepository.findByLanguageNameIgnoreCase("java")).thenReturn(LanguageStats.builder().languageName("java").percentage(0.5).build());

        githubApiProvider.getLanguageStatsAndSaveInstantly();

        verify(languageStatsRepository, times(1)).findByLanguageNameIgnoreCase(any());
        verify(languageStatsRepository, times(1)).save(any());
        verify(languageStatsHistoryRepository, times(0)).save(any());
    }

    @Order(3)
    @Test
    void getLanguageStatsAndSaveHistory() {
        var githubRepositories = getGithubRepositories();
        when(githubApiConfiguration.getEndpointUrl()).thenReturn(ENDPOINT_URL);
        when(restTemplate.getForObject(any(URI.class), any())).thenReturn(githubRepositories);

        githubApiProvider.getLanguageStatsAndSaveHistory();

        verify(languageStatsHistoryRepository, times(1)).save(any());
        verify(languageStatsRepository, times(0)).save(any());
    }

    private static GithubRepository[] getGithubRepositories() {
        return new GithubRepository[] {
                GithubRepository.builder().language("java").build(),
                GithubRepository.builder().language("kotlin").build(),
                GithubRepository.builder().language("ruby").build(),
                GithubRepository.builder().language("kotlin").build() };
    }
}
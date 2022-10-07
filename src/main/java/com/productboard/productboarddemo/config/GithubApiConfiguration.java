package com.productboard.productboarddemo.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Component storing endpoint url details.
 * For this simple case might be useless,
 * as I could have used constant to store the endpoint url,
 * but it is good practise to use configuration components.
 */
@Data
@Component
@ConfigurationProperties("connector.github-api")
public class GithubApiConfiguration {

    private String endpointUrl;
}

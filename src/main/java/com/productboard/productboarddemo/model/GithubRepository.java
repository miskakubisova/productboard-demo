package com.productboard.productboarddemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simplified representation of GitHub repository object from GitHub API.
 * I left out some fields that was not necessary for me. I could have used only language,
 * but I kept also id and name just out of curiosity (might be useless).
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GithubRepository {
    private float id;
    private String name;
    private String language;
}

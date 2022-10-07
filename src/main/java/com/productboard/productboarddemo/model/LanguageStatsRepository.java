package com.productboard.productboarddemo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageStatsRepository extends JpaRepository<LanguageStats, Integer> {
    boolean existsByLanguageNameIgnoreCase(String languageName);
    LanguageStats findByLanguageNameIgnoreCase(String languageName);
}

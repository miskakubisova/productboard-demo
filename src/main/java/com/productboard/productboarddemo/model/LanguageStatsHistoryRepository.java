package com.productboard.productboarddemo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageStatsHistoryRepository extends JpaRepository<LanguageStatsHistory, Integer> {
}

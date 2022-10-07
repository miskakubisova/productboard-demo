package com.productboard.productboarddemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Representation of DB table storing history overview of how language percentages where changing each day.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "language_stats_history")
@EntityListeners(AuditingEntityListener.class)
public class LanguageStatsHistory implements Serializable {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false, length = 2048)
    String data;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;
}

package com.ass.citysparkapplication.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 1)
    private String status; // A or I

    @Column(name = "created_dt")
    private LocalDateTime createdDt;

    @Column(name = "updated_dt")
    private LocalDateTime updatedDt;

    @PrePersist
    public void onCreate() {
        createdDt = updatedDt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedDt = LocalDateTime.now();
    }
}

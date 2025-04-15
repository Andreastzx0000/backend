package com.ass.citysparkapplication.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Preference")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 1)
    private String status; // A or I

    @Column(name = "created_dt")
    private LocalDateTime createdDt;

    @Column(name = "updated_dt")
    private LocalDateTime updatedDt;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @PrePersist
    public void onCreate() {
        createdDt = updatedDt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedDt = LocalDateTime.now();
    }
}

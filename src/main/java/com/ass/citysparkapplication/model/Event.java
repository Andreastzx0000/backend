package com.ass.citysparkapplication.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String title;

    @Column(nullable = false, length = 45)
    private String status; // Planning, Published, etc.

    @Column(length = 255)
    private String description;

    @Column(length = 255)
    private String location;

    @Column(name = "event_start_dt")
    private LocalDateTime eventStartDt;

    @Column(name = "event_end_dt")
    private LocalDateTime eventEndDt;

    @Column(name = "created_dt")
    private LocalDateTime createdDt;

    @Column(name = "updated_dt")
    private LocalDateTime updatedDt;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @PrePersist
    protected void onCreate() {
        createdDt = updatedDt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDt = LocalDateTime.now();
    }
}

package com.lokendra.teamcollab.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project projectId;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;
}

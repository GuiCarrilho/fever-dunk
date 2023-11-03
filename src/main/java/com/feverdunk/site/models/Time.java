package com.feverdunk.site.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Time")
public class Time {

    @Id
    @OneToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;
}

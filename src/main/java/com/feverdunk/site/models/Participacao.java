package com.feverdunk.site.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "participacoes")
public class Participacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participacao_id")
    private Long id;

    @Column(name = "data")
    @NotNull
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "liga_id")
    private Liga liga;

    @ManyToOne
    @JoinColumn(name = "time_id", referencedColumnName = "time_id")
    private Time time;
}

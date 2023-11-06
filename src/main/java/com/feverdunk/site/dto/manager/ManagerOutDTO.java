package com.feverdunk.site.dto.manager;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerOutDTO {
    private Long id;

    private String nome;

    private String email;

    private int dinheiro;

    private boolean premium;

    private Long timeId;
}

package com.feverdunk.site.dto.time;

import com.feverdunk.site.models.Contrato;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeOutDTO {
    private String id;
    private String nome;
    private Integer pontuacao;
    private List<Contrato> contratos;
}

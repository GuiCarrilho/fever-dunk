package com.feverdunk.site.dto.contrato;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContratoOutDTO {

    private LocalDateTime adquiridoEm;

    private LocalDateTime vendidoEm;

    private String jogadorId;

    private String timeId;

}

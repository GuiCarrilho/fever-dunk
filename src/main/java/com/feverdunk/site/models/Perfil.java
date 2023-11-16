package com.feverdunk.site.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;


@AllArgsConstructor
@Getter
public enum Perfil {
    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private final Integer codigo;
    private final String descricao;

    public static Perfil toEnum(Integer cod) {
        if (Objects.isNull(cod)) {
            return null;
        }

        for (Perfil p : Perfil.values()) {
            if (cod.equals(p.getCodigo())) {
                return p;
            }
        }

        throw new IllegalArgumentException("Codigo invalido: " + cod);
    }
}

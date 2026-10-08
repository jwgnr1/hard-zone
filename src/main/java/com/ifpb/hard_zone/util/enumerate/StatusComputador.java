package com.ifpb.hard_zone.util.enumerate;

import com.ifpb.hard_zone.exception.RegraDeNegocioException;

import java.util.Arrays;

public enum StatusComputador {
    DISPONIVEL,
    OCUPADO,
    MANUTENCAO,
    FORA_DE_USO;

    public static StatusComputador deTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            return null;
        }
        try {
            return valueOf(texto.trim().toUpperCase().replace(' ', '_'));
        } catch (IllegalArgumentException e) {
            throw new RegraDeNegocioException(
                    "Status inválido: '" + texto + "'. Valores aceitos: "
                            + Arrays.toString(values()));
        }
    }
}

package com.ifpb.hard_zone.util;

import com.ifpb.hard_zone.exception.usuariosExceptions.DadosUsuarioInvalidoException;
import com.ifpb.hard_zone.exception.dataException.DataInvalidaException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Validator {

    private static final Pattern PATTERN_NOME = Pattern.compile("\\p{L}+( \\p{L}+)*");
    private static final Pattern PATTERN_EMAIL = Pattern.compile("^[a-zA-Z0-9+_.-]+@[A-Za-z0-9.-]+" +
            "\\.[A-Za-z]{2,}$");



    public static void validarData(Date data) throws DataInvalidaException {
        if (data == null) {
            throw new DataInvalidaException("Data informada não pode ser nula.");
        }
        if (data.after(new Date())) {
            throw new DataInvalidaException("Data informada não pode estar no futuro.");
        }
    }

    public static void validarDataNascimento(Date data) throws DadosUsuarioInvalidoException {
        try {
            validarData(data);
        }catch (DataInvalidaException e) {
            throw new DadosUsuarioInvalidoException("Data de nascimento inválida: " + e.getMessage());
        }
    }


    public static void validarPeriodo(Date dataInicio, Date dataFim) throws DataInvalidaException {
        validarData(dataInicio);
        validarData(dataFim);
        if (dataInicio.after(dataFim)) {
            throw new DataInvalidaException("Data de início não pode ser posterior à data de fim.");
        }
    }

    public static void validarNome(String nome) throws DadosUsuarioInvalidoException {
        if(!isNomeValido(nome)) {
            throw new DadosUsuarioInvalidoException("Nome do usuario está invalido digite apenas letras.");
        }
    }

    public static void validarEmail(String email) throws DadosUsuarioInvalidoException {
        if(!isEmailValido(email)) {
            throw new DadosUsuarioInvalidoException("Email do usuario invalido, email não está no padrão de email.");
        }
    }

    private static boolean isNomeValido(String nome) {
        return nome != null && !nome.isBlank() && PATTERN_NOME.matcher(nome).matches();
    }


    private static boolean isEmailValido(String email) {
        return email != null && !email.isBlank() && PATTERN_EMAIL.matcher(email).matches();
    }

    //Metodos de validação de data para OffSetDateTime
    public static void validarPeriodo(OffsetDateTime dataInicio, OffsetDateTime dataFim) throws DataInvalidaException {
        validarData(dataInicio);
        validarData(dataFim);
        if (dataInicio.isAfter(dataFim)) {
            throw new DataInvalidaException("Data de início não pode ser posterior à data de fim.");
        }
    }

    public static Date criarDate(String data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        return  sdf.parse(data);

    }

    public static Date criarDateTime(String data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf.setLenient(false);
        return sdf.parse(data);
    }


}

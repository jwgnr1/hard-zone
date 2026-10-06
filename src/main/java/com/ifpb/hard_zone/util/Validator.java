package com.ifpb.hard_zone.util;

import com.ifpb.hard_zone.exception.usuariosExceptions.DadosUsuarioInvalidoException;
import com.ifpb.hard_zone.exception.dataException.DataInvalidaException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Validator {

    private static final Pattern PATTERN_NOME = Pattern.compile("\\p{L}+( \\p{L}+)*");
    private static final Pattern PATTERN_EMAIL = Pattern.compile("^[a-zA-Z0-9+_.-]+@[A-Za-z0-9.-]+" +
            "\\.[A-Za-z]{2,}$");

    private static final int IDADE_MINIMA = 10;
    private static final int IDADE_MAXIMA = 80;

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
        } catch (DataInvalidaException e) {
            throw new DadosUsuarioInvalidoException(
                    "Data de nascimento inválida: " + e.getMessage());
        }
        int idade = gerarIdade(data);



        if (idade < IDADE_MINIMA) {
            throw new DadosUsuarioInvalidoException(String.format("Data de nascimento inválida: idade mínima permitida é %d anos.", IDADE_MINIMA));

        }

        if (idade > IDADE_MAXIMA) {
            throw new DadosUsuarioInvalidoException(String.format("Data de nascimento inválida: idade máxima permitida é %d  anos.", IDADE_MAXIMA));
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
        if(nome == null || nome.isBlank()) {
            throw new DadosUsuarioInvalidoException("Nome não pode ser vazio.");
        }

        if(!isNomeValido(nome)) {
            throw new DadosUsuarioInvalidoException("Nome inválido. Use apenas letras e espaços.");
        }
    }

    public static void validarEmail(String email) throws DadosUsuarioInvalidoException {
        if (email == null || email.isBlank()) {
            throw new DadosUsuarioInvalidoException("E-mail não pode ser vazio.");
        }
        if (!isEmailValido(email)) {
            throw new DadosUsuarioInvalidoException("E-mail inválido. Use o formato nome@dominio.com.");
        }
    }

    private static int obterAno(Date data) {
        Calendar calendario =  Calendar.getInstance();
        calendario.setTime(data);
        return calendario.get(calendario.YEAR);
    }

    private static int obterDiaDoAno(Date data) {
        Calendar calendario =  Calendar.getInstance();
        calendario.setTime(data);
        return calendario.get(calendario.DAY_OF_YEAR);
    }

    private static int gerarIdade(Date dataNascimento) {

        Date dataAtual = new Date();
        int anoAtual = obterAno(dataAtual);
        int anoNascimento = obterAno(dataNascimento);

        int idade = anoAtual - anoNascimento;

        if (obterDiaDoAno(dataAtual) < obterDiaDoAno(dataNascimento)) {
            idade--;
        }

        return idade;
    }

    private static boolean isEmailValido(String email) {
        return PATTERN_EMAIL.matcher(email).matches();
    }

    private static boolean isNomeValido(String nome) {
        return PATTERN_NOME.matcher(nome).matches();
    }


    public static Date criarDate(String data) throws DataInvalidaException {

        if(data == null || data.isBlank()) {
            throw new DataInvalidaException("Data inválida!, data não pode ser nulo ou vazio.");
        }

        if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new DataInvalidaException("Data inválida! Informe no formato dd/MM/yyyy.");
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            return sdf.parse(data);

        } catch (ParseException e) {
            throw new DataInvalidaException("Data não existe! Verifique o dia e o mês informados.");        }

    }

    public static Date criarDateTime(String data) throws DataInvalidaException {

        if(data == null || data.isBlank()) {
            throw new DataInvalidaException("Data inválida!, data não pode ser nulo ou vazio.");
        }

        try {


            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf.setLenient(false);
            return sdf.parse(data);

        } catch (ParseException e) {
            throw new DataInvalidaException(
                    "Data/hora inválida! Informe no formato dd/MM/yyyy HH:mm.%n " +
                            "Exemplo: 05/10/2026 14:30:00");        }
    }


}

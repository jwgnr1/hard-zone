package com.ifpb.hard_zone.menu;

import com.ifpb.hard_zone.exception.GlobalExceptionHandler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Console {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Console() {
    }

    @FunctionalInterface
    public interface Acao {
        void executar() throws Exception;
    }


    public static void exibir(String mensagem) {
        System.out.println(">> " + mensagem);
    }

    public static void seguro(Acao acao) {
        try {
            acao.executar();
        } catch (Exception e) {
            System.out.println("Erro: " + GlobalExceptionHandler.tratar(e));
        }
    }


    public static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    public static int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    public static long lerLong(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    public static BigDecimal lerDecimal(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return new BigDecimal(scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor válido (ex.: 5,50).");
            }
        }
    }

    public static LocalDate lerData(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return LocalDate.parse(scanner.nextLine().trim(), FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato dd/MM/yyyy.");
            }
        }
    }

    public static boolean confirmar(String mensagem) {
        System.out.print(mensagem + " (s/n): ");
        return scanner.nextLine().trim().equalsIgnoreCase("s");
    }

    // ---------- datas para filtros de período ----------

    public static OffsetDateTime inicioDoDia(LocalDate data) {
        return data.atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime();
    }

    public static OffsetDateTime fimDoDia(LocalDate data) {
        return data.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }
}

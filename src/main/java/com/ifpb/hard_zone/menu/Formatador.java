package com.ifpb.hard_zone.menu;

import com.ifpb.hard_zone.model.Computador;
import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.model.Pagamento;
import com.ifpb.hard_zone.model.Sessao;
import com.ifpb.hard_zone.model.Usuario;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class Formatador {

    private static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Formatador() {
    }


    public static <T> void mostrarLista(List<T> lista, String mensagemVazia, Function<T, String> formatador) {
        if (lista == null || lista.isEmpty()) {
            System.out.println(mensagemVazia);
            return;
        }
        lista.forEach(item -> System.out.println(formatador.apply(item)));
    }

    public static <T> void mostrarLista(List<T> lista, String mensagemVazia) {
        mostrarLista(lista, mensagemVazia, item -> formatar(item));
    }

    public static void mostrarObjeto(Object objeto, String mensagemVazia) {
        System.out.println(objeto == null ? mensagemVazia : formatar(objeto));
    }


    public static String formatarComputador(Computador c) {
        return "[" + c.getId() + "] Máquina " + c.getNumeroMaquina()
                + " | " + c.getEspecificacoes() + " | " + c.getStatus();
    }

    public static String formatarJogo(Jogo j) {
        return "[" + j.getId() + "] " + j.getNome() + " | Faixa etária: " + j.getFaixaEtaria();
    }

    private static String formatarUsuario(Usuario u) {
        return "[" + u.getId() + "] " + u.getNome() + " | " + u.getEmail()
                + " | Nasc.: " + formatarData(u.getDataNascimento())
                + " | Cadastro: " + formatarData(u.getDataCadastro())
                + " | " + (u.isAtivo() ? "Ativo" : "Desativado");
    }

    private static String formatarSessao(Sessao s) {
        String usuario = s.getUsuario() == null ? "-" : s.getUsuario().getNome();
        String computador = s.getComputador() == null
                ? "-" : "Máquina " + s.getComputador().getNumeroMaquina();
        String fim = s.getDataFim() == null ? "em andamento" : formatarDataHora(s.getDataFim());
        String valor = s.getValor() == null ? "-" : "R$ " + s.getValor();
        return "[" + s.getId() + "] " + usuario + " | " + computador
                + " | Início: " + formatarDataHora(s.getDataInicio())
                + " | Fim: " + fim
                + " | Preço/h: R$ " + s.getPrecoPorHora()
                + " | Valor: " + valor;
    }

    private static String formatarPagamento(Pagamento p) {
        return "[" + p.getId() + "] R$ " + p.getValor()
                + " | " + formatarDataHora(p.getDataHora())
                + " | Código: " + codigoDoPagamento(p)
                + " | Sessão: " + idDaSessao(p);
    }

    private static String codigoDoPagamento(Pagamento p) {
        return p.getCodigoPagamento() == null ? "-" : p.getCodigoPagamento().getCodigoGerado();
    }

    // Pagamento.sessao é LAZY: se o proxy não puder ser lido fora da transação, mostra "?"
    private static String idDaSessao(Pagamento p) {
        try {
            return p.getSessao() == null ? "-" : String.valueOf(p.getSessao().getId());
        } catch (RuntimeException e) {
            return "?";
        }
    }

    private static String formatar(Object objeto) {
        if (objeto instanceof Computador) return formatarComputador((Computador) objeto);
        if (objeto instanceof Jogo) return formatarJogo((Jogo) objeto);
        if (objeto instanceof Usuario) return formatarUsuario((Usuario) objeto);
        if (objeto instanceof Sessao) return formatarSessao((Sessao) objeto);
        if (objeto instanceof Pagamento) return formatarPagamento((Pagamento) objeto);
        return String.valueOf(objeto);
    }


    private static String formatarData(Date data) {
        return data == null ? "-" : new SimpleDateFormat("dd/MM/yyyy").format(data);
    }

    private static String formatarDataHora(LocalDateTime dataHora) {
        return dataHora == null ? "-" : dataHora.format(FORMATO_DATA_HORA);
    }

    private static String formatarDataHora(OffsetDateTime dataHora) {
        return dataHora == null ? "-" : dataHora.format(FORMATO_DATA_HORA);
    }
}

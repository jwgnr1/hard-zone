package com.ifpb.hard_zone.menu;

import com.ifpb.hard_zone.controller.UsuarioController;

import static com.ifpb.hard_zone.menu.Console.*;
import static com.ifpb.hard_zone.menu.Formatador.*;

public class MenuUsuario {

    private static final String DICA_DATA_USUARIO = "dd/MM/yyyy";

    private static final UsuarioController usuarioController = new UsuarioController();

    private MenuUsuario() {
    }

    public static void abrir() {
        int opcao;
        do {
            System.out.println("\n--- USUÁRIOS ---");
            System.out.println("1  - Cadastrar");
            System.out.println("2  - Buscar por id");
            System.out.println("3  - Buscar por e-mail");
            System.out.println("4  - Buscar por nome");
            System.out.println("5  - Filtrar por nome");
            System.out.println("6  - Listar todos");
            System.out.println("7  - Listar ativos");
            System.out.println("8  - Listar desativados");
            System.out.println("9  - Listar por data de cadastro");
            System.out.println("10 - Filtrar por período de cadastro");
            System.out.println("11 - Atualizar dados (nome e e-mail)");
            System.out.println("12 - Ativar");
            System.out.println("13 - Desativar");
            System.out.println("14 - Excluir permanentemente");
            System.out.println("0  - Voltar");
            opcao = lerInt("Escolha: ");

            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> buscarUsuarioPorId();
                case 3 -> buscarUsuarioPorEmail();
                case 4 -> buscarUsuariosPorNome();
                case 5 -> filtrarUsuariosPorNome();
                case 6 -> seguro(() -> mostrarLista(usuarioController.listarTodosUsuarios(),
                        "Nenhum usuário cadastrado."));
                case 7 -> seguro(() -> mostrarLista(usuarioController.listarTodosUsuariosAtivos(),
                        "Nenhum usuário ativo."));
                case 8 -> seguro(() -> mostrarLista(usuarioController.listarTodosUsuariosDesativados(),
                        "Nenhum usuário desativado."));
                case 9 -> listarUsuariosPorDataCadastro();
                case 10 -> filtrarUsuariosPorPeriodo();
                case 11 -> atualizarUsuario();
                case 12 -> exibir(usuarioController.ativarUsuario(lerLong("Id do usuário: ")));
                case 13 -> exibir(usuarioController.desativarUsuario(lerLong("Id do usuário: ")));
                case 14 -> excluirUsuario();
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void cadastrarUsuario() {
        String nome = lerTexto("Nome: ");
        String nascimento = lerTexto("Data de nascimento (" + DICA_DATA_USUARIO + "): ");
        String email = lerTexto("E-mail: ");
        exibir(usuarioController.cadastrarUsuario(nome, nascimento, email));
    }

    private static void buscarUsuarioPorId() {
        long id = lerLong("Id do usuário: ");
        seguro(() -> mostrarObjeto(usuarioController.buscarUsuarioPorId(id),
                "Usuário não encontrado."));
    }

    private static void buscarUsuarioPorEmail() {
        String email = lerTexto("E-mail: ");
        seguro(() -> mostrarObjeto(usuarioController.buscarUsuarioPorEmail(email),
                "Usuário não encontrado."));
    }

    private static void buscarUsuariosPorNome() {
        String nome = lerTexto("Nome: ");
        seguro(() -> mostrarLista(usuarioController.buscarUsuarioPorNome(nome),
                "Nenhum usuário encontrado."));
    }

    private static void filtrarUsuariosPorNome() {
        String nome = lerTexto("Nome (ou parte dele): ");
        seguro(() -> mostrarLista(usuarioController.filtrarPorNomeUsuario(nome),
                "Nenhum usuário encontrado."));
    }

    private static void listarUsuariosPorDataCadastro() {
        String data = lerTexto("Data de cadastro (" + DICA_DATA_USUARIO + "): ");
        seguro(() -> mostrarLista(usuarioController.listarUsuariosPorDataCadastro(data),
                "Nenhum usuário cadastrado nessa data."));
    }

    private static void filtrarUsuariosPorPeriodo() {
        String inicio = lerTexto("Data inicial (" + DICA_DATA_USUARIO + "): ");
        String fim = lerTexto("Data final (" + DICA_DATA_USUARIO + "): ");
        seguro(() -> mostrarLista(usuarioController.filtrarUsuariosPorPeriodo(inicio, fim),
                "Nenhum usuário no período."));
    }

    private static void atualizarUsuario() {
        long id = lerLong("Id do usuário: ");
        String nome = lerTexto("Novo nome: ");
        String email = lerTexto("Novo e-mail: ");
        exibir(usuarioController.atualizarDadosUsuario(id, nome, email));
    }

    private static void excluirUsuario() {
        long id = lerLong("Id do usuário: ");
        if (confirmar("Excluir o usuário " + id + " PERMANENTEMENTE?")) {
            exibir(usuarioController.excluirUsuarioPermanente(id));
        }
    }
}

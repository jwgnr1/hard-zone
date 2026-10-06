package com.ifpb.hard_zone;

import com.ifpb.hard_zone.controller.UsuarioController;
import com.ifpb.hard_zone.model.Usuario;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UsuarioController usuarioController = new UsuarioController();

    public static void main(String[] args) {

        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {

                case 1 -> cadastrarUsuario();

                case 2 -> buscarUsuarioPorId();

                case 3 -> atualizarUsuario();

                case 4 -> desativarUsuario();

                case 5 -> ativarUsuario();

                case 6 -> buscarUsuarioPorNome();

                case 7 -> buscarUsuarioPorEmail();

                case 8 -> listarTodosUsuarios();

                case 9 -> listarUsuariosAtivos();

                case 10 -> listarUsuariosDesativados();

                case 11 -> listarUsuariosPorDataCadastro();

                case 12 -> filtrarPorNome();

                case 13 -> filtrarPorPeriodo();

                case 14 -> excluirUsuario();

                case 0 -> System.out.println("Programa encerrado.");

                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {

        System.out.println("\n========== MENU USUÁRIOS ==========");
        System.out.println("1  - Cadastrar usuário");
        System.out.println("2  - Buscar usuário por ID");
        System.out.println("3  - Atualizar usuário");
        System.out.println("4  - Desativar usuário");
        System.out.println("5  - Ativar usuário");
        System.out.println("6  - Buscar usuário por nome");
        System.out.println("7  - Buscar usuário por e-mail");
        System.out.println("8  - Listar todos os usuários");
        System.out.println("9  - Listar usuários ativos");
        System.out.println("10 - Listar usuários desativados");
        System.out.println("11 - Buscar por data de cadastro");
        System.out.println("12 - Filtrar por nome");
        System.out.println("13 - Filtrar por período de cadastro");
        System.out.println("14 - Excluir usuário permanentemente");
        System.out.println("0  - Sair");
        System.out.println("===================================");
    }

    private static void cadastrarUsuario() {

        System.out.println("\n--- Cadastro de usuário ---");

        String nome = lerTexto("Nome: ");
        String dataNascimento = lerTexto("Data de nascimento (dd/MM/yyyy): ");
        String email = lerTexto("E-mail: ");

        String resultado = usuarioController.cadastrarUsuario(
                nome,
                dataNascimento,
                email
        );

        System.out.println(resultado);
    }

    private static void buscarUsuarioPorId() {

        System.out.println("\n--- Buscar usuário por ID ---");

        Long id = lerLong("ID: ");

        Usuario usuario = usuarioController.buscarUsuarioPorId(id);

        exibirUsuario(usuario);
    }

    private static void atualizarUsuario() {

        System.out.println("\n--- Atualizar usuário ---");

        Long id = lerLong("ID: ");
        String nome = lerTexto("Novo nome: ");
        String email = lerTexto("Novo e-mail: ");

        String resultado = usuarioController.atualizarDadosUsuario(
                id,
                nome,
                email
        );

        System.out.println(resultado);
    }

    private static void desativarUsuario() {

        System.out.println("\n--- Desativar usuário ---");

        Long id = lerLong("ID: ");

        String resultado = usuarioController.desativarUsuario(id);

        System.out.println(resultado);
    }

    private static void ativarUsuario() {

        System.out.println("\n--- Ativar usuário ---");

        Long id = lerLong("ID: ");

        String resultado = usuarioController.ativarUsuario(id);

        System.out.println(resultado);
    }

    private static void buscarUsuarioPorNome() {

        System.out.println("\n--- Buscar usuário por nome ---");

        String nome = lerTexto("Nome: ");

        List<Usuario> usuarios = usuarioController.buscarUsuarioPorNome(nome);

        exibirUsuarios(usuarios);
    }

    private static void buscarUsuarioPorEmail() {

        System.out.println("\n--- Buscar usuário por e-mail ---");

        String email = lerTexto("E-mail: ");

        Usuario usuario = usuarioController.buscarUsuarioPorEmail(email);

        exibirUsuario(usuario);
    }

    private static void listarTodosUsuarios() {

        System.out.println("\n--- Todos os usuários ---");

        List<Usuario> usuarios = usuarioController.listarTodosUsuarios();

        exibirUsuarios(usuarios);
    }

    private static void listarUsuariosAtivos() {

        System.out.println("\n--- Usuários ativos ---");

        List<Usuario> usuarios = usuarioController.listarTodosUsuariosAtivos();

        exibirUsuarios(usuarios);
    }

    private static void listarUsuariosDesativados() {

        System.out.println("\n--- Usuários desativados ---");

        List<Usuario> usuarios = usuarioController.listarTodosUsuariosDesativados();

        exibirUsuarios(usuarios);
    }

    private static void listarUsuariosPorDataCadastro() {

        System.out.println("\n--- Buscar por data de cadastro ---");

        String data = lerTexto("Data (dd/MM/yyyy HH:mm): ");

        List<Usuario> usuarios =
                usuarioController.listarUsuariosPorDataCadastro(data);

        exibirUsuarios(usuarios);
    }

    private static void filtrarPorNome() {

        System.out.println("\n--- Filtrar por nome ---");

        String nome = lerTexto("Nome ou parte do nome: ");

        List<Usuario> usuarios =
                usuarioController.filtrarPorNomeUsuario(nome);

        exibirUsuarios(usuarios);
    }

    private static void filtrarPorPeriodo() {

        System.out.println("\n--- Filtrar por período ---");

        String inicio = lerTexto("Data inicial (dd/MM/yyyy HH:mm): ");
        String fim = lerTexto("Data final (dd/MM/yyyy HH:mm): ");

        List<Usuario> usuarios =
                usuarioController.filtrarUsuariosPorPeriodo(inicio, fim);

        exibirUsuarios(usuarios);
    }

    private static void excluirUsuario() {

        System.out.println("\n--- Excluir usuário permanentemente ---");

        Long id = lerLong("ID: ");

        String resultado =
                usuarioController.excluirUsuarioPermanente(id);

        System.out.println(resultado);
    }

    private static void exibirUsuario(Usuario usuario) {

        if (usuario == null) {
            return;
        }

        System.out.println("\n----- USUÁRIO -----");
        System.out.println("ID: " + usuario.getId());
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("Data de nascimento: " + usuario.getDataNascimento());
        System.out.println("E-mail: " + usuario.getEmail());
        System.out.println("Data de cadastro: " + usuario.getDataCadastro());
        System.out.println("Ativo: " + usuario.isAtivo());
        System.out.println("-------------------");
    }

    private static void exibirUsuarios(List<Usuario> usuarios) {

        if (usuarios == null || usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
            return;
        }

        for (Usuario usuario : usuarios) {
            exibirUsuario(usuario);
        }
    }

    private static String lerTexto(String mensagem) {

        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private static Long lerLong(String mensagem) {

        while (true) {
            try {
                System.out.print(mensagem);
                return Long.parseLong(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    private static int lerInteiro(String mensagem) {

        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Digite uma opção válida.");
            }
        }
    }
}
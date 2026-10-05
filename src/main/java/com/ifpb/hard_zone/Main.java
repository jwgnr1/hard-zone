package com.ifpb.hard_zone;

import com.ifpb.hard_zone.controller.UsuarioController;
import com.ifpb.hard_zone.model.Usuario;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UsuarioController usuarioController = new UsuarioController();

    public static void main(String[] args) {

        String opcao;

        do {
            exibirMenu();
            opcao = scanner.nextLine();

            switch (opcao) {

                case "1":
                    cadastrarUsuario();
                    break;

                case "2":
                    buscarUsuarioPorId();
                    break;

                case "3":
                    atualizarUsuario();
                    break;

                case "4":
                    buscarUsuarioPorNome();
                    break;

                case "5":
                    buscarUsuarioPorEmail();
                    break;

                case "6":
                    listarTodosUsuarios();
                    break;

                case "7":
                    filtrarUsuariosPorNome();
                    break;

                case "8":
                    filtrarUsuariosPorPeriodo();
                    break;

                case "9":
                    listarUsuarioPorDataCadastro();
                    break;

                case "10":
                    listarUsuariosAtivos();
                    break;

                case "11":
                    listarUsuariosDesativados();
                    break;

                case "12":
                    desativarUsuario();
                    break;

                case "13":
                    ativarUsuario();
                    break;

                case "14":
                    excluirUsuario();
                    break;

                case "0":
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

            if (!opcao.equals("0")) {
                System.out.println("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }

        } while (!opcao.equals("0"));

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n========================================");
        System.out.println("          SISTEMA DE USUÁRIOS");
        System.out.println("========================================");
        System.out.println("1  - Cadastrar usuário");
        System.out.println("2  - Buscar usuário por ID");
        System.out.println("3  - Atualizar usuário");
        System.out.println("4  - Buscar usuário por nome");
        System.out.println("5  - Buscar usuário por email");
        System.out.println("6  - Listar todos os usuários");
        System.out.println("7  - Filtrar usuários por nome");
        System.out.println("8  - Filtrar usuários por período");
        System.out.println("9  - Buscar por data de cadastro");
        System.out.println("10 - Listar usuários ativos");
        System.out.println("11 - Listar usuários desativados");
        System.out.println("12 - Desativar usuário");
        System.out.println("13 - Ativar usuário");
        System.out.println("14 - Excluir usuário permanentemente");
        System.out.println("0  - Sair");
        System.out.println("========================================");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarUsuario() {

        System.out.println("\n--- CADASTRAR USUÁRIO ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Data de nascimento (dd/MM/yyyy): ");
        String dataNascimento = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        usuarioController.cadastrarUsuario(nome, dataNascimento, email);
    }

    private static void buscarUsuarioPorId() {

        System.out.println("\n--- BUSCAR USUÁRIO POR ID ---");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        Usuario usuario = usuarioController.buscarUsuarioPorId(id);

        if (usuario != null) {
            exibirUsuario(usuario);
        }
    }

    private static void atualizarUsuario() {

        System.out.println("\n--- ATUALIZAR USUÁRIO ---");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();

        System.out.print("Novo email: ");
        String email = scanner.nextLine();

        usuarioController.atualizarDadosUsuario(id, nome, email);
    }

    private static void buscarUsuarioPorNome() {

        System.out.println("\n--- BUSCAR USUÁRIO POR NOME ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        List<Usuario> usuarios =
                usuarioController.buscarUsuarioPorNome(nome);

        exibirUsuarios(usuarios);
    }

    private static void buscarUsuarioPorEmail() {

        System.out.println("\n--- BUSCAR USUÁRIO POR EMAIL ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Usuario usuario =
                usuarioController.buscarUsuarioPorEmail(email);

        if (usuario != null) {
            exibirUsuario(usuario);
        }
    }

    private static void listarTodosUsuarios() {

        System.out.println("\n--- TODOS OS USUÁRIOS ---");

        List<Usuario> usuarios =
                usuarioController.listarTodosUsuarios();

        exibirUsuarios(usuarios);
    }

    private static void filtrarUsuariosPorNome() {

        System.out.println("\n--- FILTRAR USUÁRIOS POR NOME ---");

        System.out.print("Nome ou parte do nome: ");
        String nome = scanner.nextLine();

        List<Usuario> usuarios =
                usuarioController.filtrarPorNomeUsuario(nome);

        exibirUsuarios(usuarios);
    }

    private static void filtrarUsuariosPorPeriodo() {

        System.out.println("\n--- FILTRAR POR PERÍODO ---");

        System.out.print("Data inicial (dd/MM/yyyy HH:mm:ss): ");
        String inicio = scanner.nextLine();

        System.out.print("Data final (dd/MM/yyyy HH:mm:ss): ");
        String fim = scanner.nextLine();

        List<Usuario> usuarios =
                usuarioController.filtrarUsuariosPorPeriodo(inicio, fim);

        exibirUsuarios(usuarios);
    }

    private static void listarUsuarioPorDataCadastro() {

        System.out.println("\n--- BUSCAR POR DATA DE CADASTRO ---");

        System.out.print("Data (dd/MM/yyyy HH:mm:ss): ");
        String data = scanner.nextLine();

        List<Usuario> usuarios =
                usuarioController.listarUsuariosPorDataCadastro(data);

        exibirUsuarios(usuarios);
    }

    private static void listarUsuariosAtivos() {

        System.out.println("\n--- USUÁRIOS ATIVOS ---");

        List<Usuario> usuarios =
                usuarioController.listarTodosUsuariosAtivos();

        exibirUsuarios(usuarios);
    }

    private static void listarUsuariosDesativados() {

        System.out.println("\n--- USUÁRIOS DESATIVADOS ---");

        List<Usuario> usuarios =
                usuarioController.listarTodosUsuariosDesativados();

        exibirUsuarios(usuarios);
    }

    private static void desativarUsuario() {

        System.out.println("\n--- DESATIVAR USUÁRIO ---");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        usuarioController.desativarUsuario(id);
    }

    private static void ativarUsuario() {

        System.out.println("\n--- ATIVAR USUÁRIO ---");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        usuarioController.ativarUsuario(id);
    }

    private static void excluirUsuario() {

        System.out.println("\n--- EXCLUIR USUÁRIO PERMANENTEMENTE ---");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        Usuario usuario =
                usuarioController.excluirUsuarioPermanente(id);

        if (usuario != null) {
            System.out.println("Usuário removido:");
            exibirUsuario(usuario);
        }
    }

    private static void exibirUsuarios(List<Usuario> usuarios) {

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
            return;
        }

        System.out.println("\nQuantidade encontrada: " + usuarios.size());

        for (Usuario usuario : usuarios) {
            exibirUsuario(usuario);
            System.out.println("----------------------------------------");
        }
    }

    private static void exibirUsuario(Usuario usuario) {

        System.out.println("\nID: " + usuario.getId());
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("Data de nascimento: " + usuario.getDataNascimento());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Data de cadastro: " + usuario.getDataCadastro());
        System.out.println("Ativo: " + usuario.isAtivo());
    }
}
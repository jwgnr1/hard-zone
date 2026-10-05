package com.ifpb.hard_zone;

import com.ifpb.hard_zone.controller.UsuarioController;
import com.ifpb.hard_zone.model.Usuario;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UsuarioController controller = new UsuarioController();

        int opcao;

        do {
            System.out.println("\n========== HARD ZONE ==========");
            System.out.println("1 - Cadastrar usuário");
            System.out.println("2 - Buscar usuário por ID");
            System.out.println("3 - Atualizar usuário");
            System.out.println("4 - Desativar usuário");
            System.out.println("5 - Ativar usuário");
            System.out.println("6 - Buscar usuário por nome");
            System.out.println("7 - Buscar usuário por e-mail");
            System.out.println("8 - Listar todos os usuários");
            System.out.println("9 - Listar usuários ativos");
            System.out.println("10 - Listar usuários desativados");
            System.out.println("11 - Listar por data de cadastro");
            System.out.println("12 - Filtrar por nome");
            System.out.println("13 - Filtrar por período");
            System.out.println("14 - Excluir permanentemente");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Data de nascimento (dd/MM/yyyy): ");
                    String dataNascimento = scanner.nextLine();

                    System.out.print("E-mail: ");
                    String email = scanner.nextLine();

                    controller.cadastrarUsuario(
                            nome,
                            dataNascimento,
                            email
                    );
                    break;

                case 2:
                    System.out.print("ID: ");
                    String id = scanner.nextLine();

                    Usuario usuario = controller.buscarUsuarioPorId(id);

                    if (usuario != null) {
                        System.out.println("ID: " + usuario.getId());
                        System.out.println("Nome: " + usuario.getNome());
                        System.out.println("E-mail: " + usuario.getEmail());
                        System.out.println("Ativo: " + usuario.isAtivo());
                    }
                    break;

                case 3:
                    System.out.print("ID: ");
                    String idAtualizar = scanner.nextLine();

                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();

                    System.out.print("Novo e-mail: ");
                    String novoEmail = scanner.nextLine();

                    controller.atualizarDadosUsuario(
                            idAtualizar,
                            novoNome,
                            novoEmail
                    );
                    break;

                case 4:
                    System.out.print("ID: ");
                    String idDesativar = scanner.nextLine();

                    controller.desativarUsuario(idDesativar);
                    break;

                case 5:
                    System.out.print("ID: ");
                    String idAtivar = scanner.nextLine();

                    controller.ativarUsuario(idAtivar);
                    break;

                case 6:
                    System.out.print("Nome: ");
                    String nomeBusca = scanner.nextLine();

                    List<Usuario> usuariosNome =
                            controller.buscarUsuarioPorNome(nomeBusca);

                    exibirUsuarios(usuariosNome);
                    break;

                case 7:
                    System.out.print("E-mail: ");
                    String emailBusca = scanner.nextLine();

                    Usuario usuarioEmail =
                            controller.buscarUsuarioPorEmail(emailBusca);

                    if (usuarioEmail != null) {
                        System.out.println("ID: " + usuarioEmail.getId());
                        System.out.println("Nome: " + usuarioEmail.getNome());
                        System.out.println("E-mail: " + usuarioEmail.getEmail());
                        System.out.println("Ativo: " + usuarioEmail.isAtivo());
                    }
                    break;

                case 8:
                    exibirUsuarios(controller.listarTodosUsuarios());
                    break;

                case 9:
                    exibirUsuarios(controller.listarTodosUsuariosAtivos());
                    break;

                case 10:
                    exibirUsuarios(controller.listarTodosUsuariosDesativados());
                    break;

                case 11:
                    System.out.print("Data de cadastro (dd/MM/yyyy): ");
                    String data = scanner.nextLine();

                    exibirUsuarios(
                            controller.listarUsuariosPorDataCadastro(data)
                    );
                    break;

                case 12:
                    System.out.print("Nome para filtrar: ");
                    String nomeFiltro = scanner.nextLine();

                    exibirUsuarios(
                            controller.filtrarPorNomeUsuario(nomeFiltro)
                    );
                    break;

                case 13:
                    System.out.print("Data inicial (dd/MM/yyyy): ");
                    String inicio = scanner.nextLine();

                    System.out.print("Data final (dd/MM/yyyy): ");
                    String fim = scanner.nextLine();

                    exibirUsuarios(
                            controller.filtrarUsuariosPorPeriodo(inicio, fim)
                    );
                    break;

                case 14:
                    System.out.print("ID: ");
                    String idExcluir = scanner.nextLine();

                    Usuario excluido =
                            controller.excluirUsuarioPermanente(idExcluir);

                    if (excluido != null) {
                        System.out.println(
                                "Usuário excluído: " + excluido.getNome()
                        );
                    }
                    break;

                case 0:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirUsuarios(List<Usuario> usuarios) {

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
            return;
        }

        System.out.println("\n========== USUÁRIOS ==========");

        for (Usuario usuario : usuarios) {
            System.out.println(
                    "ID: " + usuario.getId()
                            + " | Nome: " + usuario.getNome()
                            + " | E-mail: " + usuario.getEmail()
                            + " | Ativo: " + usuario.isAtivo()
            );
        }
    }
}
package com.ifpb.hard_zone;

import com.ifpb.hard_zone.exception.usuariosExceptions.DadosUsuarioInvalidoException;
import com.ifpb.hard_zone.exception.dataException.DataInvalidaException;
import com.ifpb.hard_zone.exception.usuariosExceptions.UsuarioNaoEncontradoException;
import com.ifpb.hard_zone.exception.usuariosExceptions.UsuarioStatusInvalidoException;
import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.service.UsuarioService;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UsuarioService service = new UsuarioService();

        int opcao;

        do {

            System.out.println("\n========== MENU USUÁRIO ==========");
            System.out.println("1  - Salvar usuário");
            System.out.println("2  - Buscar usuário por ID");
            System.out.println("3  - Buscar usuário por nome");
            System.out.println("4  - Buscar usuário por email");
            System.out.println("5  - Listar todos os usuários");
            System.out.println("6  - Filtrar usuários por nome");
            System.out.println("7  - Filtrar usuários por período de cadastro");
            System.out.println("8  - Buscar usuário por data de cadastro");
            System.out.println("9  - Listar usuários ativos");
            System.out.println("10 - Listar usuários desativados");
            System.out.println("11 - Atualizar usuário");
            System.out.println("12 - Desativar usuário");
            System.out.println("13 - Ativar usuário");
            System.out.println("14 - Excluir usuário permanentemente");
            System.out.println("0  - Sair");
            System.out.println("==================================");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                // =====================================================
                // 1 - SALVAR USUÁRIO
                // =====================================================
                case 1:
                    try {
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();

                        System.out.print("Data de nascimento (dd/MM/yyyy): ");
                        String dataNascimento = scanner.nextLine();

                        System.out.print("Email: ");
                        String email = scanner.nextLine();

                        service.salvarUsuario(
                                nome,
                                dataNascimento,
                                email
                        );

                        System.out.println("Usuário salvo com sucesso!");

                    } catch (DadosUsuarioInvalidoException | ParseException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;


                // =====================================================
                // 2 - BUSCAR POR ID
                // =====================================================
                case 2:
                    try {
                        System.out.print("Digite o ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        Usuario usuario = service.buscarUsuarioPorId(id);

                        System.out.println("\nID: " + usuario.getId());
                        System.out.println("Nome: " + usuario.getNome());
                        System.out.println("Nascimento: " + usuario.getDataNascimento());
                        System.out.println("Email: " + usuario.getEmail());
                        System.out.println("Cadastro: " + usuario.getDataCadastro());
                        System.out.println("Ativo: " + usuario.isAtivo());

                    } catch (UsuarioNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;


                // =====================================================
                // 3 - BUSCAR POR NOME
                // =====================================================
                case 3:
                    try {
                        System.out.print("Digite o nome: ");
                        String nome = scanner.nextLine();

                        List<Usuario> usuarios =
                                service.buscarUsuarioPorNome(nome);

                        for (Usuario usuario : usuarios) {
                            System.out.println(
                                    usuario.getId() + " - " +
                                            usuario.getNome() + " - " +
                                            usuario.getEmail()
                            );
                        }

                    } catch (UsuarioNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }catch(DadosUsuarioInvalidoException e){
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;


                // =====================================================
                // 4 - BUSCAR POR EMAIL
                // =====================================================
                case 4:
                    try {
                        System.out.print("Digite o email: ");
                        String email = scanner.nextLine();

                        Usuario usuario =
                                service.buscarUsuarioPorEmail(email);

                        System.out.println(
                                usuario.getId() + " - " +
                                        usuario.getNome() + " - " +
                                        usuario.getEmail()
                        );

                    } catch (UsuarioNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }catch(DadosUsuarioInvalidoException e){
                        System.out.println("Erro: " + e.getMessage());
                    }


                    break;


                // =====================================================
                // 5 - LISTAR TODOS
                // =====================================================
                case 5:
                    List<Usuario> todos =
                            service.listarTodosUsuarios();

                    if (todos.isEmpty()) {
                        System.out.println("Nenhum usuário encontrado.");
                    } else {
                        for (Usuario usuario : todos) {
                            System.out.println(
                                    usuario.getId() + " - " +
                                            usuario.getNome() + " - " +
                                            usuario.getEmail() +
                                            " - Ativo: " +
                                            usuario.isAtivo()
                            );
                        }
                    }
                    break;


                // =====================================================
                // 6 - FILTRAR POR NOME
                // =====================================================
                case 6:
                    System.out.print("Digite o nome para filtrar: ");
                    String nomeFiltro = scanner.nextLine();

                    try {
                        List<Usuario> filtrados =
                                service.filtrarUsuariosPorNome(nomeFiltro);

                        if (filtrados.isEmpty()) {
                            System.out.println("Nenhum usuário encontrado.");
                        } else {
                            for (Usuario usuario : filtrados) {
                                System.out.println(
                                        usuario.getId() + " - " +
                                                usuario.getNome() + " - " +
                                                usuario.getEmail()
                                );
                            }
                        }

                    } catch (DadosUsuarioInvalidoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                // =====================================================
                // 7 - FILTRAR POR PERÍODO
                // =====================================================
                case 7:
                    try {
                        System.out.print("Data inicial (dd/MM/yyyy): ");
                        String inicio = scanner.nextLine();

                        System.out.print("Data final (dd/MM/yyyy): ");
                        String fim = scanner.nextLine();

                        List<Usuario> usuarios =
                                service.filtrarUsuariosPorDataCadastro(
                                        inicio,
                                        fim
                                );

                        if (usuarios.isEmpty()) {
                            System.out.println("Nenhum usuário encontrado.");
                        } else {
                            for (Usuario usuario : usuarios) {
                                System.out.println(
                                        usuario.getId() + " - " +
                                                usuario.getNome() + " - " +
                                                usuario.getDataCadastro()
                                );
                            }
                        }

                    } catch (DataInvalidaException | ParseException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;


                // =====================================================
                // 8 - BUSCAR POR DATA DE CADASTRO
                // =====================================================
                case 8:
                    try {
                        System.out.print(
                                "Data e hora do cadastro " +
                                        "(dd/MM/yyyy HH:mm:ss): "
                        );

                        String data = scanner.nextLine();

                        List<Usuario> usuarios =
                                service.listarUsuariosPorDataCadastro(data);

                        if (usuarios.isEmpty()) {
                            System.out.println("Nenhum usuário encontrado.");
                        } else {
                            for (Usuario usuario : usuarios) {
                                System.out.println(
                                        usuario.getId() + " - " +
                                                usuario.getNome() + " - " +
                                                usuario.getDataCadastro()
                                );
                            }
                        }

                    } catch (DataInvalidaException | ParseException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;


                // =====================================================
                // 9 - LISTAR ATIVOS
                // =====================================================
                case 9:
                    List<Usuario> ativos =
                            service.listarTodosUsuariosAtivos();

                    if (ativos.isEmpty()) {
                        System.out.println("Nenhum usuário ativo.");
                    } else {
                        for (Usuario usuario : ativos) {
                            System.out.println(
                                    usuario.getId() + " - " +
                                            usuario.getNome() +
                                            " - ATIVO: " +
                                            usuario.isAtivo()
                            );
                        }
                    }
                    break;


                // =====================================================
                // 10 - LISTAR DESATIVADOS
                // =====================================================
                case 10:
                    List<Usuario> desativados =
                            service.listarTodosUsuariosDesativados();

                    if (desativados.isEmpty()) {
                        System.out.println("Nenhum usuário desativado.");
                    } else {
                        for (Usuario usuario : desativados) {
                            System.out.println(
                                    usuario.getId() + " - " +
                                            usuario.getNome() +
                                            " - ATIVO: " +
                                            usuario.isAtivo()
                            );
                        }
                    }
                    break;


                // =====================================================
                // 11 - ATUALIZAR USUÁRIO
                // =====================================================
                case 11:
                    try {
                        System.out.print("ID do usuário: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Novo nome: ");
                        String nome = scanner.nextLine();

                        System.out.print("Novo email: ");
                        String email = scanner.nextLine();

                        service.atualizarUsuario(
                                id,
                                nome,
                                email
                        );

                        System.out.println(
                                "Usuário atualizado com sucesso!"
                        );

                    } catch (DadosUsuarioInvalidoException |
                             UsuarioNaoEncontradoException e) {

                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;


                // =====================================================
                // 12 - DESATIVAR USUÁRIO
                // =====================================================
                case 12:
                    try {
                        System.out.print("ID do usuário: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        service.desativarUsuario(id);

                        System.out.println(
                                "Usuário desativado com sucesso!"
                        );

                    } catch (UsuarioNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }catch (UsuarioStatusInvalidoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;


                // =====================================================
                // 13 - ATIVAR USUÁRIO
                // =====================================================
                case 13:
                    try {
                        System.out.print("ID do usuário: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        service.ativarUsuario(id);

                        System.out.println(
                                "Usuário ativado com sucesso!"
                        );

                    } catch (UsuarioNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    catch (UsuarioStatusInvalidoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;


                // =====================================================
                // 14 - EXCLUIR PERMANENTEMENTE
                // =====================================================
                case 14:
                    try {
                        System.out.print("ID do usuário: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        Usuario usuario =
                                service.excluirUsuarioPermanentemente(id);

                        System.out.println(
                                "Usuário " + usuario.getNome() +
                                        " excluído com sucesso!"
                        );

                    } catch (UsuarioNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;


                // =====================================================
                // 0 - SAIR
                // =====================================================
                case 0:
                    System.out.println("Encerrando programa...");
                    break;


                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        scanner.close();
    }
}
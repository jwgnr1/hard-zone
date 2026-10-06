package com.ifpb.hard_zone;

import com.ifpb.hard_zone.controller.UsuarioController;
import com.ifpb.hard_zone.model.Usuario;

import java.text.SimpleDateFormat;
import java.util.List;

public class Main {


    private static final UsuarioController controller =
            new UsuarioController();

    private static final String TAG =
            "teste" + System.currentTimeMillis();

    private static String emailA;
    private static String emailB;

    private static Long idA;
    private static Long idB;

    private static int ok = 0;
    private static int falhas = 0;

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("       TESTE RÁPIDO - USUARIOCONTROLLER");
        System.out.println("==============================================");
        System.out.println("TAG: " + TAG);

        try {

            preparar();

            testarCadastro();

            testarBuscas();

            testarDatas();

            testarStatus();

            testarAtualizacao();

            testarExclusao();

        } catch (Throwable e) {

            falha(
                    "ERRO GERAL",
                    e.getClass().getSimpleName()
                            + ": "
                            + e.getMessage()
            );

        } finally {

            limpar();

            System.out.println();
            System.out.println("==============================================");
            System.out.println("                 RESUMO");
            System.out.println("==============================================");
            System.out.println("OK:     " + ok);
            System.out.println("FALHAS: " + falhas);

            if (falhas == 0) {
                System.out.println("TODOS OS TESTES PASSARAM!");
            } else {
                System.out.println("EXISTEM FALHAS PARA ANALISAR.");
            }

            System.out.println("==============================================");
        }
    }

// =========================================================
// PREPARAÇÃO
// =========================================================

    private static void preparar() {

        secao("PREPARAÇÃO");

        emailA = email("alice");
        emailB = email("bruno");

        String resultadoA =
                controller.cadastrarUsuario(
                        "Alice",
                        "15/05/2000",
                        emailA
                );

        sucesso(
                "Cadastrar usuário A",
                resultadoA
        );

        String resultadoB =
                controller.cadastrarUsuario(
                        "Bruno",
                        "20/08/1999",
                        emailB
                );

        sucesso(
                "Cadastrar usuário B",
                resultadoB
        );

        Usuario a =
                controller.buscarUsuarioPorEmail(emailA);

        Usuario b =
                controller.buscarUsuarioPorEmail(emailB);

        if (a == null || b == null) {

            falha(
                    "Preparação",
                    "Não foi possível recuperar A ou B"
            );

            return;
        }

        idA = a.getId();
        idB = b.getId();

        ok(
                "Usuários A e B foram encontrados"
        );
    }

// =========================================================
// CADASTRO
// =========================================================

    private static void testarCadastro() {

        secao("CADASTRO");

        // -------------------------
        // DEVE FUNCIONAR
        // -------------------------

        sucesso(
                "Nome válido",
                controller.cadastrarUsuario(
                        "Carlos",
                        "10/10/2000",
                        email("carlos")
                )
        );

        sucesso(
                "Nome com acento",
                controller.cadastrarUsuario(
                        "José da Silva",
                        "01/01/2001",
                        email("jose")
                )
        );

        // -------------------------
        // NÃO DEVE FUNCIONAR
        // -------------------------

        erro(
                "Nome null",
                controller.cadastrarUsuario(
                        null,
                        "10/10/2000",
                        email("nullnome")
                )
        );

        erro(
                "Nome vazio",
                controller.cadastrarUsuario(
                        "",
                        "10/10/2000",
                        email("vazio")
                )
        );

        erro(
                "Nome com números",
                controller.cadastrarUsuario(
                        "Joao123",
                        "10/10/2000",
                        email("numero")
                )
        );

        erro(
                "Nome com caracteres especiais",
                controller.cadastrarUsuario(
                        "Joao@Silva",
                        "10/10/2000",
                        email("especial")
                )
        );

        erro(
                "Email null",
                controller.cadastrarUsuario(
                        "Teste",
                        "10/10/2000",
                        null
                )
        );

        erro(
                "Email vazio",
                controller.cadastrarUsuario(
                        "Teste",
                        "10/10/2000",
                        ""
                )
        );

        erro(
                "Email sem @",
                controller.cadastrarUsuario(
                        "Teste",
                        "10/10/2000",
                        "teste.com"
                )
        );

        erro(
                "Email duplicado",
                controller.cadastrarUsuario(
                        "Outro",
                        "10/10/2000",
                        emailA
                )
        );

        erro(
                "Data null",
                controller.cadastrarUsuario(
                        "Teste",
                        null,
                        email("datanull")
                )
        );

        erro(
                "Data vazia",
                controller.cadastrarUsuario(
                        "Teste",
                        "",
                        email("data-vazia")
                )
        );

        erro(
                "Data impossível",
                controller.cadastrarUsuario(
                        "Teste",
                        "31/02/2026",
                        email("data-impossivel")
                )
        );

        erro(
                "Data no futuro",
                controller.cadastrarUsuario(
                        "Teste",
                        "15/05/2999",
                        email("datafuturo")
                )
        );

        erro(
                "Formato de data errado",
                controller.cadastrarUsuario(
                        "Teste",
                        "2000-05-15",
                        email("dataformato")
                )
        );
    }

// =========================================================
// BUSCAS
// =========================================================

    private static void testarBuscas() {

        secao("BUSCAS");

        // -------------------------
        // ID
        // -------------------------

        Usuario a =
                controller.buscarUsuarioPorId(idA);

        check(
                "Buscar A por ID",
                a != null && idA.equals(a.getId())
        );

        erro(
                "Buscar ID null",
                controller.buscarUsuarioPorId(null)
        );

        erro(
                "Buscar ID inexistente",
                controller.buscarUsuarioPorId(999999999L)
        );

        // -------------------------
        // NOME
        // -------------------------

        List<Usuario> porNome =
                controller.buscarUsuarioPorNome("Alice");

        check(
                "Buscar por nome",
                porNome != null
                        && contemId(porNome, idA)
        );

        erro(
                "Buscar nome null",
                controller.buscarUsuarioPorNome(null)
        );

        erro(
                "Buscar nome vazio",
                controller.buscarUsuarioPorNome("")
        );

        // -------------------------
        // EMAIL
        // -------------------------

        Usuario porEmail =
                controller.buscarUsuarioPorEmail(emailA);

        check(
                "Buscar por email",
                porEmail != null
                        && idA.equals(porEmail.getId())
        );

        erro(
                "Buscar email null",
                controller.buscarUsuarioPorEmail(null)
        );

        erro(
                "Buscar email inexistente",
                controller.buscarUsuarioPorEmail(
                        "naoexiste@test.com"
                )
        );

        // -------------------------
        // FILTRO
        // -------------------------

        List<Usuario> filtro =
                controller.filtrarPorNomeUsuario("Alice");

        check(
                "Filtrar por nome",
                filtro != null
                        && contemId(filtro, idA)
        );
    }

// =========================================================
// DATAS
// =========================================================

    private static void testarDatas() {

        secao("DATAS");

        Usuario a =
                controller.buscarUsuarioPorId(idA);

        if (a == null || a.getDataCadastro() == null) {

            falha(
                    "Data de cadastro",
                    "A não possui data de cadastro"
            );

            return;
        }

        String dataCadastro =
                new SimpleDateFormat(
                        "dd/MM/yyyy HH:mm:ss"
                ).format(
                        a.getDataCadastro()
                );

        List<Usuario> encontrados =
                controller.listarUsuariosPorDataCadastro(
                        dataCadastro
                );

        check(
                "Buscar pela data de cadastro",
                encontrados != null
                        && contemId(encontrados, idA)
        );

        // -------------------------
        // DATAS INVÁLIDAS
        // -------------------------

        erro(
                "Data de cadastro null",
                controller.listarUsuariosPorDataCadastro(null)
        );

        erro(
                "Data de cadastro vazia",
                controller.listarUsuariosPorDataCadastro("")
        );

        erro(
                "Data sem horário",
                controller.listarUsuariosPorDataCadastro(
                        "29/09/2026"
                )
        );

        erro(
                "Data impossível",
                controller.listarUsuariosPorDataCadastro(
                        "31/02/2026 10:00:00"
                )
        );

        erro(
                "Hora impossível",
                controller.listarUsuariosPorDataCadastro(
                        "29/09/2026 25:00:00"
                )
        );

        // -------------------------
        // PERÍODO
        // -------------------------

        List<Usuario> periodo =
                controller.filtrarUsuariosPorPeriodo(
                        "01/01/2020 00:00:00",
                        "31/12/2030 23:59:59"
                );

        check(
                "Filtrar por período válido",
                periodo != null
                        && contemId(periodo, idA)
        );

        erroPeriodo(
                "Período invertido",
                "31/12/2030 00:00:00",
                "01/01/2020 00:00:00"
        );

        erroPeriodo(
                "Período com data inválida",
                "31/02/2026 00:00:00",
                "31/03/2026 00:00:00"
        );

        erroPeriodo(
                "Período sem horário",
                "01/01/2020",
                "31/12/2030"
        );
    }

// =========================================================
// ATIVAR / DESATIVAR
// =========================================================

    private static void testarStatus() {

        secao("ATIVAR / DESATIVAR");

        Usuario a =
                controller.buscarUsuarioPorId(idA);

        check(
                "Usuário começa ativo",
                a != null && a.isAtivo()
        );

        sucesso(
                "Desativar A",
                controller.desativarUsuario(idA)
        );

        a =
                controller.buscarUsuarioPorId(idA);

        check(
                "A ficou desativado",
                a != null && !a.isAtivo()
        );

        List<Usuario> desativados =
                controller.listarTodosUsuariosDesativados();

        check(
                "A aparece nos desativados",
                desativados != null
                        && contemId(desativados, idA)
        );

        sucesso(
                "Ativar A",
                controller.ativarUsuario(idA)
        );

        a =
                controller.buscarUsuarioPorId(idA);

        check(
                "A voltou a ficar ativo",
                a != null && a.isAtivo()
        );

        List<Usuario> ativos =
                controller.listarTodosUsuariosAtivos();

        check(
                "A aparece nos ativos",
                ativos != null
                        && contemId(ativos, idA)
        );

        // -------------------------
        // IDs INVÁLIDOS
        // -------------------------

        erro(
                "Desativar ID null",
                controller.desativarUsuario(null)
        );

        erro(
                "Ativar ID null",
                controller.ativarUsuario(null)
        );

        erro(
                "Desativar ID inexistente",
                controller.desativarUsuario(999999999L)
        );

        erro(
                "Ativar ID inexistente",
                controller.ativarUsuario(999999999L)
        );
    }

// =========================================================
// ATUALIZAÇÃO
// =========================================================

    private static void testarAtualizacao() {

        secao("ATUALIZAÇÃO");

        String novoNome =
                "Alice Atualizada";

        String novoEmail =
                email("alice-atualizada");

        sucesso(
                "Atualizar A",
                controller.atualizarDadosUsuario(
                        idA,
                        novoNome,
                        novoEmail
                )
        );

        Usuario a =
                controller.buscarUsuarioPorId(idA);

        check(
                "Nome foi atualizado",
                a != null
                        && novoNome.equals(a.getNome())
        );

        check(
                "Email foi atualizado",
                a != null
                        && novoEmail.equalsIgnoreCase(
                        a.getEmail()
                )
        );

        // -------------------------
        // NÃO DEVE FUNCIONAR
        // -------------------------

        erro(
                "Atualizar ID null",
                controller.atualizarDadosUsuario(
                        null,
                        "Teste",
                        email("x")
                )
        );

        erro(
                "Atualizar ID inexistente",
                controller.atualizarDadosUsuario(
                        999999999L,
                        "Teste",
                        email("x")
                )
        );

        erro(
                "Atualizar nome null",
                controller.atualizarDadosUsuario(
                        idA,
                        null,
                        novoEmail
                )
        );

        erro(
                "Atualizar nome vazio",
                controller.atualizarDadosUsuario(
                        idA,
                        "",
                        novoEmail
                )
        );

        erro(
                "Atualizar nome com número",
                controller.atualizarDadosUsuario(
                        idA,
                        "Alice123",
                        novoEmail
                )
        );

        erro(
                "Atualizar email inválido",
                controller.atualizarDadosUsuario(
                        idA,
                        novoNome,
                        "emailinvalido"
                )
        );

        erro(
                "Atualizar usando email de B",
                controller.atualizarDadosUsuario(
                        idA,
                        novoNome,
                        emailB
                )
        );

        // Verificar atomicidade
        a =
                controller.buscarUsuarioPorId(idA);

        check(
                "Falha não alterou os dados de A",
                a != null
                        && novoNome.equals(a.getNome())
                        && novoEmail.equalsIgnoreCase(
                        a.getEmail()
                )
        );
    }

// =========================================================
// EXCLUSÃO
// =========================================================

    private static void testarExclusao() {

        secao("EXCLUSÃO");

        erro(
                "Excluir ID null",
                controller.excluirUsuarioPermanente(null)
        );

        erro(
                "Excluir ID inexistente",
                controller.excluirUsuarioPermanente(
                        999999999L
                )
        );

        sucesso(
                "Excluir A",
                controller.excluirUsuarioPermanente(idA)
        );

        Usuario a =
                controller.buscarUsuarioPorId(idA);

        check(
                "A não existe após exclusão",
                a == null
        );

        Usuario emailExcluido =
                controller.buscarUsuarioPorEmail(
                        email("alice-atualizada")
                );

        check(
                "Email de A não existe após exclusão",
                emailExcluido == null
        );

        // B deve continuar existindo
        Usuario b =
                controller.buscarUsuarioPorId(idB);

        check(
                "Excluir A não afetou B",
                b != null && idB.equals(b.getId())
        );
    }

// =========================================================
// LIMPEZA
// =========================================================

    private static void limpar() {

        secao("LIMPEZA");

        List<Usuario> usuarios =
                controller.listarTodosUsuarios();

        if (usuarios == null) {
            return;
        }

        int removidos = 0;

        for (Usuario usuario : usuarios) {

            String email =
                    usuario.getEmail();

            if (email != null
                    && email.contains(TAG)) {

                try {

                    controller.excluirUsuarioPermanente(
                            usuario.getId()
                    );

                    removidos++;

                } catch (Exception ignored) {
                }
            }
        }

        System.out.println(
                "Registros de teste removidos: "
                        + removidos
        );
    }

// =========================================================
// MÉTODOS AUXILIARES
// =========================================================

    private static String email(String nome) {

        return nome
                + "."
                + TAG
                + "@teste.com";
    }

    private static boolean contemId(
            List<Usuario> usuarios,
            Long id
    ) {

        if (usuarios == null || id == null) {
            return false;
        }

        for (Usuario usuario : usuarios) {

            if (usuario != null
                    && id.equals(usuario.getId())) {

                return true;
            }
        }

        return false;
    }

    private static void sucesso(
            String descricao,
            String resultado
    ) {

        if (resultado != null
                && resultado
                .toLowerCase()
                .contains("sucesso")) {

            ok(descricao);

        } else {

            falha(
                    descricao,
                    "deveria funcionar, mas retornou: "
                            + resultado
            );
        }
    }

    private static void erro(
            String descricao,
            Object resultado
    ) {

        if (resultado == null) {

            ok(descricao);

            return;
        }

        if (resultado instanceof String
                && !((String) resultado)
                .toLowerCase()
                .contains("sucesso")) {

            ok(descricao);

        } else {

            falha(
                    descricao,
                    "aceitou algo que deveria rejeitar: "
                            + resultado
            );
        }
    }

    private static void erroPeriodo(
            String descricao,
            String inicio,
            String fim
    ) {

        Object resultado =
                controller.filtrarUsuariosPorPeriodo(
                        inicio,
                        fim
                );

        erro(
                descricao,
                resultado
        );
    }

    private static void check(
            String descricao,
            boolean condicao
    ) {

        if (condicao) {

            ok(descricao);

        } else {

            falha(
                    descricao,
                    "condição não atendida"
            );
        }
    }

    private static void ok(
            String descricao
    ) {

        ok++;

        System.out.println(
                "[OK]    " + descricao
        );
    }

    private static void falha(
            String descricao,
            String motivo
    ) {

        falhas++;

        System.out.println(
                "[FALHA] "
                        + descricao
                        + " -> "
                        + motivo
        );
    }

    private static void secao(
            String nome
    ) {

        System.out.println();
        System.out.println(
                "--- " + nome + " ---"
        );
    }


}

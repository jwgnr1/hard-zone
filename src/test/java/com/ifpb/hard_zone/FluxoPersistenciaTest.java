package com.ifpb.hard_zone;

import com.ifpb.hard_zone.model.*;
import com.ifpb.hard_zone.service.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FluxoPersistenciaTest {

    @Test
    void testarFluxoCompleto() throws Exception {
        UsuarioService usuarioService = new UsuarioService();
        ComputadorService computadorService = new ComputadorService();
        JogoService jogoService = new JogoService();

        // 1. Cadastrar 5 Usuários
        List<Usuario> usuarios = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String emailTeste = "usuario_" + i + "_" + System.currentTimeMillis() + "@email.com";
            usuarioService.salvarUsuario("Carlos", "01/01/2000", emailTeste);
            Usuario usuario = usuarioService.buscarUsuarioPorEmail(emailTeste);
            assertNotNull(usuario.getId());
            usuarios.add(usuario);
        }
        assertEquals(5, usuarios.size());
        System.out.println("5 usuários cadastrados com sucesso.");

        // 2. Cadastrar 5 Jogos
        List<Jogo> jogos = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Jogo jogo = new Jogo();
            jogo.setNome("Jogo " + i + " " + System.currentTimeMillis());
            jogo.setFaixaEtaria(16);
            jogoService.adicionar(jogo);
            Jogo jogoCriado = jogoService.buscarPorId(jogo.getId());
            assertNotNull(jogoCriado.getId());
            jogos.add(jogoCriado);
        }
        assertEquals(5, jogos.size());
        System.out.println("5 jogos cadastrados com sucesso.");

        // 3. Cadastrar 5 Computadores e vincular a pelo menos 2 jogos cada
        List<Computador> computadores = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            int numeroMaquina = (int) (Math.random() * 10000) + i;
            Computador comp = new Computador();
            comp.setNumeroMaquina(numeroMaquina);
            comp.setEspecificacoes("Core i7, RTX 3060, 16GB RAM - Maquina " + i);
            computadorService.adicionarComputador(comp);
            Computador computador = computadorService.buscarPorNumeroMaquina(numeroMaquina);
            assertNotNull(computador.getId());

            // Vincular pelo menos 2 jogos (ex: jogo i e jogo (i % 5) + 1)
            Jogo jogo1 = jogos.get(i - 1);
            Jogo jogo2 = jogos.get(i % 5);

            computadorService.adicionarJogo(computador.getId(), jogo1.getId());
            computadorService.adicionarJogo(computador.getId(), jogo2.getId());

            // Verificar se foram vinculados
            List<Jogo> jogosDoComputador = computadorService.listarJogos(computador.getId());
            assertTrue(jogosDoComputador.size() >= 2);

            computadores.add(computador);
        }
        assertEquals(5, computadores.size());
        System.out.println("5 computadores cadastrados e vinculados a pelo menos 2 jogos cada com sucesso.");
    }
}

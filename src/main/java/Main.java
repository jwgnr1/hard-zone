import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.repository.UsuarioRepository;

import java.util.Date;

public class Main {

    public static void main(String[] args) {

        Usuario usuario = new Usuario();

        usuario.setNome("João Silva");
        usuario.setDataNascimento(new Date());
        usuario.setEmail("joao@email.com");
        usuario.setDataCadastro(new Date());
        usuario.setAtivo(true);

        UsuarioRepository repository = new UsuarioRepository();

        repository.salvar(usuario);

        System.out.println("Usuário salvo!");
    }
}
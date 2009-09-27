/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projeto.dao;
import projeto.model.Usuario;
/**
 *
 * @author Nelson
 */
import java.util.List;
import projeto.model.PostNew;

public interface UsuarioDAO {

    public List buscarFavoritos(Usuario usuario) throws Exception;

	public Usuario buscarUsuario(int idUsuario) throws Exception;

    public Usuario buscarUsuarioLogin(Usuario usuario) throws Exception;

    public List buscarUsuario() throws Exception;

    public void salvar(Usuario usuario) throws Exception;

    public void cadastrar(Usuario usuario) throws Exception;

    public void excluir(Usuario usuario) throws Exception;

    public void atualizar(Usuario usuario) throws Exception;

    public void adicionarFavorito(Usuario usuario, PostNew postnew) throws Exception;

    //public abstract Usuario validarUsuario(String nome, String senha);

}
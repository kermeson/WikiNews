package projeto.dao;


import projeto.model.PostNew;
import java.util.List;
import projeto.model.ComentarioPostNew;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kermeson
 */
public interface PostNewDAO {

    public List buscarComentariosPostNew(PostNew postnew) throws Exception;

    public PostNew buscarPostNew(int idPostnew) throws Exception;

    public List buscarPostNews() throws Exception;

    public void salvar(PostNew postnew) throws Exception;

    public void excluir(PostNew postnew) throws Exception;

    public void atualizar(PostNew postnew) throws Exception;

    public void adicionarComentario(ComentarioPostNew comentario) throws Exception;


}

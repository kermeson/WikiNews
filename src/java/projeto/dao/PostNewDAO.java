package projeto.dao;


import projeto.model.PostNew;
import java.util.List;
import projeto.model.ComentarioPostNew;
import projeto.model.Tag;

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

    public List buscarPostNews(String textoBusca) throws Exception;

    public List buscarPostNews(Tag tag) throws Exception;

    public void adicionarTag(PostNew postNew, Tag tag) throws Exception;;


}

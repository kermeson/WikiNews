/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto.controller;

import java.util.ArrayList;
import projeto.model.PostNew;
import projeto.dao.PostNewDAOImp;
import projeto.dao.PostNewDAO;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.servlet.http.HttpSession;
import projeto.model.Usuario;

/**
 *
 * @author kermeson
 */
public class PostNewController {
    

    private PostNew postNew;
    private String textoBusca;
    private List<PostNew> listaPosts;


    public List<PostNew> getListaPosts() {
        return listaPosts;
    }

    public void setListaPosts(List<PostNew> listaPosts) {
        this.listaPosts = listaPosts;
    }


    /*
     * Métodos que expõem o estado à página
     */

   
    
    public String novoPostNew() {
        this.postNew = new PostNew();
        return "novo";
    }

    public String getTextoBusca() {
        return textoBusca;
    }

    public void setTextoBusca(String textoBusca) {
        this.textoBusca = textoBusca;
    }




    public PostNew getPostNew() {
        return postNew;
    }

    public void setPostNew(PostNew postnew) {
        this.postNew = postnew;
    }

    public String salvar() throws Exception {
        PostNewDAO dao = new PostNewDAOImp();
        if (postNew.getIdPostNew() == null) {
            postNew.setDataPublicacao(new Date());
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            Usuario currentUser = (Usuario) session.getAttribute("currentUser");
            postNew.setUsuario(currentUser);
            dao.salvar(postNew);
            facesContext.addMessage("formpostnew", new FacesMessage("O cadastro foi realizado com sucesso"));
        } else {
            dao.atualizar(postNew);
        }
        return "sucesso";
    }

    public String excluir() throws Exception {
        PostNewDAO dao = new PostNewDAOImp();
        dao.excluir(postNew);
        return "sucesso";
    }

    public List getTodos() throws Exception {
        PostNewDAO dao = new PostNewDAOImp();
        return dao.buscarPostNews();
    }

    public String buscarPostNews() throws Exception {
        PostNewDAO dao = new PostNewDAOImp();
        System.out.print(getTextoBusca() + "sdfsf");
        setListaPosts(dao.buscarPostNews(getTextoBusca()));
        return "resultadoBusca";
    }




    
}

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


    /*
     * Métodos que expõem o estado à página
     */

   
    
    public String novoPostNew() {
        this.postNew = new PostNew();
        return "novo";
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

    public List<String> getComplemento(Object event) throws Exception {
        PostNewDAO dao = new PostNewDAOImp();
        List lista = dao.buscarPostNews();
        String prefixo = event.toString().toLowerCase();

        List<String> retorno = new ArrayList();

        for (int pos = 0; pos < lista.size(); pos++) {
            PostNew p = (PostNew) lista.get(pos);
            
            if (p.getTitulo().toLowerCase().startsWith(prefixo)) {
                System.out.print(p.getTitulo());
                retorno.add(p.getTitulo());
            }

        }

        return retorno;

    }



    
}

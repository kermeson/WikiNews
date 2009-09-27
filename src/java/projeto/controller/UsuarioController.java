/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projeto.controller;

import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.servlet.http.HttpSession;
import projeto.model.Usuario;
import projeto.dao.UsuarioDAO;
import projeto.dao.UsuarioDAOImp;


/**
 *
 * @author Nelson
 */
public class UsuarioController {

    private Usuario usuario;
    private DataModel model;

    public String loginUsuario() {
        this.usuario = new Usuario();
        return "login";
    }

    public String cadLoginUsuario() {
        this.usuario = new Usuario();
        return "cadlogin";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String salvar() throws Exception {
        UsuarioDAO dao = new UsuarioDAOImp();
        if (usuario.getIdUsuario() == null) {
            usuario.setIdUsuario(1);
            dao.salvar(usuario);
        } else {
            dao.atualizar(usuario);
        }
        return "sucesso";
    }

     public String excluir() throws Exception {
        UsuarioDAO dao = new UsuarioDAOImp();
        dao.excluir(usuario);
        return "sucesso";
    }

     public String cadastrar() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        UsuarioDAO dao = new UsuarioDAOImp();
        try {
            dao.cadastrar(usuario);
        } catch (Exception e) {
            context.addMessage("formcadlogin", new FacesMessage(e.getMessage()));
        }
        return "sucesso";
    }

     public String login() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        UsuarioDAO dao = new UsuarioDAOImp();
        Usuario user;
        try {
             user = dao.buscarUsuarioLogin(getUsuario());
        } catch (Exception e) {
            context.addMessage("formlogin", new FacesMessage(e.getMessage()));
            return "falhou";
        }
        context.getExternalContext().getSessionMap().put("currentUser", user );
        return "sucesso";
    }

    public String logout() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
        session.invalidate();
        return "logout";
    }

    public List getTodos() throws Exception {
        UsuarioDAO dao = new UsuarioDAOImp();
        return dao.buscarUsuario();
    }


}

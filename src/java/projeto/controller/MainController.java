 /*
 * __NAME__.java
 *
 * Created on 6 de Setembro de 2009, 20:47
 */
package projeto.controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import projeto.dao.PostNewDAO;
import projeto.dao.PostNewDAOImp;
import projeto.dao.UsuarioDAO;
import projeto.dao.UsuarioDAOImp;
import projeto.model.ComentarioPostNew;
import projeto.model.PostNew;
import projeto.model.Usuario;

/** 
 *
 * @author Administrador
 * @version 
 */
public class MainController {

    public static final String HOME_STATE = "inicio";
    public static final String VISUALIZAR_POST_STATE = "visualizar";
    public static final String EDITAR_POST_STATE = "editar";

    private String currentState = HOME_STATE;
    private PostNew postNew;
    private Usuario usuario;
    private ComentarioPostNew comentario;

    public ComentarioPostNew getComentario() {
        return comentario;
    }

    public void setComentario(ComentarioPostNew comentario) {
        this.comentario = comentario;
    }

    public MainController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Usuario currentUser = (Usuario) session.getAttribute("currentUser");
        setUsuario(currentUser);
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    /*
     * Métodos que expõem o estado à página
     */
    public boolean isHomeState() {
        String state = this.getCurrentState();
        return (state == null || HOME_STATE.equals(state));
    }
     public boolean isVisualizarPostState() {
        return VISUALIZAR_POST_STATE.equals(this.getCurrentState());
    }

    public boolean isEditarPostState() {
        return EDITAR_POST_STATE.equals(this.getCurrentState());
    }

    public String ver() throws Exception {
        this.comentario = new ComentarioPostNew();
        return "verPostNew";
    }

    public String inicio() throws Exception {
        setCurrentState(HOME_STATE);
        return "inicio";
    }

    public PostNew getPostNew() {
        return postNew;
    }

    public void setPostNew(PostNew postnew) {
        this.postNew = postnew;
    }
    
    public void mostrarEditar() throws Exception {
        setCurrentState(EDITAR_POST_STATE);
    }

    public void editar() throws Exception {
        PostNewDAO dao = new PostNewDAOImp();
        dao.atualizar(postNew);
        setCurrentState(VISUALIZAR_POST_STATE);
    }

    public String editarPerfil() {
        return "editarPerfil";
    }

    public void salvarPerfil() throws Exception {
        UsuarioDAO dao = new UsuarioDAOImp();
        dao.atualizar(getUsuario());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage("formusuario", new FacesMessage("O cadastro foi realizado com sucesso"));
    }

    public void adicionarFavorito() throws Exception {
        UsuarioDAO dao = new UsuarioDAOImp();
        String msg;
        try {
            dao.adicionarFavorito(usuario, postNew);
            msg = "O favorito foi adicionado com sucesso";
        } catch (Exception e ){
            msg = "Operação não realizada: " + e.toString();
        }
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage("formusuario", new FacesMessage(msg));
    }

    public List getFavoritos() throws Exception {
        UsuarioDAO dao = new UsuarioDAOImp();
        return dao.buscarFavoritos(usuario);
    }

    public void adicionarComentarioPostNew() throws Exception {
        PostNewDAO dao = new PostNewDAOImp();
        String msg;
        try {
            getComentario().setIdPostNew(getPostNew().getIdPostNew());
            dao.adicionarComentario(getComentario());
            msg = "O comentário foi adicionado com sucesso";
        } catch (Exception e ){
            msg = "Operação não realizada: " + e.toString();
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage("formcomentario", new FacesMessage(msg));
    }

    public List getComentariosPostNew() throws Exception {
        System.out.println("lista");
        PostNewDAO dao = new PostNewDAOImp();
        return dao.buscarComentariosPostNew(postNew);
    }

    public List<String> complemento(Object event) throws Exception {
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

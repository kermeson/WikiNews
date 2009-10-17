package projeto.controller;

import projeto.model.PostNew;
import projeto.dao.PostNewDAOImp;
import projeto.dao.PostNewDAO;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Nelson
 */
public class LeitorController {
    private PostNew postNew;

    public PostNew getPostNew() {
        return postNew;
    }

    public void setPostNew(PostNew postnew) {
        this.postNew = postnew;
    }

     public List getTodos() throws Exception {
        PostNewDAO dao = new PostNewDAOImp();
        return dao.buscarPostNews();
    }

     public String verPostNew() throws Exception {
        return "verPostNew";
    }

     public String voltarPostNew() throws Exception {
        return "voltarPostNew";
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

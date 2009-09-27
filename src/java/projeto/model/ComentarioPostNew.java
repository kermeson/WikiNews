/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projeto.model;

/**
 *
 * @author Administrador
 */
public class ComentarioPostNew {
    private int idComentario;
    private int idPostNew;
    private String nome;
    private String email;
    private String comentario;

    public ComentarioPostNew() {
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public ComentarioPostNew(int idComentario, int idPostNew, String nome, String email, String comentario) {
        this.idComentario = idComentario;
        this.idPostNew = idPostNew;
        this.nome = nome;
        this.email = email;
        this.comentario = comentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdPostNew() {
        return idPostNew;
    }

    public void setIdPostNew(int idPostNew) {
        this.idPostNew = idPostNew;
    }

    

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto.model;

import java.util.Date;

/**
 *
 * @author kermeson
 */
public class PostNew {

    private Integer idPostNew;
    private Usuario usuario;
    private String titulo;
    private String texto;
    private Date dataPublicacao;

    public PostNew(Integer idPostNew, Usuario usuario, String titulo, String texto,Date dataPublicacao) {
        this.idPostNew = idPostNew;
        this.usuario = usuario;
        this.titulo = titulo;
        this.texto = texto;
        this.dataPublicacao = dataPublicacao;
    }

    public PostNew() {}


    public Usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getIdPostNew() {
        return idPostNew;
    }

    public void setIdPostNew(Integer idPostNew) {
        this.idPostNew = idPostNew;
    }

    

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @return the dataPublicacao
     */
    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    /**
     * @param dataPublicacao the dataPublicacao to set
     */
    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projeto.model;

/**
 *
 * @author Administrador
 */
public class Tag {
    private int idTag;
    private String nome;

    public Tag(int idTag, String nome) {
        this.idTag = idTag;
        this.nome = nome;
    }

    
    public int getIdTag() {
        return idTag;
    }

    public void setIdTag(int idTag) {
        this.idTag = idTag;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}

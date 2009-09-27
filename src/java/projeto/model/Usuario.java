/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projeto.model;

/**
 *
 * @author Nelson
 */
public class Usuario {

    private Integer idUsuario;
    private String nome;
    private String endereco;
    private String email;
    private String senha;

    public Usuario() {
    }

    public Usuario(Integer idUsuario, String nome, String endereco, String email, String senha) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

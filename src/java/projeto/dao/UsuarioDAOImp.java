/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projeto.dao;

/**
 *
 * @author Nelson
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import projeto.model.PostNew;
import projeto.model.Usuario;
import projeto.utils.ConnectionFactory;

public class UsuarioDAOImp implements UsuarioDAO {

    private Connection conn;

    public UsuarioDAOImp() throws Exception {
        try {
            this.conn = ConnectionFactory.getConnection();
        } catch (Exception e) {
            throw new Exception("Erro: " + e.getMessage());
        }
    }

    public void salvar(Usuario usuario) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (usuario == null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {
            String sql = "INSERT INTO usuario (idUsuario, nome, endereco, email, senha)" +
                    "values (?,?,?,?,md5(?))";
            System.out.print(sql);
            conn = this.conn;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, usuario.getIdUsuario());
            ps.setString(2, usuario.getNome());
            ps.setString(3, usuario.getEndereco());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getSenha());
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao inserir dados: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }
    //SELECT PARA PEGAR ULTIMO idUsuario OU TESTAR AUTOINCREMENTO
    public void cadastrar(Usuario usuario) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (usuario == null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {
            String sql = "INSERT INTO usuario (nome, endereco, email, senha)" +
                    "values (?,?,?,md5(?))";
            System.out.print(sql);
            conn = this.conn;
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEndereco());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getSenha());
            ps.executeUpdate();
            throw new Exception("Usuário cadastrado com sucesso!!!");
        } catch (SQLException sqle) {
            throw new Exception("Erro ao inserir dados: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public void atualizar(Usuario usuario) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (usuario == null) throw new Exception("O valor passado não pode ser nulo");
        try {
            String sql = "UPDATE usuario SET nome=?, " + "endereco=?, " + "email=?, " + "senha=? "+
                    "WHERE idUsuario=?";
            conn = this.conn;
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEndereco());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getSenha());
            ps.setInt(5, usuario.getIdUsuario());
            ps.executeUpdate();
        } catch(SQLException sqle) {
            throw new Exception("Erro ao atualizar dados: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public void excluir(Usuario usuario) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (usuario == null) throw new Exception("O valor passado não pode ser nulo");
        try {
            String sql = "DELETE FROM usuario WHERE idUsuario=?";
            conn = this.conn;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, usuario.getIdUsuario());
            ps.executeUpdate();
        } catch(SQLException sqle) {
            throw new Exception("Erro ao excluir dados: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public List buscarUsuario() throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            String sql = "SELECT * FROM usuario";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Usuario> list = new ArrayList<Usuario>();
            while (rs.next()) {
                list.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            return list;
        } catch (SQLException sqle) {
            throw new Exception("Erro: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public Usuario buscarUsuario(int idUsuario) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new Exception("Não foi encontrado usuário com o código: " + idUsuario);
            }
            return new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
        } catch (SQLException sqle) {
            throw new Exception("Erro: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

     public Usuario buscarUsuarioLogin(Usuario usuario) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            String sql = "SELECT * FROM usuario WHERE email = ? and senha = md5(?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getSenha());
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new Exception("Não foi encontrado usuário com o email '" + usuario.getEmail() + "' ou a senha está incorreta!");
            }
            return new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
        } catch (SQLException sqle) {
            throw new Exception("Erro: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public void adicionarFavorito(Usuario usuario, PostNew postnew) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (usuario == null || postnew == null) {
            throw new Exception("Faltando parâmetros: usuario, postnew");
        }
        try {
            String sql = "INSERT INTO relacao_usuario_postnew (idUsuario, idPostNew)" +
                    "values (?,?)";
            conn = this.conn;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, usuario.getIdUsuario());
            ps.setInt(2, postnew.getIdPostNew());
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao inserir dados: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public List buscarFavoritos(Usuario usuario) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            String sql = "SELECT p.*, u.* FROM usuario as u, postnew as p, relacao_usuario_postnew as r " +
                    "WHERE r.idPostNew = p.idPostnew AND p.idUsuario = u.idUsuario AND r.idUsuario = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, usuario.getIdUsuario());
            rs = ps.executeQuery();
            List<PostNew> list = new ArrayList<PostNew>();
            while (rs.next()) {
                Usuario autor = new Usuario(rs.getInt("idUsuario"), rs.getString("nome"),rs.getString("endereco"),rs.getString("email"), rs.getString("senha"));
                list.add(new PostNew(rs.getInt("idPostNew"), autor, rs.getString("titulo"), rs.getString("texto"), rs.getDate("dataPublicacao")));
            }
            return list;
        } catch (SQLException sqle) {
            throw new Exception("Erro: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

}
package projeto.dao;


import projeto.utils.ConnectionFactory;
import projeto.model.PostNew;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import projeto.model.ComentarioPostNew;
import projeto.model.Tag;
import projeto.model.Usuario;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kermeson
 */
public class PostNewDAOImp implements PostNewDAO {
    private Connection conn;

    public PostNewDAOImp() throws Exception {
        try {
            this.conn = ConnectionFactory.getConnection();
        } catch(Exception e) {
            throw new Exception("Erro: " + e.getMessage());
        }
    }

    public void salvar(PostNew postnew) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (postnew == null) throw new Exception("O valor passado não pode ser nulo");
        try {
            String sql = "INSERT INTO postnew (idUsuario, titulo, texto, dataPublicacao)" +
                    "values (?,?,?,?)";
            System.out.print(sql);
            conn = this.conn;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, postnew.getUsuario().getIdUsuario());
            ps.setString(2, postnew.getTitulo());
            ps.setString(3, postnew.getTexto());
            ps.setTimestamp(4, new java.sql.Timestamp(postnew.getDataPublicacao().getTime()));
            ps.executeUpdate();
        } catch(SQLException sqle) {
            throw new Exception("Erro ao inserir dados: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public List buscarPostNews() throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            String sql = "SELECT * FROM postnew, usuario WHERE postnew.idUsuario = usuario.idUsuario";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<PostNew> list = new ArrayList<PostNew>();
            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getInt("idUsuario"), rs.getString("nome"),rs.getString("endereco"),rs.getString("email"), rs.getString("senha"));
                list.add(new PostNew(rs.getInt("idPostNew"), usuario, rs.getString("titulo"), rs.getString("texto"), rs.getTimestamp("dataPublicacao")));
            }
            return list;
        } catch(SQLException sqle) {
            throw new Exception("Erro: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }
    public List buscarPostNews(String textoBusca) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            String sql = "SELECT * FROM postnew, usuario WHERE postnew.idUsuario = usuario.idUsuario AND (" +
                    "postnew.titulo LIKE ? OR postnew.texto LIKE ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + textoBusca + "%");
            ps.setString(2, "%" + textoBusca + "%");
            rs = ps.executeQuery();
            List<PostNew> list = new ArrayList<PostNew>();

            while (rs.next()) {
                //Pegando as tags
                String sql_tag = "SELECT * FROM relacao_postnew_tag, tag WHERE relacao_postnew_tag.idTag = tag.idTag AND " +
                        "relacao_postnew_tag.idPostnew = ?";
                PreparedStatement ps_tag = conn.prepareStatement(sql_tag);
                ps_tag.setInt(1, rs.getInt("idPostNew"));
                ResultSet rs_tag = ps_tag.executeQuery();

                List<Tag> list_tags = new ArrayList<Tag>();
                while (rs_tag.next()) {
                    list_tags.add(new Tag(rs_tag.getInt("idTag"), rs_tag.getString("nome")));
                }
                //Pegando o autor
                Usuario usuario = new Usuario(rs.getInt("idUsuario"), rs.getString("nome"),rs.getString("endereco"),rs.getString("email"), rs.getString("senha"));
                //Adicionando na lista de posts
                list.add(new PostNew(rs.getInt("idPostNew"), usuario, rs.getString("titulo"), 
                        rs.getString("texto"), rs.getTimestamp("dataPublicacao"),
                        list_tags));
            }
            return list;
        } catch(SQLException sqle) {
            throw new Exception("Erro: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public List buscarPostNews(Tag tag) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            String sql = "SELECT * FROM relacao_postnew_tag, postnew, usuario WHERE relacao_postnew_tag.idPostnew = postnew.idPostnew AND " +
                    "postnew.idUsuario = usuario.idUsuario AND relacao_postnew_tag.idTag = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, tag.getIdTag());
            rs = ps.executeQuery();
            List<PostNew> list = new ArrayList<PostNew>();

            while (rs.next()) {
                //Pegando as tags
                String sql_tag = "SELECT * FROM relacao_postnew_tag, tag WHERE relacao_postnew_tag.idTag = tag.idTag AND " +
                        "relacao_postnew_tag.idPostnew = ?";
                PreparedStatement ps_tag = conn.prepareStatement(sql_tag);
                ps_tag.setInt(1, rs.getInt("idPostNew"));
                ResultSet rs_tag = ps_tag.executeQuery();

                List<Tag> list_tags = new ArrayList<Tag>();
                while (rs_tag.next()) {
                    list_tags.add(new Tag(rs_tag.getInt("idTag"), rs_tag.getString("nome")));
                }
                //Pegando o autor
                Usuario usuario = new Usuario(rs.getInt("idUsuario"), rs.getString("nome"),rs.getString("endereco"),rs.getString("email"), rs.getString("senha"));
                //Adicionando na lista de posts
                list.add(new PostNew(rs.getInt("idPostNew"), usuario, rs.getString("titulo"),
                        rs.getString("texto"), rs.getTimestamp("dataPublicacao"),
                        list_tags));
            }
            return list;
        } catch(SQLException sqle) {
            throw new Exception("Erro: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public PostNew buscarPostNew(int idPostnew) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            String sql = "SELECT * FROM postnew, usuario WHERE postnew.idUsuario = usuario.idUsuario AND idPostnew = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idPostnew);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new Exception("Não foi encontrado notícia com o código: " + idPostnew);
            }
            //Pegando as tags
            String sql_tag = "SELECT * FROM relacao_postnew_tag, tag WHERE relacao_postnew_tag.idTag = tag.idTag AND " +
                    "relacao_postnew_tag.idPostnew = ?";
            PreparedStatement ps_tag = conn.prepareStatement(sql_tag);
            ps_tag.setInt(1, rs.getInt("idPostNew"));
            ResultSet rs_tag = ps_tag.executeQuery();

            List<Tag> list_tags = new ArrayList<Tag>();
            while (rs_tag.next()) {
                list_tags.add(new Tag(rs_tag.getInt("idTag"), rs_tag.getString("nome")));
            }
            // Pegando o autor
            Usuario usuario = new Usuario(rs.getInt("idUsuario"), rs.getString("nome"),rs.getString("endereco"),rs.getString("email"), rs.getString("senha"));
            return new PostNew(rs.getInt("idPostNew"), usuario, rs.getString("titulo"), 
                    rs.getString("texto"), rs.getDate("dataPublicacao"),list_tags);
        } catch(SQLException sqle) {
            throw new Exception("Erro: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public void atualizar(PostNew postnew) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (postnew == null) throw new Exception("O valor passado não pode ser nulo");
        try {
            String sql = "UPDATE postnew SET idUsuario=?, " + "titulo=?, " + "texto=?, " + "dataPublicacao=? " +
                    "WHERE idPostnew=?";
            conn = this.conn;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, postnew.getUsuario().getIdUsuario());
            ps.setString(2, postnew.getTitulo());
            ps.setString(3, postnew.getTexto());
            ps.setDate(4, new java.sql.Date(postnew.getDataPublicacao().getTime()));
            ps.setInt(5, postnew.getUsuario().getIdUsuario());
            ps.executeUpdate();
        } catch(SQLException sqle) {
            throw new Exception("Erro ao atualizar dados: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public void excluir(PostNew postnew) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (postnew == null) throw new Exception("O valor passado não pode ser nulo");
        try {
            String sql = "DELETE FROM postnew WHERE idPostnew=?";
            conn = this.conn;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, postnew.getUsuario().getIdUsuario());
            ps.executeUpdate();
        } catch(SQLException sqle) {
            throw new Exception("Erro ao excluir dados: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public void adicionarComentario(ComentarioPostNew comentario) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (comentario == null) throw new Exception("O objeto comentario não pode ser nulo");
        try {
            String sql = "INSERT INTO comentario_postnew (idPostNew, nome, email, comentario)" +
                    "values (?,?,?,?)";
            conn = this.conn;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, comentario.getIdPostNew());
            ps.setString(2, comentario.getNome());
            ps.setString(3, comentario.getEmail());
            ps.setString(4, comentario.getComentario());
            ps.executeUpdate();
        } catch(SQLException sqle) {
            throw new Exception("Erro ao inserir dados: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public List buscarComentariosPostNew(PostNew postnew) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            String sql = "SELECT p.*, c.* FROM comentario_postnew as c, postnew as p " +
                    "WHERE c.idPostNew = p.idPostNew AND c.idPostNew = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, postnew.getIdPostNew());
            rs = ps.executeQuery();
            List<ComentarioPostNew> list = new ArrayList<ComentarioPostNew>();
            while (rs.next()) {
                list.add(new ComentarioPostNew(rs.getInt("idComentario"), rs.getInt("idPostNew"), rs.getString("nome"), rs.getString("email"), rs.getString("comentario")));
            }
            return list;
        } catch (SQLException sqle) {
            throw new Exception("Erro: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public void adicionarTag(PostNew postNew, Tag tag) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        if (postNew == null || tag == null) throw new Exception("Faltando parâmetros para adicionar Tag.");
        try {
            //Verificar se tag existe para poder criar uma nova tag
            String sql = "SELECT * FROM realacao_postnew_tag WHERE nome = ?";
            conn = this.conn;
            ps = conn.prepareStatement(sql);
            ps.setString(1, tag.getNome());
            rs = ps.executeQuery();

            Tag tag_aux = null;
            if (rs.next()) {
                //Tag já existe
                tag_aux = new Tag(rs.getInt("idTag"), rs.getString("nome"));
            } else {
                //Tag não existe, então vou criar uma nova
                sql = "INSERT INTO tag SET nome = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, tag.getNome());
                ps.executeUpdate();
                //Buscando a tag inserida
                sql = "SELECT * FROM tag ORDER BY idTag DESC LIMIT 1";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                rs.first();
                tag_aux = new Tag(rs.getInt("idTag"), rs.getString("nome"));
            }

            //Adicionando relacao postnew com a tag
            sql = "INSERT INTO realacao_postnew_tag SET idPostnew = ?, idTag = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, postNew.getIdPostNew());
            ps.setInt(2, tag_aux.getIdTag());
            ps.executeUpdate();

        } catch(SQLException sqle) {
            throw new Exception("Erro: " + sqle.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

}

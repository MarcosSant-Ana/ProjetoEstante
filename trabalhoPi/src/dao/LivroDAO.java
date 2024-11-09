package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Livro;
import model.Usuario;

public class LivroDAO {
	private ConexaoBD conexao = new ConexaoBD();
	private Connection conn = conexao.getConnection();
	private int totalLivros;
	private String sql;

	public int getTotalLivros(String table) {
		sql = "SELECT COUNT(*) AS total FROM "+table ;
		PreparedStatement sentenca;
		try {
			sentenca = conn.prepareStatement(sql);
			ResultSet resultadoSentenca = sentenca.executeQuery();
			if(resultadoSentenca.next()) {
			this.totalLivros= resultadoSentenca.getInt("total");
			}else {
				this.totalLivros=0;
			}
				
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalLivros;
		
	}

	public boolean inserir(Livro livro, Usuario usuario,String table) throws SQLException {

		ArrayList<Livro> livros = consultar(usuario,table);
		int idComparar;
		int idLivro = existe(livro);
		if (idLivro < 0) {

			sql = "INSERT INTO livros (titulo,autor) VALUES (?,?)";

			// prepara a sentença com a consulta da string
			PreparedStatement sentenca = conn.prepareStatement(sql);

			// subtitui as interrograções da consulta, pelo valor específico
			sentenca.setString(1, livro.getTitulo());
			sentenca.setString(2, livro.getAutor());
			sentenca.execute(); // executa o comando no banco
			sentenca.close(); // fecha a sentença

			PreparedStatement sentenca1;

			sentenca1 = conn.prepareStatement("SELECT LAST_INSERT_ID() AS id");

			ResultSet rs = sentenca1.executeQuery();
			if(rs.next()) {
			idLivro = rs.getInt("id");
			}
			sentenca1.close();
			rs.close();
		} else {
			for (int i = 0; i < livros.size(); i++) {
				idComparar = livros.get(i).getId();
				if (idComparar == idLivro) {
					return false;
				}
			}
		}
		String status = "",parametro="";
		if(table.equals("livro_usuario")) {
			status =",status";
			parametro = ",?";
		}
		sql = "insert into "+table+"(idusuario,idLivros"+status+")"+" values(?,?"+parametro+"); ";
		PreparedStatement sentenca2 = conn.prepareStatement(sql);
		sentenca2.setString(1, usuario.getNome());
		sentenca2.setInt(2, idLivro);
		if(table.equals("livro_usuario"))
		sentenca2.setBoolean(3,livro.isStatus());
		sentenca2.execute();
		sentenca2.close();
		return true;

	}

	public void alterar(Livro livro, Usuario usuario, String table) {
		
		String sql = "UPDATE livros set titulo =?,autor= ? where id = ?";
		
		try {
			PreparedStatement sentenca = conn.prepareStatement(sql);
			sentenca.setString(1, livro.getTitulo());
			sentenca.setString(2, livro.getAutor());
			sentenca.setInt(3, livro.getId());
			sentenca.execute();
			sentenca.close();
			
			if(table.equals("livro_usuario")) {
			sql = "update livro_usuario set status= ? where idLivros = ? and idUsuario = ? ";
			PreparedStatement sentenca2 = conn.prepareStatement(sql);
			sentenca2.setBoolean(1,livro.isStatus());
			sentenca2.setInt(2, livro.getId());
			sentenca2.setString(3,usuario.getNome());
			sentenca2.execute();
			sentenca2.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		;

	}

	public void excluir(int id, Usuario usuario, String table) {
		sql = "DELETE FROM "+table+" WHERE idLivros = ? and idUsuario= ?";
		try {
			PreparedStatement sentenca = conn.prepareStatement(sql);
			sentenca.setInt(1, id);
			sentenca.setString(2,usuario.getNome());
			sentenca.execute();
			sentenca.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		;

	}

	public ArrayList<Livro> consultar(Usuario usuario,String table) throws SQLException {
		ArrayList<Livro> livros = new ArrayList<Livro>();
		String status="";
		if(table.equals("livro_usuario")){
			sql = "SELECT 	l.id ,l.titulo, l.autor,lu.status FROM usuario u "
					+ "JOIN livro_usuario lu ON u.nome = lu.idUsuario " + "JOIN livros l ON l.id = lu.idLivros "
					+ "WHERE u.nome = ? "
					+"ORDER BY l.titulo";
		}
		else
		sql = "SELECT 	l.id ,l.titulo, l.autor FROM usuario u "
				+ "JOIN livros_desejados ld ON u.nome = ld.idUsuario " + "JOIN livros l ON l.id = ld.idLivros "
				+ "WHERE u.nome = ? "
				+"ORDER BY l.titulo";
		PreparedStatement sentenca = conn.prepareStatement(sql);
		sentenca.setString(1, usuario.getNome());
		ResultSet resultadoSentenca = sentenca.executeQuery();
		while (resultadoSentenca.next()) {
			Livro livro = new Livro();
			livro.setId(resultadoSentenca.getInt("id"));
			livro.setTitulo(resultadoSentenca.getString("titulo"));
			livro.setAutor(resultadoSentenca.getString("autor"));
			if(table.equals("livro_usuario"))
			livro.setStatus(resultadoSentenca.getBoolean("status"));

			livros.add(livro);
		}
		resultadoSentenca.close();
		sentenca.close();
		return livros;
	}

	public int existe(Livro livroComparar) throws SQLException {
		ArrayList<Livro> livros = new ArrayList<Livro>();
		String titulo, autor;

		sql = "SELECT * FROM livros";
		PreparedStatement sentenca = conn.prepareStatement(sql);
		ResultSet resultadoSentenca = sentenca.executeQuery();
		while (resultadoSentenca.next()) {
			Livro livro = new Livro();
			livro.setId(resultadoSentenca.getInt("id"));
			livro.setTitulo(resultadoSentenca.getString("titulo"));
			livro.setAutor(resultadoSentenca.getString("autor"));

			livros.add(livro);
		}
		resultadoSentenca.close();
		sentenca.close();

		for (int i = 0; i < livros.size(); i++) {
			titulo = livros.get(i).getTitulo().replace(" ", "");
			autor = livros.get(i).getAutor().replace(" ", "");
			if (titulo.compareToIgnoreCase(livroComparar.getTitulo().replace(" ", "")) == 0
					&& autor.compareToIgnoreCase(livroComparar.getAutor().replace(" ", "")) == 0) {
				return livros.get(i).getId();
			}
		}
		return -1;
	}

}
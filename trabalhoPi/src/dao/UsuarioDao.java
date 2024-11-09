package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Usuario;

public class UsuarioDao  {
	// instancia a classe e ja abre uma conexao
	private ConexaoBD conexao = new ConexaoBD();
	// chama a conexao para poder executar os comandos 
	private Connection conn = conexao.getConnection();

	public void inserir(Usuario usuario) {
		String sql = "INSERT INTO Usuario (nome,senha) VALUES (?,?)";

		try {

			// prepara a sentença com a consulta da string
			PreparedStatement sentenca = conn.prepareStatement(sql);

			// subtitui as interrograções da consulta, pelo valor específico
			sentenca.setString(1, usuario.getNome());
			sentenca.setString(2, usuario.getSenha());
			sentenca.execute(); // executa o comando no banco
			sentenca.close(); // fecha a sentença

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

	}

	
	public void alterarSenha(Usuario usuario,String novaSenha) {
		String sql = "UPDATE usuario senha= ? where nome = ?";
		try {
			PreparedStatement sentenca = conn.prepareStatement(sql);
			sentenca.setString(1, novaSenha);
			sentenca.setString(2, usuario.getNome());
		}catch(SQLException e){
			throw new RuntimeException(e);
		};

	}

	
	public ArrayList<Usuario> consultar()throws SQLException {

		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		String sql = "SELECT * FROM usuario ORDER BY nome";
		
			PreparedStatement sentenca = conn.prepareStatement(sql);
			// recebe o resultado da consulta
			ResultSet resultadoSentenca = sentenca.executeQuery();
			// percorrer cada linha do resultado
			while (resultadoSentenca.next()) {
				// resgata o valor de cada linha, selecionando pelo nome de cada coluna da tabela
				Usuario usuario = new Usuario();
				usuario.setNome(resultadoSentenca.getString("nome"));
				usuario.setSenha(resultadoSentenca.getString("senha"));
				// adiciona ao array
				usuarios.add(usuario);
			}
			resultadoSentenca.close();
			sentenca.close();
			return usuarios;
		
	}

}

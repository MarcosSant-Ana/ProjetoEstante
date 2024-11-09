package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
	private String servidor;
	private String banco;
	private String usuario;
	private String senha;
	private Connection conexao;

	// da as info para a conxao
	public ConexaoBD() {
		this.servidor = "localhost";
		this.banco = "estantevirtual";
		this.usuario = "root";
		this.senha = "1christyan";
		//inicia sempre que instanciado
		conectar();
	}

	//Conecta com o banco
	public void conectar() {
		try {
			this.conexao = DriverManager.getConnection("jdbc:mysql://" + this.servidor + ":3306/" + this.banco, this.usuario,this.senha);
			
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao conectar ao banco de dados: " + ex.getMessage(), ex);
		}
	}

	// cria um get para não ter que repetir o codigo td vez q for fazer uma operação no Banco 
	//e retorna uma conexão ativa para que possa dar comandos no banco de dados 
	public Connection getConnection() {
		if (conexao == null) {
			throw new IllegalStateException("Conexão não estabelecida. Chame o método conectar() primeiro.");
		}
		return conexao;
	}
	
	//fecha a conexão
	public void fechar() {
		if (conexao != null) {
			try {
				conexao.close();
			} catch (SQLException ex) {
				throw new RuntimeException("Erro ao fechar a conexão: " + ex.getMessage(), ex);
			}
		}
	}
}

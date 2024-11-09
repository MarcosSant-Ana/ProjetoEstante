package model;

import java.sql.Date;

public class Livro {
	private int id;
	private String titulo,autor;
	private Date data;
	private boolean status;
	
	public Livro(int id,String titulo, String autor,boolean status) {
		this.id=id;
		this.titulo = titulo;
		this.autor=autor;
		this.status= status;
	}
	
	public Livro() {
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", data=" + data + ", status=" + status
				+ "]";
	}

	
}

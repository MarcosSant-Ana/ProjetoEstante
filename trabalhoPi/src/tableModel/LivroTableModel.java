package tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Livro;

public class LivroTableModel extends AbstractTableModel {
	private List<Livro> lista;
	private String[] cabecalho = { "ID ","Titulo", "Autor", "Status" },cabecalho2={"ID","Titulo","Autor"};
	private String table;

	public LivroTableModel(List<Livro> livros,String table) {
		this.lista = livros;
		this.table=table;
	}

	public void setLista(List<Livro> lista) {
		this.lista = lista;
	}

	@Override
	public int getRowCount() {
		return this.lista.size();
	}

	@Override
	public int getColumnCount() {
		if(table.equals("livro_usuario"))
		return cabecalho.length;
		return cabecalho2.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Livro livro = lista.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return livro.getId();
		case 1:
			return livro.getTitulo();
		case 2:
			return livro.getAutor();
		case 3:
			
			if(livro.isStatus())
			return "Lido" ;
			else
				return "Não Lido";
		default:
			return null;
		}
	}
	
	@Override
	public String getColumnName(int column) {
		if(table.equals("livro_usuario"))
		return cabecalho[column];
		return cabecalho2[column];
	}
	
}
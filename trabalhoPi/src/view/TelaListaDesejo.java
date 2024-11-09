package view;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dao.LivroDAO;
import model.Livro;
import model.Usuario;
import tableModel.LivroTableModel;

public class TelaListaDesejo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfTitulo;
	private JTextField tfAutor;
	private JTextField tfTotalLivro;
	private LivroDAO livroDao;
	private List<Livro> livros;
	private LivroTableModel tableModel;
	private JTable tbListaDesejo;
	private String table = "livros_desejados";
	private Livro livro;
	private static int idLivro;

	
	public TelaListaDesejo(Usuario usuario) {
		livroDao = new LivroDAO();

		try {
			livros = livroDao.consultar(usuario, table);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tableModel = new LivroTableModel(livros, table);

		setTitle("Estante Virtual");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1011, 568);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		setLocationRelativeTo(null);

		JLabel lblNewLabel = new JLabel("LISTA DE DESEJOS");
		lblNewLabel.setFont(new Font("Perpetua Titling MT", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 24, 975, 37);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("TÍTULO");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(55, 104, 77, 24);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("AUTOR");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(55, 167, 70, 24);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_3 = new JLabel("TOTAL DE LIVROS");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_3.setBounds(543, 104, 170, 24);
		contentPane.add(lblNewLabel_1_3);

		tfTitulo = new JTextField();
		tfTitulo.setBounds(128, 104, 350, 24);
		contentPane.add(tfTitulo);
		tfTitulo.setColumns(10);

		tfAutor = new JTextField();
		tfAutor.setColumns(10);
		tfAutor.setBounds(128, 167, 350, 24);
		contentPane.add(tfAutor);

		tfTotalLivro = new JTextField();
		tfTotalLivro.setHorizontalAlignment(SwingConstants.CENTER);
		tfTotalLivro.setDisabledTextColor(new Color(0, 0, 0));
		tfTotalLivro.setForeground(new Color(0, 0, 0));
		tfTotalLivro.setColumns(10);
		tfTotalLivro.setEnabled(false);
		tfTotalLivro.setText("" + livroDao.getTotalLivros(table));
		tfTotalLivro.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tfTotalLivro.setBounds(723, 104, 137, 24);
		contentPane.add(tfTotalLivro);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String titulo = tfTitulo.getText().trim(), autor = tfAutor.getText().trim();
				if (tfTitulo.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite um titulo");
				} else if (tfAutor.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite o Autor");
				} else {

					Livro livro = new Livro(idLivro, titulo, autor, false);
					try {
						if (livro.getId() == 0) {
							if (livroDao.inserir(livro, usuario, table))
								JOptionPane.showMessageDialog(null, "Livro Adicionado a sua Lista de Desejos");
							else
								JOptionPane.showMessageDialog(null, "O livro já esta na Lista de Desejos");
						} else {
							livroDao.alterar(livro, usuario, table);
							JOptionPane.showMessageDialog(null, "Livro Atualizado");
						}
						livros = livroDao.consultar(usuario, table);
						tableModel.setLista(livros);
						tableModel.fireTableDataChanged();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					tfTotalLivro.setText("" + livroDao.getTotalLivros(table));

					limpar();

				}

			}

		});
		btnSalvar.setBounds(93, 223, 123, 38);
		contentPane.add(btnSalvar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(TelaListaDesejo.this,
						"Deseja excluir o livro " + tfTitulo.getText()) == JOptionPane.YES_OPTION) {

					livroDao.excluir(idLivro, usuario, table);
					limpar();
				}
				try {
					livros = livroDao.consultar(usuario, table);
					tableModel.setLista(livros);
					tableModel.fireTableDataChanged();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnExcluir.setBounds(330, 223, 123, 38);
		contentPane.add(btnExcluir);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				limpar();

			}
		});
		btnLimpar.setBounds(568, 223, 123, 38);
		contentPane.add(btnLimpar);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				TelaEscolha tela = new TelaEscolha(usuario);
				tela.setVisible(true);
				dispose();
			}

		});
		btnVoltar.setBounds(812, 223, 123, 38);
		contentPane.add(btnVoltar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 289, 975, 229);
		contentPane.add(scrollPane);

		tbListaDesejo = new JTable(tableModel);
		tbListaDesejo.setToolTipText("Selecione um item para alterar ou excluir");
		tbListaDesejo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbListaDesejo.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int linha = tbListaDesejo.getSelectedRow();
				if (linha >= 0) {
					livro = livros.get(linha);
					TelaListaDesejo.idLivro = livro.getId();
					tfTitulo.setText("" + livro.getTitulo());
					tfAutor.setText("" + livro.getAutor());

				}

			}
		});
		scrollPane.setViewportView(tbListaDesejo);

		JButton btnMover = new JButton("Mover para Estante");
		btnMover.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String titulo = tfTitulo.getText().trim(), autor = tfAutor.getText().trim();
				try {
					if (tfTitulo.getText().trim().isEmpty() && tfAutor.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Selecione um livro");	
					} else {
						if (JOptionPane.showConfirmDialog(TelaListaDesejo.this, "Deseja Mover o livro "
								+ tfTitulo.getText() + " Para sua estante ?") == JOptionPane.YES_OPTION) {
							Livro livro = new Livro(idLivro, titulo, autor, false);
							livroDao.excluir(idLivro, usuario, table);
							livroDao.inserir(livro, usuario, "livro_usuario");
							limpar();
						}
						livros = livroDao.consultar(usuario, table);
						tableModel.setLista(livros);
						tableModel.fireTableDataChanged();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

		});
		btnMover.setBounds(591, 160, 205, 38);
		contentPane.add(btnMover);
		tableModel.setLista(livros);
		tableModel.fireTableDataChanged();
	}

	public void limpar() {
		tfTitulo.setText("");
		tfAutor.setText("");
		idLivro = 0;
		tfTotalLivro.setText("" + livroDao.getTotalLivros(table));
		tfTitulo.requestFocus();
	}

}

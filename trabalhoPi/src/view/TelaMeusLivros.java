package view;

import java.awt.Font;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import dao.LivroDAO;
import model.Livro;
import model.Usuario;
import tableModel.LivroTableModel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class TelaMeusLivros extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfTitulo;
	private JTextField tfAutor;
	private JTextField tfTotalLivro;
	private JTable tbMeusLivros;
	private LivroDAO livroDAO = new LivroDAO();
	private Livro livro;
	private List<Livro> livros;
	private LivroTableModel tableModel;
	private JRadioButton lido;
	private JRadioButton naoLido;
	private static int idLivro;
	private String table = "livro_usuario";

	public TelaMeusLivros(Usuario usuario) {

		LivroDAO livroDao = new LivroDAO();
		try {
			livros = livroDao.consultar(usuario,table);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tableModel = new LivroTableModel(livros,table);

		setTitle("Estante Virtual");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1011, 568);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		setLocationRelativeTo(null);

		JLabel lblNewLabel = new JLabel("MEUS LIVROS");
		lblNewLabel.setFont(new Font("Perpetua Titling MT", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 22, 995, 47);
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

		JLabel lblNewLabel_1_2 = new JLabel("STATUS");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_2.setBounds(542, 104, 95, 24);
		contentPane.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_3 = new JLabel("TOTAL DE LIVROS");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_3.setBounds(542, 167, 170, 24);
		contentPane.add(lblNewLabel_1_3);

		tfTitulo = new JTextField();
		tfTitulo.setBounds(128, 104, 350, 24);
		contentPane.add(tfTitulo);
		tfTitulo.setColumns(10);

		tfAutor = new JTextField();
		tfAutor.setColumns(10);
		tfAutor.setBounds(128, 167, 350, 24);
		contentPane.add(tfAutor);

		lido = new JRadioButton("LIDO");
		lido.setHorizontalAlignment(SwingConstants.LEFT);
		lido.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lido.setBounds(641, 104, 77, 24);
		contentPane.add(lido);

		naoLido = new JRadioButton("NÃO LIDO");
		naoLido.setHorizontalAlignment(SwingConstants.LEFT);
		naoLido.setFont(new Font("Tahoma", Font.PLAIN, 20));
		naoLido.setBounds(730, 104, 123, 24);
		contentPane.add(naoLido);

		ButtonGroup grupo = new ButtonGroup();
		grupo.add(lido);
		grupo.add(naoLido);

		tfTotalLivro = new JTextField();
		tfTotalLivro.setHorizontalAlignment(SwingConstants.CENTER);
		tfTotalLivro.setDisabledTextColor(new Color(0, 0, 0));
		tfTotalLivro.setForeground(new Color(0, 0, 0));
		tfTotalLivro.setColumns(10);
		tfTotalLivro.setEnabled(false);
		tfTotalLivro.setText("" + livroDAO.getTotalLivros(table));
		tfTotalLivro.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tfTotalLivro.setBounds(716, 167, 137, 24);
		contentPane.add(tfTotalLivro);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 289, 975, 229);
		contentPane.add(scrollPane);

		tbMeusLivros = new JTable(tableModel);
		tbMeusLivros.setToolTipText("Selecione um item para alterar ou excluir");
		tbMeusLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbMeusLivros.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int linha = tbMeusLivros.getSelectedRow();
				if (linha >= 0) {
					livro = livros.get(linha);
					TelaMeusLivros.idLivro = livro.getId();
					tfTitulo.setText("" + livro.getTitulo());
					tfAutor.setText("" + livro.getAutor());
					if (livro.isStatus()) {
						lido.setSelected(true);
						naoLido.setSelected(false);
					} else {
						lido.setSelected(false);
						naoLido.setSelected(true);
					}
				}

			}
		});
		scrollPane.setViewportView(tbMeusLivros);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String titulo = tfTitulo.getText().trim(), autor = tfAutor.getText().trim();
				boolean status;
				if (tfTitulo.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite um titulo");
				} else if (tfAutor.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite o Autor");
				} else if (!(lido.isSelected() || naoLido.isSelected())) {
					JOptionPane.showMessageDialog(null, "Selecione um Status");

				} else {
					if (lido.isSelected()) {
						status = true;
						lido.setSelected(false);
					} else {
						status = false;
						naoLido.setSelected(false);
					}

					Livro livro = new Livro(idLivro, titulo, autor, status);
					try {
						if (livro.getId() == 0) {
							if (livroDAO.inserir(livro, usuario,table))
								JOptionPane.showMessageDialog(null, "Livro Adicionado a sua estante");
							else
								JOptionPane.showMessageDialog(null, "O livro já esta na estante");
						} else {
							livroDAO.alterar(livro, usuario,table);
							JOptionPane.showMessageDialog(null, "Livro Atualizado");
						}
						livros = livroDAO.consultar(usuario,table);
						tableModel.setLista(livros);
						tableModel.fireTableDataChanged();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					tfTotalLivro.setText("" + livroDAO.getTotalLivros(table));

					limpar();
					

				}

			}

			
		});
		btnSalvar.setBounds(93, 223, 123, 38);
		contentPane.add(btnSalvar);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				limpar();

			}

		});
		btnLimpar.setBounds(568, 223, 123, 38);
		contentPane.add(btnLimpar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(TelaMeusLivros.this, "Deseja excluir o livro "+tfTitulo.getText())==JOptionPane.YES_OPTION) {
					
					livroDAO.excluir(idLivro, usuario,table);
					limpar();
				}
				try {
					livros = livroDAO.consultar(usuario,table);
					tableModel.setLista(livros);
					tableModel.fireTableDataChanged();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnExcluir.setBounds(330, 223, 123, 38);
		contentPane.add(btnExcluir);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				TelaEscolha tela = new TelaEscolha(usuario);
				tela.setVisible(true);
				dispose();
			}

		});
		btnVoltar.setBounds(774, 223, 123, 38);
		contentPane.add(btnVoltar);
	}

	private void limpar() {
		tfTitulo.setText("");
		tfAutor.setText("");
		lido.setSelected(false);
		naoLido.setSelected(true);
		idLivro = 0;
		tfTotalLivro.setText("" + livroDAO.getTotalLivros(table));
		tfTitulo.requestFocus();
	}
	
}

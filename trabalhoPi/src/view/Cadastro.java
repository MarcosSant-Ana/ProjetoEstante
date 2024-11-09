package view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.UsuarioDao;
import model.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Cadastro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfUsuario;
	JPasswordField tfSenha;

	/**
	 * Create the frame.
	 */
	public Cadastro() {
		setTitle("Estante Virtual");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1011, 568);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblNewLabel = new JLabel("CADASTRO");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Perpetua Titling MT", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 47, 975, 56);
		contentPane.add(lblNewLabel);

		JLabel lblUsuario = new JLabel("USUÁRIO");
		lblUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Perpetua Titling MT", Font.BOLD, 20));
		lblUsuario.setBounds(341, 133, 141, 56);
		contentPane.add(lblUsuario);

		tfUsuario = new JTextField();
		tfUsuario.setBounds(341, 187, 320, 34);
		contentPane.add(tfUsuario);
		tfUsuario.setColumns(10);

		JLabel lblSenha = new JLabel("SENHA");
		lblSenha.setHorizontalAlignment(SwingConstants.LEFT);
		lblSenha.setForeground(Color.WHITE);
		lblSenha.setFont(new Font("Perpetua Titling MT", Font.BOLD, 20));
		lblSenha.setBounds(341, 241, 141, 56);
		contentPane.add(lblSenha);

		tfSenha = new JPasswordField();
		tfSenha.setColumns(10);
		tfSenha.setBounds(341, 294, 320, 34);
		contentPane.add(tfSenha);

		JButton btnCadastrar = new JButton("CADASTRAR");
		btnCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nome=tfUsuario.getText().trim(),senha=tfSenha.getText().trim();
				if (nome.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite um Usuario");
				} else if (senha.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite uma Senha");
				} else {
					boolean existe = false;
					UsuarioDao usuarioDao = new UsuarioDao();
					ArrayList<Usuario> lista= null;
					try {
						lista = usuarioDao.consultar();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					for (Usuario u : lista) {
		                if (u.getNome().equalsIgnoreCase(nome)) {
		                    existe = true;
		                    break;
		                }
					}
					if (!existe) {
						Usuario usuario = new Usuario();
						usuario.setNome(nome);
						usuario.setSenha(senha);
						usuarioDao.inserir(usuario);
						JOptionPane.showMessageDialog(null, "Usuário Cadastrado");
						Login voltar = new Login();
						voltar.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Usuario já existe");
					}
				}
			}

		});
		btnCadastrar.setFont(new Font("Perpetua Titling MT", Font.PLAIN, 10));
		btnCadastrar.setBounds(357, 358, 101, 43);
		contentPane.add(btnCadastrar);

		JButton btnVoltar = new JButton("VOLTAR");
		btnVoltar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Login voltar = new Login();
				voltar.setVisible(true);
				dispose();
			}
		});
		btnVoltar.setFont(new Font("Perpetua Titling MT", Font.PLAIN, 10));
		btnVoltar.setBounds(543, 358, 101, 43);
		contentPane.add(btnVoltar);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/imagens/fundoTela.jpg")));
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(Color.RED);
		lblNewLabel_1.setBounds(0, 0, 999, 531);
		contentPane.add(lblNewLabel_1);

	}

}

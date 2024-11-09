package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import dao.UsuarioDao;
import model.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfUsuario;
	private JPasswordField tfSenha;

	// unico main para sempre que rodar a aplicação entrar na tela login
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Login() {
		
		setTitle("Estante Virtual");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1011, 568);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LOGIN");
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
		
		JButton btnEntrar = new JButton("ENTRAR");
		btnEntrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nome = tfUsuario.getText().trim(),senha = tfSenha.getText().trim(),senhaCerta="";
				if (nome.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite um Usuario");
				} else if (senha.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite uma Senha");
				} else {
					boolean existe = false;
					UsuarioDao usuarioDao = new UsuarioDao();
					ArrayList<Usuario> lista = null;
					try {
						lista = usuarioDao.consultar();
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					for (Usuario usuarioComparador : lista) {
		                if (usuarioComparador.getNome().equalsIgnoreCase(nome)) {
		                	senhaCerta = usuarioComparador.getSenha();
		                    existe = true;
		                    break;
		                }
					}
					if(existe && senha.equals(senhaCerta)) {
						Usuario usuario = new Usuario(nome,senha);
						TelaEscolha tela = new TelaEscolha(usuario);
						tela.setVisible(true);
						dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Informações de login estão incorretas");
					}
				}
			}
		});
		btnEntrar.setFont(new Font("Perpetua Titling MT", Font.PLAIN, 10));
		btnEntrar.setBounds(357, 358, 101, 43);
		contentPane.add(btnEntrar);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Cadastro cadastro = new Cadastro();
                cadastro.setVisible(true);
                dispose();	
			}			
		});
		btnCadastrar.setFont(new Font("Perpetua Titling MT", Font.PLAIN, 10));
		btnCadastrar.setBounds(543, 358, 101, 43);
		contentPane.add(btnCadastrar);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/imagens/fundoTela.jpg")));
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(Color.RED);
		lblNewLabel_1.setBounds(0, 0, 999, 531);
		contentPane.add(lblNewLabel_1);
	}
}

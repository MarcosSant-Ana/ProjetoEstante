package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import dao.LivroDAO;
import dao.UsuarioDao;
import model.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;

public class TelaMeuPerfil extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfTotalLivrosObtidos;
	private JTextField tfTotalLivrosDesejados;
	private JPasswordField tfNovaSenha, tfConfirmaNovaSenha;
	private UsuarioDao usuarioDao;
	private LivroDAO livroDao;

	
	public TelaMeuPerfil(Usuario usuario) {
		usuarioDao = new UsuarioDao();
		livroDao = new LivroDAO();
		
		setTitle("Estante Virtual");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1011, 568);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		
		JLabel lblUsuario = new JLabel(usuario.getNome());
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBounds(339, 40, 320, 28);
		getContentPane().add(lblUsuario);
		
		JLabel lblTotalDeLivros = new JLabel("Total de livros obtidos :");
		lblTotalDeLivros.setForeground(Color.WHITE);;
		lblTotalDeLivros.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotalDeLivros.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTotalDeLivros.setBounds(339, 90, 219, 28);
		getContentPane().add(lblTotalDeLivros);
		
		JLabel lblTotalLivrosDesejados = new JLabel("Total livros desejados :");
		lblTotalLivrosDesejados.setForeground(new Color(255, 255, 255));
		lblTotalLivrosDesejados.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotalLivrosDesejados.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTotalLivrosDesejados.setBounds(339, 129, 227, 28);
		getContentPane().add(lblTotalLivrosDesejados);
		
		JLabel lblTotalDeLivros_1 = new JLabel("Nova senha:");
		lblTotalDeLivros_1.setForeground(new Color(255, 255, 255));
		lblTotalDeLivros_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotalDeLivros_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTotalDeLivros_1.setBounds(339, 168, 111, 28);
		getContentPane().add(lblTotalDeLivros_1);
		
		JLabel lblTotalDeLivros_1_1 = new JLabel("Confirme a nova senha:");
		lblTotalDeLivros_1_1.setForeground(new Color(255, 255, 255));
		lblTotalDeLivros_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotalDeLivros_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTotalDeLivros_1_1.setBounds(339, 246, 219, 28);
		getContentPane().add(lblTotalDeLivros_1_1);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String novaSenha=tfNovaSenha.getText(),confirmaNovaSenha = tfConfirmaNovaSenha.getText();
				if(novaSenha.equals(confirmaNovaSenha)) {
					usuarioDao.alterarSenha(usuario, novaSenha);
					JOptionPane.showMessageDialog(TelaMeuPerfil.this, "Senha alterada com sucesso");
					limpar();
				}else {
					JOptionPane.showMessageDialog(TelaMeuPerfil.this, "As senhas não são iguais");
					limpar();
				}
			}
		});
		btnAlterar.setBounds(341, 354, 89, 34);
		getContentPane().add(btnAlterar);
		
		JButton btnLimpar = new JButton("Limpar");
		btnAlterar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				limpar();
				
			}
		});
		btnLimpar.setBounds(455, 354, 89, 34);
		getContentPane().add(btnLimpar);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaEscolha tela = new TelaEscolha(usuario);
				tela.setVisible(true);
				dispose();
				
			}
		});
		btnVoltar.setBounds(570, 354, 89, 34);
		getContentPane().add(btnVoltar);
		
		tfTotalLivrosObtidos = new JTextField();
		tfTotalLivrosObtidos.setDisabledTextColor(new Color(0, 0, 0));
		tfTotalLivrosObtidos.setHorizontalAlignment(SwingConstants.CENTER);
		tfTotalLivrosObtidos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tfTotalLivrosObtidos.setEnabled(false);
		tfTotalLivrosObtidos.setBounds(561, 90, 98, 28);
		tfTotalLivrosObtidos.setColumns(10);
		tfTotalLivrosObtidos.setText("" + livroDao.getTotalLivros("livro_usuario"));
		getContentPane().add(tfTotalLivrosObtidos);
		
		
		tfTotalLivrosDesejados = new JTextField();
		tfTotalLivrosDesejados.setDisabledTextColor(new Color(0, 0, 0));
		tfTotalLivrosDesejados.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tfTotalLivrosDesejados.setHorizontalAlignment(SwingConstants.CENTER);
		tfTotalLivrosDesejados.setEnabled(false);
		tfTotalLivrosDesejados.setColumns(10);
		tfTotalLivrosDesejados.setBounds(561, 133, 98, 28);
		tfTotalLivrosDesejados.setText("" + livroDao.getTotalLivros("livros_desejados"));
		getContentPane().add(tfTotalLivrosDesejados);
		
		tfNovaSenha = new JPasswordField();
		tfNovaSenha.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tfNovaSenha.setColumns(10);
		tfNovaSenha.setBounds(339, 207, 320, 28);
		getContentPane().add(tfNovaSenha);
		
		tfConfirmaNovaSenha = new JPasswordField();
		tfConfirmaNovaSenha.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tfConfirmaNovaSenha.setColumns(10);
		tfConfirmaNovaSenha.setBounds(339, 285, 320, 28);
		getContentPane().add(tfConfirmaNovaSenha);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(TelaMeuPerfil.class.getResource("/imagens/fundoTela.jpg")));
		lblNewLabel.setBounds(0, 0, 995, 529);
		getContentPane().add(lblNewLabel);
		
		
	}
	public void limpar() {
		tfNovaSenha.setText("");
		tfConfirmaNovaSenha.setText("");
	}
}

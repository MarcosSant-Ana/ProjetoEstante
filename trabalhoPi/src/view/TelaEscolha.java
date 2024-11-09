package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Usuario;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class TelaEscolha extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	public TelaEscolha(Usuario usuario) {
		setTitle("Estante Virtual");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1011, 568);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setLocationRelativeTo(null);
		
		JButton btnMeusLivros = new JButton("MEUS LIVROS");
		btnMeusLivros.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TelaMeusLivros tela = new TelaMeusLivros(usuario);
				tela.setVisible(true);
				dispose();
				
			}
			
		});;
		btnMeusLivros.setBounds(338, 99, 324, 39);
		contentPane.add(btnMeusLivros);
		
		JButton btnListaDesejos = new JButton("LISTA DE DESEJOS");
		btnListaDesejos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TelaListaDesejo tela = new TelaListaDesejo(usuario);
				tela.setVisible(true);
				dispose();
				
			}
			
		});;
		btnListaDesejos.setBounds(338, 206, 324, 39);
		contentPane.add(btnListaDesejos);
		
		JButton btnMeuPerfil = new JButton("MEU PERFIL");
		btnMeuPerfil.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TelaMeuPerfil tela = new TelaMeuPerfil(usuario);
				tela.setVisible(true);
				dispose();
				
			}
			
		});;
		btnMeuPerfil.setBounds(338, 313, 324, 39);
		contentPane.add(btnMeuPerfil);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(TelaEscolha.class.getResource("/imagens/fundoTela.jpg")));
		lblNewLabel.setBounds(0, 0, 995, 529);
		contentPane.add(lblNewLabel);
	}
}

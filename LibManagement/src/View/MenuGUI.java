package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class MenuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuGUI frame = new MenuGUI();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuGUI() {
		setTitle("LibManagement");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 846, 546);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnBotoes = new JPanel();
		pnBotoes.setBounds(0, 0, 830, 44);
		contentPane.add(pnBotoes);
		pnBotoes.setLayout(null);
		
		final JPanel pnPrincipal = new JPanel();
		pnPrincipal.setBounds(0, 44, 830, 463);
		contentPane.add(pnPrincipal);
		
		JButton btnAutor = new JButton("Autores");
		btnAutor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AutorMainGUI telaAutor = new AutorMainGUI();
				telaAutor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				telaAutor.setVisible(true);
				centralizarJanela(telaAutor, contentPane);
			}
		});
		btnAutor.setBounds(10, 11, 89, 23);
		pnBotoes.add(btnAutor);
		
		JButton btnEditora = new JButton("Editoras");
		btnEditora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditoraMainGUI telaEditora = new EditoraMainGUI();
				telaEditora.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				telaEditora.setVisible(true);
				centralizarJanela(telaEditora, contentPane);
			}
		});
		btnEditora.setBounds(115, 11, 89, 23);
		pnBotoes.add(btnEditora);
		
		JButton btnLivro = new JButton("Livros");
		btnLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LivroMainGUI telaLivro = new LivroMainGUI();
				telaLivro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				telaLivro.setVisible(true);
				centralizarJanela(telaLivro, contentPane);
			}
		});
		btnLivro.setBounds(220, 11, 89, 23);
		pnBotoes.add(btnLivro);	
		
		JButton btnAmigo = new JButton("Amigos");
		btnAmigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AmigoMainGUI telaAmigo = new AmigoMainGUI();
				telaAmigo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				telaAmigo.setVisible(true);
				centralizarJanela(telaAmigo, contentPane);
			}
		});
		btnAmigo.setBounds(325, 11, 89, 23);
		pnBotoes.add(btnAmigo);
	}
	
	public static void centralizarJanela(JFrame janela, JPanel painelPrincipal) {
	    int x = (painelPrincipal.getWidth() - janela.getWidth()) / 2;
	    int y = (painelPrincipal.getHeight() - janela.getHeight()) / 2;
	    janela.setLocation(x, y);
	}
}

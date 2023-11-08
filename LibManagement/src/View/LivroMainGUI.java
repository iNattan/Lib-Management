package View;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controller.AutorController;
import Controller.EditoraController;
import Controller.LivroController;
import Model.AutorBEAN;
import Model.EditoraBEAN;
import Model.LivroBEAN;
import javax.swing.JCheckBox;

public class LivroMainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableLivros;
	private JTextField txtFiltro;
	private JButton btnPesquisar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LivroMainGUI frame = new LivroMainGUI();
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
	public LivroMainGUI() {
		setTitle("Livros");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JComboBox comboBoxFiltro = new JComboBox();
		comboBoxFiltro.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBoxFiltro.setModel(new DefaultComboBoxModel(new String[] {"ID", "Nome", "Autor", "Editora"}));
		comboBoxFiltro.setSelectedIndex(1);
		comboBoxFiltro.setBounds(10, 449, 104, 23);
		contentPane.add(comboBoxFiltro);
		
		final JButton btnRestaurar = new JButton("Restaurar");
		btnRestaurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableLivros.getSelectedRow();
				if (linhaSelecionada >= 0) {
					int idRegistroEmAtivacao = (Integer) tableLivros.getValueAt(linhaSelecionada, 0);
					LivroController livroController = new LivroController();
					LivroBEAN livroAtivar = livroController.buscaLivro(idRegistroEmAtivacao, 1);
					livroController.activeLivro(livroAtivar);
			    	btnPesquisar.doClick();
				}
			}
		});
		btnRestaurar.setBounds(670, 479, 104, 23);
		contentPane.add(btnRestaurar);
		btnRestaurar.setVisible(false);	
		
		final JCheckBox chkExcluidos = new JCheckBox("Excluídos");
		chkExcluidos.setBounds(685, 449, 89, 23);
		contentPane.add(chkExcluidos);		
		chkExcluidos.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {		     
		        if (chkExcluidos.isSelected()) 
		        	btnRestaurar.setVisible(true);
		        else 
		        	btnRestaurar.setVisible(false);		        
		    }
		});	
		
		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				String filtro = txtFiltro.getText();
		        String opcaoFiltro = comboBoxFiltro.getSelectedItem().toString();
		        int situacao;
		       	if (!chkExcluidos.isSelected()) 
		       		situacao = 0;
		       	else
		       		situacao = 1;		        
		       	LivroController livroController = new LivroController();		  
		        ArrayList<LivroBEAN> resultados = new ArrayList<LivroBEAN>();	      
		        if (filtro.isEmpty()) {
		        	resultados = livroController.listaLivro(situacao);
		        } else {
		        	if ("ID".equals(opcaoFiltro)) {
		                resultados = livroController.listaLivroByID(Integer.parseInt(filtro), situacao); 
		            } else if ("Nome".equals(opcaoFiltro)) {
		                resultados = livroController.listaLivroByNome(filtro, situacao);
		            } else if ("Autor".equals(opcaoFiltro)) {
		                resultados = livroController.listaLivroByAutor(filtro, situacao);
		            } else if ("Editora".equals(opcaoFiltro)) {
		            	resultados = livroController.listaLivroByEditora(filtro, situacao);
		            }
		        }	
		        DefaultTableModel tabela = (DefaultTableModel) tableLivros.getModel();
		        tabela.setRowCount(0);
		        AutorController autorController = new AutorController();
		        EditoraController editoraController = new EditoraController();
		        for (LivroBEAN livro : resultados) {		        	
		        	AutorBEAN autor = autorController.buscaAutor(livro.getIdAutor());
		        	EditoraBEAN editora = editoraController.buscaEditora(livro.getIdEditora());
		        	
		        	tabela.addRow(new Object[] { livro.getIdLivros(), livro.getNome(), autor.getNome(), editora.getNome(), livro.getStatus()});
		        }
			}
		});
		btnPesquisar.setBounds(120, 511, 109, 23);
		contentPane.add(btnPesquisar);	

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 64, 764, 358);
		contentPane.add(scrollPane);
		
		tableLivros =new JTable();
		tableLivros.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "Autor", "Editora", "Status"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableLivros.setDefaultEditor(Object.class, null);
		tableLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableLivros);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LivroCadastroGUI telaCadastro = new LivroCadastroGUI();
				telaCadastro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				telaCadastro.setVisible(true);
				centralizarJanela(telaCadastro, contentPane);
			}
		}); 
		btnAdicionar.setBounds(10, 22, 89, 23);
		contentPane.add(btnAdicionar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if (chkExcluidos.isSelected()) 
					JOptionPane.showMessageDialog(null, "Não é possível editar um registro excluído.", "Erro", JOptionPane.ERROR_MESSAGE);
				else {
					int linhaSelecionada = tableLivros.getSelectedRow();
				    if (linhaSelecionada >= 0) {			            
				    	int idRegistroEmEdicao = (Integer) tableLivros.getValueAt(linhaSelecionada, 0);
				    	LivroController livroController = new LivroController();
				    	LivroBEAN livroEditar = livroController.buscaLivro(idRegistroEmEdicao, 0);
				    	LivroCadastroGUI telaCadastro = new LivroCadastroGUI(livroEditar);
				        telaCadastro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				        telaCadastro.setVisible(true);
				        centralizarJanela(telaCadastro, contentPane);
				    }
				}				
			}
		});
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEditar.setBounds(120, 22, 89, 23);
		contentPane.add(btnEditar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkExcluidos.isSelected()) 
					JOptionPane.showMessageDialog(null, "Não é possível excluir um registro excluído.", "Erro", JOptionPane.ERROR_MESSAGE);
				else {
					int linhaSelecionada = tableLivros.getSelectedRow();
					if (linhaSelecionada >= 0) {
						int idRegistroEmExclusao = (Integer) tableLivros.getValueAt(linhaSelecionada, 0);
						LivroController livroController = new LivroController();
						LivroBEAN livroExcluir = livroController.buscaLivro(idRegistroEmExclusao, 0);
						livroController.inativeLivro(livroExcluir);
				    	btnPesquisar.doClick();
					}	
				}				
			}
		});
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnExcluir.setBounds(230, 22, 89, 23);
		contentPane.add(btnExcluir);		
		
		JLabel lblNewLabel = new JLabel("Opção de Filtro");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 433, 104, 14);
		contentPane.add(lblNewLabel);			
		
		txtFiltro = new JTextField();
		txtFiltro.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtFiltro.setBounds(10, 480, 219, 20);
		contentPane.add(txtFiltro);
		txtFiltro.setColumns(10);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtFiltro.setText("");
			}
		});
		btnLimpar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnLimpar.setBounds(10, 511, 89, 23);
		contentPane.add(btnLimpar);					
	}
	
	public static void centralizarJanela(JFrame janela, JPanel painelPrincipal) {
	    int x = (painelPrincipal.getWidth() - janela.getWidth()) / 2;
	    int y = (painelPrincipal.getHeight() - janela.getHeight()) / 2;
	    janela.setLocation(x, y);
	}	
}
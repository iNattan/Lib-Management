package View;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.lang.ModuleLayer.Controller;
import java.time.LocalDate;
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

import Controller.AmigoController;
import Controller.EditoraController;
import Controller.EmprestimoController;
import Controller.LivroController;
import Model.AmigoBEAN;
import Model.EditoraBEAN;
import Model.EmprestimoBEAN;

import javax.swing.JCheckBox;

public class EmprestimoMainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableEmprestimos;
	private JTextField txtFiltro;
	private JButton btnPesquisar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmprestimoMainGUI frame = new EmprestimoMainGUI();
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
	public EmprestimoMainGUI() {
		setTitle("Empréstimos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JComboBox comboBoxFiltro = new JComboBox();
		comboBoxFiltro.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBoxFiltro.setModel(new DefaultComboBoxModel(new String[] {"ID", "Amigo"}));
		comboBoxFiltro.setSelectedIndex(1);
		comboBoxFiltro.setBounds(10, 449, 104, 23);
		contentPane.add(comboBoxFiltro);
		
		final JButton btnRestaurar = new JButton("Restaurar");
		btnRestaurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableEmprestimos.getSelectedRow();
				if (linhaSelecionada >= 0) {
					int idRegistroEmAtivacao = (Integer) tableEmprestimos.getValueAt(linhaSelecionada, 0);
					EmprestimoController emprestimoController = new EmprestimoController();					
					emprestimoController.atualizaStatus(idRegistroEmAtivacao, 0);
			    	btnPesquisar.doClick();
				}
			}
		});
		btnRestaurar.setBounds(670, 479, 104, 23);
		contentPane.add(btnRestaurar);
		btnRestaurar.setVisible(false);	
		
		final JCheckBox chkCancelados = new JCheckBox("Cancelados");
		chkCancelados.setBounds(670, 449, 104, 23);
		contentPane.add(chkCancelados);		
		chkCancelados.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {		     
		        if (chkCancelados.isSelected()) 
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
		       	if (!chkCancelados.isSelected()) 
		       		situacao = 0;
		       	else
		       		situacao = 1;
		        
		        EmprestimoController emprestimoController = new EmprestimoController();		  
		        ArrayList<EmprestimoBEAN> resultados = new ArrayList<EmprestimoBEAN>();	      
		        if (filtro.isEmpty()) {
		        	resultados = emprestimoController.listaEmprestimo(situacao);
		        } else {
		        	if ("ID".equals(opcaoFiltro)) {
		                resultados = emprestimoController.listaEmprestimoByID(Integer.parseInt(filtro), situacao); 
		            } else {
		                resultados = emprestimoController.listaEmprestimoByAmigo(filtro, situacao);
		            }
		        }	
		        DefaultTableModel tabela = (DefaultTableModel) tableEmprestimos.getModel();
		        tabela.setRowCount(0);
		        AmigoController amigoController = new AmigoController();
		        for (EmprestimoBEAN emprestimo : resultados) {
		        	AmigoBEAN amigo = amigoController.buscaAmigo(emprestimo.getidAmigos());
		        	tabela.addRow(new Object[] { emprestimo.getIdEmprestimos(), amigo.getNome(), emprestimo.getData() });
		        }
			}
		});
		btnPesquisar.setBounds(120, 511, 109, 23);
		contentPane.add(btnPesquisar);	

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 64, 764, 358);
		contentPane.add(scrollPane);
		
		tableEmprestimos =new JTable();
		tableEmprestimos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Amigo", "Data"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, LocalDate.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableEmprestimos.setDefaultEditor(Object.class, null);
		tableEmprestimos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableEmprestimos);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmprestimoCadastroGUI telaCadastro = new EmprestimoCadastroGUI();
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
				/*
				if (chkCancelados.isSelected()) 
					JOptionPane.showMessageDialog(null, "Não é possível editar um registro excluído.", "Erro", JOptionPane.ERROR_MESSAGE);
				else {
					int linhaSelecionada = tableEmprestimos.getSelectedRow();
				    if (linhaSelecionada >= 0) {			            
				    	int idRegistroEmEdicao = (Integer) tableEmprestimos.getValueAt(linhaSelecionada, 0);
				    	EmprestimoController emprestimoController = new EmprestimoController();
				    	EmprestimoBEAN emprestimoEditar = emprestimoController.buscaEmprestimo(idRegistroEmEdicao);
				    	EmprestimoCadastroGUI telaCadastro = new EmprestimoCadastroGUI(emprestimoEditar);
				        telaCadastro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				        telaCadastro.setVisible(true);		  
				        centralizarJanela(telaCadastro, contentPane);
				    }
				}	*/			
			}
		});
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEditar.setBounds(120, 22, 89, 23);
		contentPane.add(btnEditar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkCancelados.isSelected()) 
					JOptionPane.showMessageDialog(null, "Não é possível cancelar um registro excluído.", "Erro", JOptionPane.ERROR_MESSAGE);
				else {
					int linhaSelecionada = tableEmprestimos.getSelectedRow();
					if (linhaSelecionada >= 0) {
						int idRegistroEmExclusao = (Integer) tableEmprestimos.getValueAt(linhaSelecionada, 0);
						EmprestimoController emprestimoController = new EmprestimoController();
						emprestimoController.atualizaStatus(idRegistroEmExclusao, 1);
						LivroController livroController = new LivroController();
						livroController.atualizaLivros(idRegistroEmExclusao);
				    	btnPesquisar.doClick();
					}	
				}				
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCancelar.setBounds(230, 22, 89, 23);
		contentPane.add(btnCancelar);		
		
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
		
		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkCancelados.isSelected()) 
					JOptionPane.showMessageDialog(null, "Não é possível finalizar um registro excluído.", "Erro", JOptionPane.ERROR_MESSAGE);
				else {
					int linhaSelecionada = tableEmprestimos.getSelectedRow();
					if (linhaSelecionada >= 0) {
						int idRegistroEmExclusao = (Integer) tableEmprestimos.getValueAt(linhaSelecionada, 0);
						EmprestimoController emprestimoController = new EmprestimoController();
						EmprestimoBEAN emprestimo = emprestimoController.buscaEmprestimo(idRegistroEmExclusao);
						if (!emprestimo.getSituacao().equals("Finalizado")) {
							emprestimoController.atualizaSituacao(idRegistroEmExclusao, "Finalizado");						
							LivroController livroController = new LivroController();
							livroController.atualizaLivros(idRegistroEmExclusao);
					    	btnPesquisar.doClick();
						}						
					}	
				}			
			}
		});
		btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnFinalizar.setBounds(336, 22, 89, 23);
		contentPane.add(btnFinalizar);
	}
	
	public static void centralizarJanela(JFrame janela, JPanel painelPrincipal) {
	    int x = (painelPrincipal.getWidth() - janela.getWidth()) / 2;
	    int y = (painelPrincipal.getHeight() - janela.getHeight()) / 2;
	    janela.setLocation(x, y);
	}	
}
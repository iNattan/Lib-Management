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

import Controller.AmigoController;
import Model.AmigoBEAN;

import javax.swing.JCheckBox;

public class AmigoMainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableAmigos;
	private JTextField txtFiltro;
	private JButton btnPesquisar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AmigoMainGUI frame = new AmigoMainGUI();
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
	public AmigoMainGUI() {
		setTitle("Amigos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JComboBox comboBoxFiltro = new JComboBox();
		comboBoxFiltro.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBoxFiltro.setModel(new DefaultComboBoxModel(new String[] {"ID", "Nome", "Documento"}));
		comboBoxFiltro.setSelectedIndex(1);
		comboBoxFiltro.setBounds(10, 449, 104, 23);
		contentPane.add(comboBoxFiltro);
		
		final JButton btnRestaurar = new JButton("Restaurar");
		btnRestaurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableAmigos.getSelectedRow();
				if (linhaSelecionada >= 0) {
					int idRegistroEmAtivacao = (Integer) tableAmigos.getValueAt(linhaSelecionada, 0);
					AmigoController amigoController = new AmigoController();
					AmigoBEAN amigoAtivar = amigoController.buscaAmigo(idRegistroEmAtivacao);
					amigoController.activeAmigo(amigoAtivar);
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
		       	AmigoController amigoController = new AmigoController();		  
		        ArrayList<AmigoBEAN> resultados = new ArrayList<AmigoBEAN>();	      
		        if (filtro.isEmpty()) {
		        	resultados = amigoController.listaAmigos(situacao);
		        } else {
		        	if ("ID".equals(opcaoFiltro)) {
		                resultados = amigoController.listaAmigosByID(Integer.parseInt(filtro), situacao); 
		            } else if ("Nome".equals(opcaoFiltro)) {
		                resultados = amigoController.listaAmigosByNome(filtro, situacao);
		            } else if ("Documento".equals(opcaoFiltro)) {
		                resultados = amigoController.listaAmigosByDocumento(filtro, situacao);
		            }
		        }	
		        DefaultTableModel tabela = (DefaultTableModel) tableAmigos.getModel();
		        tabela.setRowCount(0);
		        for (AmigoBEAN amigo : resultados) {
		        	tabela.addRow(new Object[] { amigo.getIdAmigos(), amigo.getNome(), amigo.getDocumento() });
		        }
			}
		});
		btnPesquisar.setBounds(120, 511, 109, 23);
		contentPane.add(btnPesquisar);	

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 64, 764, 358);
		contentPane.add(scrollPane);
		
		tableAmigos =new JTable();
		tableAmigos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "Documento"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableAmigos.setDefaultEditor(Object.class, null);
		tableAmigos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableAmigos);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AmigoCadastroGUI telaCadastro = new AmigoCadastroGUI();
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
					int linhaSelecionada = tableAmigos.getSelectedRow();
				    if (linhaSelecionada >= 0) {			            
				    	int idRegistroEmEdicao = (Integer) tableAmigos.getValueAt(linhaSelecionada, 0);
				    	AmigoController amigoController = new AmigoController();
				    	AmigoBEAN amigoEditar = amigoController.buscaAmigo(idRegistroEmEdicao);
				    	AmigoCadastroGUI telaCadastro = new AmigoCadastroGUI(amigoEditar);
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
					int linhaSelecionada = tableAmigos.getSelectedRow();
					if (linhaSelecionada >= 0) {
						int idRegistroEmExclusao = (Integer) tableAmigos.getValueAt(linhaSelecionada, 0);
						AmigoController amigoController = new AmigoController();
						AmigoBEAN amigoExcluir = amigoController.buscaAmigo(idRegistroEmExclusao);
						amigoController.inativeAmigo(amigoExcluir);
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
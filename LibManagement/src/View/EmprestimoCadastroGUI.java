package View;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import Controller.AmigoController;
import Controller.AutorController;
import Controller.EditoraController;
import Controller.EmprestimoController;
import Controller.LivroController;
import Model.AmigoBEAN;
import Model.AutorBEAN;
import Model.EditoraBEAN;
import Model.EmprestimoBEAN;
import Model.EmprestimoDAO;
import Model.LivroBEAN;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class EmprestimoCadastroGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> cbAmigo;
    private JFormattedTextField txtData;
    private JTable tableLivros;
    private JLabel lblNewLabel;
    private JLabel lblLivrosSelecionados;
    private JTable tableLivrosSelecionados;
    private JScrollPane scrollPane_1;
    private JTextField txtFiltro;
    private JButton btnLimpar;
    private JButton btnPesquisar;
    private JButton btnCancelar;
    private JButton btnGravar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmprestimoCadastroGUI frame = new EmprestimoCadastroGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EmprestimoCadastroGUI() {
		initComponents();		
		
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 636);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cbAmigo = new JComboBox<String>();
		cbAmigo.setBounds(10, 25, 166, 22);
		contentPane.add(cbAmigo);
		
		AmigoController amigoController = new AmigoController();
        ArrayList<AmigoBEAN> amigos = amigoController.listaAmigos(0);
        for (AmigoBEAN amigo : amigos) {
            String item = amigo.getIdAmigos() + " - " + amigo.getNome();
            cbAmigo.addItem(item);
        }
		
		JLabel lblNewLabel_1 = new JLabel("Amigo");
		lblNewLabel_1.setBounds(10, 11, 48, 14);
		contentPane.add(lblNewLabel_1);
	
		JLabel lblData = new JLabel("Data de entrega");
	    lblData.setBounds(200, 11, 100, 14);
	    contentPane.add(lblData);
        try {
        	MaskFormatter mask = new MaskFormatter("##/##/####");
	        txtData = new JFormattedTextField(mask);
            txtData.setBounds(200, 25, 100, 22);
            contentPane.add(txtData);
            
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBounds(10, 105, 620, 210);
            contentPane.add(scrollPane);
            
            tableLivros =new JTable();
    		tableLivros.setModel(new DefaultTableModel(
    			new Object[][] {
    			},
    			new String[] {
    				"ID", "Nome", "Autor", "Editora"
    			}
    		) {
    			Class[] columnTypes = new Class[] {
    				Integer.class, String.class, String.class, String.class
    			};
    			public Class getColumnClass(int columnIndex) {
    				return columnTypes[columnIndex];
    			}
    		});
    		tableLivros.setDefaultEditor(Object.class, null);
    		tableLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    		scrollPane.setViewportView(tableLivros);
    		
    		tableLivros.addMouseListener(new MouseAdapter() {
    		    public void mouseClicked(MouseEvent e) {
    		        if (e.getClickCount() == 2) {
    		        	int linhaSelecionada = tableLivros.getSelectedRow();
    		            if (linhaSelecionada != -1) {
    		                int livroID = (Integer) tableLivros.getValueAt(linhaSelecionada, 0); 

    		                LivroController livroController = new LivroController();
    		                livroController.atualizaStatus(livroID, "Emprestado"); 
    		                
    		                DefaultTableModel modelLivrosSelecionados = (DefaultTableModel) tableLivrosSelecionados.getModel();
    		                Object[] livroSelecionado = {
    		                    tableLivros.getValueAt(linhaSelecionada, 0), 
    		                    tableLivros.getValueAt(linhaSelecionada, 1), 
    		                    tableLivros.getValueAt(linhaSelecionada, 2), 
    		                    tableLivros.getValueAt(linhaSelecionada, 3)  
    		                };
    		                modelLivrosSelecionados.addRow(livroSelecionado);
    		                
    		                btnPesquisar.doClick();   		                
    		            }
    		        }
    		    }
    		});
    		
    		lblNewLabel = new JLabel("Livros Disponíveis");
    		lblNewLabel.setBounds(10, 58, 108, 14);
    		contentPane.add(lblNewLabel);    	    		    		    		
    		
    		scrollPane_1 = new JScrollPane();
    		scrollPane_1.setBounds(10, 344, 620, 210);
    		contentPane.add(scrollPane_1);
    		
    		tableLivrosSelecionados =new JTable();
    		tableLivrosSelecionados.setModel(new DefaultTableModel(
    			new Object[][] {
    			},
    			new String[] {
    				"ID", "Nome", "Autor", "Editora"
    			}
    		) {
    			Class[] columnTypes = new Class[] {
    				Integer.class, String.class, String.class, String.class
    			};
    			public Class getColumnClass(int columnIndex) {
    				return columnTypes[columnIndex];
    			}
    		});
    		tableLivrosSelecionados.setDefaultEditor(Object.class, null);
    		tableLivrosSelecionados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    		scrollPane_1.setViewportView(tableLivrosSelecionados);
    		
    		tableLivrosSelecionados.addMouseListener(new MouseAdapter() {
    		    public void mouseClicked(MouseEvent e) {
    		        if (e.getClickCount() == 2) {
    		        	int linhaSelecionada = tableLivrosSelecionados.getSelectedRow();
    		            if (linhaSelecionada != -1) {
    		                int livroID = (Integer) tableLivrosSelecionados.getValueAt(linhaSelecionada, 0); 

    		                LivroController livroController = new LivroController();
    		                livroController.atualizaStatus(livroID, "Disponível"); 
    		                
    		                DefaultTableModel modelLivrosSelecionados = (DefaultTableModel) tableLivrosSelecionados.getModel();
    		                modelLivrosSelecionados.removeRow(linhaSelecionada);	                
    		            }
    		        }
    		    }
    		});
    		
    		lblLivrosSelecionados = new JLabel("Livros Selecionados");
    		lblLivrosSelecionados.setBounds(10, 326, 153, 14);
    		contentPane.add(lblLivrosSelecionados);
    		
    		txtFiltro = new JTextField();
    		txtFiltro.setFont(new Font("Tahoma", Font.PLAIN, 11));
    		txtFiltro.setColumns(10);
    		txtFiltro.setBounds(10, 73, 402, 20);
    		contentPane.add(txtFiltro);
    		
    		btnLimpar = new JButton("Limpar");
    		btnLimpar.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				txtFiltro.setText("");
    			}
    		});
    		btnLimpar.setFont(new Font("Tahoma", Font.PLAIN, 11));
    		btnLimpar.setBounds(541, 73, 89, 23);
    		contentPane.add(btnLimpar);
    		
    		btnPesquisar = new JButton("Pesquisar");
    		btnPesquisar.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				String filtro = txtFiltro.getText();
    				LivroController livroController = new LivroController();		  
    		        ArrayList<LivroBEAN> resultados = new ArrayList<LivroBEAN>();	
    				if (filtro.isEmpty()) {
    		        	resultados = livroController.listaLivroDisponiveis(0);
    		        }
    				else {
    					resultados = livroController.listaLivroDisponiveisByNome(filtro, 0);
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
    		btnPesquisar.setFont(new Font("Tahoma", Font.PLAIN, 11));
    		btnPesquisar.setBounds(422, 73, 109, 23);
    		contentPane.add(btnPesquisar);
    		
    		btnCancelar = new JButton("Cancelar");
    		btnCancelar.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				dispose();
    			}
    		});
    		btnCancelar.setBounds(210, 565, 89, 23);
    		contentPane.add(btnCancelar);
    		
    		btnGravar = new JButton("Gravar");
    		btnGravar.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				String amigoSelecionado = (String) cbAmigo.getSelectedItem();
    				int amigo = extrairID(amigoSelecionado);
    				String dataTexto = txtData.getText();
    				LocalDate data = converterParaLocalDate(dataTexto);    				
    				ArrayList<LivroBEAN> listaLivros = new ArrayList<LivroBEAN>();
    				DefaultTableModel model = (DefaultTableModel) tableLivrosSelecionados.getModel();
    				int qntRegistros = model.getRowCount();
    				for (int i = 0; i < qntRegistros; i++) {
    				    int idLivro = (Integer) model.getValueAt(i, 0); 
    				    LivroController livroController = new LivroController();
    				    LivroBEAN livro =  livroController.buscaLivro(idLivro, 0);    				        				    
    				    listaLivros.add(livro); 
    				}    				
    				EmprestimoBEAN emprestimo = new EmprestimoBEAN(0, data, "Normal", amigo, listaLivros);
    				
    				EmprestimoController emprestimoController = new EmprestimoController();
    				emprestimoController.addEmprestimo(emprestimo);
    			}
    		});
    		btnGravar.setBounds(340, 565, 89, 23);
    		contentPane.add(btnGravar);
    		
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private int extrairID(String item) {
        String[] parts = item.split(" - ");
        if (parts.length > 0) {
            try {
                return Integer.parseInt(parts[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0; 
    }
	
	private LocalDate converterParaLocalDate(String dataTexto) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    return LocalDate.parse(dataTexto, formatter);
	}
}

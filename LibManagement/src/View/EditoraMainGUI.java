package View;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.EditoraBEAN;
import Model.EditoraDAO;

public class EditoraMainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableEditora;
	private JTextField txtFiltro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditoraMainGUI frame = new EditoraMainGUI();
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
	public EditoraMainGUI() {
		setTitle("Editoras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 64, 764, 358);
		contentPane.add(scrollPane);
		
		tableEditora = new JTable();
		tableEditora.setModel(new DefaultTableModel(
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
		scrollPane.setViewportView(tableEditora);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditoraCadastroGUI telaCadastro = new EditoraCadastroGUI();
				telaCadastro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				telaCadastro.setVisible(true);
			}
		}); 
		btnAdicionar.setBounds(10, 30, 89, 23);
		contentPane.add(btnAdicionar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(120, 30, 89, 23);
		contentPane.add(btnEditar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(230, 30, 89, 23);
		contentPane.add(btnExcluir);
		
		final JComboBox comboBoxFiltro = new JComboBox();
		comboBoxFiltro.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBoxFiltro.setModel(new DefaultComboBoxModel(new String[] {"ID", "Nome", "Documento"}));
		comboBoxFiltro.setSelectedIndex(1);
		comboBoxFiltro.setBounds(10, 449, 104, 23);
		contentPane.add(comboBoxFiltro);
		
		JLabel lblNewLabel = new JLabel("Opção de Filtro");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 433, 104, 14);
		contentPane.add(lblNewLabel);
		
		txtFiltro = new JTextField();
		txtFiltro.setBounds(10, 480, 219, 20);
		contentPane.add(txtFiltro);
		txtFiltro.setColumns(10);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtFiltro.setText("");
			}
		});
		btnLimpar.setBounds(10, 511, 89, 23);
		contentPane.add(btnLimpar);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filtro = txtFiltro.getText();
		        String opcaoFiltro = comboBoxFiltro.getSelectedItem().toString();
		        ArrayList<EditoraBEAN> resultados = new ArrayList<EditoraBEAN>();
		        
		        EditoraDAO editoraDAO = EditoraDAO.getInstance();
		        if (filtro.isEmpty()) {
		        	resultados = editoraDAO.findAllEditoras();
		        } else {
		        	if ("ID".equals(opcaoFiltro)) {
		                resultados = editoraDAO.findEditoraByID(Integer.parseInt(filtro));
		            } else if ("Nome".equals(opcaoFiltro)) {
		                resultados = editoraDAO.findEditoraByNome(filtro);
		            } else if ("Documento".equals(opcaoFiltro)) {
		                resultados = editoraDAO.findEditoraByDocumento(filtro);
		            }
		        }	
		        DefaultTableModel tabela = (DefaultTableModel) tableEditora.getModel();
		        tabela.setRowCount(0);
		        for (EditoraBEAN editora : resultados) {
		        	tabela.addRow(new Object[] { editora.getIdEditoras(), editora.getNome(), editora.getDocumento() });
		        }
			}
		});
		btnPesquisar.setBounds(120, 511, 109, 23);
		contentPane.add(btnPesquisar);
	}
}

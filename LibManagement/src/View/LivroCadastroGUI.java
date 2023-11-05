package View;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.AutorBEAN;
import Model.EditoraBEAN;
import Model.LivroBEAN;
import Controller.AutorController;
import Controller.EditoraController;
import Controller.LivroController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class LivroCadastroGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblNewLabel;
    private JTextField txtNome;
    private JButton btnCancelar;
    private JButton btnGravar;
    private LivroBEAN livroEmEdicao;

    public LivroCadastroGUI() {
        setTitle("Cadastro de Livro");
        initComponents();
    }
    public LivroCadastroGUI(LivroBEAN livroEditar) {
        setTitle("Edição de Livro");
        livroEmEdicao = livroEditar;
        initComponents();
        txtNome.setText(livroEditar.getNome());
    }

    private void initComponents() {
        setBounds(100, 100, 500, 211);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblNewLabel = new JLabel("Nome");
        lblNewLabel.setBounds(10, 24, 46, 14);
        contentPane.add(lblNewLabel);

        txtNome = new JTextField();
        txtNome.setBounds(10, 39, 464, 20);
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnCancelar.setBounds(129, 139, 89, 23);
        contentPane.add(btnCancelar);
        
        final JComboBox cbAutor = new JComboBox();
        cbAutor.setBounds(10, 84, 209, 22);
        contentPane.add(cbAutor);
        
        JLabel lblNewLabel_1 = new JLabel("Autor");
        lblNewLabel_1.setBounds(10, 70, 46, 14);
        contentPane.add(lblNewLabel_1);
        
        AutorController autorController = new AutorController();
        ArrayList<AutorBEAN> autores = autorController.listaAutores(0);
        for (AutorBEAN autor : autores) {
            String item = autor.getIdAutores() + " - " + autor.getNome();
            cbAutor.addItem(item);
        }
        
        final JComboBox cbEditora = new JComboBox();
        cbEditora.setBounds(265, 84, 209, 22);
        contentPane.add(cbEditora);
        
        JLabel lblNewLabel_1_1 = new JLabel("Editora");
        lblNewLabel_1_1.setBounds(265, 70, 46, 14);
        contentPane.add(lblNewLabel_1_1);
                
        EditoraController editoraController = new EditoraController();  
        ArrayList<EditoraBEAN> editoras = editoraController.listaEditoras(0);
        for (EditoraBEAN editora : editoras) {
            String item = editora.getIdEditoras() + " - " + editora.getNome();
            cbEditora.addItem(item);
        }
        
        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                String autorSelecionado = (String) cbAutor.getSelectedItem();
                String editoraSelecionada = (String) cbEditora.getSelectedItem();
                int idAutor = extrairID(autorSelecionado); 
                int idEditora = extrairID(editoraSelecionada);
                LivroController livroController = new LivroController();

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha o nome.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (livroEmEdicao == null) {
                        LivroBEAN livro = new LivroBEAN(0, nome, idAutor, idEditora , "Disponivel", 0);
                        long resultado = livroController.addLivro(livro);
                        if (resultado > 0) {
                            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Falha ao cadastrar. Por favor, tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                    	livroEmEdicao.setNome(nome);
                    	livroEmEdicao.setIdAutor(idAutor);
                    	livroEmEdicao.setIdEditora(idEditora);
                        long resultado = livroController.updateLivro(livroEmEdicao);
                        if (resultado == 0) {
                            JOptionPane.showMessageDialog(null, "Edição realizada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Falha ao editar. Por favor, tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    dispose();
                }
            }
        });
        btnGravar.setBounds(259, 139, 89, 23);
        contentPane.add(btnGravar);        
               
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
}

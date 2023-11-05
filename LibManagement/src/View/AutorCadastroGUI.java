package View;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.AutorBEAN;
import Controller.AutorController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AutorCadastroGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblNewLabel;
    private JTextField txtNome;
    private JTextField txtDocumento;
    private JLabel lblDocumento;
    private JButton btnCancelar;
    private JButton btnGravar;
    private AutorBEAN autorEmEdicao;

    public AutorCadastroGUI() {
        setTitle("Cadastro de Autor");
        initComponents();
    }
    public AutorCadastroGUI(AutorBEAN autorEditar) {
        setTitle("Edição de Autor");
        autorEmEdicao = autorEditar;
        initComponents();
        txtNome.setText(autorEditar.getNome());
        txtDocumento.setText(autorEditar.getDocumento());
    }

    private void initComponents() {
        setBounds(100, 100, 500, 160);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblNewLabel = new JLabel("Nome");
        lblNewLabel.setBounds(10, 24, 46, 14);
        contentPane.add(lblNewLabel);

        txtNome = new JTextField();
        txtNome.setBounds(10, 39, 281, 20);
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        txtDocumento = new JTextField();
        txtDocumento.setColumns(10);
        txtDocumento.setBounds(301, 39, 173, 20);
        contentPane.add(txtDocumento);

        lblDocumento = new JLabel("Documento");
        lblDocumento.setBounds(301, 24, 89, 14);
        contentPane.add(lblDocumento);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnCancelar.setBounds(130, 80, 89, 23);
        contentPane.add(btnCancelar);

        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                String documento = txtDocumento.getText();
                AutorController autorController = new AutorController();

                if (nome.isEmpty() || documento.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha o nome e o documento.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (autorEmEdicao == null) {
                        AutorBEAN autor = new AutorBEAN(0, nome, documento, 0);
                        long resultado = autorController.addAutor(autor);
                        if (resultado > 0) {
                            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Falha ao cadastrar. Por favor, tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        autorEmEdicao.setNome(nome);
                        autorEmEdicao.setDocumento(documento);
                        long resultado = autorController.updateAutor(autorEmEdicao);
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
        btnGravar.setBounds(260, 80, 89, 23);
        contentPane.add(btnGravar);
    }
}

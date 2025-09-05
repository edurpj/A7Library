package br.com.biblioteca.ui;

import br.com.biblioteca.controller.LivroController;
import br.com.biblioteca.model.entity.Livro;
import br.com.biblioteca.service.impl.LivroServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaPesquisarLivroFrame extends JDialog {

    private JTextField jTextFieldPesquisa;
    private JButton jButtonPesquisar;
    private LivroController livroController;
    private List<Livro> resultadosDaPesquisa;

    public TelaPesquisarLivroFrame(java.awt.Window parent, LivroServiceImpl livroService) {
        super(parent, "Pesquisa de Livros", Dialog.ModalityType.APPLICATION_MODAL);

        initComponents();

        this.livroController = new LivroController(livroService);
        setSize(400, 150);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setLocationRelativeTo(parent);

    }

    private void initComponents() {
        add(new JLabel("Pesquisar por Título/Autor/ISBN:"));
        jTextFieldPesquisa = new JTextField(20);
        add(jTextFieldPesquisa);

        jButtonPesquisar = new JButton("Pesquisar");
        add(jButtonPesquisar);

        jButtonPesquisar.addActionListener(e -> pesquisarLivros());
    }

    private void pesquisarLivros() {
        String termo = jTextFieldPesquisa.getText().trim();
        if (termo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um termo de pesquisa.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            this.resultadosDaPesquisa = livroController.pesquisarLivros(termo);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar a pesquisa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public List<Livro> getResultadosDaPesquisa() {
        return resultadosDaPesquisa;
    }
}
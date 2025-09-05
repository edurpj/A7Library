package br.com.biblioteca.util;

import br.com.biblioteca.model.entity.Livro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class TabelaLivrosUtil {

    public static void preencherTabelaLivros(JTable table, List<Livro> livros) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Livro livro : livros) {
            model.addRow(new Object[]{
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutores(),
                    livro.getDataPublicacao() != null ? sdf.format(livro.getDataPublicacao()) : "",
                    livro.getIsbn(),
                    livro.getEditora()
            });
        }
    }
}

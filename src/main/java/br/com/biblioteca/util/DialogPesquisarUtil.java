package br.com.biblioteca.util;

import br.com.biblioteca.model.entity.Livro;
import br.com.biblioteca.service.impl.LivroServiceImpl;
import br.com.biblioteca.ui.TelaPesquisarLivroFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

//Responsavel por montar o Dialog de pesquisa
public class DialogPesquisarUtil extends Component {
    public static List<Livro> mostrarDialogPesquisar(Window parent, LivroServiceImpl livroService) {
        try {
            TelaPesquisarLivroFrame telaPesquisa = new TelaPesquisarLivroFrame(parent, livroService);
            telaPesquisa.setVisible(true);

            List<Livro> resultados = telaPesquisa.getResultadosDaPesquisa();

            if (resultados != null && resultados.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Nenhum livro encontrado.", "Resultados", JOptionPane.INFORMATION_MESSAGE);
            }

            return resultados;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Erro ao abrir a tela de pesquisa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }
}

package br.com.biblioteca.util;

import br.com.biblioteca.model.entity.Livro;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImportacaoXMLUtil {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ImportacaoXMLUtil.class.getName());

    public static List<Livro> lerArquivo(String caminhoArquivo) throws IOException {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            File arquivo = new File(caminhoArquivo);

            return xmlMapper.readValue(arquivo, new TypeReference<List<Livro>>() {});

        } catch (IOException e) {
            logger.severe("Erro ao ler arquivo XML: " + e.getMessage());
            throw e;
        }
    }
}
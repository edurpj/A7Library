package br.com.biblioteca.util;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Utilitário para buscar dados de livros e autores na OpenLibrary.
 */
public class OpenLibraryUtil {
    private static final Logger LOGGER = Logger.getLogger(OpenLibraryUtil.class.getName());
    private static final String URL_API = "https://openlibrary.org";
    private static final int TIMEOUT = 5000; // 5 segundos

    // Metodo  para criar e configurar a conexão
    private static HttpURLConnection criarConexao(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setConnectTimeout(TIMEOUT);
            conexao.setReadTimeout(TIMEOUT);
            return conexao;
        } catch (Exception e) {
            LOGGER.severe("Erro ao criar conexão: " + e.getMessage());
            return null;
        }
    }

    public static JSONObject buscarLivroPorISBN(String isbn) {
        try {
            String urlStr = URL_API + "/isbn/" + isbn + ".json";
            HttpURLConnection conexao = criarConexao(urlStr);

            int responseCode = conexao.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conexao.getInputStream();
                JSONTokener tokener = new JSONTokener(is);
                JSONObject obj = new JSONObject(tokener);
                is.close();
                return obj;
            }
        } catch (Exception e) {
            LOGGER.severe("Erro ao buscar livro por ISBN: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca os dados de um autor na OpenLibrary usando sua chave (key).
     * @param autorKey A chave do autor (ex: "/authors/OL2032671A").
     * @return Um JSONObject contendo os dados do autor ou null se a busca falhar.
     */
    public static JSONObject buscarAutorPorChave(String autorKey) {
        try {
            String urlStr = URL_API + autorKey + ".json";
            HttpURLConnection conexao = criarConexao(urlStr);

            int responseCode = conexao.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conexao.getInputStream();
                JSONTokener tokener = new JSONTokener(is);
                JSONObject obj = new JSONObject(tokener);
                is.close();
                return obj;
            }
        } catch (Exception e) {
            LOGGER.severe("Erro ao buscar autor pela chave: " + e.getMessage());
        }
        return null;
    }
}

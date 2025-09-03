package br.com.biblioteca.model.dto;

import java.util.Date;

/**
 * Classe responsável pela transferência de dados para a entidade.
 * */
public class LivroDTO {
    private Long id;
    private String titulo;
    private String autores;
    private Date dataPublicacao;
    private String isbn;
    private String editora;
    private Long editoraId;

    public LivroDTO(Long id, Long editoraId, String editora, String isbn, Date dataPublicacao, String autores, String titulo) {
        this.id = id;
        this.editoraId = editoraId;
        this.editora = editora;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.autores = autores;
        this.titulo = titulo;
    }

    public LivroDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEditoraId() {
        return editoraId;
    }

    public void setEditoraId(Long editoraId) {
        this.editoraId = editoraId;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}

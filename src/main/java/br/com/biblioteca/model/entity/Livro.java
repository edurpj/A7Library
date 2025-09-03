package br.com.biblioteca.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Entidade Livro para catalogação no banco de dados.
 */
@Entity
@Table(name = "livro")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autores;

    @Temporal(TemporalType.DATE)
    private Date dataPublicacao;

    @Column(unique = true, nullable = false)
    private String isbn;

    private String editora;

    @ManyToMany
    @JoinTable(
        name = "livros_semelhantes",
        joinColumns = @JoinColumn(name = "livro_id"),
        inverseJoinColumns = @JoinColumn(name = "semelhante_id")
    )
    private List<Livro> livrosSemelhantes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public List<Livro> getLivrosSemelhantes() {
        return livrosSemelhantes;
    }

    public void setLivrosSemelhantes(List<Livro> livrosSemelhantes) {
        this.livrosSemelhantes = livrosSemelhantes;
    }
}

import br.com.biblioteca.dao.impl.LivroDAOImpl;
import br.com.biblioteca.model.entity.Livro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LivroDAOImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction entityTransaction;


    @Mock
    private TypedQuery<Livro> typedQuery;

    @InjectMocks
    private LivroDAOImpl livroDAO;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testSalvar() {
        Livro livro = new Livro();
        livro.setTitulo("A Metamorfose");

        when(entityManager.getTransaction()).thenReturn(entityTransaction);

        livroDAO.salvar(livro);

        verify(entityTransaction, times(1)).begin();
        verify(entityTransaction, times(1)).commit();
        verify(entityManager, times(1)).persist(livro);
    }

    @Test
    void testAtualizar() {

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("O Pequeno Príncipe");

        when(entityManager.getTransaction()).thenReturn(entityTransaction);

        livroDAO.atualizar(livro);

        verify(entityTransaction, times(1)).begin();
        verify(entityTransaction, times(1)).commit();
        verify(entityManager, times(1)).merge(livro);
    }

    @Test
    void testExcluir() {

        Long id = 1L;
        Livro livro = new Livro();
        livro.setId(id);

        when(entityManager.find(Livro.class, id)).thenReturn(livro);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);

        livroDAO.excluir(id);

        verify(entityManager, times(1)).find(Livro.class, id);
        verify(entityManager, times(1)).remove(livro);
        verify(entityTransaction, times(1)).commit();
    }

    @Test
    void testBuscarPorId() {

        Long id = 1L;
        Livro livro = new Livro();
        livro.setId(id);

        when(entityManager.find(Livro.class, id)).thenReturn(livro);

        Livro resultado = livroDAO.buscarPorId(id);

        verify(entityManager, times(1)).find(Livro.class, id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
    }

    @Test
    void testListarTodos() {

        List<Livro> livros = Arrays.asList(new Livro(), new Livro());

        when(entityManager.createQuery(anyString(), eq(Livro.class))).thenReturn(typedQuery);

        when(typedQuery.getResultList()).thenReturn(livros);

        List<Livro> resultado = livroDAO.listarTodos();

        verify(entityManager, times(1)).createQuery("SELECT l FROM Livro l", Livro.class);
        verify(typedQuery, times(1)).getResultList();

        assertEquals(2, resultado.size());
    }
}

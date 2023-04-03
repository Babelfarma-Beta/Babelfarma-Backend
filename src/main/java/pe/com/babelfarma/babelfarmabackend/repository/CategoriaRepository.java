package pe.com.babelfarma.babelfarmabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.com.babelfarma.babelfarmabackend.entities.Categoria;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query(value="SELECT * FROM categorias where categoria like '%'||?1||'%'", nativeQuery = true)
    List<Categoria> findCategoriaSQL(String aux);

   Categoria getCategoriaById(Long id);
}

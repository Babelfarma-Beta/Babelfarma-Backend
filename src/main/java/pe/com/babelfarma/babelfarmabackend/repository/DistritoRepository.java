package pe.com.babelfarma.babelfarmabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.com.babelfarma.babelfarmabackend.entities.Distrito;

public interface DistritoRepository
    extends JpaRepository<Distrito, Long> {
    @Query("SELECT d FROM Distrito d WHERE d.id=?1")
    Distrito findByIdJPQL(long id);
}

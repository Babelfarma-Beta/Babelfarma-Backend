package pe.com.babelfarma.babelfarmabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.com.babelfarma.babelfarmabackend.entities.Envio;

import java.util.List;

public interface EnvioRepository
    extends JpaRepository<Envio, Long> {
        @Query("SELECT e FROM Envio e WHERE e.id=?1")
        Envio findByIdJPQL(long id);
        //@Query("SELECT e FROM Envio e WHERE e.direccionEnvio=?1")
        //List<Envio> findByDireccionEnvioJPQL(String direccionEnvio);
}

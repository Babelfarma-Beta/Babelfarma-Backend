package pe.com.babelfarma.babelfarmabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.com.babelfarma.babelfarmabackend.entities.Farmacia;
import pe.com.babelfarma.babelfarmabackend.entities.Producto;

import javax.transaction.Transactional;
import java.util.List;

public interface FarmaciaRepository extends JpaRepository<Farmacia, Long> {


  @Query("SELECT f FROM Farmacia f WHERE f.id = ?1")
  Farmacia findByIdJPQL(Long id);

  @Query(value = "SELECT * FROM farmacias WHERE direccion LIKE '%'||?1||'%'", nativeQuery = true)
  List<Farmacia> findByDireccionContainingSQL(String direccion);

  @Query(value = "SELECT * FROM farmacias WHERE nombre_establecimiento LIKE '%'||?1||'%'", nativeQuery = true)
  List<Farmacia> findByNombreEstablecimientoContainingSQL(String nombreEstablecimiento);

  @Query("SELECT f FROM Farmacia f JOIN f.distrito fd WHERE f.distrito.nombreDistrito= ?1")
  List<Farmacia> findByDistritoContainingJPQL(String distrito);

  @Query(value = "SELECT f.nombre_establecimiento, p.nombre, p.stock FROM farmacias f INNER JOIN farmacias_productos ON farmacias_productos.farmacia_id = f.id INNER JOIN productos p ON p.id = farmacias_productos.producto_id", nativeQuery = true)
  List<String> findProducsByStock();


  @Query("SELECT f FROM Farmacia f WHERE f.ruc=?1 AND f.correoContacto=?2")
  Farmacia findByRucyCorreo(Long ruc, String correo);


  @Query(value="select * from farmacias f inner join farmacias_productos fp on f.id = fp.farmacia_id inner join productos p on fp.producto_id = p.id where p.id=?1", nativeQuery = true)
  Farmacia farmaciaPorProducto(Long id);


}

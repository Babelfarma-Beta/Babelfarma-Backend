package pe.com.babelfarma.babelfarmabackend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.babelfarma.babelfarmabackend.entities.Categoria;
import pe.com.babelfarma.babelfarmabackend.entities.Producto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query(value = """
    SELECT p FROM Producto p 
    INNER JOIN FarmaciaProducto fp ON p.id = fp.productoId
         WHERE  LOWER(p.nombre) LIKE LOWER(concat('%', :producto, '%'))
         AND p.status= '1'
    """)
    List<Producto> findProductoByNameSQL(@Param("producto") String producto);

    @Query("select p from Producto p inner join FarmaciaProducto b on p.id = b.productoId where p.status= '1' and p.categoria.categoria = ?1")
    List<Producto> findProductoByCategoria(String categoria);

    @Query("select p from Producto p where p.id=?1")
    Producto getById(Long id);

    @Query("select p.categoria.categoria, p.stock from Producto p join p.categoria c where c.id = p.categoria.id")
    List<String> ListCantProdCategoriaJPQL();

    @Query("select p from Producto p order by p.stock desc")
    List<Producto> ListProductoStockJPQL();

    @Query(value="select a.* from productos a inner join farmacias_productos b on a.id = b.producto_id  where a.status= '1' order by a.precio asc", nativeQuery = true)
    List<Producto> ListProductoPrecioJPQL();

    @Query(value="select a.* from productos a inner join farmacias_productos b on a.id = b.producto_id inner join farmacias c on b.farmacia_id = c.id where c.id=?1 ORDER BY CASE WHEN a.status ='1' THEN 0 ELSE 1 END, a.id ASC", nativeQuery = true)
    List<Producto> ListarProductoCadaFarmacia(long id);

    @Query(value="select a.* from productos a inner join farmacias_productos b on a.id = b.producto_id inner join farmacias c on b.farmacia_id = c.id where c.id=?1 AND a.status= '1' ORDER BY a.id ASC", nativeQuery = true)
    List<Producto> ListarProductoCadaFarmaciaYActivo(long id);

    @Query(value="select a.* from productos a inner join farmacias_productos b on a.id = b.producto_id inner join farmacias c on b.farmacia_id = c.id where c.id=?1 AND a.status= '0' ORDER BY a.id ASC", nativeQuery = true)
    List<Producto> ListarProductoCadaFarmaciaYNoActivo(long id);

}

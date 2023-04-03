package pe.com.babelfarma.babelfarmabackend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.com.babelfarma.babelfarmabackend.entities.Categoria;
import pe.com.babelfarma.babelfarmabackend.entities.Producto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    @Query(value="SELECT * FROM productos a inner join farmacias_productos b on a.id = b.producto_id where nombre like '%'||?1||'%'", nativeQuery = true)
    List<Producto> findProductoByNameSQL(String producto);

    @Query("select p from Producto p inner join FarmaciaProducto b on p.id = b.productoId where p.categoria.categoria = ?1")
    List<Producto> findProductoByCategoria(String categoria);

    @Query("select p from Producto p where p.id=?1")
    Producto getById(Long id);

    @Query("select p.categoria.categoria, p.stock from Producto p join p.categoria c where c.id = p.categoria.id")
    List<String> ListCantProdCategoriaJPQL();

    @Query("select p from Producto p order by p.stock desc")
    List<Producto> ListProductoStockJPQL();

    @Query(value="select * from productos a inner join farmacias_productos b on a.id = b.producto_id order by a.precio asc", nativeQuery = true)
    List<Producto> ListProductoPrecioJPQL();


    //Reporte de productos mas vendidos


    @Query(value="select * from productos a inner join farmacias_productos b on a.id = b.producto_id inner join farmacias c on b.farmacia_id = c.id where c.id=?1", nativeQuery = true)
    List<Producto> ListarProductoCadaFarmacia(long id);

    

}

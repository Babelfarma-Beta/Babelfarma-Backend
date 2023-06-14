package pe.com.babelfarma.babelfarmabackend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.babelfarma.babelfarmabackend.dto.ProductoViewDto;
import pe.com.babelfarma.babelfarmabackend.model.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query(value = """
    SELECT NEW pe.com.babelfarma.babelfarmabackend.dto.ProductoViewDto(
     p.id,
     p.nombre,
     p.stock,
     p.precio,
     p.descripcion,
     p.picture,
     p.categoria.categoria,
     f.nombreEstablecimiento
    )
    FROM Producto p
    INNER JOIN FarmaciaProducto fp
        ON p.id = fp.productoId
    INNER JOIN Farmacia f
        ON f.id = fp.farmaciaId
        WHERE  LOWER(p.nombre) LIKE LOWER(concat('%', :producto, '%'))
        AND p.status= '1'
    """)
    List<ProductoViewDto> findProductoByNameSQL(@Param("producto") String producto);

    @Query(value = """
    SELECT NEW pe.com.babelfarma.babelfarmabackend.dto.ProductoViewDto(
     p.id,
     p.nombre,
     p.stock,
     p.precio,
     p.descripcion,
     p.picture,
     p.categoria.categoria,
     f.nombreEstablecimiento
    )
    FROM Producto p
    INNER JOIN FarmaciaProducto fp
        ON p.id = fp.productoId
    INNER JOIN Farmacia f
        ON f.id = fp.farmaciaId
        WHERE  p.categoria.categoria = ?1
        AND p.status= '1'
    """)
    List<ProductoViewDto> findProductoByCategoria(String categoria);

    @Query("select p from Producto p where p.id=?1")
    Producto getById(Long id);

    @Query("select p from Producto p order by p.stock desc")
    List<Producto> ListProductoStockJPQL();

    @Query(value = """
    SELECT NEW pe.com.babelfarma.babelfarmabackend.dto.ProductoViewDto(
     p.id,
     p.nombre,
     p.stock,
     p.precio,
     p.descripcion,
     p.picture,
     p.categoria.categoria,
     f.nombreEstablecimiento
    )
    FROM Producto p
    INNER JOIN FarmaciaProducto fp
        ON p.id = fp.productoId
    INNER JOIN Farmacia f
        ON f.id = fp.farmaciaId
        WHERE p.status= '1'
        ORDER BY p.precio ASC
    """)
    List<ProductoViewDto> ListProductoPrecioJPQL();

    @Query(value="select a.* from productos a inner join farmacias_productos b on a.id = b.producto_id inner join farmacias c on b.farmacia_id = c.id where c.id=?1 ORDER BY CASE WHEN a.status ='1' THEN 0 ELSE 1 END, a.id ASC", nativeQuery = true)
    List<Producto> ListarProductoCadaFarmacia(Long id);

    @Query(value="select a.* from productos a inner join farmacias_productos b on a.id = b.producto_id inner join farmacias c on b.farmacia_id = c.id where c.id=?1 AND a.status= '1' ORDER BY a.id ASC", nativeQuery = true)
    List<Producto> ListarProductoCadaFarmaciaYActivo(Long id);

    @Query(value="select a.* from productos a inner join farmacias_productos b on a.id = b.producto_id inner join farmacias c on b.farmacia_id = c.id where c.id=?1 AND a.status= '0' ORDER BY a.id ASC", nativeQuery = true)
    List<Producto> ListarProductoCadaFarmaciaYNoActivo(Long id);

}

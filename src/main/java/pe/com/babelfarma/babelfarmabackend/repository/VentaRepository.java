package pe.com.babelfarma.babelfarmabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.babelfarma.babelfarmabackend.entities.Venta;
import pe.com.babelfarma.babelfarmabackend.entities.VentaView;

import java.util.List;

public interface VentaRepository
    extends JpaRepository<Venta, Long> {
    List<Venta> findById(long id);

   @Query(value = """
        SELECT NEW pe.com.babelfarma.babelfarmabackend.entities.VentaView(
            v.id,
            v.fecha,
            CONCAT(v.idCliente.nombres, ' ', v.idCliente.apellidoPaterno, ' ', v.idCliente.apellidoMaterno),
            v.productName,
            v.precioUnit,
            v.cantidad,
            v.precioTotal
            )
        FROM Venta v
        WHERE v.idFarmacia.id= :id
     """)
    List<VentaView> findByFarmaciaId(@Param("id") Long id);

   @Query(value = """
        SELECT NEW pe.com.babelfarma.babelfarmabackend.entities.VentaView(
            v.id,
            v.fecha,
            CONCAT(v.idCliente.nombres, ' ', v.idCliente.apellidoPaterno, ' ', v.idCliente.apellidoMaterno),
            v.productName,
            v.precioUnit,
            v.cantidad,
            v.precioTotal
            )
        FROM Venta v
         WHERE LOWER(v.idCliente.nombres) LIKE LOWER(concat('%', :nombre, '%'))\s
                OR LOWER(v.idCliente.apellidoPaterno) LIKE LOWER(concat('%', :nombre, '%'))\s
                OR LOWER(v.idCliente.apellidoMaterno) LIKE LOWER(concat('%', :nombre, '%'))
                AND v.idFarmacia.id = :id
     """)
   List<VentaView> findByNombreCliente(@Param("nombre") String nombre,@Param("id") Long id);

     @Query(value="select v.* from venta v inner join cliente c on v.id_cliente = c.id  where c.id=?1", nativeQuery=true)
    List<Venta> findByIdCliente(Long id);

     @Query(value = """
        SELECT NEW pe.com.babelfarma.babelfarmabackend.entities.VentaView(
            v.id,
            v.fecha,
            CONCAT(v.idCliente.nombres, ' ', v.idCliente.apellidoPaterno, ' ', v.idCliente.apellidoMaterno),
            v.productName,
            v.precioUnit,
            v.cantidad,
            v.precioTotal
            )

        FROM Venta v
        WHERE EXTRACT(month from v.fecha)=:month AND v.idFarmacia.id= :id
     """)
    List<VentaView> findByMes(int month, Long id);

}

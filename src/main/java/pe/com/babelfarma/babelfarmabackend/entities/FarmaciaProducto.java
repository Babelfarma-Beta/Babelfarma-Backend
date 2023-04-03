package pe.com.babelfarma.babelfarmabackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(FarmaciaProducto.class)
@Table(name="farmacias_productos")
public class FarmaciaProducto implements Serializable {
    @Id
    @Column(name = "farmacia_id",nullable = false)
    private Long farmaciaId;

    @Id
    @Column(name = "producto_id", nullable=false)
    private Long productoId;


    public FarmaciaProducto(Long farmaciaId, Long productoId) {
        this.farmaciaId = farmaciaId;
        this.productoId = productoId;
    }
    public FarmaciaProducto(){

    }


    public Long getFarmaciaId() {
        return farmaciaId;
    }

    public void setFarmaciaId(Long farmaciaId) {
        this.farmaciaId = farmaciaId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
}

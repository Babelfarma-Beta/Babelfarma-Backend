package pe.com.babelfarma.babelfarmabackend.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="venta")
public class Venta {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Date fecha;

    @Column(name="id_cliente", nullable=false)
    private Long idCliente;

    @Column(name="id_farmacia", nullable=false)
    private Long idFarmacia;

    @Column(name="id_producto", nullable=false)
    private Long idProducto;

    @Column(name="product_name", nullable=false)
    private String productName;

    private float precioUnit;

    private int cantidad;

    private float precioTotal;

    public Venta(Date fecha, Long idCliente, Long idFarmacia, Long idProducto, String productName, float precioUnit, int cantidad, float precioTotal) {
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.idFarmacia = idFarmacia;
        this.idProducto = idProducto;
        this.productName= productName;
        this.precioUnit = precioUnit;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }

    public Venta(){

    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(Long idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public float getPrecioUnit() {
        return precioUnit;
    }

    public void setPrecioUnit(float precioUnit) {
        this.precioUnit = precioUnit;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }
}

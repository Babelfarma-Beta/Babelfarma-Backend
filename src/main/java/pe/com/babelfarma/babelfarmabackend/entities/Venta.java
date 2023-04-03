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

    @ManyToOne
    @JoinColumn(name="id_cliente", nullable=false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name="id_farmacia", nullable=false)
    private Farmacia farmacia;

    @ManyToOne
    @JoinColumn(name="id_producto", nullable=false)
    private Producto producto;

    public Venta() {

    }

    public Producto getProducto() {
        return producto;
    }

    float precioUnit;
    int cantidad;

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

    float precioTotal;

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta( Date fecha, Cliente cliente, Farmacia farmacia, Producto producto, float precioUnit, int cantidad, float precioTotal) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.farmacia = farmacia;
        this.producto = producto;
        this.precioUnit = precioUnit;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }

    public Farmacia getFarmacia() {
        return farmacia;
    }

    public Long getFarmaciaId(){
        return farmacia.getId();
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    public Long getId() {
        return id;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

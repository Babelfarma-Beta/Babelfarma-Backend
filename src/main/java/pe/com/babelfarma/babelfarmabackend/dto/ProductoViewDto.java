package pe.com.babelfarma.babelfarmabackend.dto;

public class ProductoViewDto {
    private Long id;
    private String nombre;
    private int stock;
    private double precio;
    private String descripcion;
    private byte[] picture;
    private String categoria;
    private String farmacia;

    public ProductoViewDto(Long id, String nombre, int stock, double precio, String descripcion, byte[] picture, String categoria, String farmacia) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
        this.descripcion = descripcion;
        this.picture = picture;
        this.categoria = categoria;
        this.farmacia = farmacia;
    }

    public ProductoViewDto(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

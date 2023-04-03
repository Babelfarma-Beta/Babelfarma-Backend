package pe.com.babelfarma.babelfarmabackend.entities;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "distrito")
public class Distrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreDistrito;


    public Distrito() {
    }

    public Distrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }
}

package pe.com.babelfarma.babelfarmabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.babelfarma.babelfarmabackend.model.Categoria;
import pe.com.babelfarma.babelfarmabackend.service.CategoriaService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;
    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> getAllCategorias(){
        List<Categoria> categorias = categoriaService.findAll();

        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("/categorias/buscarcategoria/{categoria}")
    public ResponseEntity<List<Categoria>> getCategoriaSearch(@PathVariable("categoria") String categoria){
        List<Categoria> categorias = categoriaService.findCategoriaSQL(categoria);
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @PostMapping("/categorias")
    public ResponseEntity<Categoria> addCategoria(@RequestBody Categoria categoria){
        Categoria newCategoria=
                categoriaService.save(
                        new Categoria(
                                categoria.getCategoria()
                        )
                );
        return new ResponseEntity<>(newCategoria, HttpStatus.CREATED);
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria> findById(
            @PathVariable("id") Long id){
        Categoria categoria = categoriaService.getCategoriaById(id);

        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }

}

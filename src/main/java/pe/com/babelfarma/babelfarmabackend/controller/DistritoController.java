package pe.com.babelfarma.babelfarmabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.babelfarma.babelfarmabackend.model.Distrito;
import pe.com.babelfarma.babelfarmabackend.exception.ResourceNotFoundException;
import pe.com.babelfarma.babelfarmabackend.service.DistritoService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class DistritoController {
    @Autowired
    private DistritoService distritoService;
    @GetMapping("/distritos")
    public ResponseEntity<List<Distrito>> getAllDistritos(){
        List<Distrito> distritos = distritoService.findAll();
        return new ResponseEntity<>(distritos, HttpStatus.OK);
    }
    @GetMapping("/distritos/{id}")
    public ResponseEntity<Distrito> findById(@PathVariable("id") Long id){
        Distrito distrito = distritoService.findByIdJPQL(id);
        return new ResponseEntity<>(distrito, HttpStatus.OK);
    }
    @PostMapping("/distritos")
    public ResponseEntity<Distrito> createDistrito(@RequestBody Distrito distrito){
        Distrito newDistrito =
                distritoService.save(new Distrito(
                        distrito.getNombreDistrito()
                )
        );
        return new ResponseEntity<>(newDistrito, HttpStatus.CREATED);
    }
    @PutMapping("distritos/{id}")
    public ResponseEntity<Distrito> updateDistrito(
            @PathVariable("id") Long id,
            @RequestBody Distrito distrito){
        Distrito distritoUpdate = distritoService.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encontró el distrito con id: " + id));
        distritoUpdate.setNombreDistrito(distrito.getNombreDistrito());
        return new ResponseEntity<>(distritoService.save(distritoUpdate), HttpStatus.OK);
    }
    @DeleteMapping("distritos/{id}")
    public ResponseEntity<HttpStatus> deleteDistrito(@PathVariable("id") Long id){
        distritoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

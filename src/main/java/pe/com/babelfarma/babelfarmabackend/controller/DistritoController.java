package pe.com.babelfarma.babelfarmabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.babelfarma.babelfarmabackend.entities.Distrito;
import pe.com.babelfarma.babelfarmabackend.exception.ResourceNotFoundException;
import pe.com.babelfarma.babelfarmabackend.repository.DistritoRepository;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class DistritoController {
    @Autowired
    private DistritoRepository distritoRepository;
    @GetMapping("/distritos")
    private ResponseEntity<List<Distrito>> getAllDistritos(){
        List<Distrito> distritos = distritoRepository.findAll();
        return new ResponseEntity<List<Distrito>>(distritos, HttpStatus.OK);
    }
    @GetMapping("/distritos/{id}")
    public ResponseEntity<Distrito> findById(@PathVariable("id") Long id){
        Distrito distrito = distritoRepository.findByIdJPQL(id);
        return new ResponseEntity<Distrito>(distrito, HttpStatus.OK);
    }
    @PostMapping("/distritos")
    private ResponseEntity<Distrito> createDistrito(@RequestBody Distrito distrito){
        Distrito newDistrito =
                distritoRepository.save(new Distrito(
                        distrito.getNombreDistrito()
                )
        );
        return new ResponseEntity<Distrito>(newDistrito, HttpStatus.CREATED);
    }
    @PutMapping("distritos/{id}")
    public ResponseEntity<Distrito> updateDistrito(
            @PathVariable("id") Long id,
            @RequestBody Distrito distrito){
        Distrito distritoUpdate = distritoRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encontró el distrito con id: " + id));
        distritoUpdate.setNombreDistrito(distrito.getNombreDistrito());
        return new ResponseEntity<Distrito>(distritoRepository.save(distritoUpdate), HttpStatus.OK);
    }
    @DeleteMapping("distritos/{id}")
    public ResponseEntity<HttpStatus> deleteDistrito(@PathVariable("id") Long id){
        distritoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

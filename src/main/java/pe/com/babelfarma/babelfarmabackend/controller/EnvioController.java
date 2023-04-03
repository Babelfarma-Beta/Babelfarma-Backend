package pe.com.babelfarma.babelfarmabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.babelfarma.babelfarmabackend.repository.EnvioRepository;
import pe.com.babelfarma.babelfarmabackend.entities.Envio;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class EnvioController {
    @Autowired
    private EnvioRepository envioRepository;
    @GetMapping("/envios")
    public ResponseEntity<List<Envio>> getAllEnvios(){
        List<Envio> envios = envioRepository.findAll();
        return new ResponseEntity<List<Envio>>(envios, HttpStatus.OK);
    }
    @GetMapping("/envios/{id}")
    public ResponseEntity<Envio> findById(@PathVariable("id") Long id){
        Envio envio = envioRepository.findByIdJPQL(id);
        return new ResponseEntity<Envio>(envio, HttpStatus.OK);
    }
    /*
    @GetMapping("/envios/{direccion}")
    public ResponseEntity<List<Envio>> findByDireccionEnvioJPQL(@PathVariable("direccion") String direccion){
        List<Envio> envios = envioRepository.findByDireccionEnvioJPQL(direccion);
        return new ResponseEntity<List<Envio>>(envios, HttpStatus.OK);
    }
     */
    @PostMapping("/envios")
    public ResponseEntity<Envio> createEnvio(@RequestBody Envio envio){
        Envio newEnvio =
                envioRepository.save(new Envio(
                    envio.getNombreEncargado(),
                    envio.getApellidoEncargado(),
                    envio.getCelular(),
                    envio.getVenta()
                )
        );
        return new ResponseEntity<Envio>(newEnvio, HttpStatus.CREATED);
    }
    @PutMapping("/envios")
    public ResponseEntity<Envio> updateEnvio(
            @PathVariable("id") Long id,
            @RequestBody Envio envio){
        Envio envioUpdate = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el envío con id: " + id));
        envioUpdate.setNombreEncargado(envio.getNombreEncargado());
        envioUpdate.setApellidoEncargado(envio.getApellidoEncargado());
        envioUpdate.setCelular(envio.getCelular());
        return new ResponseEntity<Envio>(envioRepository.save(envioUpdate), HttpStatus.OK);
    }
    @DeleteMapping("/envios/{id}")
    public ResponseEntity<HttpStatus> deleteEnvio(@PathVariable("id") Long id){
        envioRepository.deleteById(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}

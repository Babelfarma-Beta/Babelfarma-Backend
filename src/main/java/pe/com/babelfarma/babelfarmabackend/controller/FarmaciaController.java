package pe.com.babelfarma.babelfarmabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pe.com.babelfarma.babelfarmabackend.model.Farmacia;
import pe.com.babelfarma.babelfarmabackend.exception.ResourceNotFoundException;
import pe.com.babelfarma.babelfarmabackend.service.FarmaciaService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class FarmaciaController {

    @Autowired
    private FarmaciaService farmaciaService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/farmacias")
    public ResponseEntity<List<Farmacia>> getAllFarmacias(){
        List<Farmacia> farmacias = farmaciaService.findAll();
        return new ResponseEntity<>(farmacias, HttpStatus.OK);
    }
    @GetMapping("/farmacias/buscarporproducto/{id}")
    public ResponseEntity<Farmacia> findByProducto(
            @PathVariable("id") Long id){
        Farmacia farmacia = farmaciaService.farmaciaPorProducto(id);
        return new ResponseEntity<>(farmacia, HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    @GetMapping("/farmacias/ruc/{ruc}/correo/{correo}")
    public ResponseEntity<Farmacia> findByCorreoYContraseña(@PathVariable("ruc") Long ruc,
                                                            @PathVariable("correo") String correoContacto){
        Farmacia farmacia = farmaciaService.findByRucyCorreo(ruc, correoContacto);
        return new ResponseEntity<>(farmacia, HttpStatus.OK);
    }
    @GetMapping("/farmacias/buscarid/{buscarid}")
    public ResponseEntity<Farmacia> findById(
            @PathVariable("buscarid") Long id){
       Farmacia farmacia = farmaciaService.findByIdJPQL(id);

        return new ResponseEntity<>(farmacia, HttpStatus.OK);
    }
    @GetMapping("/farmacias/buscardireccion/{direccion}")
    public ResponseEntity<List<Farmacia>> findByDireccion(
            @PathVariable("direccion") String direccion
    ){

        List<Farmacia> farmacias = farmaciaService.findByDireccionContainingSQL(direccion);

        return new ResponseEntity<>(farmacias, HttpStatus.OK);
    }

    @GetMapping("/farmacias/buscarnombrefarmacia/{nombre}")
    public ResponseEntity<List<Farmacia>> findByNombreEstablecimiento(
            @PathVariable("nombre") String nombreEstablecimiento
    ){

        List<Farmacia> farmacias = farmaciaService.findByNombreEstablecimientoContainingSQL(nombreEstablecimiento);

        return new ResponseEntity<>(farmacias, HttpStatus.OK);
    }
    @GetMapping("/farmacias/buscarpordistrito/{distrito}")
    public ResponseEntity<List<Farmacia>> findByDistrito(
            @PathVariable("distrito") String distrito
    ){

        List<Farmacia> farmacias = farmaciaService.findByDistritoContainingJPQL(distrito);

        return new ResponseEntity<>(farmacias, HttpStatus.OK);
    }
    @GetMapping("/farmacias/productos")
    public ResponseEntity<List<String>> findProductosByStock(){

        List<String> farmacias = farmaciaService.findProducsByStock();

        return new ResponseEntity<>(farmacias, HttpStatus.OK);
    }
    @PostMapping("/farmacias")
    public ResponseEntity<Farmacia> createFarmacia(@RequestBody Farmacia farmacia){

        String hashedPassword = passwordEncoder.encode(farmacia.getContraseña());
        farmacia.setContraseña(hashedPassword);
        String correo = farmacia.getCorreoContacto();
        if(farmaciaService.existsFarmaciaByCorreoContacto(correo)) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        else {
            Farmacia newFarmacia =
                    farmaciaService.save(new Farmacia(
                                    farmacia.getRuc(),
                                    farmacia.getNombresDuenio(),
                                    farmacia.getApellidosDuenio(),
                                    farmacia.getNombreEstablecimiento(),
                                    farmacia.getDireccion(),
                                    farmacia.getCorreoContacto(),
                                    farmacia.getTelefonoContacto(),
                                    farmacia.getDistrito(),
                                    farmacia.getRole(),
                                    farmacia.getContraseña()
                            )
                    );
            return new ResponseEntity<>(newFarmacia, HttpStatus.CREATED);
        }
    }
    @PutMapping("/farmacias/{id}")
    public ResponseEntity<Farmacia> updateFarmacia(
            @PathVariable("id") Long id,
            @RequestBody Farmacia farmacia){
        Farmacia farmaciaUpdate = farmaciaService.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encontró la farmacia con id: " + id));
        farmaciaUpdate.setRuc(farmacia.getRuc());
        farmaciaUpdate.setNombresDuenio(farmacia.getNombresDuenio());
        farmaciaUpdate.setApellidosDuenio(farmacia.getApellidosDuenio());
        farmaciaUpdate.setNombreEstablecimiento(farmacia.getNombreEstablecimiento());
        farmaciaUpdate.setDireccion(farmacia.getDireccion());
        farmaciaUpdate.setCorreoContacto(farmacia.getCorreoContacto());
        farmaciaUpdate.setTelefonoContacto(farmacia.getTelefonoContacto());
        farmaciaUpdate.setDistrito(farmacia.getDistrito());
        farmaciaUpdate.setRole(farmacia.getRole());
        String password = farmacia.getContraseña();
        if (password != null && !password.isEmpty() && !password.equals(farmaciaUpdate.getContraseña())) {
            farmaciaUpdate.setContraseña(passwordEncoder.encode(password));
        }

        return new ResponseEntity<>(farmaciaService.save(farmaciaUpdate), HttpStatus.OK);
    }
    @DeleteMapping("/farmacias/{id}")
    public ResponseEntity<HttpStatus> deleteFarmacia(@PathVariable("id") Long id){
        farmaciaService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional(readOnly = true)
    @GetMapping("/farmacias/correo/{correo}/contraseña/{contraseña}")
    public ResponseEntity<Farmacia> findByCorreoAndContraseña(@PathVariable("correo") String correo,
                                                             @PathVariable("contraseña") String contraseña){

        Farmacia farmacia = farmaciaService.findByCorreoContacto(correo);

        if (farmacia == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(contraseña, farmacia.getContraseña())) {
            return new ResponseEntity<>(farmacia, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}

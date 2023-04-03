package pe.com.babelfarma.babelfarmabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pe.com.babelfarma.babelfarmabackend.entities.Farmacia;
import pe.com.babelfarma.babelfarmabackend.exception.ResourceNotFoundException;
import pe.com.babelfarma.babelfarmabackend.repository.VentaRepository;
import pe.com.babelfarma.babelfarmabackend.entities.Venta;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class VentaController {
    @Autowired
    private VentaRepository ventaRepository;
    @GetMapping("/ventas")
    public ResponseEntity<List<Venta>> getAllVentas(){
        List<Venta> ventas = ventaRepository.findAll();
        return new ResponseEntity<List<Venta>>(ventas, HttpStatus.OK);
    }
    @Transactional(readOnly=true)
    @GetMapping("/ventas/buscarporfarmacia/{farmaciaId}")
    public ResponseEntity<List<Venta>> getVentasByFarmaciaId(
            @PathVariable("farmaciaId") Long id
    ){
        List<Venta> ventas = ventaRepository.findByFarmaciaId(id);

        return new ResponseEntity<List<Venta>>(ventas, HttpStatus.OK);
    }

    @Transactional(readOnly=true)
    @GetMapping("/ventas/buscarporcliente/{nombre}/{idFarmacia}")
    public ResponseEntity<List<Venta>> getVentasByNombreCliente(
            @PathVariable("nombre") String nombre,
            @PathVariable("idFarmacia") Long idFarmacia
    ){
        List<Venta> ventas = ventaRepository.findByNombreCliente(nombre,idFarmacia);
        return new ResponseEntity<List<Venta>>(ventas, HttpStatus.OK);
    }

    @Transactional(readOnly=true)
    @GetMapping("/ventas/buscarporcliente/{idCliente}")
    public ResponseEntity<List<Venta>> getVentasByIdCliente(
            @PathVariable("idCliente") Long idCliente
    ){
        List<Venta> ventas = ventaRepository.findByIdCliente(idCliente);
        return new ResponseEntity<List<Venta>>(ventas, HttpStatus.OK);
    }

    @Transactional(readOnly=true)
    @GetMapping("/ventas/buscarpormes/{mes}/{idFarmacia}")
    public ResponseEntity<List<Venta>> getVentasByMes(
            @PathVariable("mes") int mes,
            @PathVariable("idFarmacia") Long idFarmacia
    ){
        List<Venta> ventas = ventaRepository.findByMes(mes,idFarmacia);
        return new ResponseEntity<List<Venta>>(ventas, HttpStatus.OK);
    }


    @PostMapping("/ventas")
    public ResponseEntity<Venta> createVenta(@RequestBody Venta venta){
        Venta newVenta =
                ventaRepository.save(new Venta(
                                venta.getFecha(),
                                venta.getCliente(),
                                venta.getFarmacia(),
                                venta.getProducto(),
                                venta.getPrecioUnit(),
                                venta.getCantidad(),
                                venta.getPrecioTotal()
                        )
                );
        return new ResponseEntity<Venta>(newVenta, HttpStatus.CREATED);

    }

    //put
    @PutMapping("/ventas/{id}")
    public ResponseEntity<Venta> updateVenta(
            @PathVariable("id") Long id,
            @RequestBody Venta venta){
        Venta ventaUpdate = ventaRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encontró el cliente con id: " + id));
        ventaUpdate.setFecha(venta.getFecha());
        return new ResponseEntity<Venta>(ventaRepository.save(ventaUpdate), HttpStatus.OK);
    }
    //deletee
    @DeleteMapping("/ventas/{id}")
    public ResponseEntity<HttpStatus> deleteVenta(@PathVariable("id") Long id){
        ventaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}



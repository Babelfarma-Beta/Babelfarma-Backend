package pe.com.babelfarma.babelfarmabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pe.com.babelfarma.babelfarmabackend.dto.VentaViewDto;
import pe.com.babelfarma.babelfarmabackend.exception.ResourceNotFoundException;
import pe.com.babelfarma.babelfarmabackend.model.Venta;
import pe.com.babelfarma.babelfarmabackend.service.VentaService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class VentaController {
    @Autowired
    private VentaService ventaService;
    @GetMapping("/ventas")
    public ResponseEntity<List<Venta>> getAllVentas(){
        List<Venta> ventas = ventaService.findAll();
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }
    @Transactional(readOnly=true)
    @GetMapping("/ventas/buscarporfarmacia/{farmaciaId}")
    public ResponseEntity<List<VentaViewDto>> getVentasByFarmaciaId(
            @PathVariable("farmaciaId") Long id
    ){
        List<VentaViewDto> ventas = ventaService.findByFarmaciaId(id);

        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    @Transactional(readOnly=true)
    @GetMapping("/ventas/buscarporcliente/{nombre}/{idFarmacia}")
    public ResponseEntity<List<VentaViewDto>> getVentasByNombreCliente(
            @PathVariable("nombre") String nombre,
            @PathVariable("idFarmacia") Long idFarmacia
    ){
        List<VentaViewDto> ventas = ventaService.findByNombreCliente(nombre,idFarmacia);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    @Transactional(readOnly=true)
    @GetMapping("/ventas/buscarporcliente/{idCliente}")
    public ResponseEntity<List<Venta>> getVentasByIdCliente(
            @PathVariable("idCliente") Long idCliente
    ){
        List<Venta> ventas = ventaService.findByIdCliente(idCliente);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    @Transactional(readOnly=true)
    @GetMapping("/ventas/buscarpormes/{mes}/{idFarmacia}")
    public ResponseEntity<List<VentaViewDto>> getVentasByMes(
            @PathVariable("mes") int mes,
            @PathVariable("idFarmacia") Long idFarmacia
    ){
        List<VentaViewDto> ventas = ventaService.findByMes(mes,idFarmacia);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    @PostMapping("/ventas")
    public ResponseEntity<Venta> createVenta(@RequestBody Venta venta){
        Venta newVenta =
                ventaService.save(new Venta(
                                venta.getFecha(),
                                venta.getIdCliente(),
                                venta.getIdFarmacia(),
                                venta.getIdProducto(),
                                venta.getProductName(),
                                venta.getPrecioUnit(),
                                venta.getCantidad(),
                                venta.getPrecioTotal()
                        )
                );
        return new ResponseEntity<>(newVenta, HttpStatus.CREATED);
    }

    @PutMapping("/ventas/{id}")
    public ResponseEntity<Venta> updateVenta(
            @PathVariable("id") Long id,
            @RequestBody Venta venta){
        Venta ventaUpdate = ventaService.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encontró el cliente con id: " + id));
        ventaUpdate.setFecha(venta.getFecha());
        return new ResponseEntity<>(ventaService.save(ventaUpdate), HttpStatus.OK);
    }


    @DeleteMapping("/ventas/{id}")
    public ResponseEntity<HttpStatus> deleteVenta(@PathVariable("id") Long id){
        ventaService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}



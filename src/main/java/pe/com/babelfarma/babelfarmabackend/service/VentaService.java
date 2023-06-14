package pe.com.babelfarma.babelfarmabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.babelfarma.babelfarmabackend.dto.VentaViewDto;
import pe.com.babelfarma.babelfarmabackend.model.Venta;
import pe.com.babelfarma.babelfarmabackend.repository.VentaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    public List<VentaViewDto> findByFarmaciaId(Long id){
        return ventaRepository.findByFarmaciaId(id);
    }

    public List<VentaViewDto> findByNombreCliente(String nombre, Long id){
        return ventaRepository.findByNombreCliente(nombre,id);
    }

    public List<Venta> findByIdCliente(Long id){
        return ventaRepository.findByIdCliente(id);
    }

    public List<VentaViewDto> findByMes(Integer month, Long id){
        return ventaRepository.findByMes(month,id);
    }

    public List<Venta> findAll(){
        return ventaRepository.findAll();
    }

    public Venta save(Venta venta){
        return ventaRepository.save(venta);
    }

    public Optional<Venta> findById(Long id){
        return ventaRepository.findById(id);
    }

    public void deleteById(Long id){
        ventaRepository.deleteById(id);
    }

}

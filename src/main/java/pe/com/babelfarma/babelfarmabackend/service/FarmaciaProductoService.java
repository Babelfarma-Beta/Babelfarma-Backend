package pe.com.babelfarma.babelfarmabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.babelfarma.babelfarmabackend.model.FarmaciaProducto;
import pe.com.babelfarma.babelfarmabackend.repository.FarmaciaProductoRepository;

@Service
public class FarmaciaProductoService {
    @Autowired
    private FarmaciaProductoRepository farmaciaProductoRepository;

    public FarmaciaProducto save(FarmaciaProducto farmaciaProducto){
        return farmaciaProductoRepository.save(farmaciaProducto);
    }
}

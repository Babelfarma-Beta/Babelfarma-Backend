package pe.com.babelfarma.babelfarmabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.babelfarma.babelfarmabackend.dto.ProductoViewDto;
import pe.com.babelfarma.babelfarmabackend.model.Producto;
import pe.com.babelfarma.babelfarmabackend.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoViewDto> findProductoByNameSQL(String producto){
        return productoRepository.findProductoByNameSQL(producto);
    }

    public List<ProductoViewDto> findProductoByCategoria(String categoria){
        return productoRepository.findProductoByCategoria(categoria);
    }

    public Producto getById(Long id){
        return productoRepository.getById(id);
    }

    public List<Producto> ListProductoStockJPQL(){
        return productoRepository.ListProductoStockJPQL();
    }

    public List<ProductoViewDto> ListProductoPrecioJPQL(){
        return productoRepository.ListProductoPrecioJPQL();
    }

    public List<Producto> ListarProductoCadaFarmacia(Long id){
        return productoRepository.ListarProductoCadaFarmacia(id);
    }

    public  List<Producto> ListarProductoCadaFarmaciaYActivo(Long id){
        return productoRepository.ListarProductoCadaFarmaciaYActivo(id);
    }

    public List<Producto> ListarProductoCadaFarmaciaYNoActivo(Long id){
        return productoRepository.ListarProductoCadaFarmaciaYNoActivo(id);
    }

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    public Optional<Producto> findById(Long id){
        return productoRepository.findById(id);
    }

    public void deleteById(Long id){
        productoRepository.deleteById(id);
    }

}

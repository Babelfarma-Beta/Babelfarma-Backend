package pe.com.babelfarma.babelfarmabackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.com.babelfarma.babelfarmabackend.dto.ProductoViewDto;
import pe.com.babelfarma.babelfarmabackend.model.Categoria;
import pe.com.babelfarma.babelfarmabackend.model.FarmaciaProducto;
import pe.com.babelfarma.babelfarmabackend.model.Producto;
import pe.com.babelfarma.babelfarmabackend.exception.ResourceNotFoundException;
import pe.com.babelfarma.babelfarmabackend.service.CategoriaService;
import pe.com.babelfarma.babelfarmabackend.service.FarmaciaProductoService;
import pe.com.babelfarma.babelfarmabackend.service.ProductoService;
import pe.com.babelfarma.babelfarmabackend.util.Util;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")

public class ProductoController {

    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private FarmaciaProductoService farmaciaProductoService;

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getAllProductos(){
        List<Producto> productos = new ArrayList<>();
        List<Producto> productosAux;

        productosAux=productoService.findAll();
        return getListResponseEntity(productos, productosAux);
    }

    @Transactional(readOnly=true)
    @GetMapping("/productos/precio")
    public ResponseEntity<List<ProductoViewDto>> getProductosPrecio(){

        List<ProductoViewDto> productos= new ArrayList<>();
        List<ProductoViewDto> productosAux;

        productosAux=productoService.ListProductoPrecioJPQL();
        return getListResponseEntityProductoView(productos, productosAux);
    }

    @GetMapping("/productos/id/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable("id") Long id){
        Producto producto=productoService.getById(id);

        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @PostMapping("/productosregistrados/{idFarmacia}")
    @Transactional
    public ResponseEntity<Producto> createProducto(
            @RequestParam("nombre") String nombre,
            @RequestParam("stock") int stock,
            @RequestParam("precio") double precio,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("picture") MultipartFile picture,
            @PathVariable("idFarmacia") Long idFarmacia,
            @RequestParam("categoryId") Long categoryID) throws IOException {

        Producto product = new Producto();
        product.setNombre(nombre);
        product.setStock(stock);
        product.setPrecio(precio);
        product.setDescripcion(descripcion);
        product.setPicture(Util.compressZLib(picture.getBytes()));
        product.setStatus("1");

        Categoria category = categoriaService.findById(categoryID)
                .orElseThrow(()-> new ResourceNotFoundException("Not found category with id="+categoryID));

        if( category!=null) {
            product.setCategoria(category);
        }

        Producto newProducto=
                productoService.save(product);
        farmaciaProductoService.save(
                new FarmaciaProducto(
                        idFarmacia,
                        newProducto.getId()
                )
        );

        return new ResponseEntity<>(newProducto, HttpStatus.CREATED);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable("id") Long id,
                                                   @RequestBody Producto producto){
        Producto productoUpdate = productoService.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encontró el producto con id: " + id));
        productoUpdate.setNombre(producto.getNombre());
        productoUpdate.setStock(producto.getStock());
        productoUpdate.setPrecio(producto.getPrecio());
        productoUpdate.setDescripcion(producto.getDescripcion());
        productoUpdate.setCategoria(producto.getCategoria());
        productoUpdate.setStatus(producto.getStatus());
        return new ResponseEntity<>(productoService.save(productoUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<HttpStatus> deleteProducto(@PathVariable("id") Long id){
        productoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional(readOnly=true)
    @GetMapping("/productos/nombre/{producto}")
    public ResponseEntity<List<ProductoViewDto>> getProductosSearch(@PathVariable("producto") String p){
        List<ProductoViewDto> productos= new ArrayList<>();
        List<ProductoViewDto> productosAux;

        productosAux=productoService.findProductoByNameSQL(p);

        return getListResponseEntityProductoView(productos, productosAux);
    }

    @Transactional(readOnly=true)
    @GetMapping("/productos/categoria/{categoria}")
    public ResponseEntity<List<ProductoViewDto>> ListarPorCategoria(@PathVariable("categoria") String c){
        List<ProductoViewDto> productos= new ArrayList<>();
        List<ProductoViewDto> productosAux;

        productosAux=productoService.findProductoByCategoria(c);

        return getListResponseEntityProductoView(productos, productosAux);
    }

    @GetMapping("/productos/stock")
    public ResponseEntity<List<Producto>> getProductosStock(){
        List<Producto> productos=productoService.ListProductoStockJPQL();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @Transactional(readOnly=true)
    @GetMapping("/productos/farmacia/{id}")
    public ResponseEntity<List<Producto>> getProductoFarmacia(@PathVariable("id")long id, @RequestParam("status") String status){
        List<Producto> productos= new ArrayList<>();
        List<Producto> productosAux = new ArrayList<>();

        if(status.equals("Todos")) {
            productosAux = productoService.ListarProductoCadaFarmacia(id);
        }
        if(status.equals("Activos")){
            productosAux=productoService.ListarProductoCadaFarmaciaYActivo(id);
        }
        if(status.equals("Inactivos")) {
            productosAux = productoService.ListarProductoCadaFarmaciaYNoActivo(id);
        }

        return getListResponseEntity(productos, productosAux);
    }

    private ResponseEntity<List<Producto>> getListResponseEntity(List<Producto> productos, List<Producto> productosAux) {
        if(!productosAux.isEmpty()){
            productosAux.forEach(producto->{
                byte[]imageDescompressed = Util.decompressZLib(producto.getPicture());
                producto.setPicture(imageDescompressed);
                productos.add(producto);
            });
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    private ResponseEntity<List<ProductoViewDto>> getListResponseEntityProductoView(List<ProductoViewDto> productos, List<ProductoViewDto> productosAux) {
        if(!productosAux.isEmpty()){
            productosAux.forEach(producto->{
                byte[]imageDescompressed = Util.decompressZLib(producto.getPicture());
                producto.setPicture(imageDescompressed);
                productos.add(producto);
            });
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

}

package pe.com.babelfarma.babelfarmabackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.com.babelfarma.babelfarmabackend.entities.Categoria;
import pe.com.babelfarma.babelfarmabackend.entities.FarmaciaProducto;
import pe.com.babelfarma.babelfarmabackend.entities.Producto;
import pe.com.babelfarma.babelfarmabackend.exception.ResourceNotFoundException;
import pe.com.babelfarma.babelfarmabackend.repository.FarmaciaProductoRepository;
import pe.com.babelfarma.babelfarmabackend.repository.ProductoRepository;
import pe.com.babelfarma.babelfarmabackend.util.Util;
import pe.com.babelfarma.babelfarmabackend.repository.CategoriaRepository;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")

public class ProductoController {
    //@GetMapping -> get
    //@PostMapping -> post
    //@PutMapping -> put
    //@DeleteMapping -> delete

    //Inyectar dependencia

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private FarmaciaProductoRepository farmaciaProductoRepository;

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getAllProductos(){
        List<Producto> productos = new ArrayList<>();
        List<Producto> productosAux = new ArrayList<>();

        productosAux=productoRepository.findAll();

        if(productosAux.size()>0){
            productosAux.stream().forEach((p)->{
                byte[]imageDescompressed = Util.decompressZLib(p.getPicture());
                p.setPicture(imageDescompressed);
                productos.add(p);
            });
        }

        //List<Producto> productos=productoRepository.findAll();
        return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
    }

    @Transactional(readOnly=true)
    @GetMapping("/productos/precio")
    public ResponseEntity<List<Producto>> getProductosPrecio(){

        List<Producto> productos= new ArrayList<>();
        List<Producto> productosAux = new ArrayList<>();

        productosAux=productoRepository.ListProductoPrecioJPQL();


        if(productosAux.size()>0){
            productosAux.stream().forEach((p)->{
                byte[]imageDescompressed = Util.decompressZLib(p.getPicture());
                p.setPicture(imageDescompressed);
                productos.add(p);
            });
        }
        return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
    }

    @GetMapping("/productos/id/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable("id") Long id){
        Producto producto=productoRepository.getById(id);

        return new ResponseEntity<Producto>(producto, HttpStatus.OK);
    }

/*    @PostMapping("/productosregistrados/{idFarmacia}")
    public ResponseEntity<Producto> createProducto(
            @PathVariable("idFarmacia") Long idFarmacia,
            @RequestBody Producto producto){
        Producto newProducto=
                productoRepository.save(
                        new Producto(
                                producto.getNombre(),
                                producto.getStock(),
                                producto.getPrecio(),
                                producto.getDescripcion(),
                                producto.getCategoria()
                        )
                );
        farmaciaProductoRepository.save(
                new FarmaciaProducto(
                        idFarmacia,
                        newProducto.getId()
                )
        );

        return new ResponseEntity<Producto>(newProducto, HttpStatus.CREATED);
    }*/
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

        Categoria category = categoriaRepository.findById(categoryID)
                .orElseThrow(()-> new ResourceNotFoundException("Not found category with id="+categoryID));

        if( category!=null) {
            product.setCategoria(category);
        }

        Producto newProducto=
                productoRepository.save(product);
        farmaciaProductoRepository.save(
                new FarmaciaProducto(
                        idFarmacia,
                        newProducto.getId()
                )
        );

        return new ResponseEntity<Producto>(newProducto, HttpStatus.CREATED);
    }




    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable("id") Long id,
                                                   @RequestBody Producto producto){
        Producto productoUpdate = productoRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encontró el producto con id: " + id));
        productoUpdate.setNombre(producto.getNombre());
        productoUpdate.setStock(producto.getStock());
        productoUpdate.setPrecio(producto.getPrecio());
        productoUpdate.setDescripcion(producto.getDescripcion());
        productoUpdate.setCategoria(producto.getCategoria());
        return new ResponseEntity<Producto>(productoRepository.save(productoUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<HttpStatus> deleteProducto(@PathVariable("id") Long id){
        productoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional(readOnly=true)
    @GetMapping("/productos/nombre/{producto}")
    public ResponseEntity<List<Producto>> getProductosSearch(@PathVariable("producto") String p){
        List<Producto> productos= new ArrayList<>();
        List<Producto> productosAux = new ArrayList<>();

        productosAux=productoRepository.findProductoByNameSQL(p);

        if(productosAux.size()>0){
            productosAux.stream().forEach((producto)->{
                byte[]imageDescompressed = Util.decompressZLib(producto.getPicture());
                producto.setPicture(imageDescompressed);
                productos.add(producto);
            });
        }
        return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
    }


    @Transactional(readOnly=true)
    @GetMapping("/productos/categoria/{categoria}")
    public ResponseEntity<List<Producto>> ListarPorCategoria(@PathVariable("categoria") String c){
        List<Producto> productos= new ArrayList<>();
        List<Producto> productosAux = new ArrayList<>();

        productosAux=productoRepository.findProductoByCategoria(c);

        if(productosAux.size()>0){
            productosAux.stream().forEach((p)->{
                byte[]imageDescompressed = Util.decompressZLib(p.getPicture());
                p.setPicture(imageDescompressed);
                productos.add(p);
            });
        }
        return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
    }

    @GetMapping("/productos/stock")
    public ResponseEntity<List<Producto>> getProductosStock(){
        List<Producto> productos=productoRepository.ListProductoStockJPQL();
        return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
    }

    @Transactional(readOnly=true)
    @GetMapping("/productos/farmacia/{id}")
    public ResponseEntity<List<Producto>> getProductoFarmacia(@PathVariable("id")long id){
        List<Producto> productos= new ArrayList<>();
        List<Producto> productosAux = new ArrayList<>();

        productosAux=productoRepository.ListarProductoCadaFarmacia(id);

        if(productosAux.size()>0){
            productosAux.stream().forEach((p)->{
                byte[]imageDescompressed = Util.decompressZLib(p.getPicture());
                p.setPicture(imageDescompressed);
                productos.add(p);
            });
        }
        return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
    }

}

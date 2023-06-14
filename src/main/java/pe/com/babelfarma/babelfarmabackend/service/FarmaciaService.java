package pe.com.babelfarma.babelfarmabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.babelfarma.babelfarmabackend.model.Farmacia;
import pe.com.babelfarma.babelfarmabackend.repository.FarmaciaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FarmaciaService {
    @Autowired
    private FarmaciaRepository farmaciaRepository;

    public Farmacia findByIdJPQL(Long id){
        return farmaciaRepository.findByIdJPQL(id);
    }

    public List<Farmacia> findByDireccionContainingSQL(String direccion){
        return farmaciaRepository.findByDireccionContainingSQL(direccion);
    }

    public List<Farmacia> findByNombreEstablecimientoContainingSQL(String nombreEstablecimiento){
        return farmaciaRepository.findByNombreEstablecimientoContainingSQL(nombreEstablecimiento);
    }

    public List<Farmacia> findByDistritoContainingJPQL(String distrito){
        return farmaciaRepository.findByDistritoContainingJPQL(distrito);
    }

    public List<String> findProducsByStock(){
        return farmaciaRepository.findProducsByStock();
    }

    public Farmacia findByRucyCorreo(Long ruc, String correo){
        return farmaciaRepository.findByRucyCorreo(ruc,correo);
    }

    public Farmacia farmaciaPorProducto(Long id){
        return farmaciaRepository.farmaciaPorProducto(id);
    }

    public Farmacia findByCorreoContacto(String correoContacto){
        return farmaciaRepository.findByCorreoContacto(correoContacto);
    }

    public boolean existsFarmaciaByCorreoContacto(String correoContacto){
        return farmaciaRepository.existsFarmaciaByCorreoContacto(correoContacto);
    }

    public List<Farmacia> findAll(){
        return farmaciaRepository.findAll();
    }

    public Farmacia save(Farmacia farmacia){
        return farmaciaRepository.save(farmacia);
    }

    public Optional<Farmacia> findById(Long id){
        return farmaciaRepository.findById(id);
    }

    public void deleteById(Long id){
        farmaciaRepository.deleteById(id);
    }

}

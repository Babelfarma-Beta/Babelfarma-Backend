package pe.com.babelfarma.babelfarmabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.babelfarma.babelfarmabackend.model.Distrito;
import pe.com.babelfarma.babelfarmabackend.repository.DistritoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DistritoService {
    @Autowired
    private DistritoRepository distritoRepository;

    public Distrito findByIdJPQL(Long id){
        return distritoRepository.findByIdJPQL(id);
    }

    public List<Distrito> findAll(){
        return distritoRepository.findAll();
    }

    public Distrito save(Distrito distrito){
        return distritoRepository.save(distrito);
    }

    public Optional<Distrito> findById(Long id){
        return distritoRepository.findById(id);
    }

    public void deleteById(Long id){
        distritoRepository.deleteById(id);
    }

}

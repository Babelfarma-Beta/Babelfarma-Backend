package pe.com.babelfarma.babelfarmabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.babelfarma.babelfarmabackend.model.Categoria;
import pe.com.babelfarma.babelfarmabackend.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findCategoriaSQL(String aux){
        return categoriaRepository.findCategoriaSQL(aux);
    }

    public Categoria getCategoriaById(Long id){
        return categoriaRepository.getCategoriaById(id);
    }

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    public Categoria save(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    public Optional<Categoria> findById(Long id){
        return categoriaRepository.findById(id);
    }

}

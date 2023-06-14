package pe.com.babelfarma.babelfarmabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.babelfarma.babelfarmabackend.model.Cliente;
import pe.com.babelfarma.babelfarmabackend.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente findByIdJPQL(long id){
        return clienteRepository.findByIdJPQL(id);
    }

    public Cliente findByDniJPQL(int dni, String correo){
        return clienteRepository.findByDniJPQL(dni, correo);
    }

    public List<Cliente> findBySexoJPQL(String sexo){
        return clienteRepository.findBySexoJPQL(sexo);
    }

    public Cliente findByCorreo(String correo){
        return clienteRepository.findByCorreo(correo);
    }

    public boolean existsClienteByCorreo(String correo){
        return clienteRepository.existsClienteByCorreo(correo);
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente save(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> findById(Long id){
        return clienteRepository.findById(id);
    }

    public void deleteById(Long id){
        clienteRepository.deleteById(id);
    }

}

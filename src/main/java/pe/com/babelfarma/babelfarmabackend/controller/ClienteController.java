package pe.com.babelfarma.babelfarmabackend.controller;

import org.etsi.uri.x01903.v13.SignerRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pe.com.babelfarma.babelfarmabackend.exception.ResourceNotFoundException;
import pe.com.babelfarma.babelfarmabackend.repository.ClienteRepository;
import pe.com.babelfarma.babelfarmabackend.entities.Cliente;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;
    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> getAllClientes(){
        List<Cliente> clientes = clienteRepository.findAll();
        return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
    }
    @GetMapping("/clientes/id/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable("id") Long id){
        Cliente cliente = clienteRepository.findByIdJPQL(id);
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    @GetMapping("/clientes/dni/{dni}/correo/{correo}")
    public ResponseEntity<Cliente> findByDNINum(@PathVariable("dni") int dni,
                                                @PathVariable("correo") String correo){
        Cliente cliente = clienteRepository.findByDniJPQL(dni, correo);
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }
    @GetMapping("/clientes/sexo/{sexo}")
    public ResponseEntity<List<Cliente>> findBySexo(@PathVariable("sexo") String sexo){
        List<Cliente> clientes = clienteRepository.findBySexoJPQL(sexo);
        return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
    }
    /*
    @GetMapping("/clientes/{edadMin}-{edadMax}")
    public ResponseEntity<List<Cliente>> findByEdadSQL(@PathVariable("edadMin") int edadMin, @PathVariable("edadMax") int edadMax){
        List<Cliente> clientes = clienteRepository.findByEdadJPQL(edadMin, edadMax);
        if(clientes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    */
    @PostMapping("/clientes")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente){
        Cliente newCliente =
                clienteRepository.save(new Cliente(
                                cliente.getDni(),
                                cliente.getNombres(),
                                cliente.getApellidoPaterno(),
                                cliente.getApellidoMaterno(),
                                cliente.getSexo(),
                                cliente.getCorreo(),
                                cliente.getCelular(),
                                cliente.getFechaNacimiento(),
                                cliente.getDireccion(),
                                cliente.getDistrito(),
                                cliente.getRole(),
                                cliente.getContraseña()
                        )
                );
        return new ResponseEntity<Cliente>(newCliente, HttpStatus.CREATED);
    }
    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> updateCliente(
            @PathVariable("id") Long id,
            @RequestBody Cliente cliente){
        Cliente clienteUpdate = clienteRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encontró el cliente con id: " + id));
        clienteUpdate.setDni(cliente.getDni());
        clienteUpdate.setNombres(cliente.getNombres());
        clienteUpdate.setApellidoPaterno(cliente.getApellidoPaterno());
        clienteUpdate.setApellidoMaterno(cliente.getApellidoMaterno());
        clienteUpdate.setSexo(cliente.getSexo());
        clienteUpdate.setCorreo(cliente.getCorreo());
        clienteUpdate.setCelular(cliente.getCelular());
        clienteUpdate.setFechaNacimiento(cliente.getFechaNacimiento());
        clienteUpdate.setDireccion(cliente.getDireccion());
        clienteUpdate.setDistrito(cliente.getDistrito());
        clienteUpdate.setRole(cliente.getRole());
        clienteUpdate.setContraseña(cliente.getContraseña());
        return new ResponseEntity<Cliente>(clienteRepository.save(clienteUpdate), HttpStatus.OK);
    }




    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<HttpStatus> deleteCliente(@PathVariable("id") Long id){
        clienteRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

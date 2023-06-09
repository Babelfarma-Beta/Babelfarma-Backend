package pe.com.babelfarma.babelfarmabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pe.com.babelfarma.babelfarmabackend.exception.ResourceNotFoundException;
import pe.com.babelfarma.babelfarmabackend.model.Cliente;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pe.com.babelfarma.babelfarmabackend.service.ClienteService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> getAllClientes(){
        List<Cliente> clientes = clienteService.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    @GetMapping("/clientes/id/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable("id") Long id){
        Cliente cliente = clienteService.findByIdJPQL(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    @GetMapping("/clientes/dni/{dni}/correo/{correo}")
    public ResponseEntity<Cliente> findByDNINum(@PathVariable("dni") int dni,
                                                @PathVariable("correo") String correo){
        Cliente cliente = clienteService.findByDniJPQL(dni, correo);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/clientes/correo/{correo}/contraseña/{contraseña}")
    public ResponseEntity<Cliente> findByCorreoAndContraseña(@PathVariable("correo") String correo,
                                                             @PathVariable("contraseña") String contraseña){

        Cliente cliente = clienteService.findByCorreo(correo);

        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(contraseña, cliente.getContraseña())) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/clientes/sexo/{sexo}")
    public ResponseEntity<List<Cliente>> findBySexo(@PathVariable("sexo") String sexo){
        List<Cliente> clientes = clienteService.findBySexoJPQL(sexo);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @PostMapping("/clientes")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente){
        String hashedPassword = passwordEncoder.encode(cliente.getContraseña());
        cliente.setContraseña(hashedPassword);
        String correo = cliente.getCorreo();
        if(clienteService.existsClienteByCorreo(correo)) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        else {
            Cliente newCliente =
                    clienteService.save(new Cliente(
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
            return new ResponseEntity<>(newCliente, HttpStatus.CREATED);
        }
    }


    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> updateCliente(
            @PathVariable("id") Long id,
            @RequestBody Cliente cliente){
        Cliente clienteUpdate = clienteService.findById(id)
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

        String password = cliente.getContraseña();
        if (password != null && !password.isEmpty() && !password.equals(clienteUpdate.getContraseña())) {
            clienteUpdate.setContraseña(passwordEncoder.encode(password));
        }

        return new ResponseEntity<>(clienteService.save(clienteUpdate), HttpStatus.OK);
    }


    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<HttpStatus> deleteCliente(@PathVariable("id") Long id){
        clienteService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package pe.com.babelfarma.babelfarmabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.babelfarma.babelfarmabackend.entities.Cliente;
import pe.com.babelfarma.babelfarmabackend.entities.Role;
import pe.com.babelfarma.babelfarmabackend.repository.RoleRepository;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roles = roleRepository.findAll();
        return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
    }
    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> findById(@PathVariable("id") Long id){
        Role role = roleRepository.findByIdJPQL(id);
        return new ResponseEntity<Role>(role, HttpStatus.OK);
    }
    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        Role newRole =
                roleRepository.save(new Role(
                    role.getRole()
                )
        );
        return new ResponseEntity<Role>(newRole, HttpStatus.CREATED);
    }
}

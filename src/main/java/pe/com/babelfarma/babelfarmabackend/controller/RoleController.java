package pe.com.babelfarma.babelfarmabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.babelfarma.babelfarmabackend.model.Role;
import pe.com.babelfarma.babelfarmabackend.service.RoleService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roles = roleService.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> findById(@PathVariable("id") Long id){
        Role role = roleService.findByIdJPQL(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        Role newRole =
                roleService.save(new Role(
                    role.getRole()
                )
        );
        return new ResponseEntity<>(newRole, HttpStatus.CREATED);
    }
}

package pe.com.babelfarma.babelfarmabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.babelfarma.babelfarmabackend.model.Role;
import pe.com.babelfarma.babelfarmabackend.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findByIdJPQL(Long id){
        return roleRepository.findByIdJPQL(id);
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public Role save(Role role){
        return roleRepository.save(role);
    }
}

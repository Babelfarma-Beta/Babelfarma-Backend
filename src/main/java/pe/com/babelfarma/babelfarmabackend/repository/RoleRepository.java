package pe.com.babelfarma.babelfarmabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.com.babelfarma.babelfarmabackend.entities.Role;

public interface RoleRepository
    extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.id=?1")
    Role findByIdJPQL(long id);
    @Query("SELECT r FROM Role r WHERE r.role=?1")
    Role findByRoleJPQL(String role);
}

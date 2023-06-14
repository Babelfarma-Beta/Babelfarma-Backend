package pe.com.babelfarma.babelfarmabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.com.babelfarma.babelfarmabackend.model.Role;

public interface RoleRepository
    extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.id=?1")
    Role findByIdJPQL(Long id);
}

package kz.bitlab.G114security.repository;

import kz.bitlab.G114security.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  @Query("select r from Role r where r.name = 'USER'")
  Role findRoleUser();
}

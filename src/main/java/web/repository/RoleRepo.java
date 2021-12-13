package web.repository;
import org.springframework.stereotype.Repository;
import web.model.Role;

import java.util.List;

public interface RoleRepo {
    Role getRoleByName(String name);

    Role getRoleById(Long id);

    List<Role> allRoles();
}

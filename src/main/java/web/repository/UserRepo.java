package web.repository;

import org.springframework.stereotype.Repository;
import web.model.User;

import java.util.List;

public interface UserRepo {
    void addUser(User user);

    void deleteUser(Long id);

    void editUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    User getUserByUsername(String username);
}

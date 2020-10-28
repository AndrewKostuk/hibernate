package by.bsuir.Andrei.dao;

import by.bsuir.Andrei.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<User> {
    Optional<User> findByName(String firstName);
}

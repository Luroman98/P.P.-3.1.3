package ru.kata.spring.boot_security.demo.web.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.web.model.User;


import java.util.List;

public interface UserService {
    void add(User user);

    boolean saveUser(User user);

    List<User> listUsers();
    Object getUser(Long id);
    void update(Long id, User updateUser);

    @Transactional
    void updateUser(User user);

    void deleteUser(Long id);
//    boolean saveUser(User user);
}

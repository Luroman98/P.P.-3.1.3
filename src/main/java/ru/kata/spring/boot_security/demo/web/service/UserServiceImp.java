package ru.kata.spring.boot_security.demo.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.web.dao.UserDao;
import ru.kata.spring.boot_security.demo.web.model.User;

import java.util.List;

@Service
public class UserServiceImp implements  UserDetailsService, UserService {

   @Autowired
   private UserDao userDao;

   @Autowired
   BCryptPasswordEncoder bCryptPasswordEncoder;


   @Transactional
   @Override
   public void add(User user) {userDao.save(user);
   }
   @Override
   public boolean saveUser(User user) {
      if (userDao.findByUsername(user.getUsername()) != null) {
         return false;
      } else {
         user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
         userDao.save(user);
         return true;
      }
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return (List<User>) userDao.findAll();
   }
   @Transactional
   @Override
   public Object getUser(Long id) {
      return userDao.findById(id).get();
   }

   @Override
   public void update(Long id, User updateUser) {

   }

   @Transactional
   @Override
   public void updateUser(User user) {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      userDao.save(user);
   }
   @Transactional
   public void deleteUser(Long id) {
      userDao.deleteById(id);
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      User user = userDao.findByUsername(username);

      if (user == null) {
         throw new UsernameNotFoundException("User not found");
      }

      return user;
   }
}

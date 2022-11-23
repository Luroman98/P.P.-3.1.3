package ru.kata.spring.boot_security.demo.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.web.model.User;
import ru.kata.spring.boot_security.demo.web.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.web.service.UserServiceImp;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminsController {
    @Autowired
    private UserServiceImp userService;
    @Autowired
    private RoleServiceImp roleService;

    @GetMapping()
    public String printUsers(ModelMap model) {
        model.addAttribute("users", userService.listUsers());
        return "admin/printUsers";
    }

    @GetMapping("/new")
    public String createUserForm(@ModelAttribute("user") User user, ModelMap model) {
        model.addAttribute("roles", roleService.listAll());
        return "admin/newUser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user, @RequestParam("idRoles") List<Long> idRoles) {
        user.setRoles(roleService.getRole(idRoles));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.listAll());
        return "admin/updateUser";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("idRoles") List<Long> idRoles) {
        user.setRoles(roleService.getRole(idRoles));
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
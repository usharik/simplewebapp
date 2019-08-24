package ru.geekbrains.jsf;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class UsersBean implements Serializable {

    @EJB
    private UserServiceLocalBean userService;

    @Inject
    private RoleService roleRepository;

    private UserRepr user;

    private List<RoleRepr> roles;

    @PostConstruct
    public void init() {
        this.roles = roleRepository.getAllRoles();
    }

    public UserRepr getUser() {
        return user;
    }

    public void setUser(UserRepr user) {
        this.user = user;
    }

    public List<UserRepr> getAllUsers() {
        return userService.getAllUsers();
    }

    public String editUser(UserRepr user) {
        this.user = user;
        return "/user.xhtml?faces-redirect=true";
    }

    public void deleteUser(UserRepr user) {
        userService.delete(user.getId());
    }

    public String createUser() {
        this.user = new UserRepr();
        return "/user.xhtml?faces-redirect=true";
    }

    public String saveUser() {
        userService.merge(this.user);
        return "/users.xhtml?faces-redirect=true";
    }

    public List<RoleRepr> getAllRoles() {
        return roles;
    }
}

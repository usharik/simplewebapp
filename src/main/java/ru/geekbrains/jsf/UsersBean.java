package ru.geekbrains.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.sql.SQLException;
import java.util.Collection;

@SessionScoped
@ManagedBean
public class UsersBean {

    private Logger logger = LoggerFactory.getLogger(UsersBean.class);

    private UserRepository userRepository;

    private User user;

    @PostConstruct
    public void init() {
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        userRepository = (UserRepository) context.getAttribute("userRepository");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<User> getUserList() throws SQLException {
        return userRepository.getAllUsers();
    }

    public String editUser(User user) {
        this.user = user;
        return "/user.xhtml?faces-redirect=true";
    }

    public void deleteUser(User user) throws SQLException {
        userRepository.delete(user);
        //return "/users.xhtml?faces-redirect=true";
    }

    public String createUser() {
        logger.info("Create user");

        this.user = new User();
        return "/user.xhtml?faces-redirect=true";
    }

    public String saveUser() throws SQLException {
        logger.info("Save user");

        userRepository.save(user);
        return "/users.xhtml?faces-redirect=true";
    }
}

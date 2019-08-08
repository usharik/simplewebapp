package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named
public class UserRepository {

    private Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Inject
    private ServletContext servletContext;

    private Connection conn;

    public UserRepository() {
    }

    public UserRepository(Connection conn) throws SQLException {
        this.conn = conn;
        createTableIfNotExists(conn);
    }

    // !!! Поля с аннотацией @Inject можно использовать для инициализации других полей в методе с аннотацией @PostConstruct
    // !!! Никогда не делайте этого в конструкторе !!!
    @PostConstruct
    public void init() throws SQLException {
        this.conn = (Connection) servletContext.getAttribute("jdbcConnection");
        createTableIfNotExists(conn);
    }

    public void insert(User user) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into users(login, password) values (?, ?);")) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.execute();
        }
    }

    public void save(User user) throws SQLException {
        logger.info("Saving user");

        if (existsById(user.getId())) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "update users set login = ?, password = ? where id = ?;")) {
                stmt.setString(1, user.getLogin());
                stmt.setString(2, user.getPassword());
                stmt.setInt(3, user.getId());
                stmt.execute();
            }
            return;
        }
        insert(user);
    }

    public void delete(User user) throws SQLException {
        logger.info("Deleting user");

        try (PreparedStatement stmt = conn.prepareStatement(
                "delete from users where id = ?;")) {
            stmt.setInt(1, user.getId());
            stmt.execute();
        }
    }

    public User findByLogin(String login) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id, login, password from users where login = ?")) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        }
        return new User(-1, "", "");
    }

    public User findById(int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id, login, password from users where id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        }
        return null;
    }

    public boolean existsById(int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select 1 from users where id = ? limit 1")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> res = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, login, password from users");

            while (rs.next()) {
                res.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        }
        return res;
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("create table if not exists users (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    login varchar(25),\n" +
                    "    password varchar(25),\n" +
                    "    unique index uq_login(login)\n" +
                    ");");
        }
    }
}

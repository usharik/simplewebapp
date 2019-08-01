package ru.geekbrains.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        logger.info("Context initialization: " + context.getContextPath());

        String jdbcConnectionString = context.getInitParameter("jdbcConnectionString");
        String username = context.getInitParameter("username");
        String password = context.getInitParameter("password");

        if (isNotNullOrEmpty(jdbcConnectionString) || isNotNullOrEmpty(username)) {
            logger.error("Connection string and DB username must be specified");
            return;
        }

        try {
            Connection connection = DriverManager.getConnection(jdbcConnectionString, username, password);
            context.setAttribute("jdbcConnection", connection);
            UserRepository userRepository = new UserRepository(connection);
            context.setAttribute("userRepository", userRepository);

            if (userRepository.getAllUsers().size() == 0) {
                userRepository.insert(new User(-1, "first_user", "ppp"));
                userRepository.insert(new User(-1, "second_user", "ppp"));
            }
        } catch (SQLException e) {
            logger.error("", e);
        }
    }

    private boolean isNotNullOrEmpty(String str) {
        return str != null && str.isEmpty();
    }
}

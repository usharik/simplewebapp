package ru.geekbrains.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UsersServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(RequestListener.class);

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        userRepository = (UserRepository) context.getAttribute("userRepository");

        if (userRepository == null) {
            throw new ServletException("No repository in Servlet Context");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Get all users");
        String id = req.getParameter("id");
        if (id != null) {
            try {
                User user = userRepository.findById(Integer.parseInt(id));
                req.setAttribute("user", user);
                req.setAttribute("title", "User " + user.getLogin());
                req.getRequestDispatcher("WEB-INF/views/user.jsp").forward(req, resp);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
        try {
            List<User> users = userRepository.getAllUsers();
            req.setAttribute("users", users);
            req.setAttribute("title", "Users");
            req.getRequestDispatcher("WEB-INF/views/users.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = userRepository.findById(Integer.parseInt(req.getParameter("id")));
            user.setLogin(req.getParameter("username"));
            user.setPassword(req.getParameter("password"));
            userRepository.save(user);

            resp.sendRedirect(getServletContext().getContextPath() + "/users");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

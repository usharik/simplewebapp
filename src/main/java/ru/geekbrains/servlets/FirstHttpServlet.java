package ru.geekbrains.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FirstHttpServlet", urlPatterns = "/httpservlet/*")
public class FirstHttpServlet extends HttpServlet {

    /**
     * http://localhost:8080/simple-webapp/httpservlet/sdfsdfsdfsdf?a=b?c=d
     *
     * Context path: /simple-webapp
     * Servlet path: /httpservlet
     * Path info: /sdfsdfsdfsdf
     * Query string: a=b?c=d
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Request parameters</h1>");
        resp.getWriter().println("<p>Context path: " + getServletContext().getContextPath() + "</p>");
        resp.getWriter().println("<p>Servlet path: " + req.getServletPath()+ "</p>");
        resp.getWriter().println("<p>Path info: " + req.getPathInfo()+ "</p>");
        resp.getWriter().println("<p>Query string: " + req.getQueryString()+ "</p>");

        req.getParameterMap().forEach((key, value) -> {
            try {
                resp.getWriter().println("<p>" + key + " : " + value[0] + "</p>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //getServletContext().getRequestDispatcher("/firstservlet").include(req, resp);
        //getServletContext().getRequestDispatcher("/firstservlet").forward(req, resp);
        //resp.sendRedirect("https://ya.ru");
        resp.sendRedirect(req.getContextPath() + "/firstservlet");
    }
}

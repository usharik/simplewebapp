package ru.geekbrains.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class FirstServlet implements Servlet {

    private static final Logger logger = LoggerFactory.getLogger(FirstServlet.class);

    private transient ServletConfig config;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.config;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        logger.info("HTTP Method " + httpReq.getMethod());
        res.getWriter().println("<b>Hello World from Servlet!!!</b>");
        res.getWriter().println("<p>HTTP Method " + httpReq.getMethod() + "</p>");
        res.getWriter().println("<p>Context attribute: " + getServletConfig().getServletContext().getAttribute("someAppAttribute") + "</p>");
    }

    @Override
    public String getServletInfo() {
        return "First servlet";
    }

    @Override
    public void destroy() {

    }
}

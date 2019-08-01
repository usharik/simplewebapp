package ru.geekbrains.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener
public class RequestListener implements ServletRequestListener {

    private Logger logger = LoggerFactory.getLogger(RequestListener.class);

    public void requestInitialized(ServletRequestEvent sre) {
        logger.info("New Http request: " + ((HttpServletRequest) sre.getServletRequest()).getRequestURI());
    }
}

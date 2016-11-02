package ua.fantotsy.web.infrastructure;

import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    MyController getController(HttpServletRequest request);
}
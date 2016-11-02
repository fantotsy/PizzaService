package ua.fantotsy.web.infrastructure;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServletRequest;

public class SimpleUrlHandlerMapping implements HandlerMapping, ApplicationContextAware {
    private ApplicationContext webContext;

    @Override
    public MyController getController(HttpServletRequest request) {
        String url = request.getRequestURI();
        String controllerName = getControllerName(url);
        return webContext.getBean(controllerName, MyController.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.webContext = applicationContext;
    }

    private String getControllerName(String url) {
        return url.substring(url.lastIndexOf("/"));
    }
}
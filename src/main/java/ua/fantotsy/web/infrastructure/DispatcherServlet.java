package ua.fantotsy.web.infrastructure;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.fantotsy.web.HelloController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {
    private ConfigurableApplicationContext webContext;

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getRequestURI();
        String controllerName = getControllerName(url);

        MyController controller = (MyController) webContext.getBean(controllerName);
        if (controller != null) {
            controller.handleRequest(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        webContext = new ClassPathXmlApplicationContext("webContext.xml");
    }

    @Override
    public void destroy() {
        webContext.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private String getControllerName(String url) {
        return url.substring(url.lastIndexOf("/"));
    }
}
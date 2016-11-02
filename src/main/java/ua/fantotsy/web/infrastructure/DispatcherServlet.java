package ua.fantotsy.web.infrastructure;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {
    private ConfigurableApplicationContext webContext;
    private ConfigurableApplicationContext[] applicationContexts;

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HandlerMapping handlerMapping = webContext.getBean("handlerMappingStrategy", HandlerMapping.class);
        //= new SimpleUrlHandlerMapping(webContext);

        MyController controller = handlerMapping.getController(request);

        if (controller != null) {
            controller.handleRequest(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        String contextsLocations = getServletContext().getInitParameter("contextConfigLocation");
        String[] contexts = contextsLocations.split(" ");

        applicationContexts = new ConfigurableApplicationContext[contexts.length];

        for (int i = 0; i < applicationContexts.length; i++) {
            ConfigurableApplicationContext context;
            if (i == 0) {
                context = new ClassPathXmlApplicationContext(contexts[i]);
            } else {
                context = new ClassPathXmlApplicationContext(new String[]{contexts[i]}, applicationContexts[i - 1]);
            }
            applicationContexts[i] = context;
        }

        String webContextConfigLocation = getInitParameter("contextConfigLocation");
        webContext = new ClassPathXmlApplicationContext(new String[]{webContextConfigLocation}, applicationContexts[applicationContexts.length - 1]);
    }

    @Override
    public void destroy() {
        webContext.close();
        for (int i = applicationContexts.length - 1; i >= 0; i--) {
            applicationContexts[i].close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }


}
package ua.fantotsy.web;

import org.springframework.stereotype.Controller;
import ua.fantotsy.web.infrastructure.MyController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller("/hello")
public class HelloController implements MyController {

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("HelloController");
        }
    }
}
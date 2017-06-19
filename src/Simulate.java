import test.Simulator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kamil on 02.06.2017.
 */
@WebServlet(urlPatterns = "/simulate")
public class Simulate extends HttpServlet {

    static Simulator simulator = new Simulator();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        simulator.start();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        simulator.start();
    }
}

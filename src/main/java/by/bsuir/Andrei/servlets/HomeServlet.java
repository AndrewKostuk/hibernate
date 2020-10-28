package by.bsuir.Andrei.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String value = req.getParameter("action");
        if (value.equals("Login")) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            if (value.equals("Sign Up")) {
                resp.sendRedirect(req.getContextPath() + "/signUp");
            } else {
                doGet(req, resp);
            }
        }
    }
}

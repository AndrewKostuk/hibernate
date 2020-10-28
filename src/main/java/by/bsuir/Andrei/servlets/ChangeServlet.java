package by.bsuir.Andrei.servlets;


import by.bsuir.Andrei.models.Product;
import by.bsuir.Andrei.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/change")
public class ChangeServlet extends HttpServlet {
    private Configuration configuration;

    @Override
    public void init() throws ServletException {
        this.configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/db_example");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "admin");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Product.class);
        configuration.setProperty("hibernate.show_sql", "true");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Integer id = Integer.parseInt(req.getParameter("id"));
        if (action.equals("delete")) {
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Product product = session.load(Product.class, id);
            product.setOwner(null);
            session.delete(product);
            session.getTransaction().commit();

            resp.sendRedirect(req.getContextPath() + "/order");
        }
        if (action.equals("update")) {
            String name = req.getParameter("new_name");
            String color = req.getParameter("new_color");
            Integer cost = Integer.parseInt(req.getParameter("new_cost"));

            SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Product product = session.load(Product.class, id);

            product.setName(name);
            product.setColor(color);
            product.setCost(cost);

            session.update(product);
            session.getTransaction().commit();

            resp.sendRedirect(req.getContextPath() + "/order");
        }
    }
}

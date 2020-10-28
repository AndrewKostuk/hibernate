package by.bsuir.Andrei.servlets;

import by.bsuir.Andrei.dao.ProductDao;
import by.bsuir.Andrei.dao.ProductDaoJdbcTemplateImpl;
import by.bsuir.Andrei.dao.UserDao;
import by.bsuir.Andrei.dao.UserDaoJdbcImpl;
import by.bsuir.Andrei.models.Product;
import by.bsuir.Andrei.models.User;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private UserDao userDao;
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getServletContext().getRealPath("WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String DriverClassName = properties.getProperty("db.driverClassName");

            userDao = new UserDaoJdbcImpl(dbUsername, dbPassword, dbUrl, DriverClassName);

            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUsername(dbUsername);
            dataSource.setPassword(dbPassword);
            dataSource.setUrl(dbUrl);
            dataSource.setDriverClassName(DriverClassName);

            productDao = new ProductDaoJdbcTemplateImpl(dataSource);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/jsp/order.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String textColor = req.getParameter("color_text");
        if (!(textColor.trim().length() == 0)) {
            Cookie textColorCookie = new Cookie("color", textColor);
            resp.addCookie(textColorCookie);
        }

        String name = req.getParameter("name");
        if (!(name.trim().length() == 0)) {
            String color = req.getParameter("color");
            Integer cost = Integer.parseInt(req.getParameter("cost"));
            String owner_name = (String) req.getSession(false).getAttribute("user");
            Optional<User> user = userDao.findByName(owner_name);

            Product product = new Product(name, color, cost);
            product.setOwner(user.get());
            productDao.save(product);
        }

        resp.sendRedirect(req.getContextPath() + "/order");
    }
}

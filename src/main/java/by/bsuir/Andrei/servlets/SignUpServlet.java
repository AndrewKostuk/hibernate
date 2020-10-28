package by.bsuir.Andrei.servlets;

import by.bsuir.Andrei.dao.UserDao;
import by.bsuir.Andrei.dao.UserDaoJdbcImpl;
import by.bsuir.Andrei.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getServletContext().getRealPath("WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String DriverClassName = properties.getProperty("db.driverClassName");

            userDao = new UserDaoJdbcImpl(dbUsername, dbPassword, dbUrl, DriverClassName);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/jsp/signUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        LocalDate birthDate = LocalDate.parse(req.getParameter("birthDate"));

        Optional<User> existingUser = userDao.findByName(name);
        if (!existingUser.isPresent()) {
            userDao.save(new User(name, password, birthDate));
            HttpSession session = req.getSession();
            session.setAttribute("user", name);
            resp.sendRedirect(req.getContextPath() + "/order");
        } else {
            PrintWriter printWriter = resp.getWriter();
            printWriter.write("Already taken. Try to set another name");
        }
    }
}

package by.bsuir.Andrei.dao;

import by.bsuir.Andrei.models.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoJdbcImpl implements UserDao {
    private Connection connection;

    public UserDaoJdbcImpl(String dbUser, String password, String connectionUrl, String driverClassName) {
        try {
            Class.forName(driverClassName);
            this.connection = DriverManager.getConnection(connectionUrl, dbUser, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    //language=SQL
    private final String SQL_SELECT_BY_FIRST_NAME =
            "select * from web_user where name = ?";

    //language=SQL
    private final String SQL_SELECT_BY_ID =
            "select * from web_user where id = ?";

    //language=SQL
    private final String SQL_SAVE =
            "insert into web_user(name, password, birth_date) values(?, ?, ?)";

    //language=SQL
    private final String SQL_SELECT_ALL =
            "select * from web_user";

    @Override
    public Optional<User> findByName(String firstName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_FIRST_NAME);
            preparedStatement.setString(1, firstName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                LocalDate date = LocalDate.parse(resultSet.getString("birth_date"));
                return Optional.of(new User(id, name, password, date));
            }
            return Optional.empty();
        } catch(SQLException e){
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> find(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                LocalDate date = LocalDate.parse(resultSet.getString("birth_date"));
                return Optional.of(new User(id, name, password, date));
            }
            return Optional.empty();
        } catch(SQLException e){
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(User model) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE);
            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getPassword());
            preparedStatement.setDate(3, Date.valueOf(model.getBirthDate()));
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<User> findAll() {
        try {
            List<User> users = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                LocalDate date = LocalDate.parse(resultSet.getString("birth_date"));

                User user = new User(id, name, password, date);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

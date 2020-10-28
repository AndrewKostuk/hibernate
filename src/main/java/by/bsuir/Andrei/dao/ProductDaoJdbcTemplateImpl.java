package by.bsuir.Andrei.dao;

import by.bsuir.Andrei.models.Product;
import by.bsuir.Andrei.models.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ProductDaoJdbcTemplateImpl implements ProductDao {
    private JdbcTemplate template;

    //language=SQL
    private final String SQL_SAVE =
            "insert into purchase (name, color, cost, owner_id) values (?, ?, ?, ?)";

    public ProductDaoJdbcTemplateImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Product> find(Integer id) {
        return Optional.empty();
    }

    @Override
    public void save(Product model) {
        template.update(SQL_SAVE, model.getName(), model.getColor(), model.getCost(), model.getOwner().getId());
    }

    @Override
    public void update(Product model) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Product> findAll() {
        return null;
    }
}

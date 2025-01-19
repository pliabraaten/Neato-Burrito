package com.PL.burritos.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.PL.burritos.entity.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class jdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    public jdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        // Uses jdbcTemplate's query method that accepts SQL
        return jdbcTemplate.query(
                "SELECT id, name, type FROM Ingredient",
                this::mapRowToIngredient);  // RowMapper to map each row in the result set to an object
    }

    @Override
    public Optional<Ingredient> findById(String id) {

        List<Ingredient> results = jdbcTemplate.query(
                "SELECT id, name, type FROM ingredient WHERE id=?",
                this::mapRowToIngredient,
                id);  // id parameter for ?

        return results.size() == 0 ?
                Optional.empty() :
                Optional.of(results.get(0));
    }

    private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
        return new Ingredient(
                row.getString("id"),
                row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type")));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {

        jdbcTemplate.update(  // Update method writes or updates to db
                "INSERT into Ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getID(),
                ingredient.getName(),
                ingredient.getType().toString());

        return ingredient;
    }

    //
}

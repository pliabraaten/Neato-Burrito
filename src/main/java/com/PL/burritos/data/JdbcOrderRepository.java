package com.PL.burritos.data;

import com.PL.burritos.entity.Burrito;
import com.PL.burritos.entity.BurritoOrder;
import com.PL.burritos.entity.Ingredient;
import jakarta.validation.constraints.Size;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.asm.Type;

import java.sql.Types;
import java.sql.Date;

import java.util.*;


public class JdbcOrderRepository implements OrderRepository{

    private JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public BurritoOrder save(BurritoOrder order) {

        // Describes insert query with field types
        PreparedStatementCreatorFactory pscf =
            new PreparedStatementCreatorFactory(
                "insert into Burrito_Order "
                    + "delivery_name, deliver_street, delivery_city, "
                    + "delivery_state, delivery_zip, cc_number, "
                    + "cc_expiration, cc_cvv, placed_at) "
                    + "values (?,?,?,?,?,?,?,?,?)",
                    Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                    Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                    Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
            );
        pscf.setReturnGeneratedKeys(true);

        order.setPlacedAt(new Date(System.currentTimeMillis()));  // Use java.sql.Date for SQL compatibility

        // Passes values of BurritoOrder
        PreparedStatementCreator psc =
            pscf.newPreparedStatementCreator(
                    Arrays.asList(
                            order.getDeliveryName(),
                            order.getDeliveryStreet(),
                            order.getDeliveryCity(),
                            order.getDeliveryState(),
                            order.getDeliveryZip(),
                            order.getCcNumber(),
                            order.getCcExpiration(),
                            order.getCcCVV(),
                            order.getPlacedAt()));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        // Save order data with update method passing in values generated key
        jdbcOperations.update(psc, keyHolder);

        // Put generated key into the BurritoOrder's id property
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        List<Burrito> burritos = order.getBurritos();

        // Save individual burritos in order
        int i=0;
        for (Burrito burrito : burritos) {
            saveBurrito(orderId, i++, burrito);
        }

        return order;
    }

    // Save burrito method
    private long saveBurrito(Long orderId, int orderKey, Burrito burrito) {

        burrito.setCreatedAt(new Date(System.currentTimeMillis()));  // Use java.sql.Date for SQL compatibility

        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                    "insert into Burrito "
                        + "(name, created_at, burrito_order, burrito_order_key) "
                        + "values (?, ?, ?, ?)",
                        Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
                );
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc =
            pscf.newPreparedStatementCreator(
                Arrays.asList(
                        burrito.getName(),
                        burrito.getCreatedAt(),
                        orderId,
                        orderKey)
            );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long burritoId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        burrito.setId(burritoId);

        // Create rows in Ingredient_Ref table to link burrito row to ingredient row
        saveIngredientsRefs(burritoId, burrito.getIngredients());

        return burritoId;
    }

    // Save ingredients with linking references to burrito
    private void saveIngredientsRefs(long burritoId, List<Ingredient> ingredients) {

        int key = 0;
        for (Ingredient ingredient : ingredients) {
            jdbcOperations.update(
                    "insert into Ingredient_Ref (ingredient, burrito, burrito_key) "
                        + "values (?, ?, ?)",
                    ingredient.getIngredient(), burritoId, key++);
        }
    }
}

package org.beru.pizzeria.persistence.repository;

import org.beru.pizzeria.persistence.entity.OrderEntity;
import org.beru.pizzeria.persistence.projection.OrderSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {
    List<OrderEntity> findAllByDateAfter(LocalDateTime date);
    List<OrderEntity> findAllByMethodIn(List<String> methods);
    @Query(value = "SELECT * FROM pizza_order WHERE id_customer=:id", nativeQuery = true)
    List<OrderEntity> findCustomerOrder(@Param("id") String idCustomer);
    @Query(value = """
            SELECT po.id AS idOrder,
                     cu.name AS customerName,
                     po.date AS orderDate,
                     po.total AS orderTotal,
                     GROUP_CONCAT(pi.name) AS pizzaNames
                FROM pizza_order po
                     INNER JOIN customer cu ON po.id_customer = cu.id
                     INNER JOIN order_item oi ON po.id = oi.id_order
                     INNER JOIN pizza pi ON oi.id_pizza = pi.id
               WHERE po.id = :orderId
            GROUP BY po.id,
                     cu.name,
                     po.date,
                     po.total
            """,
            nativeQuery = true)
    OrderSummary findSummary(@Param("orderId") int orderId);
    @Procedure(value = "take_random_pizza_order", outputParameterName = "order_taken")
    boolean saveRandomOrder(@Param("id_customer") String idCustomer, @Param("method") String method);
}

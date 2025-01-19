package com.PL.burritos.data;

import com.PL.burritos.entity.BurritoOrder;

public interface OrderRepository {

    BurritoOrder save(BurritoOrder order);  // Runs save method in JdbcOrder Repository
}

package com.homates.shoppinglist.repo;

import com.homates.shoppinglist.model.ProductInList;
import org.springframework.data.repository.CrudRepository;

public interface ProductInListRepository extends CrudRepository<ProductInList, Long> {
}

package com.homates.accounting.repo;

import com.homates.accounting.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepositoy extends CrudRepository<User, Long> {
}

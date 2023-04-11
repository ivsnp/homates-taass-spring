package com.homates.accounting.repo;

import com.homates.accounting.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepositoy extends CrudRepository<User, Long> {
}

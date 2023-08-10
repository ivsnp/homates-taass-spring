package com.homates.bacheca.repo;


import com.homates.bacheca.model.Announce;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface AnnounceRepository extends CrudRepository<Announce, Long>{

    List<Announce> findByDate(String date);

}

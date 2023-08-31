package com.codingdojo.authentication.repositories;

import com.codingdojo.authentication.models.Colaboracion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboracionRepository extends CrudRepository<Colaboracion,Long> {
}

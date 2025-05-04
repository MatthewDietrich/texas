package fun.lizard.texas.repository;

import fun.lizard.texas.entity.County;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountyRepository extends MongoRepository<County, String> {

    @Query(value = "{ 'properties.name': ?0 }")
    County findOneByName(String name);
}

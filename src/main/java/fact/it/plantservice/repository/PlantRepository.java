package fact.it.plantservice.repository;

import fact.it.plantservice.model.Plant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends MongoRepository<Plant, String> {
    Plant findPlantByName(String name);
}

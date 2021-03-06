package fact.it.plantservice.repository;

import fact.it.plantservice.model.Plant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantRepository extends MongoRepository<Plant, String> {
    Plant findPlantByName(String name);
    List<Plant> findPlantByDescriptionContaining(String description);
    Plant findPlantByPlantNumber(String plantNumber);
    List<Plant> findPlantByGardenCenterId(Integer gardenCenterId);
}

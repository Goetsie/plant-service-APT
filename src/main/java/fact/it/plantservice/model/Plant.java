package fact.it.plantservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "plants")
public class Plant {
    @Id
    private String id;
    private int plantNumber;
    private String name;
    private String description;
    // Tuincentra? !!!!


    public Plant() {
    }

    // Change when center is needed !!!!
    public Plant(int plantNumber, String name, String description) {
        this.plantNumber = plantNumber;
        this.name = name;
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPlantNumber() {
        return plantNumber;
    }

    public void setPlantNumber(int plantNumber) {
        this.plantNumber = plantNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

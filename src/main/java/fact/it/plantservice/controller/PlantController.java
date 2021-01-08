package fact.it.plantservice.controller;

import fact.it.plantservice.model.Plant;
import fact.it.plantservice.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@RestController
public class PlantController {

    @Autowired
    private PlantRepository plantRepository;

    @PostConstruct
    public void fillDB() {
        plantRepository.deleteAll();
        // If the repository is empty create new plants, otherwise manny duplicated plants
//        if (plantRepository.count() == 0) {
//            // Plant information from: https://www.buitenlevengevoel.nl/meest-populaire-tuinplanten-van-nederland/
//            plantRepository.save(new Plant("Kerstroos", "De Helleborus niger staat beter bekend als de kerstroos. Deze tuinplant dankt die bijnaam onder andere aan zijn bijzondere bloeiperiode. Wanneer de rest van de tuin in diepe winterrust is, bloeit de kerstroos namelijk helemaal op. Volgens een oude legende zou de eerste kerstroos in Bethlehem zijn ontsproten uit de tranen van een arme herder die geen geschenk had voor het kindje Jezus. Op die manier kon hij de bloemen geven."));
//            plantRepository.save(new Plant("Hortensia", "Het zal je waarschijnlijk niet verbazen de hortensia in de lijst tegen te komen. Deze plant kom je in veel Nederlandse voor- en achtertuinen tegen. De hortensia kent veel verschillende soorten, maar de populairste is toch wel de Hydrangea macrophylla."));
//            plantRepository.save(new Plant("Lavendel", "De lavendel is vooral populair door de mooie diep paarse kleur én de heerlijke geur die deze plant verspreidt. Lavendel is een kleine vaste tuinplant voor in de tuin. De plant wordt namelijk niet hoger dan één meter en doet klein aan door de dunne stengels waar de plant uit bestaat."));
//            plantRepository.save(new Plant("Buxus", "De buxus is zo'n fijne plant, omdat je deze in werkelijk alle vormen kunt snoeien waarin je maar wilt. Hierdoor zijn ze in iedere tuin precies naar de wensen van de eigenaar te gebruiken."));
//            plantRepository.save(new Plant("Viooltje", "Niet iedere populaire plant is een grote plant. Het kleine viooltje blijft een favoriet voor in de Nederlandse tuin. Dit omdat wij toch wel fan zijn van dit kleine, fleurige plantje. Door de vele kleuruitvoeringen past er in iedere tuin wel een viooltje en daarom kon hij niet ontbreken in deze lijst van de meest populaire tuinplanten van Nederland."));
//            plantRepository.save(new Plant("Geranium", "Pelargonium (pelargos) betekent ooievaarsbek en het is dan ook om die reden dat de geranium ook wel ooievaarsbek genoemd wordt. Geraniums hebben lange tijd een wat stoffig imago gehad door het bekende gezegde ‘achter de geraniums zitten'. De plant wordt veelal geassocieerd met oude mensen die de hele dag maar binnen zitten."));
//            plantRepository.save(new Plant("Spaanse margriet", "De Spaanse margriet is niet, zoals je zou verwachten, afkomstig uit Spanje. Deze populaire plant komt uit het verre Zuid-Afrika en is een éénjarige tuinplant."));
//            plantRepository.save(new Plant("Vlinderstruik", "Dat de vlinderstruik bij de meest populaire tuinplanten van Nederland zit verbaasd ons wederom niets. Deze plant is niet alleen mooi om te zien, maar trekt ook nog eens de mooiste vlinders aan. Zo zullen onder andere het koolwitje, de citroenvlinder en de atalanta de verleiding van deze plant niet kunnen weerstaan."));
//            plantRepository.save(new Plant("Klimop", "Ondanks dat sommigen hem liever kwijt zijn dan rijk, is de klimop in Nederland een erg populaire plant. Waarschijnlijk heeft de plant deze status te danken aan het feit dat het een makkelijke plant is die het overal goed doet. Ook blijft de Hedera het hele jaar door groen en is het een geschikte plant om het gevoel van een verticale tuin mee te creëren."));
//            plantRepository.save(new Plant("Hartlelie ", "Door zijn vele soorten is er wat de hartlelie betreft voor iedereen wel een passende variant te vinden. De Hosta is bijvoorbeeld te krijgen met goudgele, witte, roomwit omrande, groene of blauwe (zweem) bladeren."));
//        }

//        System.out.println("Number of plants: " + plantRepository.findAll().size());

    }

    @GetMapping("/plants")
    public List<Plant> getPlants() {
        return plantRepository.findAll();
    }

    @GetMapping("/plants/name/{name}")
    public Plant getPlantByName(@PathVariable String name) {
        return plantRepository.findPlantByName(name);
    }

    @GetMapping("/plants/description/{description}")
    public List<Plant> getPlantByDescriptionContaining(@PathVariable String description) {
        return plantRepository.findPlantByDescriptionContaining(description);
    }

    @GetMapping("/plants/{plantNumber}")
    public Plant getPlantByPlantNumber(@PathVariable String plantNumber) {
        return plantRepository.findPlantByPlantNumber(plantNumber);
    }


    // Create
    //    @PostMapping("/plants")
    //    public ResponseEntity<Plant> createPlant(@RequestBody Plant plant) {
    //        try {
    //            Plant _plant = plantRepository.save(plant);
    //            return new ResponseEntity<>(_plant, HttpStatus.CREATED);
    //        } catch (Exception e) {
    //            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    //        }
    //    }

    @PostMapping("/plants")
    public Plant addReview(@RequestBody Plant plant) {
        plantRepository.save(plant);
        return plant;
    }

    // Update
    //    @PutMapping("/plants/{plantNumber}")
    //        public ResponseEntity<Plant> updatePlant(@PathVariable("plantNumber") int id, @RequestBody Plant plant) {
    //        Plant _plant = plantRepository.findPlantByPlantNumber(id);
    //
    //        if (_plant!=null) {
    //            _plant.setName(plant.getName());
    //            _plant.setDescription(plant.getDescription());
    //            return new ResponseEntity<>(plantRepository.save(_plant), HttpStatus.OK);
    //        } else {
    //            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //        }
    //    }

    @PutMapping("/plants")
    public Plant updatePlant(@RequestBody Plant updatedPlant) {
        Plant retrievedPlant = plantRepository.findPlantByPlantNumber(updatedPlant.getPlantNumber());

        retrievedPlant.setName(updatedPlant.getName());
        retrievedPlant.setDescription(updatedPlant.getDescription());
        retrievedPlant.setGardenCenterId(updatedPlant.getGardenCenterId());

        plantRepository.save(retrievedPlant);
        return retrievedPlant;
    }


    // Delete
    //    @DeleteMapping("/plants/{plantNumber}")
    //    public ResponseEntity deleteReview(@PathVariable("plantNumber") String plantNumber ){
    //        Plant plant = plantRepository.findPlantByPlantNumber(plantNumber);
    //        if(plant!=null){
    //            plantRepository.delete(plant);
    //            return ResponseEntity.ok().build();
    //        }else{
    //            return ResponseEntity.notFound().build();
    //        }
    //    }
    @DeleteMapping("/plants/{plantNumber}")
    public ResponseEntity deleteReview(@PathVariable String plantNumber) {
        Plant plant = plantRepository.findPlantByPlantNumber(plantNumber);
        if (plant != null) {
            plantRepository.delete(plant);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

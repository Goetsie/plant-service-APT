package fact.it.plantservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.plantservice.model.Plant;
import fact.it.plantservice.repository.PlantRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PlantControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlantRepository plantRepository;

    // Test data (best no postconstruct in controller)
    // Plant information from: https://www.buitenlevengevoel.nl/meest-populaire-tuinplanten-van-nederland/
    private Plant plantCenter1Plant1 = new Plant(1, "P0100", "Kerstroos", "De Helleborus niger staat beter bekend als de kerstroos. Deze tuinplant dankt die bijnaam onder andere aan zijn bijzondere bloeiperiode. Wanneer de rest van de tuin in diepe winterrust is, bloeit de kerstroos namelijk helemaal op. Volgens een oude legende zou de eerste kerstroos in Bethlehem zijn ontsproten uit de tranen van een arme herder die geen geschenk had voor het kindje Jezus. Op die manier kon hij de bloemen geven.");
    private Plant plantCenter1Plant2 = new Plant(1, "P0101", "Hortensia", "Het zal je waarschijnlijk niet verbazen de hortensia in de lijst tegen te komen. Deze plant kom je in veel Nederlandse voor- en achtertuinen tegen. De hortensia kent veel verschillende soorten, maar de populairste is toch wel de Hydrangea macrophylla.");
    private Plant plantCenter2Plant1 = new Plant(2, "P0102", "Buxus", "De buxus is zo'n fijne plant, omdat je deze in werkelijk alle vormen kunt snoeien waarin je maar wilt. Hierdoor zijn ze in iedere tuin precies naar de wensen van de eigenaar te gebruiken.");
    private Plant plantCenter3Plant2 = new Plant(3, "P0103", "Klimop", "Ondanks dat sommigen hem liever kwijt zijn dan rijk, is de klimop in Nederland een erg populaire plant. Waarschijnlijk heeft de plant deze status te danken aan het feit dat het een makkelijke plant is die het overal goed doet. Ook blijft de Hedera het hele jaar door groen en is het een geschikte plant om het gevoel van een verticale tuin mee te creëren.");
    private Plant plantCenter3Plant3 = new Plant(3, "P0104", "Hartlelie", "Door zijn vele soorten is er wat de hartlelie betreft voor iedereen wel een passende variant te vinden. De Hosta is bijvoorbeeld te krijgen met goudgele, witte, roomwit omrande, groene of blauwe (zweem) bladeren.");

    //    private Plant plantCenter1Plant3 = new Plant(1, "Lavendel", "De lavendel is vooral populair door de mooie diep paarse kleur én de heerlijke geur die deze plant verspreidt. Lavendel is een kleine vaste tuinplant voor in de tuin. De plant wordt namelijk niet hoger dan één meter en doet klein aan door de dunne stengels waar de plant uit bestaat.");
    //    private Plant plantCenter2Plant2 = new Plant(2, "Viooltje", "Niet iedere populaire plant is een grote plant. Het kleine viooltje blijft een favoriet voor in de Nederlandse tuin. Dit omdat wij toch wel fan zijn van dit kleine, fleurige plantje. Door de vele kleuruitvoeringen past er in iedere tuin wel een viooltje en daarom kon hij niet ontbreken in deze lijst van de meest populaire tuinplanten van Nederland.");
    //    private Plant plantCenter2Plant3 = new Plant(2, "Geranium", "Pelargonium (pelargos) betekent ooievaarsbek en het is dan ook om die reden dat de geranium ook wel ooievaarsbek genoemd wordt. Geraniums hebben lange tijd een wat stoffig imago gehad door het bekende gezegde ‘achter de geraniums zitten'. De plant wordt veelal geassocieerd met oude mensen die de hele dag maar binnen zitten.");
    //    private Plant plantCenter2Plant4 = new Plant(2, "Spaanse margriet", "De Spaanse margriet is niet, zoals je zou verwachten, afkomstig uit Spanje. Deze populaire plant komt uit het verre Zuid-Afrika en is een éénjarige tuinplant.");
    //    private Plant plantCenter3Plant1 = new Plant(3, "Vlinderstruik", "Dat de vlinderstruik bij de meest populaire tuinplanten van Nederland zit verbaasd ons wederom niets. Deze plant is niet alleen mooi om te zien, maar trekt ook nog eens de mooiste vlinders aan. Zo zullen onder andere het koolwitje, de citroenvlinder en de atalanta de verleiding van deze plant niet kunnen weerstaan.");


    @BeforeEach
    public void beforeAllTests() {
        // Make sure EVERYTHING is deleted from the db
        plantRepository.deleteAll();
        // Save all the plants
        plantRepository.save(plantCenter1Plant1);
        plantRepository.save(plantCenter1Plant2);
        plantRepository.save(plantCenter2Plant1);
        plantRepository.save(plantCenter3Plant2);
        plantRepository.save(plantCenter3Plant3);

        //        plantRepository.save(plantCenter1Plant3);
        //        plantRepository.save(plantCenter2Plant2);
        //        plantRepository.save(plantCenter2Plant3);
        //        plantRepository.save(plantCenter2Plant4);
        //        plantRepository.save(plantCenter3Plant1);
    }

    @AfterEach
    public void afterAllTests() {
        // Watch out with deleteAll() methods when you have other data in the test database!!
        plantRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenPlant_whenGetPlantByName_ThenReturnJsonPlant() throws Exception {
        mockMvc.perform(get("/plants/name/{name}", "Kerstroos"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gardenCenterId", is(1)))
                .andExpect(jsonPath("$.plantNumber", is("P0100")))
                .andExpect(jsonPath("$.name", is("Kerstroos")))
                .andExpect(jsonPath("$.description", is("De Helleborus niger staat beter bekend als de kerstroos. Deze tuinplant dankt die bijnaam onder andere aan zijn bijzondere bloeiperiode. Wanneer de rest van de tuin in diepe winterrust is, bloeit de kerstroos namelijk helemaal op. Volgens een oude legende zou de eerste kerstroos in Bethlehem zijn ontsproten uit de tranen van een arme herder die geen geschenk had voor het kindje Jezus. Op die manier kon hij de bloemen geven.")));
    }

    @Test
    public void givenPlant_whenGetPlantByPlantNumber_ThenReturnJsonPlant() throws Exception {
        mockMvc.perform(get("/plants/{plantNumber}", "P0100"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gardenCenterId", is(1)))
                .andExpect(jsonPath("$.plantNumber", is("P0100")))
                .andExpect(jsonPath("$.name", is("Kerstroos")))
                .andExpect(jsonPath("$.description", is("De Helleborus niger staat beter bekend als de kerstroos. Deze tuinplant dankt die bijnaam onder andere aan zijn bijzondere bloeiperiode. Wanneer de rest van de tuin in diepe winterrust is, bloeit de kerstroos namelijk helemaal op. Volgens een oude legende zou de eerste kerstroos in Bethlehem zijn ontsproten uit de tranen van een arme herder die geen geschenk had voor het kindje Jezus. Op die manier kon hij de bloemen geven.")));
    }

    @Test
    public void givenPlant_whenGetPlantByContainingDescription_ThenReturnJsonPlants() throws Exception {
        // Only one plant found
        mockMvc.perform(get("/plants/description/{description}", "Het zal je waarschijnlijk niet verbazen de hortensia in de lijst tegen te komen."))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].gardenCenterId", is(1)))
                .andExpect(jsonPath("$[0].plantNumber", is("P0101")))
                .andExpect(jsonPath("$[0].name", is("Hortensia")))
                .andExpect(jsonPath("$[0].description", is("Het zal je waarschijnlijk niet verbazen de hortensia in de lijst tegen te komen. Deze plant kom je in veel Nederlandse voor- en achtertuinen tegen. De hortensia kent veel verschillende soorten, maar de populairste is toch wel de Hydrangea macrophylla.")));

        // Two plants founf
        mockMvc.perform(get("/plants/description/{description}", "groen"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].gardenCenterId", is(3)))
                .andExpect(jsonPath("$[0].plantNumber", is("P0103")))
                .andExpect(jsonPath("$[0].name", is("Klimop")))
                .andExpect(jsonPath("$[0].description", is("Ondanks dat sommigen hem liever kwijt zijn dan rijk, is de klimop in Nederland een erg populaire plant. Waarschijnlijk heeft de plant deze status te danken aan het feit dat het een makkelijke plant is die het overal goed doet. Ook blijft de Hedera het hele jaar door groen en is het een geschikte plant om het gevoel van een verticale tuin mee te creëren.")))
                .andExpect(jsonPath("$[1].gardenCenterId", is(3)))
                .andExpect(jsonPath("$[1].plantNumber", is("P0104")))
                .andExpect(jsonPath("$[1].name", is("Hartlelie")))
                .andExpect(jsonPath("$[1].description", is("Door zijn vele soorten is er wat de hartlelie betreft voor iedereen wel een passende variant te vinden. De Hosta is bijvoorbeeld te krijgen met goudgele, witte, roomwit omrande, groene of blauwe (zweem) bladeren.")));
    }

    @Test
    public void givenPlant_whenGetPlantByGardeCenterId_ThenReturnJsonPlants() throws Exception {
        mockMvc.perform(get("plants/gardencenterid/{gardenCenterId}", 3))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].gardenCenterId", is(3)))
                .andExpect(jsonPath("$[0].plantNumber", is("P0103")))
                .andExpect(jsonPath("$[0].name", is("Klimop")))
                .andExpect(jsonPath("$[0].description", is("Ondanks dat sommigen hem liever kwijt zijn dan rijk, is de klimop in Nederland een erg populaire plant. Waarschijnlijk heeft de plant deze status te danken aan het feit dat het een makkelijke plant is die het overal goed doet. Ook blijft de Hedera het hele jaar door groen en is het een geschikte plant om het gevoel van een verticale tuin mee te creëren.")))
                .andExpect(jsonPath("$[1].gardenCenterId", is(3)))
                .andExpect(jsonPath("$[1].plantNumber", is("P0104")))
                .andExpect(jsonPath("$[1].name", is("Hartlelie")))
                .andExpect(jsonPath("$[1].description", is("Door zijn vele soorten is er wat de hartlelie betreft voor iedereen wel een passende variant te vinden. De Hosta is bijvoorbeeld te krijgen met goudgele, witte, roomwit omrande, groene of blauwe (zweem) bladeren.")));
    }

    @Test
    public void givenPlant_whenPlants_ThenReturnAllJsonPlants() throws Exception {
        mockMvc.perform(get("/plants"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].gardenCenterId", is(1)))
                .andExpect(jsonPath("$[0].plantNumber", is("P0100")))
                .andExpect(jsonPath("$[0].name", is("Kerstroos")))
                .andExpect(jsonPath("$[0].description", is("De Helleborus niger staat beter bekend als de kerstroos. Deze tuinplant dankt die bijnaam onder andere aan zijn bijzondere bloeiperiode. Wanneer de rest van de tuin in diepe winterrust is, bloeit de kerstroos namelijk helemaal op. Volgens een oude legende zou de eerste kerstroos in Bethlehem zijn ontsproten uit de tranen van een arme herder die geen geschenk had voor het kindje Jezus. Op die manier kon hij de bloemen geven.")))
                .andExpect(jsonPath("$[1].gardenCenterId", is(1)))
                .andExpect(jsonPath("$[1].plantNumber", is("P0101")))
                .andExpect(jsonPath("$[1].name", is("Hortensia")))
                .andExpect(jsonPath("$[1].description", is("Het zal je waarschijnlijk niet verbazen de hortensia in de lijst tegen te komen. Deze plant kom je in veel Nederlandse voor- en achtertuinen tegen. De hortensia kent veel verschillende soorten, maar de populairste is toch wel de Hydrangea macrophylla.")))
                .andExpect(jsonPath("$[2].gardenCenterId", is(2)))
                .andExpect(jsonPath("$[2].plantNumber", is("P0102")))
                .andExpect(jsonPath("$[2].name", is("Buxus")))
                .andExpect(jsonPath("$[2].description", is("De buxus is zo'n fijne plant, omdat je deze in werkelijk alle vormen kunt snoeien waarin je maar wilt. Hierdoor zijn ze in iedere tuin precies naar de wensen van de eigenaar te gebruiken.")))
                .andExpect(jsonPath("$[3].gardenCenterId", is(3)))
                .andExpect(jsonPath("$[3].plantNumber", is("P0103")))
                .andExpect(jsonPath("$[3].name", is("Klimop")))
                .andExpect(jsonPath("$[3].description", is("Ondanks dat sommigen hem liever kwijt zijn dan rijk, is de klimop in Nederland een erg populaire plant. Waarschijnlijk heeft de plant deze status te danken aan het feit dat het een makkelijke plant is die het overal goed doet. Ook blijft de Hedera het hele jaar door groen en is het een geschikte plant om het gevoel van een verticale tuin mee te creëren.")))
                .andExpect(jsonPath("$[4].gardenCenterId", is(3)))
                .andExpect(jsonPath("$[4].plantNumber", is("P0104")))
                .andExpect(jsonPath("$[4].name", is("Hartlelie")))
                .andExpect(jsonPath("$[4].description", is("Door zijn vele soorten is er wat de hartlelie betreft voor iedereen wel een passende variant te vinden. De Hosta is bijvoorbeeld te krijgen met goudgele, witte, roomwit omrande, groene of blauwe (zweem) bladeren.")));
    }

    @Test
    public void whenPostPlant_thenReturnJsonPlant() throws Exception {
        Plant plantCenter2Plant5 = new Plant(2, "P0105", "Ice Dance", "De Carex morrowii 'Ice Dance' is een laagblijvend siergras dat in de breedte groeit met schuin opgaande bladeren. De 'Ice Dance' wordt max. 40cm hoog worden.");

        mockMvc.perform(post("/plants")
                .content(mapper.writeValueAsString(plantCenter2Plant5))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gardenCenterId", is(2)))
                .andExpect(jsonPath("$.plantNumber", is("P0105")))
                .andExpect(jsonPath("$.name", is("Ice Dance")))
                .andExpect(jsonPath("$.description", is("De Carex morrowii 'Ice Dance' is een laagblijvend siergras dat in de breedte groeit met schuin opgaande bladeren. De 'Ice Dance' wordt max. 40cm hoog worden.")));
    }

    @Test
    public void givenPlant_whenPutPlant_thenReturnJsonPlant() throws Exception {

        Plant updatedPlant = new Plant(2, "P0102", "BuxusUPDATE", "De buxus is zo'n fijne plant, omdat je deze in werkelijk alle vormen kunt snoeien waarin je maar wilt. Hierdoor zijn ze in iedere tuin precies naar de wensen van de eigenaar te gebruiken.");

        mockMvc.perform(put("/plants")
                .content(mapper.writeValueAsString(updatedPlant))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gardenCenterId", is(2)))
                .andExpect(jsonPath("$.plantNumber", is("P0102")))
                .andExpect(jsonPath("$.name", is("BuxusUPDATE")))
                .andExpect(jsonPath("$.description", is("De buxus is zo'n fijne plant, omdat je deze in werkelijk alle vormen kunt snoeien waarin je maar wilt. Hierdoor zijn ze in iedere tuin precies naar de wensen van de eigenaar te gebruiken.")));
    }

    @Test
    public void givenPlant_whenDeletePlant_thenStatusOk() throws Exception {
        // Plant is IN db
        mockMvc.perform(delete("/plants/{plantNumber}", "P0100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoRPlant_whenDeletePlant_thenStatusNotFound() throws Exception {
        // Plant is NOT in db
        mockMvc.perform(delete("/plants/{plantNumber}", "K9999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}

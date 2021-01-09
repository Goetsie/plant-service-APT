package fact.it.plantservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.plantservice.model.Plant;
import fact.it.plantservice.repository.PlantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PlantControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlantRepository plantRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenPlant_whenGetPlantByName_thenReturnJsonPlant() throws Exception {
        Plant plantCenter1Plant1 = new Plant(1, "P0100", "Kerstroos", "De Helleborus niger staat beter bekend als de kerstroos. Deze tuinplant dankt die bijnaam onder andere aan zijn bijzondere bloeiperiode. Wanneer de rest van de tuin in diepe winterrust is, bloeit de kerstroos namelijk helemaal op. Volgens een oude legende zou de eerste kerstroos in Bethlehem zijn ontsproten uit de tranen van een arme herder die geen geschenk had voor het kindje Jezus. Op die manier kon hij de bloemen geven.");

        given(plantRepository.findPlantByName("Kerstroos")).willReturn(plantCenter1Plant1);

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
        Plant plantCenter1Plant1 = new Plant(1, "P0100", "Kerstroos", "De Helleborus niger staat beter bekend als de kerstroos. Deze tuinplant dankt die bijnaam onder andere aan zijn bijzondere bloeiperiode. Wanneer de rest van de tuin in diepe winterrust is, bloeit de kerstroos namelijk helemaal op. Volgens een oude legende zou de eerste kerstroos in Bethlehem zijn ontsproten uit de tranen van een arme herder die geen geschenk had voor het kindje Jezus. Op die manier kon hij de bloemen geven.");

        given(plantRepository.findPlantByPlantNumber("P0100")).willReturn(plantCenter1Plant1);

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
//        Plant plantCenter1Plant1 = new Plant(1, "P0100", "Kerstroos", "De Helleborus niger staat beter bekend als de kerstroos. Deze tuinplant dankt die bijnaam onder andere aan zijn bijzondere bloeiperiode. Wanneer de rest van de tuin in diepe winterrust is, bloeit de kerstroos namelijk helemaal op. Volgens een oude legende zou de eerste kerstroos in Bethlehem zijn ontsproten uit de tranen van een arme herder die geen geschenk had voor het kindje Jezus. Op die manier kon hij de bloemen geven.");
        Plant plantCenter3Plant2 = new Plant(3, "P0103", "Klimop", "Ondanks dat sommigen hem liever kwijt zijn dan rijk, is de klimop in Nederland een erg populaire plant. Waarschijnlijk heeft de plant deze status te danken aan het feit dat het een makkelijke plant is die het overal goed doet. Ook blijft de Hedera het hele jaar door groen en is het een geschikte plant om het gevoel van een verticale tuin mee te creëren.");
        Plant plantCenter3Plant3 = new Plant(3, "P0104", "Hartlelie", "Door zijn vele soorten is er wat de hartlelie betreft voor iedereen wel een passende variant te vinden. De Hosta is bijvoorbeeld te krijgen met goudgele, witte, roomwit omrande, groene of blauwe (zweem) bladeren.");

        List<Plant> plantList = new ArrayList<>();
//        plantList.add(plantCenter1Plant1);
        plantList.add(plantCenter3Plant2);
        plantList.add(plantCenter3Plant3);

        given(plantRepository.findPlantByDescriptionContaining("groen")).willReturn(plantList);

        mockMvc.perform(get("/plants/description/{description}", "groen"))                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
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
        Plant plantCenter3Plant2 = new Plant(3, "P0103", "Klimop", "Ondanks dat sommigen hem liever kwijt zijn dan rijk, is de klimop in Nederland een erg populaire plant. Waarschijnlijk heeft de plant deze status te danken aan het feit dat het een makkelijke plant is die het overal goed doet. Ook blijft de Hedera het hele jaar door groen en is het een geschikte plant om het gevoel van een verticale tuin mee te creëren.");
        Plant plantCenter3Plant3 = new Plant(3, "P0104", "Hartlelie", "Door zijn vele soorten is er wat de hartlelie betreft voor iedereen wel een passende variant te vinden. De Hosta is bijvoorbeeld te krijgen met goudgele, witte, roomwit omrande, groene of blauwe (zweem) bladeren.");

        List<Plant> plantList = new ArrayList<>();
        plantList.add(plantCenter3Plant2);
        plantList.add(plantCenter3Plant3);

        given(plantRepository.findPlantByGardenCenterId(3)).willReturn(plantList);

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
        Plant plantCenter1Plant1 = new Plant(1, "P0100", "Kerstroos", "De Helleborus niger staat beter bekend als de kerstroos. Deze tuinplant dankt die bijnaam onder andere aan zijn bijzondere bloeiperiode. Wanneer de rest van de tuin in diepe winterrust is, bloeit de kerstroos namelijk helemaal op. Volgens een oude legende zou de eerste kerstroos in Bethlehem zijn ontsproten uit de tranen van een arme herder die geen geschenk had voor het kindje Jezus. Op die manier kon hij de bloemen geven.");
        Plant plantCenter3Plant2 = new Plant(3, "P0103", "Klimop", "Ondanks dat sommigen hem liever kwijt zijn dan rijk, is de klimop in Nederland een erg populaire plant. Waarschijnlijk heeft de plant deze status te danken aan het feit dat het een makkelijke plant is die het overal goed doet. Ook blijft de Hedera het hele jaar door groen en is het een geschikte plant om het gevoel van een verticale tuin mee te creëren.");
        Plant plantCenter3Plant3 = new Plant(3, "P0104", "Hartlelie", "Door zijn vele soorten is er wat de hartlelie betreft voor iedereen wel een passende variant te vinden. De Hosta is bijvoorbeeld te krijgen met goudgele, witte, roomwit omrande, groene of blauwe (zweem) bladeren.");

        List<Plant> plantList = new ArrayList<>();
        plantList.add(plantCenter1Plant1);
        plantList.add(plantCenter3Plant2);
        plantList.add(plantCenter3Plant3);

        given(plantRepository.findAll()).willReturn(plantList);

        mockMvc.perform(get("/plants"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].gardenCenterId", is(1)))
                .andExpect(jsonPath("$[0].plantNumber", is("P0100")))
                .andExpect(jsonPath("$[0].name", is("Kerstroos")))
                .andExpect(jsonPath("$[0].description", is("De Helleborus niger staat beter bekend als de kerstroos. Deze tuinplant dankt die bijnaam onder andere aan zijn bijzondere bloeiperiode. Wanneer de rest van de tuin in diepe winterrust is, bloeit de kerstroos namelijk helemaal op. Volgens een oude legende zou de eerste kerstroos in Bethlehem zijn ontsproten uit de tranen van een arme herder die geen geschenk had voor het kindje Jezus. Op die manier kon hij de bloemen geven.")))
                .andExpect(jsonPath("$[1].gardenCenterId", is(3)))
                .andExpect(jsonPath("$[1].plantNumber", is("P0103")))
                .andExpect(jsonPath("$[1].name", is("Klimop")))
                .andExpect(jsonPath("$[1].description", is("Ondanks dat sommigen hem liever kwijt zijn dan rijk, is de klimop in Nederland een erg populaire plant. Waarschijnlijk heeft de plant deze status te danken aan het feit dat het een makkelijke plant is die het overal goed doet. Ook blijft de Hedera het hele jaar door groen en is het een geschikte plant om het gevoel van een verticale tuin mee te creëren.")))
                .andExpect(jsonPath("$[2].gardenCenterId", is(3)))
                .andExpect(jsonPath("$[2].plantNumber", is("P0104")))
                .andExpect(jsonPath("$[2].name", is("Hartlelie")))
                .andExpect(jsonPath("$[2].description", is("Door zijn vele soorten is er wat de hartlelie betreft voor iedereen wel een passende variant te vinden. De Hosta is bijvoorbeeld te krijgen met goudgele, witte, roomwit omrande, groene of blauwe (zweem) bladeren.")));
    }

    @Test
    public void whenPostPlant_thenReturnJsonPlant() throws Exception{
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
    public void givenPlant_whenPutPlant_thenReturnJsonPlant() throws Exception{
        // We do need to feed some predetermined data into the mock repository: the Plant to be updated.
        Plant plantCenter2Plant1 = new Plant(2, "P0102", "Buxus", "De buxus is zo'n fijne plant, omdat je deze in werkelijk alle vormen kunt snoeien waarin je maar wilt. Hierdoor zijn ze in iedere tuin precies naar de wensen van de eigenaar te gebruiken.");

        given(plantRepository.findPlantByPlantNumber("P0102")).willReturn(plantCenter2Plant1);

        Plant updatedPlant = new Plant(2, "P0102", "BuxusUPDATE", "De buxusUPDATE is zo'n fijne plant, omdat je deze in werkelijk alle vormen kunt snoeien waarin je maar wilt. Hierdoor zijn ze in iedere tuin precies naar de wensen van de eigenaar te gebruiken.");

        mockMvc.perform(put("/plants")
                .content(mapper.writeValueAsString(updatedPlant))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gardenCenterId", is(2)))
                .andExpect(jsonPath("$.plantNumber", is("P0102")))
                .andExpect(jsonPath("$.name", is("BuxusUPDATE")))
                .andExpect(jsonPath("$.description", is("De buxusUPDATE is zo'n fijne plant, omdat je deze in werkelijk alle vormen kunt snoeien waarin je maar wilt. Hierdoor zijn ze in iedere tuin precies naar de wensen van de eigenaar te gebruiken.")));
    }

    @Test
    public void givenPlant_whenDeletePlant_thenStatusOk() throws Exception{
        Plant plantToBeDeleted = new Plant(2, "P0105", "Ice Dance", "De Carex morrowii 'Ice Dance' is een laagblijvend siergras dat in de breedte groeit met schuin opgaande bladeren. De 'Ice Dance' wordt max. 40cm hoog worden.");

        given(plantRepository.findPlantByPlantNumber("P0105")).willReturn(plantToBeDeleted);

        mockMvc.perform(delete("/plants/{plantNumber}","P0105")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenPlant_whenDeletePlant_thenStatusNotFound() throws Exception{
        // No plant created because none should be found
        given(plantRepository.findPlantByPlantNumber("K9999")).willReturn(null);

        mockMvc.perform(delete("/plants/{plantNumber}","K9999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }




}

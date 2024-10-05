package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.exception.CountryNotFoundException;
import ar.edu.utn.frc.tup.lciii.model.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        @Autowired
        private RestTemplate restTemplate;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .code((String) countryData.get("cca3"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .borders((List<String>) countryData.get("borders"))
                        .build();
        }


        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }

        //UNO
        public List<CountryDTO> getCountries() {
                List<Country> countries = getAllCountries();

                List<CountryDTO> response = countries.stream().map(this::mapToDTO).collect(Collectors.toList());
                return response;
        }

        //DOS
        public List<CountryDTO> getCountriesByCodeOrName(String code, String name) {
                List<Country> countries = getAllCountries();
                List<CountryDTO> response = countries.stream().map(this::mapToDTO).collect(Collectors.toList());

                if (code == null){
                        response = response.stream()
                                .filter(dto -> dto.getName().equals(name))
                                .collect(Collectors.toList());
                }
                else if(name == null){
                        response = response.stream()
                                .filter(dto -> dto.getCode().equals(code))
                                .collect(Collectors.toList());
                }
                else {
                        response = response.stream()
                                .filter(dto -> dto.getName().equals(name)
                                        && dto.getCode().equals(code))
                                .collect(Collectors.toList());
                }
                return response;
        }

        //TRES
        public List<CountryDTO> getCountriesByContinent(String continent) {

                if (continent == null){
                        throw new CountryNotFoundException("Especifique un continente.");
                }

                List<Country> countries = getAllCountries();
                countries = countries.stream()
                        .filter(dto -> dto.getRegion().equals(continent))
                        .collect(Collectors.toList());

                List<CountryDTO> response = countries.stream().map(this::mapToDTO).collect(Collectors.toList());

                return response;
        }

        //CUATRO
        public List<CountryDTO> getCountriesByLanguage(String language) {
                if (language == null || language.isEmpty()) {
                        throw new CountryNotFoundException("Especifique un lenguaje.");
                }

                List<Country> countries = getAllCountries();

                List<Country> filteredCountries = countries.stream()
                        .filter(country -> country.getLanguages() != null)
                        .filter(country -> country.getLanguages().values().stream()
                                .anyMatch(lang -> lang.equalsIgnoreCase(language)))
                        .collect(Collectors.toList());

                List<CountryDTO> response = filteredCountries.stream()
                        .map(this::mapToDTO)
                        .collect(Collectors.toList());

                return response;
        }
}
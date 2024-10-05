package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private final CountryService countryService;

    @GetMapping("dataFromApi")
    public ResponseEntity<List<Country>> getCountriesFromApi(){
        List<Country> countries = countryService.getAllCountries();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("")
    public ResponseEntity<List<CountryDTO>> getCountries(){
        List<CountryDTO> countries = countryService.getCountries();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("code")
    public ResponseEntity<List<CountryDTO>> getCountriesByCodeOrName(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name) {

        List<CountryDTO> countries = countryService.getCountriesByCodeOrName(code, name);
        return ResponseEntity.ok(countries);
    }

    @GetMapping("{continent}/continent")
    public ResponseEntity<List<CountryDTO>> getCountriesByContinent(
            @PathVariable String continent) {

        List<CountryDTO> countries = countryService.getCountriesByContinent(continent);
        return ResponseEntity.ok(countries);
    }

    @GetMapping("{language}/language")
    public ResponseEntity<List<CountryDTO>> getCountriesByLanguage(
            @PathVariable String language) {

        List<CountryDTO> countries = countryService.getCountriesByLanguage(language);
        return ResponseEntity.ok(countries);
    }
}
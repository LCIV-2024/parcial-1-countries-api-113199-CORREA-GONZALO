package ar.edu.utn.frc.tup.lciii.clients;

import ar.edu.utn.frc.tup.lciii.clients.records.CountryRecord;
import ar.edu.utn.frc.tup.lciii.model.Country;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountriesClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModelMapper modelMapper;

    private static final String API_URL = "https://restcountries.com/v3.1/all";

    public List<Country> getAllCountries (){

        CountryRecord[] countries = restTemplate.getForObject(API_URL, CountryRecord[].class);

        return Arrays.stream(countries)
                .map(this::convertToCountry)
                .collect(Collectors.toList());
    }

    private Country convertToCountry(CountryRecord countryRecord) {
        Country country = new Country();
        country.setCode(countryRecord.cca3());
        country.setName(countryRecord.name().common());
        country.setRegion(countryRecord.region());
        country.setBorders(countryRecord.borders());
        return country;
    }
}

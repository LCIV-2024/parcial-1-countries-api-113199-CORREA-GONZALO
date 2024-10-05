package ar.edu.utn.frc.tup.lciii.clients.records;

import java.util.List;

public record CountryRecord(
        NameRecord name,
        String cca3,
        String region,
        List<String> borders) {
}

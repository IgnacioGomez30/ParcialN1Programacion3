package org.example.parcial_n1.services;

import org.example.parcial_n1.dto.StatsResponse;
import org.example.parcial_n1.repositories.AdnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class StatsService {
    private final AdnRepository adnRepository;

    @Autowired
    public StatsService(AdnRepository adnRepository) {
        this.adnRepository = adnRepository;
    }

    public StatsResponse getStats() {
        long countMutantAdn = adnRepository.countByIsMutant(true);
        long countHumanAdn = adnRepository.countByIsMutant(false);
        double ratio = countHumanAdn == 0 ? 0 : (double) countMutantAdn / countHumanAdn;
        return new StatsResponse(countMutantAdn, countHumanAdn, ratio);
    }
}

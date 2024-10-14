package org.example.parcial_n1.services;

import org.example.parcial_n1.entities.Adn;
import org.example.parcial_n1.repositories.AdnRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdnService {

    private final AdnRepository adnRepository;

    public AdnService(AdnRepository adnRepository) {
        this.adnRepository = adnRepository;
    }

    public boolean analyzeAdn(String[] adn) {
        // Validar el ADN antes de procesar
        if (!isValidAdn(adn)) {
            throw new IllegalArgumentException("Secuencia de ADN inválida");
        }

        String adnSequence = String.join("", adn);

        // Verificar si el ADN ya fue analizado previamente
        Optional<Adn> existingAdn = adnRepository.findByAdn(adnSequence);
        if (existingAdn.isPresent()) {
            return existingAdn.get().isMutant();
        }

        // Lógica para detectar si es mutante
        boolean isMutant = detectMutant(adn);

        // Guardar resultado en la base de datos
        Adn newAdn = Adn.builder().adn(adnSequence).isMutant(isMutant).build();
        adnRepository.save(newAdn);

        return isMutant;
    }

    private boolean isValidAdn(String[] adn) {
        if (adn == null || adn.length == 0) {
            return false;
        }

        for (String sequence : adn) {
            if (sequence == null || sequence.length() != adn.length) {
                return false;
            }
            for (char c : sequence.toCharArray()) {
                if (!"ATCG".contains(String.valueOf(c))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean detectMutant(String[] adn) {
        int size = adn.length;
        int secuenciasEncontradas = 0;  // Contador de secuencias encontradas

        for (int i = 0; i < size && secuenciasEncontradas < 2; i++) {
            for (int j = 0; j < size && secuenciasEncontradas < 2; j++) {
                // Verificar secuencias en las cuatro direcciones
                if (verificarHorizontal(adn, i, j) || verificarVertical(adn, i, j) ||
                        verificarDiagonalDerecha(adn, i, j) || verificarDiagonalIzquierda(adn, i, j)) {
                    secuenciasEncontradas++;
                    if (secuenciasEncontradas >= 2) {
                        return true;  // Mutante si se encuentran al menos 2 secuencias
                    }
                }
            }
        }
        return false;  // Humano si no se encuentran suficientes secuencias
    }

    private boolean verificarHorizontal(String[] adn, int x, int y) {
        if (y + 3 < adn.length) {
            if (adn[x].charAt(y) == adn[x].charAt(y + 1) &&
                    adn[x].charAt(y) == adn[x].charAt(y + 2) &&
                    adn[x].charAt(y) == adn[x].charAt(y + 3)) {
                return true;
            }
        }
        return false;
    }

    private boolean verificarVertical(String[] adn, int x, int y) {
        if (x + 3 < adn.length) {
            if (adn[x].charAt(y) == adn[x + 1].charAt(y) &&
                    adn[x].charAt(y) == adn[x + 2].charAt(y) &&
                    adn[x].charAt(y) == adn[x + 3].charAt(y)) {
                return true;
            }
        }
        return false;
    }

    private boolean verificarDiagonalDerecha(String[] adn, int x, int y) {
        if (x + 3 < adn.length && y + 3 < adn.length) {
            if (adn[x].charAt(y) == adn[x + 1].charAt(y + 1) &&
                    adn[x].charAt(y) == adn[x + 2].charAt(y + 2) &&
                    adn[x].charAt(y) == adn[x + 3].charAt(y + 3)) {
                return true;
            }
        }
        return false;
    }

    private boolean verificarDiagonalIzquierda(String[] adn, int x, int y) {
        if (x + 3 < adn.length && y - 3 >= 0) {
            if (adn[x].charAt(y) == adn[x + 1].charAt(y - 1) &&
                    adn[x].charAt(y) == adn[x + 2].charAt(y - 2) &&
                    adn[x].charAt(y) == adn[x + 3].charAt(y - 3)) {
                return true;
            }
        }
        return false;
    }
}

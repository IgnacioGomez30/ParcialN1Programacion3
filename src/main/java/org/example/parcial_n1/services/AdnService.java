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
            // Lanzar una excepción si la secuencia es inválida
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

        System.out.println("ADN guardado en la base de datos: " + newAdn);

        return isMutant;
    }

    private boolean isValidAdn(String[] adn) {
        if (adn == null || adn.length == 0) {
            return false;
        }

        int length = adn.length; // Guardar la longitud del ADN para comparación

        for (String sequence : adn) {
            // Comprobar si la secuencia es nula o tiene una longitud diferente
            if (sequence == null || sequence.length() != length) {
                return false;
            }
            for (char c : sequence.toCharArray()) {
                // Comprobar si el carácter es inválido
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

        for (int i = 0; i < size; i += 2) {  // Avanzar de dos en dos filas
            for (int j = 0; j < size; j += 2) {  // Avanzar de dos en dos columnas
                // Revisión horizontal discontinua
                if (j <= size - 4 && adn[i].charAt(j) == adn[i].charAt(j + 2)) {
                    // Revisa los elementos contiguos
                    if (adn[i].charAt(j) == adn[i].charAt(j + 1) && adn[i].charAt(j) == adn[i].charAt(j + 3)) {
                        secuenciasEncontradas++;
                    }
                }

                // Revisión vertical discontinua
                if (i <= size - 4 && adn[i].charAt(j) == adn[i + 2].charAt(j)) {
                    // Revisa los elementos contiguos
                    if (adn[i].charAt(j) == adn[i + 1].charAt(j) && adn[i].charAt(j) == adn[i + 3].charAt(j)) {
                        secuenciasEncontradas++;
                    }
                }

                // Revisión diagonal discontinua
                if (i <= size - 4 && j <= size - 4 && adn[i].charAt(j) == adn[i + 2].charAt(j + 2)) {
                    // Revisa los elementos contiguos
                    if (adn[i].charAt(j) == adn[i + 1].charAt(j + 1) && adn[i].charAt(j) == adn[i + 3].charAt(j + 3)) {
                        secuenciasEncontradas++;
                    }
                }

                // Revisión diagonal invertida discontinua
                if (i <= size - 4 && j >= 3 && adn[i].charAt(j) == adn[i + 2].charAt(j - 2)) {
                    // Revisa los elementos contiguos
                    if (adn[i].charAt(j) == adn[i + 1].charAt(j - 1) && adn[i].charAt(j) == adn[i + 3].charAt(j - 3)) {
                        secuenciasEncontradas++;
                    }
                }

                // Si encontramos al menos 2 secuencias, consideramos que es un mutante
                if (secuenciasEncontradas >= 2) {
                    return true;
                }
            }
        }

        return false;
    }
}

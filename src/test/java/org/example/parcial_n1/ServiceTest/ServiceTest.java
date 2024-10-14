package org.example.parcial_n1.ServiceTest;

import org.example.parcial_n1.services.AdnService;
import org.example.parcial_n1.repositories.AdnRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceTest {
    private AdnService adnService;
    private AdnRepository mockAdnRepository;

    @BeforeEach
    void setUp() {
        mockAdnRepository = mock(AdnRepository.class);
        adnService = new AdnService(mockAdnRepository);
    }

    @Test
    void testAnalyzeAdn_WithMutantDNA_ReturnsTrue() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        when(mockAdnRepository.findByAdn(anyString())).thenReturn(Optional.empty());
        boolean result = adnService.analyzeAdn(dna);
        assertTrue(result);
    }

    @Test
    void testAnalyzeAdn_WithHumanDNA_ReturnsFalse() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        when(mockAdnRepository.findByAdn(anyString())).thenReturn(Optional.empty());
        boolean result = adnService.analyzeAdn(dna);
        assertFalse(result);
    }

    @Test
    void testAnalyzeAdn_WithInvalidAdn_ThrowsException() {
        String[] dna = {"AATGCGA", "CAGTGC", "ATTTTT", "AGACGG", "GCGTCA", "TCCCCG"};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            adnService.analyzeAdn(dna);
        });
        assertEquals("Secuencia de ADN inválida", exception.getMessage());
    }

    @Test
    void testAnalyzeAdn_WithNullAdn_ThrowsException() {
        String[] dna = null;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            adnService.analyzeAdn(dna);
        });
        assertEquals("Secuencia de ADN inválida", exception.getMessage());
    }

    @Test
    void testAnalyzeAdn_WithEmptyAdn_ThrowsException() {
        String[] dna = {};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            adnService.analyzeAdn(dna);
        });
        assertEquals("Secuencia de ADN inválida", exception.getMessage());
    }

    @Test
    void testAnalyzeAdn_WithNonSquareAdn_ThrowsException() {
        String[] dna = {"ATGC", "CAGTGC", "TTATGT"};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            adnService.analyzeAdn(dna);
        });
        assertEquals("Secuencia de ADN inválida", exception.getMessage());
    }

    @Test
    void testAnalyzeAdn_WithNullElements_ThrowsException() {
        String[] dna = {"ATGCGA", null, "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            adnService.analyzeAdn(dna);
        });
        assertEquals("Secuencia de ADN inválida", exception.getMessage());
    }

    @Test
    void testAnalyzeAdn_WithInvalidCharacters_ThrowsException() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "BGAAGG", "CCCCTA", "TCACTG"};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            adnService.analyzeAdn(dna);
        });
        assertEquals("Secuencia de ADN inválida", exception.getMessage());
    }
}

package org.example.parcial_n1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Adn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // o GenerationType.AUTO
    private Long id; // Asegúrate de tener un ID como clave primaria
    private String adn;
    private boolean isMutant;
}

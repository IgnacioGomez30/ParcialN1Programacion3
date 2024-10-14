    package org.example.parcial_n1.dto;

    import com.fasterxml.jackson.annotation.JsonProperty;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.Setter;

    @Getter
    @Setter@AllArgsConstructor

    public class StatsResponse {
        @JsonProperty("count_mutant_adn")
        private long countMutantAdn;

        @JsonProperty("count_human_adn")
        private long countHumanAdn;

        private double ratio;
    }

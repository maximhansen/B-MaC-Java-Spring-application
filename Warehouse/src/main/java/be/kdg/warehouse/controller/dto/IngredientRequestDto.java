package be.kdg.warehouse.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class IngredientRequestDto {
    private UUID id;
    private String name;

    public IngredientRequestDto() {
    }

    public IngredientRequestDto(UUID id, String name) {
        this.name = name;
        this.id = id;
    }
}

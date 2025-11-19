package cl.huertohogar.ordenes_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO con información de la categoría del producto")
public class CategoriaDTO {

    @JsonProperty("idCategoria")
    @Schema(description = "ID único de la categoría", example = "1")
    private Integer idCategoria;

    @JsonProperty("nombreCategoria")
    @Schema(description = "Nombre de la categoría", example = "Verduras")
    private String nombreCategoria;

    @JsonProperty("descripcionCategoria")
    @Schema(description = "Descripción de la categoría", example = "Productos frescos de huerta")
    private String descripcionCategoria;
    
}

package cl.huertohogar.ordenes_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO con información del país de origen del producto")
public class PaisOridenDTO {
    
    @JsonProperty("idPaisOrigen")
    @Schema(description = "ID único del país", example = "1")
    private Integer idPais;

    @JsonProperty("nombrePais")
    @Schema(description = "Nombre del país", example = "Chile")
    private String nombrePais;

    

}

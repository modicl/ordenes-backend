package cl.huertohogar.ordenes_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

    @JsonProperty("idCategoria")
    private Integer idCategoria;

    @JsonProperty("nombreCategoria")
    private String nombreCategoria;

    @JsonProperty("descripcionCategoria")
    private String descripcionCategoria;
    
}

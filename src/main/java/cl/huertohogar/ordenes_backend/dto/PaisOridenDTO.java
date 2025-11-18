package cl.huertohogar.ordenes_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaisOridenDTO {
    
    @JsonProperty("idPaisOrigen")
    private Integer idPais;

    @JsonProperty("nombrePais")
    private String nombrePais;

    

}

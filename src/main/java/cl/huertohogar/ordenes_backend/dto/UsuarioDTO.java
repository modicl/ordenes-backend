package cl.huertohogar.ordenes_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    @JsonProperty("idUsuario")
    private Integer idUsuario;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("apaterno")
    private String apellido;

    @JsonProperty("apmaterno")
    private String apmaterno;

    @JsonProperty("rut")
    private String rut;

    @JsonProperty("dv")
    private String dv;

    @JsonProperty("direccion")
    private String direccion;

    @JsonProperty("telefono")
    private String telefono;


    @JsonProperty("email")
    private String email;
    
}
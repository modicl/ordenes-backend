package cl.huertohogar.ordenes_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO con información del usuario obtenida desde el microservicio de usuarios")
public class UsuarioDTO {

    @JsonProperty("idUsuario")
    @Schema(description = "ID único del usuario", example = "5")
    private Integer idUsuario;

    @JsonProperty("nombre")
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @JsonProperty("apaterno")
    @Schema(description = "Apellido paterno del usuario", example = "Pérez")
    private String apellido;

    @JsonProperty("apmaterno")
    @Schema(description = "Apellido materno del usuario", example = "González")
    private String apmaterno;

    @JsonProperty("rut")
    @Schema(description = "RUT del usuario sin dígito verificador", example = "12345678")
    private String rut;

    @JsonProperty("dv")
    @Schema(description = "Dígito verificador del RUT", example = "9")
    private String dv;

    @JsonProperty("direccion")
    @Schema(description = "Dirección del usuario", example = "Av. Libertador 1234, Santiago")
    private String direccion;

    @JsonProperty("telefono")
    @Schema(description = "Teléfono del usuario", example = "+56912345678")
    private String telefono;


    @JsonProperty("email")
    @Schema(description = "Email del usuario", example = "juan.perez@example.com")
    private String email;
    
}
package cl.huertohogar.ordenes_backend.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import cl.huertohogar.ordenes_backend.client.UsuarioFeignClient;
import cl.huertohogar.ordenes_backend.model.DetalleOrden;

// Esta es la respuesta que devolvemos para cada orden ya que trae el usuario

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenResponseDTO {

    @JsonProperty("idOrden")
    private Integer idOrden;

    @JsonProperty("idUsuario")
    private Integer idUsuario;

    @JsonProperty("usuario")
    private UsuarioDTO usuario;

    @JsonProperty("fechaOrden")
    private LocalDate fechaOrden;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("totalOrden")
    private Integer totalOrden;

    @JsonProperty("direccionEnvio")
    private String direccionEnvio;

    @JsonProperty("detalleOrden")
    private List<DetalleOrdenDTO> detalles;
}
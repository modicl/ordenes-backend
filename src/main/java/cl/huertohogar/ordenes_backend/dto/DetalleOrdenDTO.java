package cl.huertohogar.ordenes_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleOrdenDTO {

    @JsonProperty("idDetalle")
    private Integer idDetalle;

    @JsonProperty("producto")
    private ProductoDTO producto;

    @JsonProperty("cantidad")
    private Integer cantidad;

    @JsonProperty("precioUnitario")
    private Integer precioUnitario;
}
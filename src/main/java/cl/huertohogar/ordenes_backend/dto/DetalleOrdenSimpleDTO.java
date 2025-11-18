package cl.huertohogar.ordenes_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Usamos esta para devoler info de los detalles de la compra, no genera un loop infinito

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleOrdenSimpleDTO {

    @JsonProperty("idOrden")
    private Integer idOrden;

    @JsonProperty("idDetalle")
    private Integer idDetalle;

    @JsonProperty("idProducto")
    private Integer idProducto;

    @JsonProperty("cantidad")
    private Integer cantidad;

    @JsonProperty("precioUnitario")
    private Integer precioUnitario;
}
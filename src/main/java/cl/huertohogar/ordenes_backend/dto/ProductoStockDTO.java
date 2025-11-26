package cl.huertohogar.ordenes_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoStockDTO {
    
    @Schema(description = "ID del producto", example = "1")
    private Integer idProducto;

    @Schema(description = "Nombre del producto", example = "Manzana")
    private String nombreProducto;

    @Schema(description = "Stock anterior", example = "10")
    private Integer stockAnterior;

    @Schema(description = "Stock nuevo", example = "8")
    private Integer stockNuevo;

    @Schema(description = "Cantidad descontada", example = "2")
    private Integer cantidadDescontada;
}
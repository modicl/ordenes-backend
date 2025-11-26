package cl.huertohogar.ordenes_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Detalle de un item en la orden")
public class ItemOrdenDTO {
    
    @Schema(description = "ID del producto", example = "1")
    private Integer idProducto;

    @Schema(description = "Cantidad del producto", example = "2")
    private Integer cantidad;


}

package cl.huertohogar.ordenes_backend.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Solicitud para actualizar stock de múltiples productos")
public class ActualizacionStockRequestDTO {
    
    @Schema(description = "ID de la orden", example ="1")
    private Integer idOrden;

    @Schema(description = "Lista de items a procesar")
    private List<ItemOrdenDTO> items;

}


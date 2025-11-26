package cl.huertohogar.ordenes_backend.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Respuesta de la actualización de stock")
public class ActualizacionStockResponseDTO {
    
    @Schema(description = "Indica si la operación fue exitosa")
    private boolean exitoso;
    
    @Schema(description = "Mensaje descriptivo del resultado")
    private String mensaje;
    
    @Schema(description = "Lista de productos procesados")
    private List<ProductoStockDTO> productosActualizados;
    
    @Schema(description = "Lista de errores si los hay")
    private List<String> errores;
}

package cl.huertohogar.ordenes_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO con información completa del producto obtenida desde el microservicio de productos")
public class ProductoDTO {
    
    @JsonProperty("idProducto")
    @Schema(description = "ID único del producto", example = "10")
    private Integer idProducto;

    @JsonProperty("nombreProducto")
    @Schema(description = "Nombre del producto", example = "Lechuga Orgánica")
    private String nombreProducto;

    @JsonProperty("categoria")
    @Schema(description = "Categoría del producto")
    private CategoriaDTO categoria;


    @JsonProperty("descripcionProducto")
    @Schema(description = "Descripción detallada del producto", example = "Lechuga fresca cultivada orgánicamente")
    private String descripcionProducto;

    @JsonProperty("precioProducto")
    @Schema(description = "Precio del producto en pesos chilenos", example = "5000")
    private Integer precioProducto;

    @JsonProperty("stockProducto")
    @Schema(description = "Cantidad disponible en inventario", example = "50")
    private Integer stockProducto;

    @JsonProperty("paisOrigen")
    @Schema(description = "País de origen del producto")
    private PaisOridenDTO paisOrigen;

    @JsonProperty("imagenUrl")
    @Schema(description = "URL de la imagen del producto", example = "https://example.com/lechuga.jpg")
    private String imagenUrl;


}

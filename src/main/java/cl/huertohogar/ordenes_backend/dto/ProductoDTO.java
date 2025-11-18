package cl.huertohogar.ordenes_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductoDTO {
    
    @JsonProperty("idProducto")
    private Integer idProducto;

    @JsonProperty("nombreProducto")
    private String nombreProducto;

    @JsonProperty("categoria")
    private CategoriaDTO categoria;


    @JsonProperty("descripcionProducto")
    private String descripcionProducto;

    @JsonProperty("precioProducto")
    private Integer precioProducto;

    @JsonProperty("stockProducto")
    private Integer stockProducto;

    @JsonProperty("paisOrigen")
    private PaisOridenDTO paisOrigen;

    @JsonProperty("imagenUrl")
    private String imagenUrl;


}

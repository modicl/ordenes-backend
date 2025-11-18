package cl.huertohogar.ordenes_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detalle_ordenes")

public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    @JsonProperty("idDetalle")
    private Integer idDetalleOrden;

    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    @JsonBackReference
    private Orden orden;

    @Column(name = "id_producto")
    @JsonProperty("idProducto")
    private Integer idProducto;

    @Column(name = "cantidad")
    @JsonProperty("cantidad")
    private Integer cantidad;

    @Column(name = "precio_unitario")
    @JsonProperty("precioUnitario")
    private Integer precioUnitario;

}

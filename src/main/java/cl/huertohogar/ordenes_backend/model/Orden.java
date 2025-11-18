package cl.huertohogar.ordenes_backend.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ordenes")

public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    @JsonProperty("idOrden")
    private Integer idOrden;

    @Column(name = "id_usuario")
    @JsonProperty("idUsuario")
    private Integer idUsuario;

    @Column(name = "fecha_orden")
    @JsonProperty("fechaOrden")
    private LocalDate fechaOrden;

    @Column(name = "estado")
    @JsonProperty("estado")
    private String estado;

    @Column(name = "total_orden")
    @JsonProperty("totalOrden")
    private Integer totalOrden;

    @Column(name = "direccion_envio")
    @JsonProperty("direccionEnvio")
    private String direccionEnvio;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("detalleOrden")
    private List<DetalleOrden> detalleOrden;

}

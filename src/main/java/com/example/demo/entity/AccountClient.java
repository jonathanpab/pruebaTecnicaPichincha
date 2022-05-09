package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cuenta_cliente")
public class AccountClient {
    @Id
    @Column(name = "numero_cuenta", nullable = false)
    private long numero_cuenta;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "saldo_inicial", nullable = false)
    private Float saldo_inicial;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Client client;
}


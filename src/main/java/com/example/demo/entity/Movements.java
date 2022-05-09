package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "movimientos")
public class Movements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_movimiento;

    @Column(name = "fecha")
    private Timestamp fecha;

    @Column(name = "tipo_movimiento")
    private Character tipo_movimiento;

    @Column(name = "valor")
    private Float valor;

    @Column(name = "saldo")
    private Float saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_cuenta", referencedColumnName = "numero_cuenta")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AccountClient cuenta;
}


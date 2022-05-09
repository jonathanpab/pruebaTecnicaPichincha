package com.example.demo.structure;

public class AccountStructure {
    private long numero_cuenta;
    private String tipo;
    private Float saldo_inicial;
    private Boolean estado;
    private String cliente;

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public long getNumero_cuenta() {
        return numero_cuenta;
    }

    public void setNumero_cuenta(long numero_cuenta) {
        this.numero_cuenta = numero_cuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Float getSaldo_inicial() {
        return saldo_inicial;
    }

    public void setSaldo_inicial(Float saldo_inicial) {
        this.saldo_inicial = saldo_inicial;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}

package com.example.tipo_cambio.Model;

public class TipoCambio {
    private String moneda;
    private String compra;
    private String venta;
    private String fecha;

    // Getters y Setters
    public String getMoneda() {
        return moneda;
    }
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    public String getCompra() {
        return compra;
    }
    public void setCompra(String compra) {
        this.compra = compra;
    }
    public String getVenta() {
        return venta;
    }
    public void setVenta(String venta) {
        this.venta = venta;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

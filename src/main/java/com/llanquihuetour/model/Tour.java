package com.llanquihuetour.model;

/**
 * Clase que representa a un Tour
 * Posee los atributos: nombre, tipoTour, destino y precio
 */
public class Tour {
    private String nombre;
    private String tipoTour;
    private String destino;
    private double precio;

    /**
     * Constructor para crear los objetos e inicializar sus atributos
     * @param nombre
     * @param tipoTour
     * @param destino
     * @param precio
     */
    public Tour(String nombre, String tipoTour, String destino, double precio) {
        this.nombre = nombre;
        this.tipoTour = tipoTour;
        this.destino = destino;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoTour() {
        return tipoTour;
    }
    public void setTipoTour(String tipoTour) {
        this.tipoTour = tipoTour;
    }

    public String getDestino() {
        return destino;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Metodo que convierte el objeto Tour a una representación textual y lo retorna
     * @return
     */
    @Override
    public String toString() {
        return nombre+", Tipo de tour: "+tipoTour+", Destino: "+destino+", Precio: "+precio;
    }
}

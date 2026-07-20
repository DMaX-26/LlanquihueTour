package com.llanquihuetour.model;

/**
 * Clase que representa a una Dirección
 */
public class Direccion {
    private String calle;
    private String ciudad;

    /**
     * Constructor para crear los objetos e inicializar sus atributos
     * @param calle
     * @param ciudad
     */
    public Direccion(String calle, String ciudad) {
        this.calle = calle;
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }
    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Dirección: "+calle+", Ciudad: "+ciudad;
    }
}

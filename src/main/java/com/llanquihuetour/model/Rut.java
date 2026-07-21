package com.llanquihuetour.model;

import com.llanquihuetour.exception.RutInvalidoException;

/**
 * Clase que representa a un rut
 */
public class Rut {
    private String numeroRut;

    /**
     * Constructor para crear los objetos e inicializar sus atributos
     * @param numeroRut
     * @throws RutInvalidoException
     */
    public Rut(String numeroRut) throws RutInvalidoException {
        /**
         * Si el numeroRut no coincide con el formato
         * Se lanza una excepción personalizada
         */
        if (!numeroRut.matches("[0-9]+-[0-9Kk]")) {
            throw new RutInvalidoException("Formato de RUT no válido.");
        }
        this.numeroRut = numeroRut;
    }

    public String getNumeroRut() {
        return numeroRut;
    }

    public void setNumeroRut(String numeroRut) {
        this.numeroRut = numeroRut;
    }

    /**
     * Conversion de la clase Rut a una representación textual
     * @return
     */
    @Override
    public String toString() {
        return numeroRut;
    }
}

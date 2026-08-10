package com.llanquihuetour.model;

import com.llanquihuetour.exception.CantidadPersonasInvalidaException;
import com.llanquihuetour.exception.PrecioInvalidoException;
import com.llanquihuetour.interfaces.Registrable;

/**
 * Clase que representa a una Reserva
 * Implementa la interfaz Registrable
 * Una Reserva contiene un Cliente y un Tour
 */
public class Reserva implements Registrable {
    private String id;
    private String fecha;
    private int cantidadPersonas;
    private Cliente cliente;
    private Tour tour;

    /**
     * Constructor para crear los objetos
     * @param id
     * @param fecha
     * @param cantidadPersonas
     * @param cliente
     * @param tour
     */
    public Reserva(String id, String fecha, int cantidadPersonas, Cliente cliente, Tour tour) throws PrecioInvalidoException, CantidadPersonasInvalidaException {
        this.id = id;
        this.fecha = fecha;
        /**
         * Se valida que el precio del tour y la cantidad de personas que toman una reserva sean correctos, antes de inicializar sus atributos
         */
        if (tour.getPrecio() < 0){
            throw new PrecioInvalidoException("El precio ingresado no es válido");
        }
        if (cantidadPersonas <= 0){
            throw new CantidadPersonasInvalidaException("La cantidad de personas debe ser mayor que Cero");
        }
        this.cantidadPersonas = cantidadPersonas;
        this.cliente = cliente;
        this.tour = tour;
    }

    /**
     * Métodos getter and setter
     * @return
     */
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }
    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tour getTour() {
        return tour;
    }
    public void setTour(Tour tour) {
        this.tour = tour;
    }

    /**
     * Metodo toString que convierte el objeto Reserva en una representación textual
     * @return
     */
    @Override
    public String toString() {
        return "Id: "+id+", Fecha de reserva: "+fecha+", Cantidad de Personas: "+cantidadPersonas+", Cliente: "+cliente+", Tour: "+tour;
    }

    /**
     * Se implementa el metodo registrar de la interfaz Registrable
     */
    @Override
    public void registrar() {
        System.out.println();
        System.out.println(">>> \033[32mReserva "+id+" registrada correctamente\u001B[0m <<<");
        System.out.println();
    }

    /**
     * Se implementa el metodo mostrarDatos de la interfaz Registrable
     */
    @Override
    public void mostrarDatos() {
        System.out.println("\033[35m°°°Reserva°°°\u001B[0m");
        System.out.println("Id: "+id);
        System.out.println("Fecha de reserva: "+fecha);
        System.out.println("Cantidad de personas: "+cantidadPersonas);
        System.out.println("Cliente: "+cliente);
        System.out.println("Tour reservado: "+tour);
    }
}

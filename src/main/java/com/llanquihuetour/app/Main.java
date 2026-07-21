package com.llanquihuetour.app;

import com.llanquihuetour.interfaces.Registrable;
import com.llanquihuetour.data.GestorDatos;
import com.llanquihuetour.exception.CantidadPersonasInvalidaException;
import com.llanquihuetour.exception.EdadInvalidaException;
import com.llanquihuetour.exception.PrecioInvalidoException;
import com.llanquihuetour.exception.RutInvalidoException;
import com.llanquihuetour.model.*;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, RutInvalidoException {

        //Try-catch para manejo de excepciones
        try {
            //Se crea una instancia de gestorDatos
            GestorDatos gestorDatos = new GestorDatos();
            //gestorDatos llama al metodo crearRegistros y los guarda en una variable de tipo List<Registrable>
            List<Registrable> registrables = gestorDatos.crearRegistros();

            //For each para recorrer la lista "registrables"
            for (Registrable r : registrables){
                //"r" llama al los métodos mostrarDatos y registrar, correspondientes a cada clase que implemente la interfaz "Registrable"
                r.mostrarDatos();
                r.registrar();
            }
            //Llamada del metodo buscarPorIdioma, que filtra los guías turísticos por su idioma
            gestorDatos.buscarPorIdioma("español");
            //Llamada del metodo buscarPorNacionalidad, que filtra los clientes por su nacionalidad
            gestorDatos.buscarPorNacionalidad("chilena");

        } catch (RutInvalidoException e) {
            System.out.println("Error: "+e.getMessage());;
        } catch (PrecioInvalidoException e) {
            System.out.println("Error: "+e.getMessage());;
        } catch (EdadInvalidaException e) {
            System.out.println("Error: "+e.getMessage());
        } catch (CantidadPersonasInvalidaException e) {
            System.out.println("Error: "+e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al leer el archivo");
        }
    }
}
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

            //For each para recorrer la lista registrables
            for (Registrable r : registrables){
                //Si el recorrido es de tipo "GuiaTuristico"
                if (r instanceof GuiaTuristico){
                    GuiaTuristico guia = (GuiaTuristico) r;//Trata a "r" como un GuiaTuristico y cada elemento se guarda en la variable guia de tipo GuiaTuristico
                    //guia llama a los metodos mostrarDatos y registrar
                    guia.mostrarDatos();
                    guia.registrar();
                }
                //Si cada elemento del recorrido es de tipo Cliente
                if (r instanceof Cliente){
                    Cliente cliente = (Cliente) r;//Se trata como a un Cliente
                    //cliente llama a los metodos mostrarDatos y registrar
                    cliente.mostrarDatos();
                    cliente.registrar();
                }
                //Si cada elemento del recorrido es de tipo ProveedorTransporte
                if (r instanceof ProveedorTransporte){
                    ProveedorTransporte proveedorTransporte = (ProveedorTransporte) r;//Se trata como a un ProveedorTransporte
                    //proveedorTransporte llama a los metodos mostrarDatos y registrar
                    proveedorTransporte.mostrarDatos();
                    proveedorTransporte.registrar();
                }
                //Si cada elemento del recorrido es de tipo ProveedorAlojamiento
                if (r instanceof ProveedorAlojamiento){
                    ProveedorAlojamiento proveedorAlojamiento = (ProveedorAlojamiento) r;//Se trata como a un ProveedorAlojamiento
                    //proveedorAlojamiento llama a los metodos mostrarDatos y registrar
                    proveedorAlojamiento.mostrarDatos();
                    proveedorAlojamiento.registrar();
                }
                //Si cada elemento del recorrido es de tipo Reserva
                if (r instanceof Reserva){
                    Reserva reserva = (Reserva) r;//Se trata como a una Reserva
                    //reserva llama a los metodos mostrarDatos y registrar
                    r.mostrarDatos();
                    r.registrar();
                }
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
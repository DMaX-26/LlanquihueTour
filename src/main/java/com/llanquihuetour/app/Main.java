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
                if (r instanceof Cliente){
                    Cliente cliente = (Cliente) r;
                    cliente.mostrarDatos();
                    cliente.registrar();
                }
                if (r instanceof ProveedorTransporte){
                    ProveedorTransporte proveedorTransporte = (ProveedorTransporte) r;
                    proveedorTransporte.mostrarDatos();
                    proveedorTransporte.registrar();
                }
                if (r instanceof ProveedorAlojamiento){
                    ProveedorAlojamiento proveedorAlojamiento = (ProveedorAlojamiento) r;
                    proveedorAlojamiento.mostrarDatos();
                    proveedorAlojamiento.registrar();
                }
                if (r instanceof Reserva){
                    Reserva reserva = (Reserva) r;
                    r.mostrarDatos();
                    r.registrar();
                }
            }
            gestorDatos.buscarPorIdioma("español");
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
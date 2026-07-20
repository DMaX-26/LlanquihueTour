package com.llanquihuetour.data;

import com.llanquihuetour.interfaces.Registrable;
import com.llanquihuetour.exception.CantidadPersonasInvalidaException;
import com.llanquihuetour.exception.EdadInvalidaException;
import com.llanquihuetour.exception.PrecioInvalidoException;
import com.llanquihuetour.exception.RutInvalidoException;
import com.llanquihuetour.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GestorDatos {
    private List<Registrable> registros;
    //Hashmap que filtra guías turísticos por idioma, donde su "clave" es un idioma y "valor" la lista de guías que hablan ese idioma
    private HashMap<String, List<GuiaTuristico>> buscarPorIdioma;
    //Hashmap que filtra clientes por su nacionalidad, donde su "clave" es una nacionalidad y "valor" la lista de clientes con esa nacionalidad
    private HashMap<String, List<Cliente>> buscarPorNacionalidad;

    public List<Registrable> crearRegistros() throws IOException, RutInvalidoException, PrecioInvalidoException, EdadInvalidaException, CantidadPersonasInvalidaException {
        this.registros = new ArrayList<>();
        this.buscarPorIdioma = new HashMap<>();
        this.buscarPorNacionalidad = new HashMap<>();

        /**
         * Se crea el lector del archivo registros.txt
         */
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/registros.txt"));

        String linea;
        GuiaTuristico guia = null;

        /**
         * Lee línea por línea hasta el final
         */
        while ((linea = reader.readLine()) != null){
            /**
             * Se divide cada dato del recorrido cuando se encuentra un ";" (para separar datos). Luego esos datos se guardan en el arreglo "partes"
             */
            String[] partes = linea.split(";");

            /**
             * Validamos si el dato en la posición 0 es un "GUIA"
             */
            if(partes[0].equals("GUIA")){
                /**
                 * Se crea la instancia rutGuia y le pasamos partes[2] que es la posicion de rut en el archivo
                 */
                Rut rutGuia = new Rut(partes[2]);

                /**
                 * Se crea una instancia de GuiaTuristico y le pasamos la posición de cada atributo dentro del archivo
                 */
                guia = new GuiaTuristico(partes[1], rutGuia, partes[3], partes[4], Integer.parseInt(partes[5]), partes[6]);

                /**
                 * Si la edad del Guia es menor que 0, se lanza una excepción personalizada
                 */
                if (guia.getEdad()<0){
                    throw new EdadInvalidaException("La edad ingresada no es válida");
                }

                /**
                 * Se agrega "guia" a la lista registros
                 */
                registros.add(guia);

                /**
                 * Si el idioma obtenido de guiaTuristico no existe en el HashMap
                 */
                if (!buscarPorIdioma.containsKey(guia.getIdiomas())){
                    /**
                     * Se agrega el idioma obtenido como clave al hashmap y una lista vacía como valor
                     */
                    buscarPorIdioma.put(guia.getIdiomas(), new ArrayList<>());
                }
                /**
                 * Se obtiene el idioma como clave
                 * Se añade guiaTuristico a la lista asociada a ese idioma
                 */
                buscarPorIdioma.get(guia.getIdiomas()).add(guia);
            }

            /**
             * Validamos si el dato en la posición 0 es un "CLIENTE"
             */
            if (partes[0].equals("CLIENTE")){
                /**
                 * Se crea la instancia rutCliente y le pasamos partes[1] que es la posicion de rut en el archivo
                 */
                Rut rutCliente = new Rut(partes[2]);

                Cliente cliente = new Cliente(partes[1], rutCliente, partes[3], partes[4], Integer.parseInt(partes[5]), partes[6], guia);

                if (cliente.getEdad() < 0){
                    throw new EdadInvalidaException("La edad ingresada no es válida");
                }

                registros.add(cliente);

                /**
                 * Si la nacionalidad obtenida de cliente no existe en el hashmap
                 */
                if (!buscarPorNacionalidad.containsKey(cliente.getNacionalidad())){
                    /**
                     * Se agrega la nacionalidad obtenida como "clave" al hashmap y una lista vacía como "valor"
                     */
                    buscarPorNacionalidad.put(cliente.getNacionalidad(), new ArrayList<>());
                }
                /**
                 * Se obtiene la nacionalidad como clave
                 * Se añade cliente a la lista asociada a esa nacionalidad
                 */
                buscarPorNacionalidad.get(cliente.getNacionalidad()).add(cliente);
            }

            /**
             * Validamos si el dato en la posición 0 es un "PROVEEDOR_TRANSPORTE"
             */
            if (partes[0].equals("PROVEEDOR_TRANSPORTE")){
                /**
                 * Se crea una instancia de direccion y le pasamos las partes correspondientes a su posición dentro de ProveedorTransporte
                 */
                Direccion direccion = new Direccion(partes[6], partes[7]);

                /**
                 * Se crea una instancia de Rut y le pasamos su posición correpondiente dentro de ProveedorTransporte
                 */
                Rut rutProvTransporte = new Rut(partes[2]);

                /**
                 * Se crea una instancia de ProveedorTransporte y le pasamos cada posición de su correspondiente atributo dentro del archivo
                 */
                ProveedorTransporte provTransporte = new ProveedorTransporte(partes[1], rutProvTransporte, partes[3], partes[4], partes[5], direccion);

                /**
                 * Se agrega provTransporte a la lista registros
                 */
                registros.add(provTransporte);
            }

            /**
             * Validamos si el dato en la posición 0 es un "PROVEEDOR_ALOJAMIENTO"
             */
            if (partes[0].equals("PROVEEDOR_ALOJAMIENTO")){
                /**
                 * Se crea una instancia de direccion y le pasamos las partes correspondientes a su posición dentro de ProveedorTransporte
                 */
                Direccion direccion = new Direccion(partes[6], partes[7]);

                /**
                 * Se crea una instancia de Rut y le pasamos su posición correspondiente dentro de ProveedorAlojamiento
                 */
                Rut rutProvAlojamiento = new Rut(partes[2]);

                /**
                 * Se crea una instancia de ProveedorAlojamiento y le pasamos cada posición de su correspondiente atributo dentro del archivo
                 */
                ProveedorAlojamiento provAlojamiento = new ProveedorAlojamiento(partes[1], rutProvAlojamiento, partes[3], partes[4], Integer.parseInt(partes[5]), direccion);

                /**
                 * Si el precio por noche es menor a 0, se lanza una excepción personalizada
                 */
                if (provAlojamiento.getPrecioPorNoche() < 0){
                    throw new PrecioInvalidoException("El precio por noche ingresado no es válido");
                }

                /**
                 * Se agrega provAlojamiento a la lista registros
                 */
                registros.add(provAlojamiento);
            }

            if (partes[0].equals("RESERVA")){
                /**
                 * Se crea la instancia rutGuia y se la pasa su posición dentro de guiaTuristico
                 */
                Rut rutGuia = new Rut(partes[11]);

                /**
                 * Se crea una instancia de GuiaTuristico y le pasamos cada posición de su correspondiente atributo dentro del archivo
                 */
                GuiaTuristico guiaTuristico = new GuiaTuristico(partes[10], rutGuia, partes[12], partes[13], Integer.parseInt(partes[14]), partes[15]);

                /**
                 * Se crea la instancia rutCliente y le pasamos su posición dentro de Cliente
                 */
                Rut rutCliente = new Rut(partes[5]);

                /**
                 * Se crea una instancia de Cliente y le pasamos cada posición de su correspondiente atributo dentro del archivo
                 */
                Cliente cliente = new Cliente(partes[4], rutCliente, partes[6], partes[7], Integer.parseInt(partes[8]), partes[9], guiaTuristico);

                /**
                 * Se crea una instancia de Tour y le pasamos cada posición de su correspondiente atributo dentro del archivo
                 */
                Tour tour = new Tour(partes[16], partes[17], partes[18], Integer.parseInt(partes[19]));

                /**
                 * Se crea una instancia de Reserva, le pasamos cada posición de su correspondiente atributo dentro del archivo
                 */
                Reserva reserva = new Reserva(partes[1], partes[2], Integer.parseInt(partes[3]), cliente, tour);

                /**
                 * Se valida que el precio del tour y la cantidad de personas que toman una reserva sean correctos
                 */
                if (tour.getPrecio() < 0){
                    throw new PrecioInvalidoException("El precio ingresado no es válido");
                }
                if (reserva.getCantidadPersonas() <= 0){
                    throw new CantidadPersonasInvalidaException("La cantidad de personas debe ser mayor que Cero");
                }
                /**
                 * Se agrega reserva a la lista registros
                 */
                registros.add(reserva);
            }
        }
        return registros;
    }

    /**
     * Metodo que imprime el idioma obtenido (clave) y el Guía turístico asociado (valor) dentro del hashmap
     * @param idiomas
     */
    public void buscarPorIdioma(String idiomas){
        System.out.println("\033[33mFiltrar Guías Turísticos por idioma/s:\u001B[0m "+idiomas);
        System.out.println(buscarPorIdioma.get(idiomas));
        System.out.println();
    }

    /**
     * Metodo que muestra la nacionalidad obtenida (clave) y el cliente asociado (valor)
     * @param nacionalidad
     */
    public void buscarPorNacionalidad(String nacionalidad){
        System.out.println("\033[33mFiltrar Clientes por nacionalidad:\u001B[0m "+nacionalidad);
        System.out.println(buscarPorNacionalidad.get(nacionalidad));
        System.out.println();
    }
}

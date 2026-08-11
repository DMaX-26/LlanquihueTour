package com.llanquihuetour.app;

import com.llanquihuetour.exception.CantidadPersonasInvalidaException;
import com.llanquihuetour.exception.EdadInvalidaException;
import com.llanquihuetour.exception.PrecioInvalidoException;
import com.llanquihuetour.exception.RutInvalidoException;
import com.llanquihuetour.model.*;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class gui extends JFrame {
    private ArrayList<GuiaTuristico> guiasTuristicos = new ArrayList<>();
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<ProveedorTransporte> proveedoresTransporte = new ArrayList<>();
    private ArrayList<ProveedorAlojamiento> proveedoresAlojamiento = new ArrayList<>();
    private ArrayList<Tour> tours = new ArrayList<>();
    private ArrayList<Reserva> reservas = new ArrayList<>();
    //Hashmap que filtra guías turísticos por idioma, donde su "clave" es un idioma (String) y "valor" la lista de guías que hablan ese idioma
    private HashMap<String, List<GuiaTuristico>> buscarPorIdioma = new HashMap<>();
    //Hashmap que filtra clientes por nacionalidad, donde su "clave" es una nacionalidad (String) y "valor" la lista de clientes con esa nacionalidad
    private HashMap<String, List<Cliente>> buscarPorNombre = new HashMap<>();

    // Lista visual de organizadores (campo para poder refrescarla desde agregarOrganizador)
    private final JList<GuiaTuristico> guiasTuristicosList = new JList<>();
    private final JList<Cliente> clientesList = new JList<>();
    private final JList<ProveedorTransporte> proveedoresTransporteList = new JList<>();
    private final JList<ProveedorAlojamiento> proveedoresAlojamientoList = new JList<>();
    private final JList<Tour> toursList = new JList<>();
    private final JList<Reserva> reservasList = new JList<>();

    //Combobox de tipo GuiaTuristico, Cliente y Tour
    private JComboBox<GuiaTuristico> comboGuias;
    private JComboBox<Cliente> comboClientes;
    private JComboBox<Tour> comboTours;

    //Constructor
    public gui(){
        //Titulo
        setTitle("Gestión de Agencia");
        //Tamaño ventana
        setSize(750, 550);
        //Ubicar ventana al centro de la pantalla
        setLocationRelativeTo(null);
        //organizar las componentes en 5 zonas
        setLayout(new BorderLayout());
        //Cerrar la ventana al presionar "x"
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Se muestra toda la información especificada del objeto GuiaTuristico en el Jlist
        guiasTuristicosList.setCellRenderer(new IdRenderer<GuiaTuristico>(g -> "Nombre: "+ g.getNombre()+ " | Rut: "+ g.getRut()+ " | Correo: " +g.getCorreo()+ " | Teléfono: "+g.getTelefono()+ " | Edad: "+g.getEdad()+ " | Idiomas: "+g.getIdiomas()));
        //Se muestra toda la información especificada del objeto ColaboradorExterno en el Jlist
        clientesList.setCellRenderer(new IdRenderer<Cliente>(c -> "Nombre: "+c.getNombre() + " | Rut: "+c.getRut()+ " | Correo: "+c.getCorreo()+ " | Teléfono: "+c.getTelefono()+ " | Edad: "+c.getEdad()+ " | Nacionalidad: "+c.getNacionalidad()+" | Guía Turístico: "+c.getGuiaTuristico()));
        //Se muestra toda la información especificada del objeto ProveedorTransporte en el Jlist
        proveedoresTransporteList.setCellRenderer(new IdRenderer<ProveedorTransporte>(pt -> "Nombre: "+pt.getNombre()+ " | Rut: "+pt.getRut()+" | Correo: "+pt.getCorreo()+ " | Teléfono: "+pt.getTelefono()+ " | Tipo de transporte: "+pt.getTipoTransporte()+ " | Dirección: "+pt.getDireccion()));
        //Se muestra toda la información especificada del objeto ProveedorAlojamiento en el Jlist
        proveedoresAlojamientoList.setCellRenderer(new IdRenderer<ProveedorAlojamiento>(pa -> "Nombre: "+pa.getNombre()+ " | Rut: "+pa.getRut()+" | Correo: "+pa.getCorreo()+ " | Teléfono: "+pa.getTelefono()+" | Precio por noche: "+pa.getPrecioPorNoche()+ " | Dirección: "+pa.getDireccion()));
        //Se muestra toda la información especificada del objeto Tour en el Jlist
        toursList.setCellRenderer(new IdRenderer<Tour>(t -> "Nombre: "+t.getNombre()+ " | Tipo de Tour: "+t.getTipoTour()+" | Destino: "+t.getDestino()+ " | Precio: "+t.getPrecio()));
        //Se muestra toda la información especificada del objeto Reserva en el Jlist
        reservasList.setCellRenderer(new IdRenderer<Reserva>(r -> "ID: "+r.getId()+ " | Fecha: "+r.getFecha()+" | Cantidad de Personas: "+r.getCantidadPersonas()+ " | Cliente: "+r.getCliente()+ " | Tour: "+r.getTour()));

        JTabbedPane tabs = new JTabbedPane(); // Contenedor de pestañas
        tabs.addTab("Guías Turísticos", crearPanelGuiasTuristicos()); // Pestaña 1: gestión de guías turísticos. Se agrega un título y el metodo "crearPanelGuiasTuristicos()"
        tabs.addTab("Clientes", crearPanelClientes()); // Pestaña 2: gestión de clientes. Se agrega un título y el metodo "crearPanelClientes()"
        tabs.addTab("Proveedores de Transporte", crearPanelProveedoresTransporte()); // Pestaña 3: gestión de proveedores de transporte. Se agrega un título y el metodo "crearPanelProveedoresTransporte()"
        tabs.addTab("Proveedores de Alojamiento", crearPanelProveedoresAlojamiento()); // Pestaña 4: gestión de proveedores de alojamiento. Se agrega un título y el metodo "crearPanelProveedoresAlojamiento()"
        tabs.addTab("Tour", crearPanelTours()); // Pestaña 5: gestión de Tours. Se agrega un título y el metodo "crearPanelTours()"
        tabs.addTab("Reservas", crearPanelReservas()); // Pestaña 6: gestión de Reservas. Se agrega un título y el metodo "crearPanelReservas()"

        setContentPane(tabs); // Las pestañas ocupan todo el contenido de la ventana
        cargarDatos();//Se llama al metodo que carga los datos en los Arraylist al iniciar el programa
        cargarGuiasEnCombo();//Se llama al metodo que carga los Guías turísticos en el combobox al iniciar el programa
        cargarClientesEnCombo();//Se llama al metodo que carga los Clientes en el combobox al iniciar el programa
        cargarToursEnCombo();//Se llama al metodo que carga los Tours en el combobox al iniciar el programa
    }

    //Metodo para cargar los datos (limpios) del archivo en el arrayList al iniciar el programa
    public void cargarDatos() {
        /**
         * Se crea el lector del archivo registros.txt dentro de un Try-catch para manejo de errores de lectura y cerrar automáticamente el archivo
         */
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/registros.txt"))){

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
                 * Se evalúa si partes[0] es un GUIA, CLIENTE, PROVEEDOR_TRANSPORTE, PROVEEDOR_ALOJAMIENTO, RESERVA O TOUR.
                 */
                switch (partes[0]){
                    /**
                     * Validamos si el dato en la posición 0 es un "GUIA"
                     */
                    case "GUIA":
                        /**
                         * Se crea la instancia rutGuia y le pasamos partes[2] que es la posicion de rut en el archivo
                         */
                        Rut rutGuia = new Rut(partes[2]);

                        /**
                         * Se crea una instancia de GuiaTuristico y le pasamos la posición de cada atributo dentro del archivo
                         */
                        guia = new GuiaTuristico(partes[1], rutGuia, partes[3], partes[4], Integer.parseInt(partes[5]), partes[6]);

                        /**
                         * Se agrega "guia" a la lista registros
                         */
                        guiasTuristicos.add(guia);
                        /**
                         * Se llama al metodo agregarGuias
                         */
                        agregarGuias(guia);

                        break;//se corta la ejecución

                    /**
                     * Validamos si el dato en la posición 0 es un "CLIENTE"
                     */
                    case "CLIENTE":
                        /**
                         * Se crea la instancia rutCliente y le pasamos partes[2] que es la posicion de rut en el archivo
                         */
                        Rut rutCliente = new Rut(partes[2]);

                        Cliente cliente = new Cliente(partes[1], rutCliente, partes[3], partes[4], Integer.parseInt(partes[5]), partes[6], guia);

                        //Se agrega cliente a la lista clientes
                        clientes.add(cliente);
                        //Se llama al metodo agregarCliente
                        agregarClientes(cliente);

                        break;//se corta la ejecución

                    /**
                     * Validamos si el dato en la posición 0 es un "PROVEEDOR_TRANSPORTE"
                     */
                    case "PROVEEDOR_TRANSPORTE":
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
                        proveedoresTransporte.add(provTransporte);

                        break;//se corta la ejecución

                    /**
                     * Validamos si el dato en la posición 0 es un "PROVEEDOR_ALOJAMIENTO"
                     */
                    case "PROVEEDOR_ALOJAMIENTO":
                        /**
                         * Se crea una instancia de direccion y le pasamos las partes correspondientes a su posición dentro de ProveedorTransporte
                         */
                        Direccion dir = new Direccion(partes[6], partes[7]);

                        /**
                         * Se crea una instancia de Rut y le pasamos su posición correspondiente dentro de ProveedorAlojamiento
                         */
                        Rut rutProvAlojamiento = new Rut(partes[2]);

                        /**
                         * Se crea una instancia de ProveedorAlojamiento y le pasamos cada posición de su correspondiente atributo dentro del archivo
                         */
                        ProveedorAlojamiento provAlojamiento = new ProveedorAlojamiento(partes[1], rutProvAlojamiento, partes[3], partes[4], Integer.parseInt(partes[5]), dir);

                        /**
                         * Se agrega provAlojamiento a la lista registros
                         */
                        proveedoresAlojamiento.add(provAlojamiento);

                        break;//se corta la ejecución

                    case "RESERVA":
                        /**
                         * Se crea la instancia rutGuia y se la pasa su posición dentro de guiaTuristico
                         */
                        Rut rutGuiaTuristico = new Rut(partes[11]);

                        /**
                         * Se crea una instancia de GuiaTuristico y le pasamos cada posición de su correspondiente atributo dentro del archivo
                         */
                        GuiaTuristico guiaTuristico = new GuiaTuristico(partes[10], rutGuiaTuristico, partes[12], partes[13], Integer.parseInt(partes[14]), partes[15]);

                        /**
                         * Se crea la instancia rutCliente y le pasamos su posición dentro de Cliente
                         */
                        Rut rutClient = new Rut(partes[5]);

                        /**
                         * Se crea una instancia de Cliente y le pasamos cada posición de su correspondiente atributo dentro del archivo
                         */
                        Cliente c = new Cliente(partes[4], rutClient, partes[6], partes[7], Integer.parseInt(partes[8]), partes[9], guiaTuristico);

                        /**
                         * Se crea una instancia de Tour y le pasamos cada posición de su correspondiente atributo dentro del archivo
                         */
                        Tour tour = new Tour(partes[16], partes[17], partes[18], Integer.parseInt(partes[19]));

                        /**
                         * Se crea una instancia de Reserva, le pasamos cada posición de su correspondiente atributo dentro del archivo
                         */
                        Reserva reserva = new Reserva(partes[1], partes[2], Integer.parseInt(partes[3]), c, tour);

                        /**
                         * Se agrega reserva a la lista registros
                         */
                        reservas.add(reserva);

                        break;//se corta la ejecución

                    case "TOUR":
                        /**
                         * Se crea una instancia de Tour y le pasamos cada posición de su correspondiente atributo dentro del archivo
                         */
                        Tour tour1 = new Tour(partes[1], partes[2], partes[3], Double.parseDouble(partes[4]));

                        //Se agrega la instancia tour1 al arraylist "tours"
                        tours.add(tour1);

                        break;//se corta la ejecución
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Component crearPanelGuiasTuristicos(){

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5)); // Grilla de 2 columnas (etiqueta + campo)

        //Se crean las cajas de texto
        JTextField txtNombre = new JTextField();
        JTextField txtRut = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtEdad = new JTextField();
        JTextField txtIdiomas = new JTextField();

        //Se crean las etiquetas, se agregan las etiquetas y las cajas de texto al JPanel
        form.add(new JLabel("NOMBRE:"));
        form.add(txtNombre);
        form.add(new JLabel("RUT:"));
        form.add(txtRut);
        form.add(new JLabel("CORREO:"));
        form.add(txtCorreo);
        form.add(new JLabel("TELÉFONO:"));
        form.add(txtTelefono);
        form.add(new JLabel("EDAD:"));
        form.add(txtEdad);
        form.add(new JLabel("IDIOMAS:"));
        form.add(txtIdiomas);

        form.setBorder(BorderFactory.createTitledBorder("Nuevo Guía Turístico")); // Se ingresa un título al marco del JPanel
        JButton btnRegistrarGuiaTuristico = new JButton("Registrar Guía Turístico"); //se crea un botón para registrar un Vehículo
        //Se agrega la accion registrarVehiculo (se le pasan los cuatro JTextField))
        btnRegistrarGuiaTuristico.addActionListener(ActiveEvent -> registrarGuiaTuristico(txtNombre, txtRut, txtCorreo, txtTelefono, txtEdad, txtIdiomas));
        JButton btnVisualizarRegistros = new JButton("Visualizar Guías Turísticos Registrados");//Se crea un botón para ver los guía turísticos registrados
        btnVisualizarRegistros.addActionListener(ActiveEvent -> visualizarGuias());//Se agrega la acción visualizarGuias al botón
        JButton btnSalir = new JButton("Salir");//se crea un botón "Salir"
        btnSalir.addActionListener(ActiveEvent -> salir());//se agrega la acción salir() al botón
        JButton btnFiltrarPorIdiomas = new JButton("Filtrar Guías por idiomas");
        btnFiltrarPorIdiomas.addActionListener(ActiveEvent -> filtrarGuiasPorIdioma());

        //Se crea un JPanel con 4 filas y una columna y se agregan botones al JPanel "botones"
        JPanel botones = new JPanel(new GridLayout(4, 1, 5, 5));
        botones.add(btnRegistrarGuiaTuristico);
        botones.add(btnVisualizarRegistros);
        botones.add(btnFiltrarPorIdiomas);
        botones.add(btnSalir);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(botones, BorderLayout.SOUTH);

        JPanel panel = new JPanel(new BorderLayout(10, 10)); // Panel general de la pestaña
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes internos
        panel.add(top, BorderLayout.NORTH); // Formulario arriba
        panel.add(new JScrollPane(guiasTuristicosList), BorderLayout.CENTER); // Lista con scroll al centro

        return panel;
    }

    public void registrarGuiaTuristico(JTextField txtNombre, JTextField txtRut, JTextField txtCorreo, JTextField txtTelefono, JTextField txtEdad, JTextField txtIdiomas){
        String nombre;
        String rut;
        String correo;
        String telefono;
        String edad;
        String idiomas;

        //Se obtienen los datos de los campos de texto y se guardan en sus variables correspondientes
        nombre = txtNombre.getText();
        rut = txtRut.getText();
        correo = txtCorreo.getText();
        telefono = txtTelefono.getText();
        edad = txtEdad.getText();
        idiomas = txtIdiomas.getText();

        //Se valida que los campos de texto no queden vacíos antes de continuar con el registro
        if (nombre.isBlank() || rut.isBlank() || correo.isBlank() || telefono.isBlank() || edad.isBlank() || idiomas.isBlank()){
            JOptionPane.showMessageDialog(this, "Debes llenar el formulario completo para completar el registro");
            return;
        }

        //Try-catch para manejo de excepciones
        try {
            //Se convierte la edad a "int" y se guarda en una nueva variable "edadGuia"
            int edadGuia = Integer.parseInt(edad);

            //Se crean las instancias de rut y guia. Le pasamos el rutGuia al guia
            Rut rutGuia = new Rut(rut);
            GuiaTuristico guia = new GuiaTuristico(nombre, rutGuia, correo, telefono, edadGuia, idiomas);

            //Agregamos la instancia de guia a la lista guiasTuristicos
            guiasTuristicos.add(guia);

            JOptionPane.showMessageDialog(this, "Guía Turístico registrado correctamente");

            //Luego de registrar el Guía Turístico, se llama al metodo que carga los guías turísticos en el combobox
            cargarGuiasEnCombo();
            //Luego de registrar el Guía Turístico, se llama al metodo que agrega GuiaTuristico al HashMap
            agregarGuias(guia);

            //Se vacían las cajas de texto luego de registrar Guía turístico
            txtNombre.setText("");
            txtRut.setText("");
            txtCorreo.setText("");
            txtTelefono.setText("");
            txtEdad.setText("");
            txtIdiomas.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "La 'EDAD' debe ser un valor numérico positivo");
        } catch (RutInvalidoException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }catch (EdadInvalidaException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void visualizarGuias(){
        if (guiasTuristicos.isEmpty()){
            JOptionPane.showMessageDialog(this, "No existen Guías Turísticos registrados");
        }else{
            //convierte el arraylist guiasTuristicos en una arreglo y muestra el contenido en el JList
            guiasTuristicosList.setListData(guiasTuristicos.toArray(new GuiaTuristico[0]));
        }
    }

    /**
     * Metodo que agrega GuiaTuristico al hashmap (se le pasa GuiaTuristico como parámetro)
     * @param guia
     */
    public void agregarGuias(GuiaTuristico guia){

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

        //System.out.println("Después de agregar: " + buscarPorIdioma);
    }

    /**
     * Metodo que filtra guías turísticos por idioma
     */
    public void filtrarGuiasPorIdioma(){

        String idioma;

        //Se ejecuta el código al menos una vez
        do {
            //Se ingresa el idioma y su valor se guarda en la variable "idioma"
            idioma = JOptionPane.showInputDialog(this, "Ingrese el idioma buscado:");

            //Si el usuario presiona cancelar
            if (idioma == null){
                return;//se corta la ejecución
            }
            //Si no se ingresa nada
            if (idioma.isBlank()){
                JOptionPane.showMessageDialog(this, "Debes ingresar un idioma para continuar con la búsqueda");
            }

            //Mientras no se hayan ingresado los datos
        }while (idioma == null || idioma.isBlank());

        //Se obtiene el idioma ingresado, se busca dentro del hashmap y cada coincidencia se guarda en la lista "guias"
        List<GuiaTuristico> guias = buscarPorIdioma.get(idioma);

        //Se crea un modelo vacío para el Jlist de tipo "GuiaTuristico" (aquí se muestran los datos)
        DefaultListModel<GuiaTuristico> modelo = new DefaultListModel<>();

        //Si la lista no está vacía (es decir, si se encontró algún guía con el idioma buscado)
        if (guias != null) {
            //Se ejecuta una ventana con un mensaje
            JOptionPane.showMessageDialog(this, "¡Se encontraron coincidencias!");

            //Se recorre la lista "guias" y cada elemento del recorrido se guarda en la variable "g"
            for (GuiaTuristico g : guias) {
                //Cada elemento del recorrido se agrega al modelo del JList
                modelo.addElement(g);
            }
            //Si no se encontró algún guía con el idioma buscado
        } else {
            JOptionPane.showMessageDialog(this, "No existen guías para ese idioma");
            return;
        }

        //Modifica el JList guiasTuristicos y muestra solo el contenido del "modelo"
        guiasTuristicosList.setModel(modelo);

    }

    private Component crearPanelClientes() {
        //Se crea el panel del formulario
        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5)); // Grilla de 2 columnas (etiqueta + campo)

        //Se crean las cajas de texto y un combobox vacío
        JTextField txtNombre = new JTextField();
        JTextField txtRut = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtEdad = new JTextField();
        JTextField txtNacionalidad = new JTextField();
        comboGuias = new JComboBox<>();

        //Se crean las etiquetas, se agregan las etiquetas, las cajas de texto y el combobox al JPanel
        form.add(new JLabel("NOMBRE:"));
        form.add(txtNombre);
        form.add(new JLabel("RUT:"));
        form.add(txtRut);
        form.add(new JLabel("CORREO:"));
        form.add(txtCorreo);
        form.add(new JLabel("TELÉFONO:"));
        form.add(txtTelefono);
        form.add(new JLabel("EDAD:"));
        form.add(txtEdad);
        form.add(new JLabel("NACIONALIDAD:"));
        form.add(txtNacionalidad);
        form.add(new JLabel("GUÍA TURÍSTICO ASIGNADO:"));
        form.add(comboGuias);

        form.setBorder(BorderFactory.createTitledBorder("Nuevo Cliente")); // Se ingresa un título al marco del JPanel
        JButton btnRegistrarCliente = new JButton("Registrar Cliente"); //se crea un botón para registrar un Vehículo
        //Se agrega la accion registrarVehiculo (se le pasan los cuatro JTextField))
        btnRegistrarCliente.addActionListener(ActiveEvent -> registrarCliente(txtNombre, txtRut, txtCorreo, txtTelefono, txtEdad, txtNacionalidad));
        JButton btnVisualizarRegistros = new JButton("Visualizar Clientes Registrados");//Se crea un botón para ver los clients registrados
        btnVisualizarRegistros.addActionListener(ActiveEvent -> visualizarClientes());//Se agrega la acción visualizarClientes al botón
        JButton btnFiltrarPorNombre = new JButton("Filtrar clientes por Nombre");//Se crea el botón para filtrar clientes por su nombre
        btnFiltrarPorNombre.addActionListener(ActiveEvent -> filtrarClientesPorNombre());//Se agrega la acción "filtrarClientesPorNombre" al botón
        JButton btnSalir = new JButton("Salir");//se crea un botón "Salir"
        btnSalir.addActionListener(ActiveEvent -> salir());//se agrega la acción salir() al botón

        //Se crea un JPanel con 4 filas y una columna y se agregan botones al JPanel "botones"
        JPanel botones = new JPanel(new GridLayout(4, 1, 5, 5));
        botones.add(btnRegistrarCliente);
        botones.add(btnVisualizarRegistros);
        botones.add(btnFiltrarPorNombre);
        botones.add(btnSalir);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(botones, BorderLayout.SOUTH);

        JPanel panel = new JPanel(new BorderLayout(10, 10)); // Panel general de la pestaña
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes internos
        panel.add(top, BorderLayout.NORTH); // Formulario arriba
        panel.add(new JScrollPane(clientesList), BorderLayout.CENTER); // Lista con scroll al centro

        return panel;
    }

    /**
     * Metodo que carga cada elemento de la lista guiasTuristicos en el ComboBox
     */
    private void cargarGuiasEnCombo() {
        //Evita ítems duplicados
        comboGuias.removeAllItems();

        //Se recorre la lista guiasTuristicos y cada elemento de esa lista se guarda en la variable "guia"
        for (GuiaTuristico guia : guiasTuristicos) {
            //cada elemento del recorrido se agrega como ítem al combobox
            comboGuias.addItem(guia);
        }
        //Deja el combobox sin ningún elemento seleccionado
        comboGuias.setSelectedItem(null);
    }

    private void registrarCliente(JTextField txtNombre, JTextField txtRut, JTextField txtCorreo, JTextField txtTelefono, JTextField txtEdad, JTextField txtNacionalidad) {
        String nombre;
        String rut;
        String correo;
        String telefono;
        String edad;
        String nacionalidad;

        //Se obtienen los datos de las cajas de texto y se guardan en sus variables correspondiente
        nombre = txtNombre.getText();
        rut = txtRut.getText();
        correo = txtCorreo.getText();
        telefono = txtTelefono.getText();
        edad = txtEdad.getText();
        nacionalidad = txtNacionalidad.getText();

        //Se valida que los campos de texto no queden vacíos antes de continuar con el registro
        if (nombre.trim().isEmpty() || rut.trim().isEmpty() || correo.trim().isEmpty() || telefono.trim().isEmpty() || edad.trim().isEmpty() || nacionalidad.trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Debes llenar el formulario completo para completar el registro");
            return;
        }

        //Try-catch para manejo de excepciones
        try {
            int edadCliente = Integer.parseInt(edad);

            if (edadCliente < 0){
                JOptionPane.showMessageDialog(this, "La edad ingresada no es válida");
                return;
            }
            //Se crea una instancia de rut, se le pasa el rut y se guarda en la variable rutCliente
            Rut rutCliente = new Rut(rut);
            //el Guia turístico seleccionado del combobox, se guarda en la variable guiaSeleccionado de tipo GuiaTuristico
            GuiaTuristico guiaSeleccionado = (GuiaTuristico) comboGuias.getSelectedItem();

            //Se valida que se seleccione un guia turístico del combobox
            if (guiaSeleccionado == null){
                JOptionPane.showMessageDialog(this, "El cliente debe tener un Guía turístico asignado");
                return;//se corta la ejecución
            }
            //Se crea una instancia de cliente y se le pasan los datos
            Cliente cliente = new Cliente(nombre, rutCliente, correo, telefono, edadCliente, nacionalidad, guiaSeleccionado);

            //Se agrega el cliente a la lista clientes
            clientes.add(cliente);

            JOptionPane.showMessageDialog(this, "Cliente registrado correctamente");

            //Luego de registrar el cliente, se llama al metodo que carga estos clientes en el combobox de Reserva
            cargarClientesEnCombo();

            //Se vacían las cajas de texto luego de registrar cliente
            txtNombre.setText("");
            txtRut.setText("");
            txtCorreo.setText("");
            txtTelefono.setText("");
            txtEdad.setText("");
            txtNacionalidad.setText("");

        } catch (RutInvalidoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (EdadInvalidaException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "La 'EDAD' debe ser un valor numérico");
        }
    }

    private void visualizarClientes() {
        if (clientes.isEmpty()){
            JOptionPane.showMessageDialog(this, "No existen Clientes registrados");
        }else{
            //convierte el arraylist clientes en una arreglo y muestra el contenido en el JList
            clientesList.setListData(clientes.toArray(new Cliente[0]));
        }
    }

    /**
     * Metodo que agrega Cliente al hashmap (Se le pasa Cliente como parámetro)
     * @param cliente
     */
    public void agregarClientes(Cliente cliente){
        //Si el nombre obtenido de cliente, no existe en el hashmap "buscarPorNombre"
        if (!buscarPorNombre.containsKey(cliente.getNombre())){
            //Se agrega el nombre obtenido como "clave" al hashmap y una lista vacía como "valor"
            buscarPorNombre.put(cliente.getNombre(), new ArrayList<>());
        }
        //Se obtiene el nombre como clave y se añade a la lista asociada a ese nombre
        buscarPorNombre.get(cliente.getNombre()).add(cliente);
    }

    /**
     * Metodo que filtra los clientes por su nombre
     */
    public void filtrarClientesPorNombre(){
        String nombre;

        //El código se ejecuta al menos una vez
        do {
            //Se ingresa el nombre y lo ingresado se guarda en la variable "nombre"
            nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del Cliente:");

            //Si el nombre no se ingresa (si cancelamos)
            if (nombre == null){
                return;//Se corta la ejecución
            }
            //Si avanzamos sin ingresar nada
            if (nombre.isBlank()){
                //Se abre una ventana con un mensaje
                JOptionPane.showMessageDialog(this, "Debes ingresar el nombre del cliente para poder continuar");
            }

            //Mientras nombre no se ingrese
        }while (nombre == null || nombre.isBlank());

        //Se obtiene el nombre ingresado, se busca dentro del hashmap y se guarda cada coincidencia en la lista "client"
        List<Cliente> client = buscarPorNombre.get(nombre);

        //Se crea un modelo vacío para el Jlist de tipo "Cliente" (aquí se muestran los datos)
        DefaultListModel<Cliente> model = new DefaultListModel<>();

        //Si la lista client no está vacía (es decir, se encontró algún cliente con el nombre buscado)
        if (client != null){
            //Se ejecuta una ventana con un mensaje
            JOptionPane.showMessageDialog(this, "¡Se encontraron coincidencias!");

            //Se recorre cada elemento dentro de la lista
            for (Cliente c : client){
                //cada cliente que coincida con el nombre buscado se agrega al "model"
                model.addElement(c);
            }
            //Si la lista client está vacía (es decir, no se encontró ningún cliente con el nombre buscado)
        }else{
            JOptionPane.showMessageDialog(null, "No existen coincidencias para ese nombre");
            return;
        }
        //Modifica el JList clientes y muestra solo el contenido del "model"
        clientesList.setModel(model);
    }

    private Component crearPanelProveedoresTransporte() {
        //Se crea un panel principal (contenedor) del formulario
        JPanel form = new JPanel();
        //Ubica los elementos del panel uno debajo del otro
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        // Se ingresa un título al marco del JPanel
        form.setBorder(BorderFactory.createTitledBorder("Nuevo Proveedor de Transporte"));

        //Se crea el JPanel datos
        JPanel datos = new JPanel(new GridLayout(5, 2, 5, 5));

        //Se crean las cajas de texto
        JTextField txtNombre = new JTextField();
        JTextField txtRut = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtTipoTransporte = new JTextField();
        JTextField txtCalle = new JTextField();
        JTextField txtCiudad = new JTextField();

        //Se crean las etiquetas, se agregan las etiquetas y las cajas de texto al JPanel datos
        datos.add(new JLabel("NOMBRE:"));
        datos.add(txtNombre);
        datos.add(new JLabel("RUT:"));
        datos.add(txtRut);
        datos.add(new JLabel("CORREO:"));
        datos.add(txtCorreo);
        datos.add(new JLabel("TELÉFONO:"));
        datos.add(txtTelefono);
        datos.add(new JLabel("TIPO DE TRANSPORTE:"));
        datos.add(txtTipoTransporte);

        //Se crea el JPanel "panelDireccion"
        JPanel panelDireccion = new JPanel(new GridLayout(2, 2, 5, 5));
        // Se ingresa un título al marco del panelDireccion con una separación superior de 5 píxeles
        panelDireccion.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0), BorderFactory.createTitledBorder("Dirección del Proveedor de Transporte")));

        //Se crean las etiquetas, se agregan las etiquetas y las cajas de texto al JPanel "panelDireccion"
        panelDireccion.add(new JLabel("CALLE:"));
        panelDireccion.add(txtCalle);
        panelDireccion.add(new JLabel("CIUDAD:"));
        panelDireccion.add(txtCiudad);

        //Se agregan los JPanel datos y panelDireccion al JPanel principal "form"
        form.add(datos);
        form.add(panelDireccion);

        //se crea un botón para registrar un Proveedor de transporte
        JButton btnRegistrarProveedorTransporte = new JButton("Registrar Proveedor de Transporte");
        //Se agrega la accion registrarProveedorTransporte al botón (se le pasan los 7 JTextField))
        btnRegistrarProveedorTransporte.addActionListener(ActiveEvent -> registrarProveedorTransporte(txtNombre, txtRut, txtCorreo, txtTelefono, txtTipoTransporte, txtCalle, txtCiudad));
        //Se crea un botón para ver los Proveedores de Transporte registrados
        JButton btnVisualizarRegistros = new JButton("Visualizar Proveedores de Transporte");
        //Se agrega la acción visualizarProveedoresTransporte al botón
        btnVisualizarRegistros.addActionListener(ActiveEvent -> visualizarProveedoresTransporte());
        //se crea un botón "Salir"
        JButton btnSalir = new JButton("Salir");
        //se agrega la acción salir() al botón
        btnSalir.addActionListener(ActiveEvent -> salir());

        //Se crea un JPanel con 3 filas y una columna y se agregan botones al JPanel "botones"
        JPanel botones = new JPanel(new GridLayout(3, 1, 5, 5));
        botones.add(btnRegistrarProveedorTransporte);
        botones.add(btnVisualizarRegistros);
        botones.add(btnSalir);

        //Estructura general de la interfaz
        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);//Agrega JPanel form al centro
        top.add(botones, BorderLayout.SOUTH);//Agrega JPanel botones abajo

        //Se crea el Panel general (Contiene el formulario, botones, lista, etc.)
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes internos
        panel.add(top, BorderLayout.NORTH); // Formulario arriba
        panel.add(new JScrollPane(proveedoresTransporteList), BorderLayout.CENTER); // Lista con scroll al centro

        return panel;
    }

    private void registrarProveedorTransporte(JTextField txtNombre, JTextField txtRut, JTextField txtCorreo, JTextField txtTelefono, JTextField txtTipoTransporte, JTextField txtCalle, JTextField txtCiudad) {
        String nombre;
        String rut;
        String correo;
        String telefono;
        String tipoTransporte;
        String calle;
        String ciudad;

        //Se obtienen los datos de las cajas de texto y se guardan en sus correspondientes variables
        nombre = txtNombre.getText();
        rut = txtRut.getText();
        correo = txtCorreo.getText();
        telefono = txtTelefono.getText();
        tipoTransporte = txtTipoTransporte.getText();
        calle = txtCalle.getText();
        ciudad = txtCiudad.getText();

        //Se valida que los campos no queden vacíos
        if (nombre.trim().isEmpty() || rut.trim().isEmpty() || correo.trim().isEmpty() || telefono.trim().isEmpty() || tipoTransporte.trim().isEmpty() || calle.trim().isEmpty() || ciudad.trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Debes llenar el formulario completo para completar el registro");
            return;
        }

        //Try-catch para manejo de excepciones
        try {
            //Se crea una instancia de Rut, se le pasa "rut" y se guarda en una nueva variable "rutProveedorTransporte"
            Rut rutProveedorTransporte = new Rut(rut);
            //Se crea una instancia de Direccion, se le pasan calle y ciudad y se guardan en una nueva variable "direccion"
            Direccion direccion = new Direccion(calle, ciudad);
            //Se crea una instancia de ProveedorTransporte y se le pasan todos los datos
            ProveedorTransporte proveedorTransporte = new ProveedorTransporte(nombre, rutProveedorTransporte, correo, telefono, tipoTransporte, direccion);

            proveedoresTransporte.add(proveedorTransporte);

            JOptionPane.showMessageDialog(this, "Proveedor de Transporte registrado correctamente");

            //Se vacían las cajas de texto luego del registro
            txtNombre.setText("");
            txtRut.setText("");
            txtCorreo.setText("");
            txtTelefono.setText("");
            txtTipoTransporte.setText("");
            txtCalle.setText("");
            txtCiudad.setText("");

        } catch (RutInvalidoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void visualizarProveedoresTransporte() {
        if (proveedoresTransporte.isEmpty()){
            JOptionPane.showMessageDialog(this, "No existen Proveedores de Transporte registrados");
        }else{
            //convierte el arraylist proveedoresTransporte en una arreglo y muestra el contenido en el JList
            proveedoresTransporteList.setListData(proveedoresTransporte.toArray(new ProveedorTransporte[0]));
        }
    }

    private Component crearPanelProveedoresAlojamiento() {
        //Se crea un panel principal (contenedor) del formulario
        JPanel form = new JPanel();
        //Ubica los elementos del panel uno debajo del otro
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        // Se ingresa un título al marco del JPanel
        form.setBorder(BorderFactory.createTitledBorder("Nuevo Proveedor de Alojamiento"));

        //Se crea el JPanel datos
        JPanel datos = new JPanel(new GridLayout(5, 2, 5, 5));

        //Se crean las cajas de texto
        JTextField txtNombre = new JTextField();
        JTextField txtRut = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtPrecioPorNoche = new JTextField();
        JTextField txtCalle = new JTextField();
        JTextField txtCiudad = new JTextField();

        //Se crean las etiquetas, se agregan las etiquetas y las cajas de texto al JPanel datos
        datos.add(new JLabel("NOMBRE:"));
        datos.add(txtNombre);
        datos.add(new JLabel("RUT:"));
        datos.add(txtRut);
        datos.add(new JLabel("CORREO:"));
        datos.add(txtCorreo);
        datos.add(new JLabel("TELÉFONO:"));
        datos.add(txtTelefono);
        datos.add(new JLabel("PRECIO POR NOCHE:"));
        datos.add(txtPrecioPorNoche);

        //Se crea el JPanel "panelDireccion"
        JPanel panelDireccion = new JPanel(new GridLayout(2, 2, 5, 5));
        // Se ingresa un título al marco del panelDireccion con una separación superior de 5 píxeles
        panelDireccion.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0), BorderFactory.createTitledBorder("Dirección del Proveedor de Alojamiento")));

        //Se crean las etiquetas y se agregan las etiquetas y las cajas de texto al JPanel "panelDireccion"
        panelDireccion.add(new JLabel("CALLE:"));
        panelDireccion.add(txtCalle);
        panelDireccion.add(new JLabel("CIUDAD:"));
        panelDireccion.add(txtCiudad);

        //Se agregan los JPanel datos y panelDireccion al JPanel principal "form"
        form.add(datos);
        form.add(panelDireccion);

        //se crea un botón para registrar un Proveedor de Alojamiento
        JButton btnRegistrarProveedorAlojamiento = new JButton("Registrar Proveedor de Alojamiento");
        //Se agrega la accion registrarProveedorAlojamiento al botón (se le pasan los 7 JTextField))
        btnRegistrarProveedorAlojamiento.addActionListener(ActiveEvent -> registrarProveedorAlojamiento(txtNombre, txtRut, txtCorreo, txtTelefono, txtPrecioPorNoche, txtCalle, txtCiudad));
        //Se crea un botón para ver los Proveedores de Alojamiento registrados
        JButton btnVisualizarRegistros = new JButton("Visualizar Proveedores de Alojamiento");
        //Se agrega la acción visualizarProveedoresAlojamiento al botón
        btnVisualizarRegistros.addActionListener(ActiveEvent -> visualizarProveedoresAlojamiento());
        //se crea un botón "Salir"
        JButton btnSalir = new JButton("Salir");
        //se agrega la acción salir() al botón
        btnSalir.addActionListener(ActiveEvent -> salir());

        //Se crea un JPanel con 3 filas y una columna y se agregan botones al JPanel "botones"
        JPanel botones = new JPanel(new GridLayout(3, 1, 5, 5));
        botones.add(btnRegistrarProveedorAlojamiento);
        botones.add(btnVisualizarRegistros);
        botones.add(btnSalir);

        //Estructura general de la interfaz
        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);//Agrega JPanel form al centro
        top.add(botones, BorderLayout.SOUTH);//Agrega JPanel botones abajo

        //Se crea el Panel general (Contiene el formulario, botones, lista, etc.)
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        // Márgenes internos
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Formulario arriba
        panel.add(top, BorderLayout.NORTH);
        // Lista con scroll al centro
        panel.add(new JScrollPane(proveedoresAlojamientoList), BorderLayout.CENTER);

        return panel;
    }

    private void registrarProveedorAlojamiento(JTextField txtNombre, JTextField txtRut, JTextField txtCorreo, JTextField txtTelefono, JTextField txtPrecioPorNoche, JTextField txtCalle, JTextField txtCiudad) {
        //Se crean las variables como String
        String nombre;
        String rut;
        String correo;
        String telefono;
        String precioPorNoche;
        String calle;
        String ciudad;

        //Se obtienen los datos de los campos de texto y se guardan en sus correspondientes variables
        nombre = txtNombre.getText();
        rut = txtRut.getText();
        correo = txtCorreo.getText();
        telefono = txtTelefono.getText();
        precioPorNoche = txtPrecioPorNoche.getText();
        calle = txtCalle.getText();
        ciudad = txtCiudad.getText();

        //Se valida que no existan campos vacíos antes de completar el registro
        if (nombre.isBlank() || rut.isBlank() || correo.isBlank() || telefono.isBlank() || precioPorNoche.isBlank() || calle.isBlank() || ciudad.isBlank()){
            JOptionPane.showMessageDialog(this, "Debes llenar el formulario completo para completar el registro");
            return;//Se corta la ejecución
        }

        //Try-catch para manejo de excepciones
        try {
            //Se convierte la variable precioPorNoche a double y se guarda en la variable "precioNoche"
            double precioNoche = Double.parseDouble(precioPorNoche);

            //Se crean las instancias de Rut y Dirección, se guardan en variables y se agregan como atributo al objeto ProveedorAlojamiento
            Rut rutProveedorAlojamiento = new Rut(rut);
            Direccion direccion = new Direccion(calle, ciudad);
            ProveedorAlojamiento proveedorAlojamiento = new ProveedorAlojamiento(nombre, rutProveedorAlojamiento, correo, telefono, precioNoche, direccion);

            //Se agrega proveedorAlojamiento al ArrayList "proveedoresAlojamiento"
            proveedoresAlojamiento.add(proveedorAlojamiento);

            JOptionPane.showMessageDialog(this, "Proveedor de Alojamiento registrado correctamente");

            //Se vacían las cajas de texto luego de completar el registro
            txtNombre.setText("");
            txtNombre.setText("");
            txtNombre.setText("");
            txtNombre.setText("");
            txtNombre.setText("");
            txtNombre.setText("");
            txtNombre.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El 'PRECIO POR NOCHE' debe ser una valor numérico positivo");
        } catch (RutInvalidoException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (PrecioInvalidoException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void visualizarProveedoresAlojamiento() {
        if (proveedoresAlojamiento.isEmpty()){
            JOptionPane.showMessageDialog(this, "No existen Proveedores de Alojamiento registrados");
        }else{
            //convierte el arraylist proveedoresTransporte en una arreglo y muestra el contenido en el JList
            proveedoresAlojamientoList.setListData(proveedoresAlojamiento.toArray(new ProveedorAlojamiento[0]));
        }
    }

    private Component crearPanelTours() {
        //Se crea el panel del formulario
        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5)); // Grilla de 2 columnas (etiqueta + campo)

        //Se crean las cajas de texto y un combobox
        JTextField txtNombre = new JTextField();
        JTextField txtTipoTour = new JTextField();
        JTextField txtDestino = new JTextField();
        JTextField txtPrecio = new JTextField();

        //Se crean las etiquetas, se agregan las etiquetas, las cajas de texto y el combobox al JPanel
        form.add(new JLabel("NOMBRE:"));
        form.add(txtNombre);
        form.add(new JLabel("TIPO DE TOUR:"));
        form.add(txtTipoTour);
        form.add(new JLabel("DESTINO:"));
        form.add(txtDestino);
        form.add(new JLabel("PRECIO:"));
        form.add(txtPrecio);

        form.setBorder(BorderFactory.createTitledBorder("Nuevo Tour")); // Se ingresa un título al marco del JPanel
        JButton btnRegistrarTour = new JButton("Registrar Tour"); //se crea un botón para registrar un Vehículo
        //Se agrega la accion registrarTour (se le pasan los 3 JTextField))
        btnRegistrarTour.addActionListener(ActiveEvent -> registrarTour(txtNombre, txtTipoTour, txtDestino, txtPrecio));
        JButton btnVisualizarRegistros = new JButton("Visualizar Tours Registrados");//Se crea un botón para ver los tours registrados
        btnVisualizarRegistros.addActionListener(ActiveEvent -> visualizarTours());//Se agrega la acción visualizarTours al botón
        JButton btnSalir = new JButton("Salir");//se crea un botón "Salir"
        btnSalir.addActionListener(ActiveEvent -> salir());//se agrega la acción salir() al botón

        //Se crea un JPanel con 3 filas y una columna y se agregan botones al JPanel "botones"
        JPanel botones = new JPanel(new GridLayout(3, 1, 5, 5));
        botones.add(btnRegistrarTour);
        botones.add(btnVisualizarRegistros);
        botones.add(btnSalir);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(botones, BorderLayout.SOUTH);

        JPanel panel = new JPanel(new BorderLayout(10, 10)); // Panel general de la pestaña
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes internos
        panel.add(top, BorderLayout.NORTH); // Formulario arriba
        panel.add(new JScrollPane(toursList), BorderLayout.CENTER); // Lista con scroll al centro

        return panel;
    }

    private void registrarTour(JTextField txtNombre, JTextField txtTipoTour, JTextField txtDestino, JTextField txtPrecio) {
        String nombre;
        String tipoTour;
        String destino;
        String precio;

        nombre = txtNombre.getText();
        tipoTour = txtTipoTour.getText();
        destino = txtDestino.getText();
        precio = txtPrecio.getText();

        //Se valida que los campos de texto no queden vacíos antes de continuar con el registro
        if (nombre.isBlank() || tipoTour.isBlank() || destino.isBlank() || precio.isBlank()){
            JOptionPane.showMessageDialog(this, "Debes llenar el formulario completo para completar el registro");
            return;
        }

        //Try-catch para manejo de excepciones
        try {
            //Se convierte precio a "double" y se guarda en la nueva variable "precioTour"
            double precioTour = Double.parseDouble(precio);

            //Se crea una instancia de Tour y se le pasa cada dato
            Tour tour = new Tour(nombre, tipoTour, destino, precioTour);

            //Se agrega el objeto "tour" al arraylist "tours"
            tours.add(tour);

            JOptionPane.showMessageDialog(this, "Tour registrado correctamente");

            //Luego de registrar un tour, se llama al metodo que carga los tours en el combobox de Reserva
            cargarToursEnCombo();

            //Se vacían las cajas de texto luego del registro
            txtNombre.setText("");
            txtTipoTour.setText("");
            txtDestino.setText("");
            txtPrecio.setText("");

        } catch (PrecioInvalidoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El 'PRECIO' no es válido");
        }
    }

    private void visualizarTours() {
        if (tours.isEmpty()){
            JOptionPane.showMessageDialog(null, "No existen Tours registrados");
        }else{
            //convierte el arraylist tours en una arreglo y muestra el contenido en el JList
            toursList.setListData(tours.toArray(new Tour[0]));
        }
    }

    private Component crearPanelReservas(){

        //Se crea el panel del formulario
        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5)); // Grilla de 2 columnas (etiqueta + campo)

        //Se crean las cajas de texto y dos combobox vacíos
        JTextField txtId = new JTextField();
        JTextField txtFecha = new JTextField();
        JTextField txtCantidadPersonas = new JTextField();
        comboClientes = new JComboBox<>();
        comboTours = new JComboBox<>();

        //Se crean las etiquetas, se agregan las etiquetas, las cajas de texto y los combobox al JPanel
        form.add(new JLabel("ID:"));
        form.add(txtId);
        form.add(new JLabel("FECHA:"));
        form.add(txtFecha);
        form.add(new JLabel("CANTIDAD DE PERSONAS:"));
        form.add(txtCantidadPersonas);
        form.add(new JLabel("CLIENTE:"));
        form.add(comboClientes);
        form.add(new JLabel("TOUR:"));
        form.add(comboTours);

        form.setBorder(BorderFactory.createTitledBorder("Nueva Reserva")); // Se ingresa un título al marco del JPanel
        JButton btnRegistrarReserva = new JButton("Registrar Reserva"); //se crea un botón para registrar una Reserva
        //Se agrega la accion registrarReserva al botón (se le pasan los 3 JTextField))
        btnRegistrarReserva.addActionListener(ActiveEvent -> registrarReserva(txtId, txtFecha, txtCantidadPersonas));
        JButton btnVisualizarRegistros = new JButton("Visualizar Reservas Registradas");//Se crea un botón para ver las Reservas registradas
        btnVisualizarRegistros.addActionListener(ActiveEvent -> visualizarReservas());//Se agrega la acción visualizarReservas al botón
        JButton btnSalir = new JButton("Salir");//se crea un botón "Salir"
        btnSalir.addActionListener(ActiveEvent -> salir());//se agrega la acción salir() al botón

        //Se crea un JPanel con 3 filas y una columna y se agregan botones al JPanel "botones"
        JPanel botones = new JPanel(new GridLayout(3, 1, 5, 5));
        botones.add(btnRegistrarReserva);
        botones.add(btnVisualizarRegistros);
        botones.add(btnSalir);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(botones, BorderLayout.SOUTH);

        JPanel panel = new JPanel(new BorderLayout(10, 10)); // Panel general de la pestaña
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes internos
        panel.add(top, BorderLayout.NORTH); // Formulario arriba
        panel.add(new JScrollPane(reservasList), BorderLayout.CENTER); // Lista con scroll al centro

        return panel;
    }

    private void registrarReserva(JTextField txtId, JTextField txtFecha, JTextField txtCantidadPersonas) {
        String id;
        String fecha;
        String cantidadPersonas;

        //Se obtienen los datos de los campos de texto y se guardan en sus variables correspondienes
        id = txtId.getText();
        fecha = txtFecha.getText();
        cantidadPersonas = txtCantidadPersonas.getText();

        //Se valida que se hayan ingresado los datos antes de continuar con el registro
        if (id.isBlank() || fecha.isBlank() || cantidadPersonas.isBlank()){
            JOptionPane.showMessageDialog(this, "Debes llenar el formulario completo para completar el registro");
            return;
        }

        //Try-catch para manejo de excepciones
        try {
            //Se convierte cantidadPersonas a tipo "int" y se guarda en la variable "cantPersonas"
            int cantPersonas = Integer.parseInt(cantidadPersonas);

            //el Cliente seleccionado del combobox, se guarda en la variable clienteSeleccionado de tipo Cliente
            Cliente clienteSeleccionado = (Cliente) comboClientes.getSelectedItem();
            //El Tour seleccionado del combobox se guarda en la variable "tourSeleccionado"
            Tour tourSeleccionado = (Tour) comboTours.getSelectedItem();

            //Se valida que se haya seleccionado un cliente
            if (clienteSeleccionado == null){
                JOptionPane.showMessageDialog(this, "Debes seleccionar un Cliente para la reserva");
                return;
            }
            //Se valida que se haya seleccionado un tour
            if (tourSeleccionado == null){
                JOptionPane.showMessageDialog(this, "Debes seleccionar un Tour");
                return;
            }
            //Se crea una instancia de Reserva, le pasamos sus datos y el cliente y tour seleccioandos desde un combobox
            Reserva reserva = new Reserva(id, fecha, cantPersonas, clienteSeleccionado, tourSeleccionado);

            //Se agrega la instancia reserva al arraylist "reservas"
            reservas.add(reserva);

            JOptionPane.showMessageDialog(this, "Reserva registrada correctamente");

            //Se vacían los campos de texto luego del registro
            txtId.setText("");
            txtFecha.setText("");
            txtCantidadPersonas.setText("");

        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "La 'CANTIDAD DE PERSONAS' debe ser un valor numérico positivo");
        } catch (CantidadPersonasInvalidaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (PrecioInvalidoException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo que carga cada elemento de la lista clientes en el ComboBox
     */
    private void cargarClientesEnCombo() {
        //Evita ítems duplicados
        comboClientes.removeAllItems();

        //Se recorre la lista clientes y cada elemento de esa lista se guarda en la variable "cliente"
        for (Cliente cliente : clientes) {
            //cada elemento del recorrido se agrega como ítem al combobox
            comboClientes.addItem(cliente);
        }
        //Deja el combobox sin ningún elemento seleccionado
        comboClientes.setSelectedItem(null);
    }

    /**
     * Metodo que carga cada elemento de la lista tours en el ComboBox
     */
    private void cargarToursEnCombo() {
        //Evita ítems duplicados
        comboTours.removeAllItems();

        //Se recorre la lista tours y cada elemento de esa lista se guarda en la variable "tour"
        for (Tour tour : tours) {
            //cada elemento del recorrido se agrega como ítem al combobox
            comboTours.addItem(tour);
        }
        //Deja el combobox sin ningún elemento seleccionado
        comboTours.setSelectedItem(null);
    }

    private void visualizarReservas() {
        if (reservas.isEmpty()){
            JOptionPane.showMessageDialog(this, "No existen reservas registradas");
        }else{
            //convierte el arraylist reservas en una arreglo y muestra el contenido en el JList
            reservasList.setListData(reservas.toArray(new Reserva[0]));
        }
    }

    public void salir(){
        System.exit(0);
    }

    // Punto de entrada alternativo: permite ejecutar esta clase directamente además de com.llanquihuetour.Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new gui().setVisible(true));
    }

    // Renderer genérico: muestra en la lista/combo el "id" del objeto (según la función recibida)
    // en vez del toString() por defecto, sin necesitar una clase envoltorio ni tocar los modelos.
    private static class IdRenderer<T> extends DefaultListCellRenderer {
        private final Function<T, String> obtenerId; // Cómo sacar el "id" del objeto T

        IdRenderer(Function<T, String> obtenerId) {
            this.obtenerId = obtenerId;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            // Primero deja que el renderer por defecto arme el componente base
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value != null) {
                setText(obtenerId.apply((T) value)); // Reemplaza el texto por el "id" del objeto
            }
            return this;
        }
    }
}



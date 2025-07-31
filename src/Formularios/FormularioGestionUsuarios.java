package Formularios;

import Modelos.Usuario;
import Servicios.UsuarioService;
import Utilidades.Estilos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Clase que permite al administrador gestionar usuarios del sistema PoliCine.
 * Ofrece funcionalidades como agregar, eliminar, actualizar y listar usuarios.
 * La interfaz esta dividida en pestañas con un JTabbedPane.
 */
public class FormularioGestionUsuarios extends JFrame {
    private JPanel panelPrincipal;
    private JTabbedPane tabbedPane;

    // --- Pestaña 1: Agregar Usuario ---
    private JTextField campoUsuarioAgregar;
    private JPasswordField campoClaveAgregar;
    private JComboBox<String> comboRolAgregar;
    private JButton botonAgregarUsuario;

    // --- Pestaña 2: Eliminar Usuario ---
    private JTextField campoUsuarioEliminar;
    private JButton botonEliminarUsuario;

    // --- Pestaña 3: Mostrar Usuarios ---
    private JTable tablaUsuarios;
    private JButton botonRefrescar;

    // --- Pestaña 4: Actualizar Usuario ---
    private JTextField campoUsuarioActualizar;
    private JPasswordField campoNuevaClave;
    private JComboBox<String> comboNuevoRol;
    private JButton botonActualizarUsuario;
    private JButton regresarButton;
    private JButton regresarButton1;
    private JButton regresarButton2;
    private Usuario usuario;

    private UsuarioService usuarioService = new UsuarioService();

    /**
     * Constructor de la interfaz de gestión de usuarios
     * @param usuario Usuario autenticado con permisos de administrador
     */
    public FormularioGestionUsuarios(Usuario usuario) {
        this.usuario = usuario;

        setLayout(null);
        setTitle("Gestionar Usuarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        setSize(700, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        comboRolAgregar.addItem("administrador");
        comboRolAgregar.addItem("cajero");

        comboNuevoRol.addItem("administrador");
        comboNuevoRol.addItem("cajero");

        // Acciones de botones
        botonAgregarUsuario.addActionListener(e -> agregarUsuario());
        botonEliminarUsuario.addActionListener(e -> eliminarUsuario());
        botonActualizarUsuario.addActionListener(e -> actualizarUsuario());
        botonRefrescar.addActionListener(e -> cargarUsuarios());

        regresarButton.addActionListener(e -> volverAlMenu());
        regresarButton1.addActionListener(e -> volverAlMenu());
        regresarButton2.addActionListener(e -> volverAlMenu());

        // Estilos
        Estilos.estiloPanel(panelPrincipal);
        Estilos.aplicarEstiloVentana(this);
        Estilos.estilizarLabels(panelPrincipal, Color.WHITE, new Font("Arial", Font.BOLD, 14));
        Estilos.estiloBoton(botonEliminarUsuario);
        Estilos.estilizarTabbedPane(tabbedPane);
        Estilos.estilizarTabbedPaneCompleto(tabbedPane);

        cargarUsuarios();
    }

    /**
     * Carga los usuarios registrados y los muestra en la tabla
     */
    private void cargarUsuarios() {
        List<Usuario> lista = usuarioService.obtenerTodos();
        String[] columnas = {"Usuario", "Rol"};
        String[][] datos = new String[lista.size()][2];

        for (int i = 0; i < lista.size(); i++) {
            datos[i][0] = lista.get(i).getUsuario();
            datos[i][1] = lista.get(i).getRol();
        }

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(datos, columnas));
    }

    /**
     * Lógica para agregar un nuevo usuario
     */
    private void agregarUsuario() {
        String nombre = campoUsuarioAgregar.getText();
        String clave = new String(campoClaveAgregar.getPassword());
        String rol = (String) comboRolAgregar.getSelectedItem();

        if (nombre.isEmpty() || clave.isEmpty()) {
            Estilos.personalizarJOptionPane();
            JOptionPane.showMessageDialog(panelPrincipal, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario nuevo = new Usuario(nombre, clave, rol);
        if (usuarioService.agregarUsuario(nuevo)) {
            Estilos.personalizarJOptionPane();
            JOptionPane.showMessageDialog(panelPrincipal, "Usuario agregado correctamente.");
            campoUsuarioAgregar.setText("");
            campoClaveAgregar.setText("");
        } else {
            JOptionPane.showMessageDialog(panelPrincipal, "El usuario ya existe o hubo un error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Lógica para eliminar un usuario existente
     */
    private void eliminarUsuario() {
        String nombre = campoUsuarioEliminar.getText();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(panelPrincipal, "Ingresa el nombre de usuario a eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(panelPrincipal, "¿Estas seguro de eliminar al usuario?");
        if (confirm == JOptionPane.YES_OPTION) {
            if (usuarioService.eliminarUsuario(nombre)) {
                JOptionPane.showMessageDialog(panelPrincipal, "Usuario eliminado.");
                campoUsuarioEliminar.setText("");
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(panelPrincipal, "No se pudo eliminar al usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Lógica para actualizar los datos de un usuario existente
     */
    private void actualizarUsuario() {
        String nombre = campoUsuarioActualizar.getText();
        String nuevaClave = new String(campoNuevaClave.getPassword());
        String nuevoRol = (String) comboNuevoRol.getSelectedItem();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(panelPrincipal, "Ingresa el nombre del usuario.", "Pregunta", JOptionPane.QUESTION_MESSAGE);
            return;
        }

        boolean actualizado = usuarioService.actualizarUsuario(nombre, nuevaClave, nuevoRol);
        if (actualizado) {
            JOptionPane.showMessageDialog(panelPrincipal, "Usuario actualizado correctamente.");
            campoUsuarioActualizar.setText("");
            campoNuevaClave.setText("");
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(panelPrincipal, "Error al actualizar usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Regresa al formulario principal del administrador
     */
    private void volverAlMenu() {
        dispose();
        new FormularioCRUD(usuario).setVisible(true);
    }

    /**
     * Devuelve el panel principal
     * @return panel principal
     */
    public JPanel getPanel() {
        return panelPrincipal;
    }
}

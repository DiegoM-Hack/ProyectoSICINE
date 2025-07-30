package Formularios;

import Modelos.Usuario;
import Servicios.UsuarioService;
import Utilidades.Estilos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FormularioGestionUsuarios extends JFrame {
    private JPanel panelPrincipal;
    private JTabbedPane tabbedPane;

    // --- Pestaña 1: Agregar Modelos.Usuario ---
    private JTextField campoUsuarioAgregar;
    private JPasswordField campoClaveAgregar;
    private JComboBox<String> comboRolAgregar;
    private JButton botonAgregarUsuario;

    // --- Pestaña 2: Eliminar Modelos.Usuario ---
    private JTextField campoUsuarioEliminar;
    private JButton botonEliminarUsuario;

    // --- Pestaña 3: Mostrar Usuarios ---
    private JTable tablaUsuarios;
    private JButton botonRefrescar;

    // --- Pestaña 4: Actualizar Modelos.Usuario ---
    private JTextField campoUsuarioActualizar;
    private JPasswordField campoNuevaClave;
    private JComboBox<String> comboNuevoRol;
    private JButton botonActualizarUsuario;
    private JButton regresarButton;
    private JButton regresarButton1;
    private JButton regresarButton2;
    private Usuario usuario;

    private UsuarioService usuarioService = new UsuarioService();

    public FormularioGestionUsuarios(Usuario usuario) {
        this.usuario = usuario;

        setLayout(null);
        setTitle("Gestionar Usuarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        setSize(700, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        // Rellenar combo de roles
        comboRolAgregar.addItem("administrador");
        comboRolAgregar.addItem("cajero");



        // Botón Agregar Modelos.Usuario
        botonAgregarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuarioAgregar.getText();
                String clave = new String(campoClaveAgregar.getPassword());
                String rol = (String) comboRolAgregar.getSelectedItem();

                if (usuario.isEmpty() || clave.isEmpty()) {
                    JOptionPane.showMessageDialog(panelPrincipal, "Por favor, completa todos los campos.","Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Usuario nuevoUsuario = new Usuario(usuario, clave, rol);
                if (usuarioService.agregarUsuario(nuevoUsuario)) {
                    Estilos.personalizarJOptionPane();
                    JOptionPane.showMessageDialog(panelPrincipal, "Usuario agregado correctamente.");
                    campoUsuarioAgregar.setText("");
                    campoClaveAgregar.setText("");
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal, "El usuario ya existe o hubo un error.","Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Botón Eliminar Modelos.Usuario
        botonEliminarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuarioEliminar.getText();
                if (usuario.isEmpty()) {
                    Estilos.personalizarJOptionPane();
                    JOptionPane.showMessageDialog(panelPrincipal, "Ingresa el nombre de usuario a eliminar.");
                    return;
                }

                Estilos.personalizarJOptionPane();
                int confirm = JOptionPane.showConfirmDialog(panelPrincipal, "¿Estás seguro de eliminar al usuario?");
                if (confirm == JOptionPane.YES_OPTION) {
                    if (usuarioService.eliminarUsuario(usuario)) {
                        JOptionPane.showMessageDialog(panelPrincipal, "Usuario eliminado.");
                        campoUsuarioEliminar.setText("");
                        cargarUsuarios(); // Refrescar tabla

                    } else {
                        JOptionPane.showMessageDialog(panelPrincipal, "No se pudo eliminar al usuario.","Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Botón Refrescar Tabla
        botonRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarUsuarios();
            }
        });

        // Cargar usuarios al iniciar
        cargarUsuarios();

        comboNuevoRol.addItem("administrador");
        comboNuevoRol.addItem("cajero");

        botonActualizarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuarioActualizar.getText();
                String nuevaClave = new String(campoNuevaClave.getPassword());
                String nuevoRol = (String) comboNuevoRol.getSelectedItem();

                if (usuario.isEmpty()) {
                    Estilos.personalizarJOptionPane();
                    JOptionPane.showMessageDialog(panelPrincipal, "Ingresa el nombre del usuario.","Pregunta", JOptionPane.QUESTION_MESSAGE);
                    return;
                }

                boolean actualizado = usuarioService.actualizarUsuario(usuario, nuevaClave, nuevoRol);
                if (actualizado) {
                    Estilos.personalizarJOptionPane();
                    JOptionPane.showMessageDialog(panelPrincipal, "Usuario actualizado correctamente.");
                    campoUsuarioActualizar.setText("");
                    campoNuevaClave.setText("");
                    cargarUsuarios(); // Refrescar tabla

                } else {
                    JOptionPane.showMessageDialog(panelPrincipal, "Error al actualizar usuario.","Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        regresarButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new FormularioCRUD(usuario).setVisible(true);
            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new FormularioCRUD(usuario).setVisible(true);
            }
        });
        regresarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new FormularioCRUD(usuario).setVisible(true);
            }
        });

        // Aplicar estilos
        Estilos.estiloPanel(panelPrincipal);
        Estilos.aplicarEstiloVentana(this);
        Estilos.estilizarLabels(panelPrincipal, Color.WHITE, new Font("Arial", Font.BOLD, 14));
        Estilos.estiloBoton(botonEliminarUsuario);
        Estilos.estilizarTabbedPane(tabbedPane);
        Estilos.estilizarTabbedPaneCompleto(tabbedPane);
    }

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

    public JPanel getPanel() {
        return panelPrincipal;
    }


}

package Formularios;

import javax.swing.*;

public class Cartelera extends JPanel {
    private JPanel panelPrincipal;
/*
    public Cartelera() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        cargarFuncionesDelDia();
    }


    private void cargarFuncionesDelDia() {
        Servicios.FuncionService funcionService = new Servicios.FuncionService();
        Servicios.PeliculaService peliculaService = new Servicios.PeliculaService();

        List<Modelos.Funcion> funcionesHoy = funcionService.obtenerFuncionesPorFecha(LocalDate.now());

        for (Modelos.Funcion funcion : funcionesHoy) {
            Modelos.Pelicula peli = peliculaService.buscarPeliculaPorTitulo(funcion.getTituloPelicula());

            JPanel panelFuncion = new JPanel(new BorderLayout());
            panelFuncion.setBorder(BorderFactory.createTitledBorder(peli.getTitulo()));

            // Imagen
            JLabel imagenLabel = new JLabel();
            try {
                ImageIcon icon = new ImageIcon(peli.getRutaImagen());
                Image imagen = icon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
                imagenLabel.setIcon(new ImageIcon(imagen));
            } catch (Exception e) {
                imagenLabel.setText("Sin imagen");
            }

            // Detalles
            JTextArea detalles = new JTextArea(
                    "Género: " + peli.getGenero() + "\n" +
                            "Duración: " + peli.getDuracion() + "\n" +
                            "Clasificación: " + peli.getClasificacion() + "\n" +
                            "Hora: " + funcion.getFechaHora().toLocalTime()
            );
            detalles.setEditable(false);

            panelFuncion.add(imagenLabel, BorderLayout.WEST);
            panelFuncion.add(detalles, BorderLayout.CENTER);

            panelPrincipal.add(panelFuncion);
        }

        revalidate();
        repaint();
    }

     */
}

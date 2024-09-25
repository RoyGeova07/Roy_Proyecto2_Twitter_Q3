/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_twit_roy_moises;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author royum
 */
public class VisualInteracciones {

    private JFrame frame;
    private JPanel panelInteracciones;
    private String UsuarioActual;

    public VisualInteracciones(String UsuarioActual) {
        this.UsuarioActual = UsuarioActual;
        InicioDeInteracciones();
        mostrarInteracciones();
    }

    private void InicioDeInteracciones() {
        frame = new JFrame("Interacciones");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        panelInteracciones = new JPanel();
        panelInteracciones.setLayout(new BoxLayout(panelInteracciones, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(panelInteracciones);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton regresarButton = new JButton("Regresar");
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Cerrar la ventana actual
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(regresarButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void mostrarInteracciones() {
        boolean hayMenciones = false;
        Twit[] todosLosTwits = GestorDeTwits.obtenerTodoLosTwits();

        for (int mostrar = 0; mostrar < todosLosTwits.length; mostrar++) {
            Twit twit = todosLosTwits[mostrar];

            if (!UsuarioManager.UsuarioActivo(twit.getUsername())) {
                continue; // Omitir twits de usuarios desactivados
            }

            if (contieneMencion(twit.getContenido(), UsuarioActual)) {
                hayMenciones = true;

                JTextPane twitPane = new JTextPane();
                twitPane.setEditable(false);

                String contenidoFiltrado = eliminarMencionesDeUsuariosDesactivados(twit.getContenido());
                twitPane.setText(twit.getUsername() + " te menciono: \"" + contenidoFiltrado + "\nPublicado el " + twit.getFechapublicacion() + " .");

                StyledDocument doc = twitPane.getStyledDocument();
                Style hastagEstilo = twitPane.addStyle("hastagEstilo", null);
                Style mencionEstilo = twitPane.addStyle("mencionEstilo", null);

                StyleConstants.setForeground(hastagEstilo, new Color(30, 144, 255));
                StyleConstants.setBold(mencionEstilo, true);
                StyleConstants.setForeground(mencionEstilo, Color.BLACK);

                String text = twitPane.getText();

                resaltarHashtags(text, doc, hastagEstilo);

                resaltarMenciones(text, doc, mencionEstilo);

                twitPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                panelInteracciones.add(twitPane);
            }
        }

        if (!hayMenciones) {
            JLabel mensaje = new JLabel("Lo sentimos, aun no has sido mencionado.");
            mensaje.setHorizontalAlignment(SwingConstants.CENTER);
            panelInteracciones.add(mensaje);
        }

        panelInteracciones.revalidate();
        panelInteracciones.repaint();
    }

    private String eliminarMencionesDeUsuariosDesactivados(String contenido) {
        StringBuilder resultado = new StringBuilder();
        String[] palabras = contenido.split(" ");

        for (String palabra : palabras) {
            if (palabra.startsWith("@")) {
                String usernameMencionado = palabra.substring(1);
                if (!UsuarioManager.UsuarioActivo(usernameMencionado)) {
                    continue;
                }
            }
            resultado.append(palabra).append(" ");
        }
        return resultado.toString().trim();
    }

    private void resaltarHashtags(String text, StyledDocument doc, Style hashtagEstilo) {
        int index = 0;
        while (index < text.length()) {
            int hashtagInicio = text.indexOf("#", index);
            if (hashtagInicio == -1) {
                break;
            }
            int hashtagFin = text.indexOf(" ", hashtagInicio);
            if (hashtagFin == -1) {
                hashtagFin = text.length();
            }
            doc.setCharacterAttributes(hashtagInicio, hashtagFin - hashtagInicio, hashtagEstilo, false);
            index = hashtagFin;
        }
    }

    private void resaltarMenciones(String text, StyledDocument doc, Style mencionEstilo) {
        int index = 0;
        while (index < text.length()) {
            int mencionInicio = text.indexOf("@", index);
            if (mencionInicio == -1) {
                break;
            }
            int mencionFin = text.indexOf(" ", mencionInicio);
            if (mencionFin == -1) {
                mencionFin = text.length();
            }
            String username = text.substring(mencionInicio + 1, mencionFin);
            if (UsuarioManager.UsuarioActivo(username)) {
                doc.setCharacterAttributes(mencionInicio, mencionFin - mencionInicio, mencionEstilo, false);
            }
            index = mencionFin;
        }
    }

    private boolean contieneMencion(String contenido, String usuario) {
        String mencion = "@" + usuario;
        return contenido.contains(mencion);
    }

}

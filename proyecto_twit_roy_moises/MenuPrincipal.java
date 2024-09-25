/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_twit_roy_moises;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.text.*;

/**
 *
 * @author royum
 */
public class MenuPrincipal {

    VisualInteracciones visual;
    UsuarioManager user;
    private static MenuPrincipal menu;
    private JFrame frame;
    private JTextPane tweetArea;
    private JLabel letrasContadasLabel;
    private JPanel espacioPanel;
    private Twits twits;
    private String UsuarioActual;
    private String[] hashtagExistente = new String[100]; // solo se podran guardar 100 hashtag
    private int numHashatgExistente = 0;

    public MenuPrincipal(String UsuarioActual) {
        this.UsuarioActual = UsuarioActual;
        this.twits = UsuarioManager.obtenerTwitsUsuario(UsuarioActual);
        if (this.twits == null) {
            this.twits = new Twits();
        }

        inicioDelUsuario();
        actualizarTimeline();
    }

    public static MenuPrincipal getMenu(String UsuarioActual) {
        if (menu == null) {
            menu = new MenuPrincipal(UsuarioActual);
        }
        return menu;
    }

    public void mostrarMenu() {
        if (frame != null) {
            frame.setVisible(true);
        }
    }

    private void inicioDelUsuario() {
        frame = new JFrame("Menu Principal");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);

        // Panel de botones de navegacion
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(1, 6, 10, 10)); // Seis botones en una fila
        navPanel.setBackground(Color.lightGray);

        JButton InteraccionesBoton = new JButton("Interacciones");
        JButton Editar = new JButton("Editar Perfil");
        JButton BuscarHash = new JButton("Buscar Hashtags");
        JButton logoutButton = new JButton("Cerrar Sesion");

    
        navPanel.add(InteraccionesBoton);
        navPanel.add(Editar);
        navPanel.add(BuscarHash);
        navPanel.add(logoutButton);

        // Panel de contenido
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.white);

        // Panel para escribir tweets
        JPanel tweetPanel = new JPanel(new BorderLayout());
        tweetPanel.setBorder(BorderFactory.createTitledBorder("Hola " + UsuarioActual + "! ¿En que estas pensando?"));
        tweetArea = new JTextPane(); // Cambiado a JTextPane
        tweetArea.setPreferredSize(new Dimension(400, 100));
        tweetPanel.add(new JScrollPane(tweetArea), BorderLayout.CENTER);

        // Contador de caracteres
        JPanel bottomPanel = new JPanel(new BorderLayout());
        letrasContadasLabel = new JLabel("0/140 caracteres");
        bottomPanel.add(letrasContadasLabel, BorderLayout.WEST);

        // Panel para los botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton publicarboton = new JButton("Publicar");
        buttonPanel.add(publicarboton);

        bottomPanel.add(publicarboton, BorderLayout.EAST);

        tweetPanel.add(bottomPanel, BorderLayout.SOUTH);

        contentPanel.add(tweetPanel, BorderLayout.NORTH);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Panel para mostrar los tweets
        espacioPanel = new JPanel();
        espacioPanel.setLayout(new BoxLayout(espacioPanel, BoxLayout.Y_AXIS));
        espacioPanel.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 2));  // borde azulito
        JScrollPane timelineScrollPane = new JScrollPane(espacioPanel);
        contentPanel.add(timelineScrollPane, BorderLayout.CENTER);

        
        mainPanel.add(navPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Configuracion de los botones de navegacion
        Editar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MiPerfil miPerfilVentana = new MiPerfil(UsuarioActual);  // Pasar el nombre de usuario actual
                miPerfilVentana.setVisible(true);
                frame.setVisible(false);  // Cerrar la ventana actual
            }
        });

        BuscarHash.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                BuscarHashtag buscarHashtag = new BuscarHashtag(twits, MenuPrincipal.this);
            }
        });

        InteraccionesBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visual = new VisualInteracciones(UsuarioActual);
            }
        });

        publicarboton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contenido = tweetArea.getText().trim();
                if (!contenido.isEmpty() && contenido.length() <= 140) {
                    agregarHashtagsExistente(contenido);
                    twits.Publicartwit(UsuarioActual, contenido);
                    JOptionPane.showMessageDialog(frame, "Tweet publicado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTimeline();
                    tweetArea.setText("");
                    letrasContadasLabel.setText("0/140 caracteres");
                } else if (contenido.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "El tweet no puede estar vacio.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "El tweet excede los 140 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                resaltarHashtags();
                resaltarArroba();
            }
        });

        tweetArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int contadorLetras = tweetArea.getText().length();
                letrasContadasLabel.setText(contadorLetras + "/140 caracteres");
                if (contadorLetras > 140) {
                    letrasContadasLabel.setForeground(Color.RED);
                } else {
                    letrasContadasLabel.setForeground(Color.BLACK);
                }
                resaltarHashtags();
                resaltarArroba();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });

        frame.setVisible(true);
    }

    private void agregarHashtagsExistente(String contenido) {
        int longitud = contenido.length();
        StringBuilder palabraActual = new StringBuilder();
        boolean esHashtag = false;

        for (int i = 0; i < longitud; i++) {
            char caracterActual = contenido.charAt(i);

            if (caracterActual == '#') {
                esHashtag = true;
            } else if (esHashtag && (caracterActual == ' ' || i == longitud - 1)) {
                if (i == longitud - 1 && caracterActual != ' ') {
                    palabraActual.append(caracterActual);
                }

                String hashtag = palabraActual.toString();
                if (!hashtagYaExiste(hashtag) && !hashtag.isEmpty()) {
                    if (numHashatgExistente < hashtagExistente.length) {
                        hashtagExistente[numHashatgExistente] = hashtag;
                        numHashatgExistente++;
                        System.out.println("Hashtag agregado : " + hashtag);
                    }
                }
                esHashtag = false;
                palabraActual.setLength(0);
            } else if (esHashtag) {
                palabraActual.append(caracterActual);
            }
        }
    }

    private boolean hashtagYaExiste(String hashtag) {
        for (int buscarHash = 0; buscarHash < numHashatgExistente; buscarHash++) {
            if (hashtagExistente[buscarHash] != null && hashtagExistente[buscarHash].equals(hashtag)) {
                return true;
            }
        }
        return false;
    }   

    private void resaltarHashtags() {
        StyledDocument doc = tweetArea.getStyledDocument();

        Style defaultStyle = tweetArea.getStyle("default");
        if (defaultStyle == null) {
            defaultStyle = tweetArea.addStyle("default", null);
        }

        Style hashtagStyle = tweetArea.getStyle("HashtagStyle");
        if (hashtagStyle == null) {
            hashtagStyle = tweetArea.addStyle("HashtagStyle", null);
            StyleConstants.setBold(hashtagStyle, true);
            StyleConstants.setForeground(hashtagStyle, new Color(30, 144, 255));
        }

        String text = tweetArea.getText();
        doc.setCharacterAttributes(0, text.length(), defaultStyle, true);

        int index = 0;
        int longitud = text.length();

        while (index < longitud) {
            int hashtagInicia = text.indexOf("#", index);
            if (hashtagInicia == -1) {
                break;
            }

            int hashtagTermina = hashtagInicia + 1;
            while (hashtagTermina < longitud && !Character.isWhitespace(text.charAt(hashtagTermina))) {
                hashtagTermina++;
            }

            String hashtag = text.substring(hashtagInicia + 1, hashtagTermina);

            if (hashtagYaExiste(hashtag)) {
                doc.setCharacterAttributes(hashtagInicia, hashtagTermina - hashtagInicia, hashtagStyle, false);
            }

            index = hashtagTermina;
        }
    }

    private void resaltarArroba() {
        StyledDocument arro = tweetArea.getStyledDocument();
        Style negro = tweetArea.addStyle("default", null);
        Style boldStyle = tweetArea.addStyle("boldStyle", null);
        StyleConstants.setBold(boldStyle, true);
        StyleConstants.setForeground(boldStyle, Color.BLACK);

        String text = tweetArea.getText();
        arro.setCharacterAttributes(0, text.length(), tweetArea.getStyle("default"), true);

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

            boolean existeUsuario = UsuarioManager.usuarioExiste(username);
            boolean estaActivo = UsuarioManager.UsuarioActivo(username);

            if (UsuarioManager.usuarioExiste(username) && UsuarioManager.UsuarioActivo(username)) {
                arro.setCharacterAttributes(mencionInicio, mencionFin - mencionInicio, boldStyle, false);
            }
            index = mencionFin;
        }
    }

    public void actualizarTimeline() {
        espacioPanel.removeAll();

        if (UsuarioManager.UsuarioActivo(UsuarioActual)) {
            Twits twitsUsuario = UsuarioManager.obtenerTwitsUsuario(UsuarioActual);
            if (twitsUsuario != null) {
                mostrarTwits(twitsUsuario);
            }
        }

        String[] usuariosSeguidos = UsuarioManager.obtenerUsuariosSeguidos(UsuarioActual);

        for (String seguido : usuariosSeguidos) {
            if (UsuarioManager.UsuarioActivo(seguido)) {
                Twits twitsSeguido = UsuarioManager.obtenerTwitsUsuario(seguido);
                if (twitsSeguido != null) {
                    mostrarTwits(twitsSeguido);
                }
            }
        }

        espacioPanel.revalidate();
        espacioPanel.repaint();
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

    private String eliminarHashtagsDeUsuariosDesactivados(String contenido, String creadorTweet) {
        StringBuilder resultado = new StringBuilder();
        String[] palabras = contenido.split(" ");

        for (String palabra : palabras) {
            if (palabra.startsWith("#")) {
                if (!UsuarioManager.UsuarioActivo(creadorTweet)) {
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

            boolean existeUsuario = UsuarioManager.usuarioExiste(username);
            boolean estaActivo = UsuarioManager.UsuarioActivo(username);

            if (UsuarioManager.usuarioExiste(username) && UsuarioManager.UsuarioActivo(username)) {
                doc.setCharacterAttributes(mencionInicio, mencionFin - mencionInicio, mencionEstilo, false);
            }
            index = mencionFin;
        }
    }

    private void mostrarTwits(Twits twits) {
        Twit[] listaTwits = twits.getTwits();

        for (int i = listaTwits.length - 1; i >= 0; i--) {
            Twit twit = listaTwits[i];

            if (twit != null && UsuarioManager.UsuarioActivo(twit.getUsername())) {
                JTextPane tweetPane = new JTextPane();
                tweetPane.setEditable(false);

                String contenidoFiltrado = eliminarMencionesDeUsuariosDesactivados(twit.getContenido());
                contenidoFiltrado = eliminarHashtagsDeUsuariosDesactivados(contenidoFiltrado, twit.getUsername());

                tweetPane.setText(twit.getUsername() + " escribió: \"" + contenidoFiltrado + " \nPublicado el " + twit.getFechapublicacion() + ".\n-------------------------------------------------------------------------------------------------------");

                StyledDocument doc = tweetPane.getStyledDocument();
                Style hashtagEstilo = tweetPane.addStyle("hashtagEstilo", null);
                Style mencionEstilo = tweetPane.addStyle("mencionEstilo", null);

                StyleConstants.setForeground(hashtagEstilo, new Color(30, 144, 255));
                StyleConstants.setBold(mencionEstilo, true);
                StyleConstants.setForeground(mencionEstilo, Color.BLACK);

                String text = tweetPane.getText();

                resaltarHashtags(text, doc, hashtagEstilo);

                resaltarMenciones(text, doc, mencionEstilo);

                tweetPane.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
                espacioPanel.add(tweetPane);
            }
        }
    }

    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(frame, "¿Estas seguro de que deseas cerrar sesion?",
                "Confirmar cierre de sesión", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Cerrar todas las ventanas activas
            for (java.awt.Window window : java.awt.Window.getWindows()) {
                window.dispose();  // Cierra cada ventana abierta
            }
            LogIn logIn = new LogIn();
            frame.dispose();
        }
    }
}

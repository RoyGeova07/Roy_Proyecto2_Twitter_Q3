package proyecto_twit_roy_moises;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author royum
 */
public class LogIn {

    UsuarioManager usu;

    private static LogIn instancia;
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LogIn() {
        initUI();
    }

    
    public static LogIn getInstancia() {
        if (instancia == null) {
            instancia = new LogIn();
        }
        return instancia;
    }

    
    public void mostrarLog() {
        if (frame != null) {
            frame.setVisible(true);
        }
    }

    public void ocultarLog() {
        if (frame != null) {
            frame.setVisible(false);
        }
    }

    public static void main(String[] args) {
        LogIn.getInstancia();
    }

    private void initUI() {
        frame = new JFrame("Login");
        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        frame.add(mainPanel);

        // Cargar la imagen desde el paquete imagenes
        ImageIcon imagenFondo = new ImageIcon(LogIn.class.getResource("/imagenes/mini.png"));
        JLabel imageLabel = new JLabel(imagenFondo);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(imageLabel, BorderLayout.WEST);

        // Panel de campos y botones
        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new BoxLayout(panelEntrada, BoxLayout.Y_AXIS));
        panelEntrada.setBackground(Color.WHITE);
        panelEntrada.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(panelEntrada, BorderLayout.CENTER);

        // Mensaje de bienvenida
        JLabel bienvenidaLabel = new JLabel("Bienvenido a tu red social favorita Twitter!");
        bienvenidaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        bienvenidaLabel.setForeground(new Color(30, 144, 255));
        bienvenidaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelEntrada.add(bienvenidaLabel);
        panelEntrada.add(Box.createVerticalStrut(15)); // Espacio vertical

        // Configuracion de los campos de entrada
        panelEntrada.add(crearCampo("Username:", usernameField = new JTextField()));
        panelEntrada.add(crearCampo("Contraseña:", passwordField = new JPasswordField()));

        JLabel NoCuenta = new JLabel("¿No tienes cuenta?");
        NoCuenta.setFont(new Font("Arial", Font.PLAIN, 14));
        NoCuenta.setForeground(Color.GRAY);
        NoCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelEntrada.add(Box.createVerticalStrut(10)); // Espacio vertical
        panelEntrada.add(NoCuenta);

    
        JButton crearCuentaButton = new JButton("¡Click aqui, Crea tu cuenta!");
        crearCuentaButton.setForeground(new Color(30, 144, 255));
        crearCuentaButton.setBackground(Color.WHITE);
        crearCuentaButton.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        crearCuentaButton.setFont(new Font("Roboto", Font.BOLD, 14));
        crearCuentaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelEntrada.add(crearCuentaButton);

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);

        JButton loginButton = new JButton("LOG IN");
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(30, 144, 255)); // color azulito
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(Box.createVerticalStrut(10)); // Espacio vertical
        buttonPanel.add(loginButton);

        // Boton de Salir
        JButton salirButton = new JButton("Salir de Twitter");
        salirButton.setForeground(Color.white);
        salirButton.setBackground(new Color(255, 0, 0)); // color rojo
        salirButton.setFont(new Font("Arial", Font.BOLD, 14));
        salirButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(Box.createVerticalStrut(10)); // Espacio vertical
        buttonPanel.add(salirButton);

        panelEntrada.add(Box.createVerticalStrut(20)); 
        panelEntrada.add(buttonPanel);

      
        crearCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CrearCuenta();
                frame.dispose();
            }
        });

        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identificacion();
            }
        });

        
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Salir del programa
            }
        });

        frame.setVisible(true); 
    }

    private JPanel crearCampo(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(150, 30));
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label, BorderLayout.WEST);

        field.setPreferredSize(new Dimension(150, 30));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setForeground(Color.BLACK);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 2)); // Borde azulito claro
        panel.add(field, BorderLayout.CENTER);

        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Espacio alrededor

        return panel;
    }

   private void identificacion() {
    String username = usernameField.getText().trim();
    String password = new String(passwordField.getPassword()).trim();

    // Verificar si las credenciales son correctas
    if (UsuarioManager.autenticar(username, password)) {
        // Restaurar la cuenta si estaba desactivada
        UsuarioManager.activarCuenta(username);

        // Cargar el menú principal
        MenuPrincipal menu = new MenuPrincipal(username);
        menu.mostrarMenu();

        // Cerrar la ventana de login después de iniciar sesión
        frame.dispose();
    } else {
        // Si las credenciales son incorrectas, mostrar un mensaje de error
        int respuesta = JOptionPane.showConfirmDialog(
                null,
                "Usuario o contraseña incorrectos. ¿Desea intentar nuevamente (Yes) o crear cuenta (No)?",
                "Error de Login",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE
        );

        // Si elige crear una cuenta, abrir la ventana de creación de cuenta
        if (respuesta == JOptionPane.NO_OPTION) {
            new CrearCuenta();
            frame.dispose();
        }
    }
}

    public void setVisible(boolean visible) {
        if (frame != null) {
            frame.setVisible(visible);
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_twit_roy_moises;



/**
 *
 * @author royum
 */
public class User {
    
    CrearCuenta crear;

    public String nombre;
    public char genero;
    public String nombreUsuario;
    public String contrasena;
    public int edad;
    private Twit[] twits;
    public boolean estadoActivo;
    private int contadorTwits;
    private boolean ensesion;

    public boolean isEnsesion() {
        return ensesion;
    }

    // Constructor
    public User(String nombre, char genero, String nombreUsuario, String contrasena, int edad) {
        this.nombre = nombre;
        this.genero = genero;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.edad = edad;
        this.twits = new Twit[100]; // Espacio para 100 tweets
        this.estadoActivo = true; // Por defecto, la cuenta esta activa
        this.contadorTwits = 0;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public char getGenero() {
        return genero;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void agregarTwit(Twit twit) {
        if (contadorTwits < twits.length) {
            twits[contadorTwits++] = twit;
        } else {
            System.out.println("No se pueden agregar más tweets, límite alcanzado.");
        }
    }

    public Twit[] getTwits() {
        return twits;
    }

    public int getContadorTwits() {
        return contadorTwits;
    }

    public int getEdad() {
        return edad;
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    // para desactivar la cuenta
    public void desactivarCuenta() {
        this.estadoActivo = false;
    }
}

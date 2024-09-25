/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_twit_roy_moises;

/**
 *
 * @author royum
 */
public class following {

    private String[] following;
    private int contador;
    private final int MAX_FOLLOWING = 500;

    public following() {
        following = new String[MAX_FOLLOWING];
        contador = 0;
    }

    public boolean agregarSeguido(String usernameSeguido) {
        if (contador >= MAX_FOLLOWING) {
            return false; // no se puede seguir a mas usuarios
        }
        for (int i = 0; i < contador; i++) {
            if (following[i].equals(usernameSeguido)) {
                return false; // ya se sigue a este usuario
            }
        }
        following[contador] = usernameSeguido;
        contador++;
        return true;
    }

    public boolean eliminarSeguido(String usernameSeguido) {
        for (int i = 0; i < contador; i++) {
            if (following[i].equals(usernameSeguido)) {
                
                following[i] = following[contador - 1];
                following[contador - 1] = null;
                contador--;
                return true;
            }
        }
        return false; // el usuario no estaba en la lista
    }

    public boolean esSeguido(String usernameSeguido) {
        for (int i = 0; i < contador; i++) {
            if (following[i].equals(usernameSeguido)) {
                return true;
            }
        }
        return false;
    }

    public String[] obtenerFollowing() {
        String[] listaFollowing = new String[contador];
        System.arraycopy(following, 0, listaFollowing, 0, contador);
        return listaFollowing;
    }

    public int obtenerNumeroFollowing() {
        return contador;
    }
}

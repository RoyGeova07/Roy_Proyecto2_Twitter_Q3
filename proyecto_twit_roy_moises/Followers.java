/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_twit_roy_moises;

/**
 *
 * @author royum
 */
public class Followers {

    private final int MAX_SEGUIDORES = 500;
    private String[] followers; 
    private int contador; 

    public Followers() {
        this.followers = new String[MAX_SEGUIDORES];
        this.contador = 0;
    }

    public boolean agregarSeguidor(String usernameSeguidor) {
        if (contador >= MAX_SEGUIDORES) {
            return false; 
        }
        for (int i = 0; i < contador; i++) {
            if (followers[i].equals(usernameSeguidor)) {
                return false; 
            }
        }
        followers[contador] = usernameSeguidor;
        contador++;
        return true;
    }

    public boolean eliminarSeguidor(String usernameSeguidor) {
        for (int i = 0; i < contador; i++) {
            if (followers[i].equals(usernameSeguidor)) {
                // aqui movemos  el ultimo elemento a la posicion actual y reducir el contador
                followers[i] = followers[contador - 1];
                followers[contador - 1] = null;
                contador--;
                return true;
            }
        }
        return false; // el usuario no estaba en la lista
    }

    public String[] getFollowers() {
        return followers;
    }

    public boolean esSeguidor(String usernameSeguidor) {
        for (int i = 0; i < contador; i++) {
            if (followers[i].equals(usernameSeguidor)) {
                return true; 
            }
        }
        return false; // no es seguidor
    }

    public String[] Obtenerfolllowers() {
        String[] listaSeguidores = new String[contador];
        System.arraycopy(followers, 0, listaSeguidores, 0, contador);
        return listaSeguidores;
    }

    public int obtenerNumeroSeguidores() {
        return contador;
    }
}

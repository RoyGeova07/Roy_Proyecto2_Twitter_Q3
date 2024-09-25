/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_twit_roy_moises;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

// esta clase twit  maneja el almacenamiento de los twits publicados por el usuario. 
// Cada twit almacena el nombre de usuario, la fecha de publicación y el contenido del twit
/**
 *
 * @author royum
 */
public class Twit {

    private String username;
    private Calendar Fechapublicacion;
    private String contenido;
    private boolean contenidovalido;
    private int NumeroTwits;

    public int getNumeroTwits() {
        return NumeroTwits;
    }

    private HashTag[] has;

    // constrcutor del twitz x.
    public Twit(String username, String contenido) {

        this.username = username;
        this.Fechapublicacion = Calendar.getInstance();
        if (contenido.length() <= 140) {
            this.contenido = contenido;
            this.contenidovalido = true; // el contenido que se ingreso es valido
        } else {

            this.contenido = "Contenido invalido, excede los 140 caracteres.";
            this.contenidovalido = false; // contenido no validooo
        }

    }

    public String getUsername() {
        return username;
    }

    public String getFechapublicacion() {
        SimpleDateFormat formatoFecha =  new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatoFecha.format(Fechapublicacion.getTime());
    }

    public String getContenido() {
        return contenido;
    }

    public boolean isContenidovalido() {
        return contenidovalido;
    }

    public HashTag[] getHas() {
        return has;
    }
    
    // funcion para verificar si el usuario es mencionadocon el @
    public boolean MencionUsuario(String username){
        int arrobaIndex = contenido.indexOf("@"+username);
        if(arrobaIndex == -1){
            return false;
        }
        int siguienteCaracter = arrobaIndex + username.length() + 1;
        if(siguienteCaracter >= contenido.length() || contenido.charAt(siguienteCaracter) == ' '){
            return true;
        }
        return false;
    }
    

}

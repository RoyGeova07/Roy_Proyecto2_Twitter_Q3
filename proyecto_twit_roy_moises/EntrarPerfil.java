/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_twit_roy_moises;

import java.util.Date;
import java.util.Calendar;

/**
 *
 * @author royum
 */
public class EntrarPerfil {

    private UsuarioManager usuarioManager;
    private EntrarPerfil perfil;
    private String usuarioActual; // Nombre de usuario del usuario actual
    private static final int MAX_USUARIOS = 500;
    private static boolean[] cuentasActivas = new boolean[MAX_USUARIOS];

    // buscar perfil 
    public static String[] BuscarPerfil(String username) {
        // Obtener todos los nombres de usuario
        String[] userPerfil = UsuarioManager.getUsernames();
        String[] resultado = null;

        // Iterar sobre todos los perfiles
        for (int perfil = 0; perfil < UsuarioManager.getContador(); perfil++) {
            // Verificar si el perfil no es nulo, esta activo, y coincide con el nombre de usuario buscado
            if (userPerfil[perfil] != null && UsuarioManager.UsuarioActivo(userPerfil[perfil]) && userPerfil[perfil].equalsIgnoreCase(username)) {
                String nombre = UsuarioManager.getNombres()[perfil];
                String genero = UsuarioManager.getGeneros()[perfil];
                Calendar fechaIngreso = UsuarioManager.getFechasIngreso()[perfil];
                int seguidores = manejoPerfil.getNumFollowers()[perfil];
                int siguiendo = manejoPerfil.getNumFollowing()[perfil];
                int numeroTwetts = UsuarioManager.obtenerTwitsUsuario(username).getNumeroTwits();

                String rutaImagen;
                if (genero.equalsIgnoreCase("hombre")) {
                    rutaImagen = "/imagenes/Masculino.jpg";
                } else if (genero.equalsIgnoreCase("mujer")) {
                    rutaImagen = "/imagenes/Femenino.jpg";
                } else {
                    rutaImagen = "/imagenes/mini.png";
                }

                // Asignar los resultados encontrados
                resultado = new String[]{
                    nombre, genero, fechaIngreso.toString(), String.valueOf(seguidores),
                    String.valueOf(siguiendo), String.valueOf(numeroTwetts), rutaImagen
                };
                break;
            }
        }
        return resultado;
    }

}

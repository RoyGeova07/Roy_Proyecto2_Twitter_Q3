package proyecto_twit_roy_moises;

public class manejoPerfil {

    private static final int MAX_USUARIOS = 500;
    private static boolean[] cuentasActivas = new boolean[MAX_USUARIOS];
    private static String[][] following = new String[MAX_USUARIOS][MAX_USUARIOS]; // corresponde a los usuarios que el usuario, contiene el nombre de un usuario que está siendo seguido por el usuario
    private static int[] numFollowers = new int[MAX_USUARIOS]; // Contador de seguidores para cada usuario
    private static int[] numFollowing = new int[MAX_USUARIOS]; // Contador de seguidos para cada usuario
    private static int contador = 0;

    // Inicializa los arrays
    static {
        for (int i = 0; i < MAX_USUARIOS; i++) {
            numFollowers[i] = 0;
            numFollowing[i] = 0;
            cuentasActivas[i] = true; // Inicialmente, todas las cuentas estan activas
        }
    }

    public static boolean dejarDeSeguirUsuario(String usuarioActual, String usuarioObjetivo) {
        int indiceActual = obtenerIndiceUsuario(usuarioActual);
        int indiceObjetivo = obtenerIndiceUsuario(usuarioObjetivo);

        if (indiceActual == -1 || indiceObjetivo == -1) {
            return false; // Si alguno de los usuarios no existe
        }

        for (int i = 0; i < numFollowing[indiceActual]; i++) {
            if (following[indiceActual][i].equals(usuarioObjetivo)) {
                // Eliminar al usuario seguido
                following[indiceActual][i] = following[indiceActual][numFollowing[indiceActual] - 1];
                following[indiceActual][numFollowing[indiceActual] - 1] = null;
                numFollowing[indiceActual]--;
                numFollowers[indiceObjetivo]--;
                return true; // Dejar de seguir realizado
            }
        }
        return false; 
    }

    public static boolean sigueUsuario(String usuarioActual, String usuarioObjetivo) {
        int indiceActual = obtenerIndiceUsuario(usuarioActual);
        int indiceObjetivo = obtenerIndiceUsuario(usuarioObjetivo);

        if (indiceActual == -1 || indiceObjetivo == -1) {
            return false; // Si alguno de los usuarios no existe
        }

        for (int verificar = 0; verificar < numFollowing[indiceActual]; verificar++) {
            if (following[indiceActual][verificar] != null && following[indiceActual][verificar].equals(usuarioObjetivo)) {
                return true; // Ya sigue al usuario
            }
        }
        return false; // No sigue al usuario
    }

    private static int obtenerIndiceUsuario(String username) {
        String[] usernames = UsuarioManager.getUsernames();
        for (int nombre = 0; nombre < usernames.length; nombre++) {
            if (usernames[nombre] != null && usernames[nombre].equals(username)) {
                return nombre;
            }
        }
        return -1; // Usuario no encontrado
    }

    public static boolean seguirUsuario(String usuarioActual, String usuarioObjetivo) {
    if (!sigueUsuario(usuarioActual, usuarioObjetivo)) {
        int indiceActual = obtenerIndiceUsuario(usuarioActual);
        int indiceObjetivo = obtenerIndiceUsuario(usuarioObjetivo);

        if (indiceActual != -1 && indiceObjetivo != -1 && cuentasActivas[indiceObjetivo]) {
            following[indiceActual][numFollowing[indiceActual]] = usuarioObjetivo;
            numFollowing[indiceActual]++; 
            numFollowers[indiceObjetivo]++; 
            return true; // Seguimiento exitoso
        }
    }
    return false; // No se pudo seguir al usuario
}

    public static String[] BuscarUsuario(String username) {
        String[] usernames = UsuarioManager.getUsernames(); 
        String[] resultado = new String[UsuarioManager.getContador()]; 
        int resultadoContador = 0;

        for (int buscar = 0; buscar < UsuarioManager.getContador(); buscar++) {
            if (usernames[buscar] != null && cuentasActivas[buscar] && usernames[buscar].contains(username)) {
                resultado[resultadoContador] = usernames[buscar];
                resultadoContador++;
            }
        }

        String[] resultadoFinal = new String[resultadoContador];
        System.arraycopy(resultado, 0, resultadoFinal, 0, resultadoContador);
        return resultadoFinal;
    }
    
    

    public static int[] getNumFollowers() {
        return numFollowers;
    }

    public static int[] getNumFollowing() {
        return numFollowing;
    }

}
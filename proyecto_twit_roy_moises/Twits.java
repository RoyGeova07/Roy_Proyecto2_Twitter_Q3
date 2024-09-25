/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_twit_roy_moises;

// esta clasew sirve para administar los twits del usuario
/**
 *
 * @author royum
 */
public class Twits {

    private Twit[] twits;
    private int numeroTwits;
    private HashTag[] hash;
     private Twit[] listaTwits;  // Los tweets del usuario
    private Twit[] tweetsOcultos; // Lista temporal para tweets ocultos
    private int numTweets;

    // constructos de twitsss
    public Twits() {

        this.twits = new Twit[1000]; // mil tuwtsss
        this.numeroTwits = 0;
        listaTwits = new Twit[500];  // Ejemplo de capacidad
        tweetsOcultos = new Twit[500]; 

    }

    // funcion para public twitsssss]
    public void Publicartwit(String nombreUsuario, String contenido) {

        if (numeroTwits < 1000) {
            Twit nuevoTwit = new Twit(nombreUsuario, contenido);
            if (nuevoTwit.isContenidovalido()) {
                twits[numeroTwits] = nuevoTwit;
                numeroTwits++;
                GestorDeTwits.agregarTwit(nuevoTwit);

            } else {
                System.out.println(nuevoTwit.getContenido()); // aqui muestra el mensaje de un error
            }
        } else {
            System.out.println("No pudes publicar mas twits. se ha alcanzado el limite");
        }

    }

    public Twit[] getTwits() {
        return twits;
    }

    public int getNumeroTwits() {
        return numeroTwits;
    }

    
    public HashTag[] getHash() {
        return hash;
    }
    
     public void eliminarTwitsDeUsuario(String seguido) {
        for (int i = 0; i < numeroTwits; i++) {
            if (twits[i] != null && twits[i].getUsername().equals(seguido)) {
                // Mover los tweets hacia la izquierda para sobreescribir el eliminado
                for (int j = i; j < numeroTwits - 1; j++) {
                    twits[j] = twits[j + 1];
                }
                twits[numeroTwits - 1] = null;  // Eliminar el ultimo tweet
                numeroTwits--;  // Reducir el contador de tweets
                i--;  // Volver a verificar el nuevo tweet en la misma posicion
            }
        }
    }

    public void agregarTwitsDeUsuario(Twits twitsSeguido) {
        for (int i = 0; i < twitsSeguido.getNumeroTwits(); i++) {
            if (twitsSeguido.getTwits()[i] != null) {
                // Solo agregamos si hay espacio en el arreglo
                if (numeroTwits < twits.length) {
                    twits[numeroTwits] = twitsSeguido.getTwits()[i];
                    numeroTwits++;
                }
            }
        }
    }
    
    public void agregarTwitsDeSeguido(Twits twitsSeguido) {
        //aqui de no sobrepasar el limite de tweets
        for (int i = 0; i < twitsSeguido.getNumeroTwits() && numeroTwits < twits.length; i++) {
            twits[numeroTwits] = twitsSeguido.getTwits()[i];
            numeroTwits++;
        }
    }

    public void eliminarTwitsDeSeguido(String usernameSeguido) {
        int nuevoIndice = 0;

        // Recorre los tweets actuales del usuario
        for (int i = 0; i < numeroTwits; i++) {
            Twit twit = twits[i];
            // Si el tweet no pertenece al usuario que seguimos lo mantenemos
            if (twit != null && !twit.getUsername().equals(usernameSeguido)) {
                twits[nuevoIndice] = twits[i];
                nuevoIndice++;
            }
        }

        // Actualiza el numero de tweets
        numeroTwits = nuevoIndice;

        // Limpia el espacio restante en el arreglo
        for (int i = nuevoIndice; i < twits.length; i++) {
            twits[i] = null;
        }
    }
    
    
    // Ocultar los tweets de un usuario específico mover a tweetsOcultos
    public void ocultarTwitsDeUsuario(String username) {
        for (int i = 0; i < numTweets; i++) {
            if (listaTwits[i] != null && listaTwits[i].getUsername().equals(username)) {
                // Mover el tweet a la lista de tweets ocultos
                moverTwitAListaOculta(i);
            }
        }
    }

    // Restaurar los tweets de un usuario especifico
    public void mostrarTwitsDeUsuario(String username) {
        for (int i = 0; i < tweetsOcultos.length; i++) {
            if (tweetsOcultos[i] != null && tweetsOcultos[i].getUsername().equals(username)) {
                // Mover el tweet de vuelta a la lista principal
                moverTwitAListaVisible(i);
            }
        }
    }

    // Mover un tweet a la lista de ocultos
    private void moverTwitAListaOculta(int index) {
        for (int i = 0; i < tweetsOcultos.length; i++) {
            if (tweetsOcultos[i] == null) {
                tweetsOcultos[i] = listaTwits[index];
                listaTwits[index] = null;
                reorganizarTwits();
                break;
            }
        }
    }

    // Mover un tweet de la lista de ocultos a la lista visible
    private void moverTwitAListaVisible(int index) {
        for (int i = 0; i < listaTwits.length; i++) {
            if (listaTwits[i] == null) {
                listaTwits[i] = tweetsOcultos[index];
                tweetsOcultos[index] = null;
                break;
            }
        }
    }

    // aqui funcion para reorganizar la lista de tweets después de una eliminacion
    private void reorganizarTwits() {
        Twit[] nuevoArray = new Twit[listaTwits.length];
        int j = 0;
        for (Twit twit : listaTwits) {
            if (twit != null) {
                nuevoArray[j] = twit;
                j++;
            }
        }
        listaTwits = nuevoArray;
        numTweets = j;
    }
    
    public void agregarTwitsDeSeguidoSinDuplicar(Twits twitsSeguido) {
        for (int i = 0; i < twitsSeguido.getNumeroTwits(); i++) {
            Twit twitSeguido = twitsSeguido.getTwits()[i];

            // Evitar duplicar tweets del mismo usuario
            boolean existe = false;
            for (int j = 0; j < numeroTwits; j++) {
                if (twits[j].getContenido().equals(twitSeguido.getContenido()) &&
                    twits[j].getUsername().equals(twitSeguido.getUsername())) {
                    existe = true;
                    break;
                }
            }

            // Solo agregar si el tweet no existe en el timeline
            if (!existe) {
                agregarTwit(twitSeguido);
            }
        }
    }

     public void agregarTwit(Twit twit) {
        if (numeroTwits < twits.length) {
            twits[numeroTwits] = twit;
            numeroTwits++;
        }
    }

}

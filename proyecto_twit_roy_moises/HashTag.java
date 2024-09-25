/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_twit_roy_moises;

// para diferenciar los hashtag
/**
 *
 * @author royum
 */
public class HashTag {

    private String etiquetaHash;
    private Twit twits;

    public HashTag(Twit twits) {
        this.twits = twits;
    }

    public HashTag(String etiquetaHash) {
        this.etiquetaHash = etiquetaHash;
    }

    public String getEtiquetaHash() {
        return etiquetaHash;
    }

    public Twit[] buscarTwitsConHashTag(){
        Twit[] todosLosTwits = GestorDeTwits.obtenerTodoLosTwits();
        Twit[] twitConHashTag = new Twit[todosLosTwits.length];
        
        int contador = 0;
        for (int buscar = 0; buscar < todosLosTwits.length; buscar++) {
            Twit twitActual = todosLosTwits[buscar];
            if(twitActual != null && ContieneHashTag(twitActual)){
                twitConHashTag[contador] = twitActual;
                contador++;
            }
            
        }
        Twit[] resultadosFinales = new Twit[contador];
        System.arraycopy(twitConHashTag, 0, resultadosFinales, 0, contador);
        return resultadosFinales;
        
    }

    private boolean ContieneHashTag(Twit twit) {
        String contenido = twit.getContenido();
        return contenido.contains("#" + etiquetaHash);
    }

}

import java.util.ArrayList;
import java.util.List;

public class Carrera extends Thread {

    private static List<Tortuga> listaCorredores;
    public static String ganador;


    @Override
    public void run(){

        listaCorredores = Servidor.getList();
        for (Tortuga t : listaCorredores){

            t.start();

        }
    }

    public static void terminarCarrera(String winner){
        System.out.flush();
        System.out.printf("La carrera ha acabado! %s es el ganador.\n\n", winner);
        ganador = winner;
        for (Tortuga th: listaCorredores){
            th.interrupt();
        }

    }

    public String getGanador(){
        return this.ganador;
    }

}

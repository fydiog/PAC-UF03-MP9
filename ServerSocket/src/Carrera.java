import java.util.ArrayList;
import java.util.List;

public class Carrera extends Thread {

    private static ArrayList<Tortuga> listaCorredores;
    public static String ganador;


    public void empezarCarrera(){

        listaCorredores = Servidor.getList();
        for (Tortuga t : listaCorredores){

            t.start();

        }

        for (Tortuga t: listaCorredores){
            try{
                t.join();
            }
            catch (InterruptedException e){
                System.out.println("La carrera se ha interrumpido.");
                break;
            }
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

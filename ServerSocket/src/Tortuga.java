import java.io.Serializable;
import java.util.Random;

public class Tortuga extends Thread implements Serializable {
    private String nombre;
    private String dorsal;
    private static volatile boolean finished;

    public Tortuga() {
        this.nombre = nombre;
        this.dorsal = dorsal;

    }

    public String getNombre() {
        return nombre;
    }

    public String getDorsal() {
        return dorsal;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDorsal(String dorsal) {
        this.dorsal = dorsal;
    }

    @Override
    public void run() {

        Random rand = new Random();
        try {
            for (int i = 1; i <= 500; i++) {
                Thread.sleep(rand.nextInt(20));
                System.out.println(dorsal + " corriendo por el metro " + i);
            }
            if (finished == false) {
                finished = true; //Cuando una tortuga entra en este if automaticamente termina la carrera y manda a cerrar los demás hilos
                Carrera.terminarCarrera(getNombre());
            } else {
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

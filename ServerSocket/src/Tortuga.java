import java.io.Serializable;
import java.util.Random;

public class Tortuga extends Thread implements Serializable {
    private String nombre;
    private String dorsal;
    private static volatile boolean finished;
    private static Object obj = new Object();   // Para el bloque synchronized

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

        Random rand = new Random(); //Objeto random para los nros aleatorios
        try {
            for (int i = 1; i <= 500; i++) {
                Thread.sleep(rand.nextInt(10));
                System.out.println(dorsal + " corriendo por el metro " + i);
            }

            synchronized (obj){
                if (finished == false){  // Solo el primer hilo en acabar el bucle entrará al if
                    finished = true;  // El primer hilo deja en true finished, acabando así la carrera
                    Thread.sleep(200);  // Espero a que los demás corredores acaben
                    Carrera.terminarCarrera(getNombre()); //Llama al método que finaliza la carrera
                }
                else  // Los no ganadores
                    Thread.sleep(1000);
            }


        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

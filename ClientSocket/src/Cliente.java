import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Cliente implements Serializable {
    private final String HOST = "localhost";
    private final int PORT = 6666;
    private Socket socket;
    DataInputStream in;
    DataOutputStream out;
    BufferedReader br;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectIntputStream;


    public void startClient() throws IOException {
        try {
            //Socket con el host y el puerto declarados anteriormente
            socket = new Socket(HOST, PORT);
            //Flujos de entrada y salida de datos del cliente
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            //Flujos de entrada y salida de objetos del cliente
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectIntputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println(in.readUTF());

            int opcionSeleccionada = 0; //Variable que almacena la opción que vaya a elegir el usuario
            String nombre, dorsal;

            while (opcionSeleccionada != 5) { //Hasta que no introduzcamos el numero 5 seguira mostrando el menú

                showMenu(); //Método que muestra el menu en cada vuelta del bucle.
                opcionSeleccionada = Integer.parseInt(br.readLine());

                switch (opcionSeleccionada) {

                    case 1:
                        out.writeUTF("1");
                        PaqueteEnvio datos = new PaqueteEnvio();
                        System.out.println("Has seleccionado añadir tortugas");
                        System.out.println("Introduce el nombre de la tortuga:");
                        nombre = br.readLine();
                        datos.setNombre(nombre);
                        System.out.println("Introduce el dorsal de la tortuga:");
                        dorsal = br.readLine();
                        datos.setDorsal(dorsal);
                        objectOutputStream.writeObject(datos);
                        System.out.println("Se ha enviado al servidor la tortuga " + nombre + " con dorsal " + dorsal);


                        break;
                    case 2:
                        out.writeUTF("2");
                        System.out.println("Has seleccionado eliminar tortugas");
                        System.out.println("Introduce la posición a eliminar (empezando por 1)");
                        int posicionAEliminar = Integer.parseInt(br.readLine());
                        out.writeInt(posicionAEliminar);

                        break;
                    case 3:
                        out.writeUTF("3");
                        ArrayList<Tortuga> listaTortugasRecibida;
                        listaTortugasRecibida = (ArrayList<Tortuga>) objectIntputStream.readObject();

                        for (Tortuga t : listaTortugasRecibida){
                            System.out.println(t.getNombre() + " " + t.getDorsal());
                        }

                        break;
                    case 4:
                        out.writeUTF("4");
                        System.out.println("La carrera ha empezado!");
                        System.out.println("El ganador es" + in.readUTF());
                        break;
                    case 5:
                        out.writeUTF("5");
                        System.out.println("Hasta pronto");
                        break;
                }
            }

            socket.close();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }


    public void showMenu() {
        String menu = "\nMenu Principal Turbo Turtles 9000\n\n"
                + "Selecciona una opción: \n\n"
                + "1. Añadir tortuga\n"
                + "2. Eliminar tortuga\n"
                + "3. Mostrar tortugas\n"
                + "4. Iniciar carrera\n"
                + "5. Salir\n";
        System.out.println(menu);
    }


}


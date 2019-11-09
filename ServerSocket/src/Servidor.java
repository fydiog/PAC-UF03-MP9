import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

    private static ArrayList<Tortuga> listaTortugas;
    private final int PORT = 6666;
    private ServerSocket serverSocket;
    private Socket socket;
    DataInputStream in;
    DataOutputStream out;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectIntputStream;
    PaqueteEnvio datos_recibidos;


    public Servidor() throws IOException {


        serverSocket = new ServerSocket(PORT);
        socket = new Socket();

    }

    public void startServer() throws IOException {

        System.out.println("Iniciando server....");
        System.out.println("Escuchando en el puerto " + serverSocket.getLocalPort());

        try {


            while (true) {
                System.out.println("Esperando conexión del cliente");
                socket = serverSocket.accept(); // El servidor se queda a la espera de que un cliente haga una petición

                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectIntputStream = new ObjectInputStream(socket.getInputStream());

                out.writeUTF("Petición recibida.");
                String opcionCliente = "";
                String nombre, dorsal;
                listaTortugas = new ArrayList<Tortuga>();


                while (opcionCliente != "5") {
                    opcionCliente = in.readUTF();

                    if (opcionCliente.equals("1")) {
                        datos_recibidos = (PaqueteEnvio) objectIntputStream.readObject();
                        nombre = datos_recibidos.getNombre();
                        dorsal = datos_recibidos.getDorsal();
                        Tortuga tortuga = new Tortuga();
                        tortuga.setNombre(nombre);
                        tortuga.setDorsal(dorsal);

                        listaTortugas.add(tortuga);

                        System.out.println("Añadida al servidor la tortuga de nombre " + tortuga.getNombre() + " y dorsal " + tortuga.getDorsal());

                    } else if (opcionCliente.equals("2")) {
                        int posicionAEliminar = in.readInt();
                        listaTortugas.remove(posicionAEliminar - 1);

                    } else if (opcionCliente.equals("3")) {
                        objectOutputStream.writeObject(listaTortugas);
                        System.out.println("-------------------------------\nLISTA DE TORTUGAS \n");
                        for (Tortuga t : listaTortugas){
                            System.out.println(t.getNombre() +" "+ t.getDorsal());
                        }
                        System.out.println("-------------------------------\n");

                    } else if (opcionCliente.equals("4")) {
                        Carrera carrera = new Carrera();
                        carrera.start();
                        out.writeUTF(carrera.getGanador());
                    } else if (opcionCliente.equals("5")) {
                        System.out.println("Terminando conexión");
                        break;
                    }
                }

                socket.close();
                //serverSocket.close();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() throws IOException {

        try {

            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Tortuga> getList() {
        return listaTortugas;
    }

}



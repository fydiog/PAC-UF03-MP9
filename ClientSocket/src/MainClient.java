import java.io.IOException;

public class MainClient {
    public static void main(String[] args) throws IOException {
        Cliente client = new Cliente();
        System.out.println("Iniciando cliente...");
        client.startClient();

    }
}

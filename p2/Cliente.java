import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost";
        final int PUERTO = 1024;

        try (Socket socket = new Socket(SERVIDOR, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor.");

            // Hilo para recibir mensajes del servidor
            Thread recibir = new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = entrada.readLine()) != null) {
                        System.out.println("Servidor: " + mensaje);
                    }
                } catch (IOException e) {
                    System.out.println("Desconectado del servidor.");
                }
            });
            recibir.start();

            // Enviar mensajes al servidor
            String mensaje;
            while ((mensaje = teclado.readLine()) != null) {
                salida.println(mensaje);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

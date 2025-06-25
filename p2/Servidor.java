import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        final int PUERTO = 1024;

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Esperando conexion...");
            Socket socket = servidor.accept();
            System.out.println("Cliente conectado: ");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            // Hilo para recibir mensajes del cliente
            Thread recibir = new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = entrada.readLine()) != null) {
                        System.out.println("Cliente: " + mensaje);
                    }
                } catch (IOException e) {
                    System.out.println("Cliente desconectado.");
                }
            });
            recibir.start();

            // Enviar mensajes al cliente
            String mensaje;
            while ((mensaje = teclado.readLine()) != null) {
                salida.println(mensaje);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * cliente que se conecta al servidor de la calculadora.
 * envia operaciones seleccionadas y muestra los resultados recibidos.
 * 
 * @author UzielTheCreator
 */

public class Cliente {
    public static void main(String[] args) {
        final String HOST = "127.0.0.1"; // dirección del servidor (localhost)
        final int PUERTO = 9000;         // puerto donde escucha el servidor
        String eleccion = "si";          // controla si se desea continuar

        // uso de try-with-resources: cierra todo automáticamente
        try (Socket sc = new Socket(HOST, PUERTO);
             DataInputStream in = new DataInputStream(sc.getInputStream());
             DataOutputStream out = new DataOutputStream(sc.getOutputStream());
             Scanner leer = new Scanner(System.in)) {

            // mientras el usuario quiera seguir haciendo operaciones
            while (eleccion.equals("si")) {
                System.out.println();
                System.out.println(in.readUTF()); // mostramos el menu recibido
                int opcion = leer.nextInt();      // leemos la opción del usuario
                out.writeInt(opcion);             // la enviamos al servidor
                leer.nextLine();                  // limpieza del buffer

                if (opcion >= 1 && opcion <= 4) {
                    // se solicitan los números
                    System.out.println(in.readUTF());
                    out.writeInt(leer.nextInt());
                    leer.nextLine();

                    System.out.println(in.readUTF());
                    out.writeInt(leer.nextInt());
                    leer.nextLine();

                    // leemos el estado ("okey" o "error")
                    String estado = in.readUTF();

                    if (estado.equals("error")) {
                        // si hubo error (como división entre 0)
                        System.out.println(in.readUTF());
                    } else {
                        // si fue correcto, mostramos el resultado
                        System.out.println("\nel resultado es: " + in.readInt() + "\n");
                    }
                } else {
                    // si se elige salir o una opción invalida
                    System.out.println(in.readUTF());
                }

                // preguntamos si desea seguir usando la calculadora
                System.out.println(in.readUTF());
                eleccion = leer.nextLine();
                out.writeUTF(eleccion);
            }

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    public static void main(String[] args) {

        final String HOST = "127.0.0.1";
        final int PUERTO = 9000;

        try (Socket sc = new Socket(HOST, PUERTO);

             DataInputStream in = new DataInputStream(sc.getInputStream());
             DataOutputStream out = new DataOutputStream(sc.getOutputStream());

             Scanner leer = new Scanner(System.in)) {

            System.out.println(in.readUTF());
            int opcion = leer.nextInt();
            out.writeInt(opcion);

            if (opcion >= 1 && opcion <= 4) {
                System.out.println(in.readUTF());
                out.writeInt(leer.nextInt());

                System.out.println(in.readUTF());
                out.writeInt(leer.nextInt());

                System.out.println("el resultado es: " + in.readInt());
            } else {
                System.out.println(in.readUTF());
            }

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

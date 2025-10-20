import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    public static void main(String[] args) {
        final int PUERTO = 9000;

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {

            while (true) {
                try (Socket sc = servidor.accept()) {
                    System.out.println("cliente conectado");

                    DataInputStream in = new DataInputStream(sc.getInputStream());
                    DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                    out.writeUTF("""
                        calculadora basica
                        ingresa tu operacion a elegir:
                        1.- suma
                        2.- resta
                        3.- multiplicación
                        4.- division
                        5.- salir
                        """);

                    int opcion = in.readInt();

                    switch (opcion) {
                        case 1 -> operar(in, out, '+');
                        case 2 -> operar(in, out, '-');
                        case 3 -> operar(in, out, '*');
                        case 4 -> operar(in, out, '/');
                        case 5 -> out.writeUTF("adiooooos");
                        default -> out.writeUTF("opcion invalida.");
                    }

                    System.out.println("cliente desconectado");
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void operar(DataInputStream in, DataOutputStream out, char op) throws IOException {
        out.writeUTF("ingresa el 1er numero:");
        int n1 = in.readInt();
        out.writeUTF("ingresa el 2do numero:");
        int n2 = in.readInt();

        int r = 0;

        switch (op) {
            case '+': r = n1 + n2; break;
            case '-': r = n1 - n2; break;
            case '*': r = n1 * n2; break;
            case '/':
                if (n2 == 0) {
                    out.writeUTF("no se puede dividir entre 0");
                    return;
                }
                r = n1 / n2;
                break;
        }

        out.writeInt(r);
    }
}

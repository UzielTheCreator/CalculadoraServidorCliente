import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * servidor de una calculadora básica en red.
 * espera conexiones de un cliente, recibe operaciones y devuelve los resultados.
 * soporta suma, resta, multiplicación y division.
 */
public class Servidor {
    public static void main(String[] args) {
        final int PUERTO = 9000;   // puerto en el que escucha el servidor
        String eleccion = "si";    // controla si el cliente desea continuar

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            // bucle infinito: acepta multiples clientes, uno a la vez
            while (true) {
                try (Socket sc = servidor.accept()) { // espera conexión
                    System.out.println("cliente conectado");

                    // Flujos de entrada y salida para comunicación binaria
                    DataInputStream in = new DataInputStream(sc.getInputStream());
                    DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                    // mientras el cliente quiera seguir haciendo calculos
                    while (eleccion.equals("si")) {
                        // enviamos el menu de opciones
                        out.writeUTF("""
                                calculadora basica
                                ingresa tu operacion a elegir:
                                1.- suma
                                2.- resta
                                3.- multiplicación
                                4.- division
                                5.- salir
                                """);

                        // recibimos la opción seleccionada por el cliente
                        int opcion = in.readInt();

                        // ejecutamos la operación correspondiente
                        switch (opcion) {
                            case 1 -> operar(in, out, '+');
                            case 2 -> operar(in, out, '-');
                            case 3 -> operar(in, out, '*');
                            case 4 -> operar(in, out, '/');
                            case 5 -> {
                                out.writeUTF("adiooooos");
                                eleccion = "no"; // sale del ciclo
                                continue;
                            }
                            default -> out.writeUTF("opcion invalida.");
                        }

                        // pregunta si el cliente quiere seguir calculando
                        out.writeUTF("quieres hacer otro calculo? (si/no): ");
                        eleccion = in.readUTF(); // recibimos respuesta
                        System.out.println("cliente eligio: " + eleccion);
                    }
                    System.out.println("Cliente desconectado");
                    eleccion = "si"; // reinicia para aceptar nuevo cliente
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * metodo auxiliar que ejecuta la operación matemática seleccionada.
     * 
     * @param in flujo de entrada para recibir los números
     * @param out flujo de salida para enviar los resultados
     * @param op operador (+, -, *, /)
     */
    public static void operar(DataInputStream in, DataOutputStream out, char op) throws IOException {
        out.writeUTF("ingresa el 1er numero:");
        int n1 = in.readInt();

        out.writeUTF("ingresa el 2do numero:");
        int n2 = in.readInt();

        // validación especial para la división entre cero
        if (op == '/' && n2 == 0) {
            out.writeUTF("error"); // indicador de error
            out.writeUTF("no se puede dividir entre 0");
            return;
        }

        // realizamos la operacion correspondiente
        int r = switch (op) {
            case '+' -> n1 + n2;
            case '-' -> n1 - n2;
            case '*' -> n1 * n2;
            case '/' -> n1 / n2;
            default -> 0;
        };

        // indicamos que la operación fue exitosa y enviamos el resultado
        out.writeUTF("okey");
        out.writeInt(r);
    }
}

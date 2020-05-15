import java.io.IOException;

public class driver {

    public static void main(String args[]) throws IOException {
        Client client = new Client();

        System.out.println("========================");
        System.out.println("======loaded graph======");
        System.out.println("========================\n\n\n");

        client.start();
    }
}

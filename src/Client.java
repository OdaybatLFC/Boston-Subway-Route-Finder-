import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {

    private BostonMetro metro;

    public Client() throws IOException {
        metro = new BostonMetro();
        metro.readFile();
    }

    private void findPath(String src, String dest){
        if(src.equals(dest)){
            System.out.println("You are already at your destination...");
            return;
        }
        ArrayList<AbstractMap.SimpleEntry<INode, Edge>> path = metro.findPath(src, dest);

        int stopCounter = 0;
        String initalStation = src;

        if(path !=null) {
            String currentLine = path.get(0).getValue().getLabel();
            System.out.println("Initial line: " + currentLine);
            for (int i = 0; i < path.size()-1 ; i++) {
//                System.out.println(path.get(i).getValue().getLabel());
                if (currentLine.equals(path.get(i).getValue().getLabel())) {
                    stopCounter++;
                    continue;
                }
                else if(stopCounter>1){
                    System.out.printf("Get on at Station: %s | for %d Stops to %s | on the %s line\n", initalStation, stopCounter, path.get(i-1).getKey().getName(), currentLine);
                    if(i+1< path.size()){

                        currentLine = path.get(i-1).getValue().getLabel();
                    }
                    stopCounter = 0;

                    initalStation = path.get(i-1).getKey().getName();
                }

                if (stopCounter == 0) {
//                    currentLine = path.get(i).getValue().getLabel();
                    stopCounter++;
                }
                System.out.printf("Get on at Station: %s | for %d Stops to %s | on the %s line\n", initalStation, stopCounter, path.get(i).getKey().getName(), currentLine);
                stopCounter = 0;
                currentLine = path.get(i).getValue().getLabel();
                initalStation = path.get(i).getKey().getName();

            }
            System.out.printf("Get on at Station: %s | for %d Stops to %s (Destination) | on the %s line\n", initalStation, stopCounter, path.get(path.size()-1).getKey().getName(), currentLine);
        }
        else{
            System.out.println("No route found.");
        }
    }

    public void start(){
        welcome();
        int option = menu();

        while(option!=0){

            switch(option){
                case 1:
                    findRouteByStation();
            }
            System.out.println(); //new line :)
            option = menu();
        }
        System.out.println(option);
    }

    private void findRouteByStation() {
        System.out.println(); //new line :)

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your start station");
        System.out.print("> ");
        String src = sc.nextLine();

        System.out.println(); //new line :)
        System.out.println("Enter your destination station");
        System.out.print("> ");
        String dest = sc.nextLine();

        System.out.println(); //new line :)
        findPath(src,dest);
    }

    //@nope
//    public void findRouteById(){
//        System.out.println(); //new line :)
//
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter your start station");
//        System.out.print("> ");
//        int src = sc.nextInt();
//
//        System.out.println(); //new line :)
//        System.out.println("Enter your destination station");
//        System.out.print("> ");
//        int dest = sc.nextInt();
//
//        System.out.println(); //new line :)
////        graph.
////        findPath(src,dest);
//    }



    private static int menu(){
        // Select from the following options
        // 1 - find a route by Station Name

        Scanner sc = new Scanner(System.in);

        System.out.println("\nSelect from the following options:");
        System.out.println(" 0 - to exit");
        System.out.println(" 1 - find a route by Station Name");

        System.out.println(); //new line :)
        System.out.print("> ");


        try{
            return sc.nextInt();
//            return sc.nextInt();
        }
        catch (InputMismatchException e){
            System.out.println("Please enter a number corresponding to the options available.\n");
            return 100; //using error code 100
        }


        //return returnOption;
    }

    private static void welcome(){
        System.out.println("Ｗｅｌｃｏｍｅ  ｔｏ  Ｂｏｓｔｏｎ  Ｍｅｔｒｏ");
    }
}

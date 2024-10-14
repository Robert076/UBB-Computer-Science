import Controller.Controller;
import Repository.Repo;
import Model.Flour;
import Model.IEnt;
import Model.Salt;
import Model.Sugar;

public class Main {
    public static void main(String[] args) {
        System.out.println("");

        Repo repo = new Repo(10);
        Controller controller = new Controller(repo);

        // Declare initial value

        Sugar s1 = new Sugar(500);
        Flour f2 = new Flour(200);
        Flour f3 = new Flour(250);
        Flour f4 = new Flour(200);
        Flour f5 = new Flour(230);
        Flour f6 = new Flour(330);

        IEnt entities1[] = { s1, f2, f3, f4, f5, f6 };

        // Write them in the controller

        for (IEnt entity : entities1) {
            controller.writeEntity(entity);
        }

        IEnt result[] = controller.getPricesAbove20();

        // Prices above 200 from the initial 6 values
        // Excepted output:
        // Sugar: 500
        // Flour: 250
        // Flour: 230
        // Flour: 330

        System.out.println("Prices above 200: ");
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i].getType() + ": " + result[i].Compute());
        }
        System.out.println("");

        // Add entities until repository is full. No exception should be thrown here

        Salt s7 = new Salt(205);
        Salt s8 = new Salt(150);
        Flour f9 = new Flour(200);
        Sugar s10 = new Sugar(300);

        IEnt entities2[] = { s7, s8, f9, s10 };

        for (IEnt entity : entities2) {
            controller.writeEntity(entity);
        }

        Salt s11 = new Salt(1);
        controller.writeEntity(s11); // will raise an exception in the repo and controller handles it

        // Read operation
        System.out.println(controller.readEntity(2));

        // Read operation that raises an exception
        controller.readEntity(11);

        result = controller.getPricesAbove20();
        System.out.println("Prices above 200: ");
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i].getType() + ": " + result[i].Compute());
        }
        System.out.println("");

        controller.deleteEntity(0);
        controller.deleteEntity(1);
        controller.deleteEntity(2);
        controller.deleteEntity(2);
        controller.deleteEntity(2);
        result = controller.getPricesAbove20();
        System.out.println("Prices above 200: ");
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i].getType() + ": " + result[i].Compute());
        }
        System.out.println("");

        // raises exception
        controller.deleteEntity(100);
        controller.deleteEntity(5);
    }
}

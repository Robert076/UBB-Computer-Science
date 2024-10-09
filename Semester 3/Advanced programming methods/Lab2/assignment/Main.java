import Controller.Controller;
import Repository.Repo;
import Model.Flour;

public class Main {
    public static void main(String[] args) {
        Repo repo = new Repo(1);
        Controller controller = new Controller(repo);

        Flour f2 = new Flour(200);

        controller.writeEntity(f2);

    }
}

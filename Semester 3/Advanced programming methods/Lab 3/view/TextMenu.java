package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import view.command.Command;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu() {
        this.commands = new HashMap<>();
    }

    public void addCommand(Command c) {
        this.commands.put(c.getKey(), c);
    }

    public void printMenu() {
        for (Command c : this.commands.values()) {
            String line = String.format("%4s : %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                this.printMenu();
                System.out.println("Input the option: ");
                String key = scanner.nextLine();
                Command c = commands.get(key);
                if (c == null) {
                    System.out.println("Invalid option");
                } else {
                    c.execute();
                }
            }
        }
    }
}

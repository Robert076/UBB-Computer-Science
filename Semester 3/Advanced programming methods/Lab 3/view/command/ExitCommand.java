package view.command;

public class ExitCommand extends Command {
    public ExitCommand(String _key, String _description) {
        super(_key, _description);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}

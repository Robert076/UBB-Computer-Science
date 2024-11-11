package view.command;

public abstract class Command {
    String key, description;

    public Command(String _key, String _description) {
        this.key = _key;
        this.description = _description;
    }

    public abstract void execute();

    public String getKey() {
        return this.key;
    }

    public String getDescription() {
        return this.description;
    }
}

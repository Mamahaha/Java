package tools.ssh_executor;

import java.util.UUID;

public class RunCommandException extends Exception{
    private static final String serialVersionUID = UUID.randomUUID().toString().replaceAll("-", "");

    public RunCommandException(String message) {
        super(message);
    }

    public RunCommandException(Exception e) {
        super(e);
    }

    public RunCommandException(String message, Exception e) {
        super(message, e);
    }
}

package tools.ssh_executor;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


import java.util.Properties;

public class StrategySession implements IRemoteSession {
    private static final int DEFAULT_TIMEOUT = 6000;

    private Session session = null;

    public String getChannel() {
        return "exec";
    }

    public Session createSession(String user, String password, String host, int port, int timeout) throws RunCommandException {
        JSch jsch = new JSch();

        try {
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setTimeout(timeout);
            session.connect();
        } catch (JSchException e) {
            System.out.println("StrategySession getSession Exception: " + e);
            throw new RunCommandException("Failed to get ssh session towards " + user + ":" + host + ":" + port + " Detail: "
                    + e.getMessage(), e);
        }
        return session;
    }

    public Session createSession(String user, String password, String host, int port) throws RunCommandException {
        return createSession(user, password, host, port, DEFAULT_TIMEOUT);
    }

    public void deleteSession() {
        if (session != null) {
            session.disconnect();
        }
    }

}

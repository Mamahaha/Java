package tools.ssh_executor;

import com.jcraft.jsch.Session;

public interface IRemoteSession {

    String getChannel();

    Session createSession(String user, String password, String host, int port, int timeout) throws RunCommandException;

    Session createSession(String user, String password, String host, int port) throws RunCommandException;

    void deleteSession();
}

package tools.ssh_executor;

import com.jcraft.jsch.Session;

public class SshTest {
    public static void main(String[] args) {
        String createStrategyCmd = "ls";
        IRemoteSession strategySession = new StrategySession();
        try {
            System.out.println("createStrategyOrder");
            Session session = strategySession.createSession("led", "bmc", "127.0.0.1", 22);
            RemoteExecutor executor = new RemoteExecutor(session, strategySession.getChannel());
            executor.runCmd(createStrategyCmd);
        } catch (Exception e) {
            System.out.println("createStrategyOrder error: " + e);
        } finally {
            strategySession.deleteSession();
        }
    }
}

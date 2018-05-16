package tools.ssh_executor;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RemoteExecutor {
    private static final int GET_CMD_RESULT_WAIT_INTERVAL = 100;

    private ChannelExec channelExec;
    private Session session;
    private String channel;

    public RemoteExecutor(Session session, String channel) {
        this.session = session;
        this.channel = channel;
    }

    public void runCmd(String cmd) throws RunCommandException{
        try {
            channelExec = (ChannelExec) session.openChannel(channel);
        } catch (JSchException e) {
            throw new RunCommandException("Failed to open exec session to " + session.getHost() + ":"
                    + session.getPort() + " when executing command", e);
        }

        channelExec.setCommand(cmd);
        channelExec.setInputStream(null);
        try {
            InputStream inputStreamInfo = channelExec.getInputStream();
            BufferedReader bufferedReaderInfo = new BufferedReader(new InputStreamReader(inputStreamInfo));
            InputStream inputStreamErr = channelExec.getErrStream();
            BufferedReader bufferedReaderErr = new BufferedReader(new InputStreamReader(inputStreamErr));

            channelExec.connect();
            getOutput(bufferedReaderInfo, bufferedReaderErr);
        } catch (IOException e) {
            throw new RunCommandException("Failed to get response from " + session.getHost() + ":"
                    + session.getPort() + " when executing command" + cmd, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RunCommandException(
                    "Thread was interrupted when executing command on "
                            + session.getHost() + ":" + session.getPort(), e);
        } catch (JSchException e) {
            throw new RunCommandException("Failed to connect to " + session.getHost() + ":"
                    + session.getPort() + " when executing command" + cmd, e);
        } finally {
            if (channelExec != null) {
                channelExec.disconnect();
            }
        }
    }

    private void getOutput(BufferedReader bufferedReaderInfo, BufferedReader bufferedReaderErr)
            throws IOException, InterruptedException {
        StringBuilder infoStr = new StringBuilder();
        StringBuilder errStr = new StringBuilder();
        String lineInfo;
        String lineErr;

        while (true) {
            while ((lineInfo = bufferedReaderInfo.readLine()) != null) {
                infoStr.append(lineInfo).append("\n");
            }

            while ((lineErr = bufferedReaderErr.readLine()) != null) {
                errStr.append(lineErr).append("\n");
            }

            if (channelExec.isClosed()) {
                if (infoStr.length() > 0) {
                    System.out.println("Run command completed with result: " + infoStr.toString());
                }
                break;
            }
            Thread.sleep(GET_CMD_RESULT_WAIT_INTERVAL);
        }
    }
}

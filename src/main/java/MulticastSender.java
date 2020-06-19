import java.io.IOException;
import java.net.*;

public class MulticastSender {
    private static DatagramSocket mcastSocket;
    private static byte[] payload;
    private static InetAddress inetAddress;
    private static int remotePort;

    public static void main(String[] args) throws IOException, InterruptedException {
        initCommandLineArguments(args);
        mcastSocket = new DatagramSocket();
        System.out.println("Sending...");
        while (true) {
            DatagramPacket packet = new DatagramPacket(payload, payload.length, inetAddress, remotePort);
            mcastSocket.send(packet);
            Thread.sleep(700); //TODO MAGIC NUMBER! REPLACE WITH CLA OR STATIC VAR
        }
    }

    private static void initCommandLineArguments(String[] args) throws UnknownHostException {
        if (args.length > 0) {
            for (int i = 0; i<args.length; i++) {
                switch (args[i]) {
                    case "-p":
                    case "-port": {
                        remotePort = Integer.parseInt(args[i+1]);
                        i++;
                    }
                    break;

                    case "-addr":
                    case "-ip": {
                        if (isValidIpAddr(args[i+1])) {
                            inetAddress = InetAddress.getByName(args[i+1]);
                        } else {
                            System.out.println("Invalid ip address " + args[i+1]);
                            System.exit(-1);
                        }
                        i++;
                    }
                    break;

                    case "-payload":{
                        payload = args[i+1].getBytes();
                        i++;
                    }
                    break;

                    default:
                        System.out.println("Invalid parameter");
                }
            }

            if (inetAddress == null || remotePort == 0) {
                System.out.println("Missing command line parameters");
                printHelp();
            }

            if (payload == null) {
                payload = "Message".getBytes();
            }
        } else {
            printHelp();
        }
    }

    private static void printHelp() {
        //TODO Print help here
        System.exit(0);
    }

    private static boolean isValidIpAddr(String ipAddr) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ipAddr.matches(PATTERN);
    }
}
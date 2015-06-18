package httpService;

import com.nct.framework.common.LogUtil;
import java.io.File;
import org.apache.log4j.Logger;

public class ServiceDaemon {
    private static final Logger logger = LogUtil.getLogger(ServiceDaemon.class);

    public static void main(String[] args) throws Exception {
        WebServer webserver = new WebServer();

        String pidFile = System.getProperty("pidfile");

        try {
            logger.info("API_Services_Server start...");

            if (pidFile != null) {
                new File(pidFile).deleteOnExit();
            }
            if (System.getProperty("foreground") == null) {              
                System.out.close();
                System.err.close();               
            }
            logger.info("webserver start");
            webserver.start();

        } catch (Throwable e) {
            System.out.println(e.getMessage());
            String msg = "Exception encountered during startup.";

            // try to warn user on stdout too, if we haven't already detached
            System.out.println(msg);

            System.exit(3);
        }
    }
}

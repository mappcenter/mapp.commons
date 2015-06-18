package httpService;

import com.nct.framework.common.LogUtil;
import config.ConfigInfo;
import databaseUtils.DatabaseAccess;
import java.lang.management.ManagementFactory;
import org.apache.log4j.Logger;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RewriteRegexRule;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler;

public class WebServer {

    private static final Logger logger = LogUtil.getLogger(WebServer.class);

    public void start() throws Exception {
        try {

            QueuedThreadPool threadPool = new QueuedThreadPool();

            threadPool.setMinThreads(ConfigInfo.MIN_THREAD);
            threadPool.setMaxThreads(ConfigInfo.MAX_THREAD);

            Server server = new Server(threadPool);

            //// Setup JMX
            MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());

            server.addBean(mbContainer);
            server.addBean(new ScheduledExecutorScheduler());

            HttpConfiguration httpConfig = new HttpConfiguration();
            httpConfig.setOutputBufferSize(32768);
            httpConfig.setRequestHeaderSize(8192);
            httpConfig.setResponseHeaderSize(8192);
            httpConfig.setSendServerVersion(true);
            httpConfig.setSendDateHeader(true);

            ServerConnector connector = new ServerConnector(server, new ConnectionFactory[]{new HttpConnectionFactory(httpConfig)});
            connector.setHost(ConfigInfo.HOST_LISTEN);
            connector.setPort(Integer.valueOf(ConfigInfo.PORT_LISTEN));

            server.setConnectors(new Connector[]{connector});

            // URL Rewrite handler
            RewriteHandler rewriteHandler = new RewriteHandler();
            rewriteHandler.setRewriteRequestURI(true);
            rewriteHandler.setRewritePathInfo(true);
            rewriteHandler.setOriginalPathAttribute("requestedPath");

            RewriteRegexRule anime = new RewriteRegexRule();
//            rewriteRegexRule_shorten_category_params.setRegex("^/v([0-9.]{1,})/([a-zA-Z0-9._-]*)(/([a-zA-Z0-9._-]*)/(.*))?$");
//            rewriteRegexRule_shorten_category_params.setReplacement("/$2/t?v=$1&item=$4&data=$5");

            anime.setRegex("/api/anime");
            anime.setReplacement("/api/anime?t=anime");
            rewriteHandler.addRule(anime);

            RewriteRegexRule cartoon = new RewriteRegexRule();
            cartoon.setRegex("/api/cartoon");
            cartoon.setReplacement("/api/anime?t=cartoon");
            rewriteHandler.addRule(cartoon);

            //set resource dir   
            ContextHandler capHandler = new ContextHandler();
            capHandler.setContextPath("/");
            ResourceHandler resHandler = new ResourceHandler();
            resHandler.setBaseResource(Resource.newClassPathResource("./"));
            capHandler.setHandler(resHandler);

            //// Servlet Handlers
            ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
//          // Ajax Modules API
//            handler.addServlet("webServlet.ajax.AddOn", "/ajax/addon");
//            handler.addServlet("webServlet.ajax.Stream", "/ajax/stream");
//            handler.addServlet("webServlet.ajax.Mapping", "/ajax/mapping");
//            handler.addServlet("web.api.Anime", "/api/box");
//            handler.addServlet("web.api.Anime", "/api/anime");
//            handler.addServlet("web.api.Anime", "/api/cartoon");
//            handler.addServlet("web.api.GetConfig", "/api/config");
//            handler.addServlet("web.api.ErrorReport", "/api/feedback");
//            handler.addServlet("web.api.UnSubcribe", "/api/unsubcribe");
//            handler.addServlet("web.api.Test", "/test");

//            handler.addServlet("webServlet.ajax.GetPicasaContent", "/getdata");
            // Common Pages
            handler.addServlet("webServlet.controller.MainController", "");
            handler.addServlet("webServlet.controller.MainController", "/test");
            handler.addServlet("webServlet.controller.HoatHinhHD_Controller", "/hoathinhhd");

            // Set handlers
            HandlerCollection handlers = new HandlerCollection();
            handlers.addHandler(rewriteHandler);
            handlers.addHandler(handler);
            handlers.addHandler(capHandler);
            server.setHandler(handlers);

            server.setStopAtShutdown(true);
            DatabaseAccess.getInstance();

            server.start();
            server.join();
            System.out.println("Webserver start success");
        } catch (Exception ex) {
            logger.info("webserver start error: " + ex.getMessage());
            System.out.println(LogUtil.stackTrace(ex));
        }
    }
}
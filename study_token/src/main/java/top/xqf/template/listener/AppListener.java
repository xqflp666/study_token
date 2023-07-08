package top.huhuiyu.servlet.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 应用监听器
 *
 * @author 胡辉煜
 */
@WebListener("AppListener")
public class AppListener implements ServletContextListener {
  private static Logger logger = LoggerFactory.getLogger(AppListener.class);

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    logger.info("应用程序销毁...");
  }

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    logger.info("应用程序启动...");
  }

}

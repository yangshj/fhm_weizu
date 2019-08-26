package com.fh.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fh.util.Const;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * 
* 类名称：WebAppContextListener.java
* 类描述： 
* 作者：FH 
* 联系方式：
* @version 1.0
 */
public class WebAppContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("************** Shutting down! **************");
		System.out.println("Destroying Context...");
		System.out.println("Calling MySQL AbandonedConnectionCleanupThread checkedShutdown");
		AbandonedConnectionCleanupThread.checkedShutdown();
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			if (driver.getClass().getClassLoader() == cl) {
				try {
					System.out.println("Deregistering JDBC driver {}");
					DriverManager.deregisterDriver(driver);
				} catch (SQLException ex) {
					System.out.println("Error deregistering JDBC driver {}");
					ex.printStackTrace();
				}
			} else {
				System.out.println("Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader");
			}
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		System.out.println("========获取Spring WebApplicationContext开始");
		Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		System.out.println("========获取Spring WebApplicationContext结束");
	}

}

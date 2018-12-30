package com.mfcc.beer_shop.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author stefansebii@gmail.com
 */
@Component
public class JDBCUtils {

    private Logger logger = LoggerFactory.getLogger(JDBCUtils.class);

    @Value("${storage.first.db.url}")
    private String firstDbUrl;

    @Value("${storage.first.db.username}")
    private String firstDbUsername;

    @Value("${storage.first.db.password}")
    private String firstDbPassword;

    @Value("${storage.second.db.url}")
    private String secondDbUrl;

    @Value("${storage.second.db.username}")
    private String secondDbUsername;

    @Value("${storage.second.db.password}")
    private String secondDbPassword;

    private ThreadLocal<Connection> firstConnection = ThreadLocal.withInitial(this::getConnectionToFirstDb);
    private ThreadLocal<Connection> secondConnection = ThreadLocal.withInitial(this::getConnectionToSecondDb);

    public Connection getConnectionToBeerDb() {
        return firstConnection.get();
    }

    public Connection getConnectionToReceiptDb() {
        return secondConnection.get();
    }

    private Connection getConnectionToFirstDb() {
        logger.debug("Initializing connection to first database");
        Properties properties = new Properties();
        properties.setProperty("user", firstDbUsername);
        properties.setProperty("password", firstDbPassword);
        try {
            return DriverManager.getConnection(firstDbUrl, properties);
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            return null;
        }
    }

    private Connection getConnectionToSecondDb() {
        logger.debug("Initializing connection to second database");
        Properties properties = new Properties();
        properties.setProperty("user", secondDbUsername);
        properties.setProperty("password", secondDbPassword);
        try {
            return DriverManager.getConnection(secondDbUrl, properties);
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            return null;
        }
    }

}

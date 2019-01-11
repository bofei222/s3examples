package com.bf.io;

/**
 * @author bofei
 * @date 2019/1/11 13:39
 */

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

public class TestConfiguration {

    public static void main(String[] args) {
        Configurations configs = new Configurations();
        try
        {
            Configuration config = configs.properties(new File("C:\\test\\database.properties"));
            // access configuration properties
            String dbHost = config.getString("database.host");

            int dbPort = config.getInt("database.port");
            String dbUser = config.getString("database.user");
            String dbPassword = config.getString("database.password", "secret");  // provide a default
            long dbTimeout = config.getLong("database.timeout");
            System.out.println(dbHost);
            System.out.println(dbPort);
            System.out.println(dbTimeout);
        }
        catch (ConfigurationException cex)
        {
            // Something went wrong
        }
    }

}


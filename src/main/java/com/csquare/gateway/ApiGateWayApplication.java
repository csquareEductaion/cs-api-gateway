package com.csquare.gateway;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.csquare.framework.util.SystemUtil;
import com.csquare.gateway.config.AppInitializer;


@SpringBootApplication
public class ApiGateWayApplication extends SpringBootServletInitializer {

    private static final Logger LOGGER = LogManager.getLogger(ApiGateWayApplication.class);

    public static void main(String[] args) {

        LOGGER.info("Starting CSquareApplication.main");
        SystemUtil.setProperty("CONTAINER", "SELF");
        SystemUtil.setEnv(SystemUtil.SystemKey.CS_APPCONFIG_PATH, "G:/CsquareProject/backend/backend/cs-app-config");
        // SystemUtil.setEnv(SystemUtil.SystemKey.CS_APPCONFIG_PATH, "s3://cs-app-config");
        SpringApplicationBuilder application = new SpringApplicationBuilder();
        application.logStartupInfo(false);
        application.bannerMode(Banner.Mode.OFF);
        application.sources(ApiGateWayApplication.class, AppInitializer.class);
        application.run(args);

        LOGGER.info("Completing CSquareApplication.main");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        LOGGER.info("Starting CSquareApplication.configure");
        SystemUtil.setProperty("CONTAINER", "SERVER");

        application.logStartupInfo(false);
        application.bannerMode(Banner.Mode.OFF);
        application.sources(ApiGateWayApplication.class, AppInitializer.class);

        LOGGER.info("Completing CSquareApplication.configure");
        return application;
    }
}

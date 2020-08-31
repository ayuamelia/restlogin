package com.ayu.restservice;

import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@ComponentScan(basePackages={"com.ayu.restservice"})
public class RestserviceApplication extends SpringBootServletInitializer{
	
	private static final Logger LOGGER = LogManager.getLogger(RestserviceApplication.class);
 
    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(RestserviceApplication.class, args);
        
//        System.err.println("Initialisied Logger");
//        LOGGER.info("Info level log message");
//        LOGGER.debug("Debug level log message");
//        LOGGER.error("Error level log message");
//        System.err.println("END");
        
     // Creating PrintStream Object
        PrintStream print = new PrintStream(System.out);
        print.println("Hello World!");
         
        // Flushing the Stream
        print.flush();
    }
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	      return application.sources(RestserviceApplication.class);
	   }

//	public static void main(String[] args) {
//		SpringApplication.run(RestserviceApplication.class, args);
//	}

}

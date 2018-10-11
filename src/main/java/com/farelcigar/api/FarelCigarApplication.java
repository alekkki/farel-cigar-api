package com.farelcigar.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class FarelCigarApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FarelCigarApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FarelCigarApplication.class, args);
    }
}

/*@SpringBootApplication
public class FarelCigarApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarelCigarApplication.class, args);
    }
}*/


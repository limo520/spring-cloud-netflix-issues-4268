package com.example.demo;

import com.netflix.discovery.Jersey3DiscoveryClientOptionalArgs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.configuration.SSLContextFactory;
import org.springframework.cloud.configuration.TlsProperties;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.security.GeneralSecurityException;

@SpringBootApplication
@EnableEurekaServer
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     *  If we comment this bean out , eurekaserver will fail to register itself or send heartbeat.
     */
    @Bean
    public Jersey3DiscoveryClientOptionalArgs jersey3DiscoveryClientOptionalArgs(TlsProperties tlsProperties)
            throws GeneralSecurityException, IOException {
        Jersey3DiscoveryClientOptionalArgs optionalArgs = new Jersey3DiscoveryClientOptionalArgs();
        //The follow is optional because of DiscoveryClientOptionalArgsConfiguration$DiscoveryClientOptionalArgsTlsConfiguration
        if (tlsProperties.isEnabled()) {
            SSLContextFactory factory = new SSLContextFactory(tlsProperties);
            optionalArgs.setSSLContext(factory.createSSLContext());
        }
        return optionalArgs;
    }
}

package com.rudysysu.sample.metrics.config;


import io.prometheus.client.hotspot.DefaultExports;
import io.prometheus.client.spring.web.EnablePrometheusTiming;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@EnablePrometheusTiming
public class PrometheusConfig {
    private static Logger LOGGER = Logger.getLogger(PrometheusConfig.class);

    @PostConstruct
    public void initialize() {
        LOGGER.info("Prometheus init...");
        DefaultExports.initialize();
        LOGGER.info("Prometheus has been initialized...");
    }
}

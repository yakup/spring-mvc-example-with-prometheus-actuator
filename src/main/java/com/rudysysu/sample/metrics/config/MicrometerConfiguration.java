package com.rudysysu.sample.metrics.config;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfiguration {
    @Bean
    public PrometheusMeterRegistry prometheusMeterRegistry() {
        CollectorRegistry registry = CollectorRegistry.defaultRegistry;
        PrometheusMeterRegistry prometheusMeterRegistry =
                new PrometheusMeterRegistry(PrometheusConfig.DEFAULT, registry, Clock.SYSTEM);

        new ClassLoaderMetrics().bindTo(prometheusMeterRegistry);
        new JvmMemoryMetrics().bindTo(prometheusMeterRegistry);
        new JvmGcMetrics().bindTo(prometheusMeterRegistry);
        new ProcessorMetrics().bindTo(prometheusMeterRegistry);
        new JvmThreadMetrics().bindTo(prometheusMeterRegistry);
        return prometheusMeterRegistry;
    }

    @Bean
    public TimedAspect timedAspect() {
        return new TimedAspect(prometheusMeterRegistry());
    }

    @Bean
    public CountedAspect countedAspect() {
        return new CountedAspect(prometheusMeterRegistry());
    }
}

package com.github.anthonych.springbundle;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheck;
import com.github.anthonych.springbundle.configuration.SpringBundleConfiguration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.servlets.tasks.Task;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.util.component.LifeCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.ws.rs.Path;
import java.util.Map;

/**
 * SpringBundle for Dropwizard
 *
 * Created by anthonychen on 10/8/14.
 */
public class SpringBundle<T extends SpringBundleConfiguration> implements ConfiguredBundle<T> {

    private static final Logger logger = LoggerFactory.getLogger(SpringBundle.class);
    private GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext();

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        applicationContext.getBeanFactory().registerResolvableDependency(MetricRegistry.class, bootstrap.getMetricRegistry());
    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        logger.info("Start Spring Bundle");
        initSpringConfiguration(configuration, environment);

        // Register Spring Beans to Dropwizard
        registerResources(environment);
        registerHealthChecks(environment);
        registerLifecycle(environment);
        registerManaged(environment);
        registerTasks(environment);
        registerContextAsManaged(environment);
    }

    /**
     * Initialize Spring application context with Dropwizard configurations.
     *
     * @param configuration
     * @param environment
     */
    private void initSpringConfiguration(T configuration, Environment environment) {
        for (String config : configuration.getSpringConfiguration().getApplicationContext()) {
            applicationContext.load(config);
        }

        applicationContext.refresh();
    }

    /**
     *  Register Resource classes (which annotated with @Path) into Dropwizard context.
     *
     * @param environment
     */
    private void registerResources(Environment environment) {
        final Map<String, Object> resources = applicationContext.getBeansWithAnnotation(Path.class);
        for (String beanName : resources.keySet()) {
            Object resource = resources.get(beanName);
            environment.jersey().register(resource);
        }
    }

    /**
     * Register Managed classes into Dropwizard context.
     *
     * @param environment
     */
    private void registerManaged(Environment environment) {
        final Map<String, Managed> beansOfType = applicationContext.getBeansOfType(Managed.class);
        for (String beanName : beansOfType.keySet()) {
            Managed managed = beansOfType.get(beanName);
            environment.lifecycle().manage(managed);
            logger.info("Registering managed: " + managed.getClass().getName());
        }
    }

    /**
     * Register LifeCycle classes into Dropwizard context.
     *
     * @param environment
     */
    private void registerLifecycle(Environment environment) {
        Map<String, LifeCycle> beansOfType = applicationContext.getBeansOfType(LifeCycle.class);
        for (String beanName : beansOfType.keySet()) {
            LifeCycle lifeCycle = beansOfType.get(beanName);
            environment.lifecycle().manage(lifeCycle);
            logger.info("Registering lifeCycle: " + lifeCycle.getClass().getName());
        }
    }

    /**
     * Register Task classes into Dropwizard context.
     *
     * @param environment
     */
    private void registerTasks(Environment environment) {
        final Map<String, Task> beansOfType = applicationContext.getBeansOfType(Task.class);
        for (String beanName : beansOfType.keySet()) {
            Task task = beansOfType.get(beanName);
            environment.admin().addTask(task);
            logger.info("Registering task: " + task.getClass().getName());
        }
    }

    /**
     * Register HealthCheck classes into Dropwizard context.
     *
     * @param environment
     */
    private void registerHealthChecks(Environment environment) {
        final Map<String, HealthCheck> beansOfType = applicationContext.getBeansOfType(HealthCheck.class);
        for (Map.Entry<String, HealthCheck> entry : beansOfType.entrySet()) {
            environment.healthChecks().register(entry.getKey(), entry.getValue());
            logger.info("Registering healthCheck: " + entry.getValue().getClass().getName());
        }
    }

    /**
     * Register application context as Dropwizard Managed.
     * @param environment
     */
    private void registerContextAsManaged(Environment environment) {
        environment.lifecycle().manage(new Managed() {
            @Override
            public void start() throws Exception {
            }

            @Override
            public void stop() throws Exception {
                applicationContext.close();
            }
        });
    }

    public GenericXmlApplicationContext getApplicationContext() {
        return applicationContext;
    }
}

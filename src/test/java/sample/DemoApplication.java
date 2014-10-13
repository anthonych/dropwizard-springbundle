package sample;

import com.github.anthonych.springbundle.SpringBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.conf.DemoConfiguration;

/**
 * Created by anthonychen on 10/8/14.
 */
public class DemoApplication extends Application<DemoConfiguration> {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws Exception {
        new DemoApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<DemoConfiguration> bootstrap) {
        logger.info("### Initialize Demo Application ###");
        bootstrap.addBundle(new SpringBundle<DemoConfiguration>());
    }

    @Override
    public void run(DemoConfiguration configuration, Environment environment) throws Exception {
        logger.info("### Run Demo Application ###");

    }

}

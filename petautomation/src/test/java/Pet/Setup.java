package Pet;

import configutartion.Config;
import org.testng.annotations.BeforeSuite;

public class Setup {

    @BeforeSuite
    public void beforeSuite() {
        Config.getConfig().initialize();
    }
}

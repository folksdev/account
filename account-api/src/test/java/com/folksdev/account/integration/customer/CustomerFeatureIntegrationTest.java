package com.folksdev.account.integration.customer;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/customer",
        plugin = {"pretty", "html:target/cucumber/customer-report.html"},
        glue = {"com.folksdev.account.integration"} )
public class CustomerFeatureIntegrationTest {
}

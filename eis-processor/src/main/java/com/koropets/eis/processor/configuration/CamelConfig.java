package com.koropets.eis.processor.configuration;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

    @Autowired
    private CamelContext camelContext;
}

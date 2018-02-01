package com.nc.kpi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.nc.kpi.persistence"})
public class AppConfig {
}

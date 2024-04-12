package com.ProjX.projxpersitance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.ProjX.projxpersitance.repository")
@EnableTransactionManagement
public class PersistenceConfig {

}
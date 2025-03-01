package com.hayley.spring_batch.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackages = ["com.hayley.spring_batch.config"])
class ConfigurablePropertyConfig

@ConfigurationProperties(prefix = "spring.datasource-meta")
data class DataSourceMetaProperties(
    val driverClassName: String,
    val jdbcUrl: String,
    val userName: String,
    val password: String,
)

@ConfigurationProperties(prefix = "spring.datasource-data")
data class DataSourceDataProperties(
    val driverClassName: String,
    val jdbcUrl: String,
    val userName: String,
    val password: String,
)
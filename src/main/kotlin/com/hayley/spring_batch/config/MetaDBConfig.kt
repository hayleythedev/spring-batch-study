package com.hayley.spring_batch.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource


@Configuration
class MetaDBConfig {
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-meta")
    fun metaDBSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Primary
    @Bean
    fun metaTransactionManager(): PlatformTransactionManager {
        return DataSourceTransactionManager(metaDBSource())
    }
}
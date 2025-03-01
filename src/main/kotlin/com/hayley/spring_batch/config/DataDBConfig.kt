package com.hayley.spring_batch.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource


@Configuration
@EnableJpaRepositories(
    basePackages = ["com.hayley.spring_batch.repository"],
    entityManagerFactoryRef = "dataEntityManager",
    transactionManagerRef = "dataTransactionManager"
)
class DataDBConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-data")
    fun dataDBSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean
    fun dataEntityManager(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()

        em.dataSource = dataDBSource()
        em.setPackagesToScan(*arrayOf("com.hayley.spring_batch.entity"))
        em.jpaVendorAdapter = HibernateJpaVendorAdapter()

        val properties = HashMap<String, Any?>()
        properties["hibernate.hbm2ddl.auto"] = "update"
        properties["hibernate.show_sql"] = "true"
        em.setJpaPropertyMap(properties)

        return em
    }

    @Bean
    fun dataTransactionManager(): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()

        transactionManager.entityManagerFactory = dataEntityManager().getObject()

        return transactionManager
    }
}
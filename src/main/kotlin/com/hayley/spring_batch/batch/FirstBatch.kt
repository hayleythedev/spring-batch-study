package com.hayley.spring_batch.batch

import com.hayley.spring_batch.entity.User
import com.hayley.spring_batch.entity.UserNew
import com.hayley.spring_batch.repository.UserNewRepository
import com.hayley.spring_batch.repository.UserRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.data.RepositoryItemReader
import org.springframework.batch.item.data.RepositoryItemWriter
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Sort
import org.springframework.transaction.PlatformTransactionManager


@Configuration
class FirstBatch(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
    private val userRepository: UserRepository,
    private val userNewRepository: UserNewRepository,
) {
    companion object {
        const val CHUNK_SIZE = 10
    }

    @Bean
    fun firstJob(): Job {
        println("first job")

        return JobBuilder("firstJob", jobRepository)
            .start(firstStep())
            .build()
    }

    @Bean
    fun firstStep(): Step {
        println("first step")

        return StepBuilder("firstStep", jobRepository)
            .chunk<User, UserNew>(10, platformTransactionManager)
            .reader(userReader())
            .processor(itemProcessor())
            .writer(userNewWriter())
            .build()
    }

    @Bean
    @StepScope
    fun userReader(): RepositoryItemReader<User> {
        return RepositoryItemReaderBuilder<User>()
            .name("userReader")
            .pageSize(CHUNK_SIZE)
            .methodName("findAll")
            .repository(userRepository)
            .sorts(mapOf("id" to Sort.Direction.ASC))
            .build()
    }

    @Bean
    @StepScope
    fun itemProcessor(): ItemProcessor<User, UserNew> {
        return ItemProcessor { item ->
            UserNew(item.name)
        }
    }

    @Bean
    @StepScope
    fun userNewWriter(): RepositoryItemWriter<UserNew> {
        return RepositoryItemWriterBuilder<UserNew>()
            .repository(userNewRepository)
            .methodName("save")
            .build()
    }
}
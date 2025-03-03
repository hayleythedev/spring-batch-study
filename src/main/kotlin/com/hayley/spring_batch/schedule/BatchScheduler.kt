package com.hayley.spring_batch.schedule

import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class BatchScheduler(
    private val jobLauncher: JobLauncher,
    private val jobRegistry: JobRegistry,
) {
    @Scheduled(cron = "10 * * * * *", zone = "Asia/Seoul")
    fun runFirstJob() {
        println("RunFirstJob scheduler starting...")

        val jobParameterMap = mapOf(
            "date" to JobParameter(OffsetDateTime.now().toString(), String::class.java)
        )
        val jobParameters = JobParameters(jobParameterMap)
        val job = jobRegistry.getJob("firstJob")
        jobLauncher.run(job, jobParameters)
    }
}
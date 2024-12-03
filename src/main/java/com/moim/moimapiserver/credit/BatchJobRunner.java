package com.moim.moimapiserver.credit;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BatchJobRunner implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job downGradeMember;

    public BatchJobRunner(JobLauncher jobLauncher, Job downGradeMember) {
        this.jobLauncher = jobLauncher;
        this.downGradeMember = downGradeMember;
    }

    @Override
    public void run(String... args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()) // 유니크한 파라미터 추가
                .toJobParameters();

        jobLauncher.run(downGradeMember, jobParameters);
        System.out.println("Batch job has been executed at application startup.");
    }
}

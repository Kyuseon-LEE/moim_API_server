package com.moim.moimapiserver.credit;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Log4j2
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job downGradeMember;

    public BatchScheduler(JobLauncher jobLauncher, Job downGradeMember) {
        this.jobLauncher = jobLauncher;
        this.downGradeMember = downGradeMember;
    }

    @Scheduled(cron = "0 40 17 * * ?") //실행 시간
    public void runJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // 유니크 파라미터
                    .addString("jobName", "downGradeMemberJob")   // 추가 식별자
                    .toJobParameters();

            jobLauncher.run(downGradeMember, jobParameters);

        } catch (Exception e) {
            log.error("Batch job downGradeMember failed: {}", e.getMessage(), e);
        }
    }
}

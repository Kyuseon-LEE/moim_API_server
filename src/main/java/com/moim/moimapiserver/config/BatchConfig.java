package com.moim.moimapiserver.config;
import com.moim.moimapiserver.credit.CreditDto;
import com.moim.moimapiserver.credit.ExpiredPaymentReader;
import com.moim.moimapiserver.credit.ExpiredPaymentWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BatchConfig {

    private final ExpiredPaymentReader expiredPaymentReader;
    private final ExpiredPaymentWriter expiredPaymentWriter;

    @Bean
    public Job downGradeMember(PlatformTransactionManager transactionManager, JobRepository jobRepository) {
        return new JobBuilder("downGradeMember", jobRepository)
                .start(expiredPatmentStep(transactionManager, jobRepository))
                .build();
    }

    @Bean
    public Step expiredPatmentStep(PlatformTransactionManager transactionManager, JobRepository jobRepository) {
        return new StepBuilder("testSimpleStep", jobRepository)
                .tasklet(testTasklet(), transactionManager)
                .build();

    }

    public Tasklet testTasklet() {
        return (contribution, chunkContext) -> {
            log.info("start expired payment processing task");

            List<CreditDto> expiredMembers = new ArrayList<>();
            CreditDto member;
            do {
                member = expiredPaymentReader.read();
                if (member != null) {
                    expiredMembers.add(member);
                }
            } while (member != null);

            log.info("검색된 만료된 회원" + expiredMembers.size());

            if(!expiredMembers.isEmpty()) {
                try {

                    expiredPaymentWriter.write(expiredMembers);
                    log.info("만료된 회원을 업데이트 했습니다.");

                } catch (Exception e) {
                    log.info("회원기간이 만료된 사람이 없습니다.");
                }
            }

            return RepeatStatus.FINISHED;
        };
    }
}

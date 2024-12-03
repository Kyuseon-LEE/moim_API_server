//package com.moim.moimapiserver.credit;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//@EnableScheduling
//public class BatchScheduler {
//
//    private final JobLauncher jobLauncher;
//    private final Job downGradeMember;
//
//    public BatchScheduler(JobLauncher jobLauncher, Job downGradeMember) {
//        this.jobLauncher = jobLauncher;
//        this.downGradeMember = downGradeMember;
//    }
//
//    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정 실행
//    public void runJob() {
//        try {
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addLong("time", System.currentTimeMillis()) // Job의 유니크 파라미터 추가
//                    .toJobParameters();
//
//            jobLauncher.run(downGradeMember, jobParameters);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

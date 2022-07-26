package com.spring.batch.log.processor.config;

import com.spring.batch.log.processor.entity.Event;
import com.spring.batch.log.processor.util.PerformanceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@EnableBatchProcessing
@Configuration
public class SpringBatchConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBatchConfig.class);
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private long startTime;

    public JsonItemReader<Event> jsonItemReader() {
        LOGGER.info("## Completed writing logs to database in {} milli seconds  ",PerformanceUtil.getTimeDiff(startTime,System.currentTimeMillis(),1));
        return new JsonItemReaderBuilder<Event>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Event.class))
                .resource(new ClassPathResource("data.json"))
                .name("EventJsonItemReader")
                .saveState(true)
                .build();

    }



    @Bean
    public EventItemProcessor processor() {
        return new EventItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Event> writer() {
        JdbcBatchItemWriter<Event> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("insert into EVENT(id,host,type,duration,alert) values (:id,:host,:type,:timestamp,:alert)");
        writer.setDataSource(dataSource);
        startTime = System.currentTimeMillis();
        return writer;
    }


    @Bean
    public Job writeEventDataIntoSqlDb() {
        JobBuilder jobBuilder = jobBuilderFactory.get("Event_JOB");
        jobBuilder.incrementer(new RunIdIncrementer());
        FlowJobBuilder flowJobBuilder = jobBuilder.flow(getFirstStep()).end();
        Job job = flowJobBuilder.build();
        return job;
    }

    @Bean
    public Step getFirstStep() {
        StepBuilder stepBuilder = stepBuilderFactory.get("getFirstStep");
        SimpleStepBuilder<Event, Event> simpleStepBuilder = stepBuilder.chunk(1);
        return simpleStepBuilder.reader(jsonItemReader()).processor(processor()).writer(writer()).build();
    }

}

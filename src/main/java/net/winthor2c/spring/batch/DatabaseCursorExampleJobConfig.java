package net.winthor2c.spring.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 * This configuration class configures the Spring Batch job that
 * is used to demonstrate that our item reader reads the correct
 * information from the database by using a database cursor.
 */
@Configuration
public class DatabaseCursorExampleJobConfig {

        private static final String QUERY_FIND_CLIENT = "SELECT * FROM PCCLIENT " +
                        "ORDER BY CODCLI ASC";

        @Bean
        public ItemReader<ClientDTO> databaseCursorItemReader(DataSource dataSource) {
                return new JdbcCursorItemReaderBuilder<ClientDTO>()
                                .name("cursorItemReader")
                                .dataSource(dataSource)
                                .sql(QUERY_FIND_CLIENT)
                                .rowMapper(new BeanPropertyRowMapper<>(ClientDTO.class))
                                .build();
        }

        @Bean
        public ItemWriter<ClientDTO> databaseCursorItemWriter() {
                return new LoggingItemWriter();
        }

        /**
         * Creates a bean that represents the only step of our batch job.
         * 
         * @param reader
         * @param writer
         * @param stepBuilderFactory
         * @return
         */
        @Bean
        public Step databaseCursorStep(@Qualifier("databaseCursorItemReader") ItemReader<ClientDTO> reader,
                        @Qualifier("databaseCursorItemWriter") ItemWriter<ClientDTO> writer,
                        StepBuilderFactory stepBuilderFactory) {
                return stepBuilderFactory.get("databaseCursorStep")
                                .<ClientDTO, ClientDTO>chunk(1)
                                .reader(reader)
                                .writer(writer)
                                .build();
        }

        /**
         * Creates a bean that represents our example batch job.
         * 
         * @param exampleJobStep
         * @param jobBuilderFactory
         * @return
         */
        @Bean
        public Job databaseCursorJob(@Qualifier("databaseCursorStep") Step exampleJobStep,
                        JobBuilderFactory jobBuilderFactory) {
                return jobBuilderFactory.get("databaseCursorJob")
                                .incrementer(new RunIdIncrementer())
                                .flow(exampleJobStep)
                                .end()
                                .build();
        }
}

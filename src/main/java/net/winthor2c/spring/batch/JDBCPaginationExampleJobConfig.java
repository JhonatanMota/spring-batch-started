package net.winthor2c.spring.batch;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 * This configuration class configures the Spring Batch job that
 * is used to demonstrate that our item reader reads the correct
 * information from the database by using JDBC pagination.
 */
@Configuration
public class JDBCPaginationExampleJobConfig {

    @Bean
    public ItemReader<ClientDTO> jdbcPaginationItemReader(DataSource dataSource, PagingQueryProvider queryProvider) {
        return new JdbcPagingItemReaderBuilder<ClientDTO>()
                .name("pagingItemReader")
                .dataSource(dataSource)
                .pageSize(1)
                .queryProvider(queryProvider)
                .rowMapper(new BeanPropertyRowMapper<>(ClientDTO.class))
                .build();
    }

    @Bean
    public SqlPagingQueryProviderFactoryBean queryProvider(DataSource dataSource) {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();

        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause(
                "SELECT CODCLI, CLIENTE, ENDERCOB, BAIRROCOB, TELCOB,MUNICCOB,ESTCOB,CEPCOB,ENDERENT,BAIRROENT,TELENT,MUNICENT,ESTENT,CEPENT,CGCENT");
        queryProvider.setFromClause("FROM PCCLIENT");
        queryProvider.setSortKeys(sortByCodCliAsc());

        return queryProvider;
    }

    private Map<String, Order> sortByCodCliAsc() {
        Map<String, Order> sortConfiguration = new HashMap<>();
        sortConfiguration.put("CODCLI", Order.ASCENDING);
        return sortConfiguration;
    }

    @Bean
    public ItemWriter<ClientDTO> jdbcPaginationItemWriter() {
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
    public Step jdbcPaginationStep(@Qualifier("jdbcPaginationItemReader") ItemReader<ClientDTO> reader,
            @Qualifier("jdbcPaginationItemWriter") ItemWriter<ClientDTO> writer,
            StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("jdbcPaginationStep")
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
    public Job jdbcPaginationJob(@Qualifier("jdbcPaginationStep") Step exampleJobStep,
            JobBuilderFactory jobBuilderFactory) {
        Job databaseCursorJob = jobBuilderFactory.get("jdbcPaginationJob")
                .incrementer(new RunIdIncrementer())
                .flow(exampleJobStep)
                .end()
                .build();
        return databaseCursorJob;
    }
}

package net.winthor2c.spring.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * This {@code ItemWriter} writes the received {@link ClientDTO} objects
 * to a log file. The goal of this component is to help us to demonstrate
 * that our item reader reads the correct information from the database.
 */
public class LoggingItemWriter implements ItemWriter<ClientDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingItemWriter.class);

    @Override
    public void write(List<? extends ClientDTO> list) throws Exception {
        LOGGER.info("Writing clients: {}", list);
    }
}

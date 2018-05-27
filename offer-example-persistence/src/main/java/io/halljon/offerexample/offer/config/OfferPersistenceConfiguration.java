package io.halljon.offerexample.offer.config;

import io.halljon.offerexample.offer.repository.OfferRepository;
import io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class OfferPersistenceConfiguration {
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(final DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public OfferRepository offerRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new OfferRepositoryJdbcImpl(namedParameterJdbcTemplate);
    }
}

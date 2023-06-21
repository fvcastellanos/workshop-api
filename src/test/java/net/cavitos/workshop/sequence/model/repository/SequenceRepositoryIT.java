package net.cavitos.workshop.sequence.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class SequenceRepositoryIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SequenceRepository sequenceRepository;

    @Test
    void testFindByPrefix() {

        jdbcTemplate.update("insert into sequence (prefix, value) values ('TEST', '123')");

        final var entity = sequenceRepository.findByPrefix("TEST")
                        .orElseThrow();

        Assertions.assertThat(entity)
            .isNotNull()
            .hasFieldOrPropertyWithValue("prefix", "TEST")
            .hasFieldOrPropertyWithValue("value", "123")
            .hasFieldOrProperty("id")
            .hasFieldOrProperty("updated");
    }
}

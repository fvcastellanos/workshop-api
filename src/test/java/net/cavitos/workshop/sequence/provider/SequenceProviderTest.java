package net.cavitos.workshop.sequence.provider;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import net.cavitos.workshop.sequence.domain.SequenceType;

@Transactional
@SpringBootTest
public class SequenceProviderTest {

    @Autowired
    private SequenceProvider sequenceProvider;

    @Test
    void testSequenceProviderNext() {

        final var sequence = sequenceProvider.calculateNext(SequenceType.CUSTOMER);

        Assertions.assertThat(sequence)
            .isEqualTo("C-00001");
    }
}

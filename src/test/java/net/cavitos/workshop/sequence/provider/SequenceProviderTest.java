package net.cavitos.workshop.sequence.provider;

import net.cavitos.workshop.domain.exception.BusinessException;
import net.cavitos.workshop.factory.BusinessExceptionFactory;
import net.cavitos.workshop.sequence.domain.SequenceType;
import net.cavitos.workshop.sequence.model.repository.SequenceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SequenceProviderTest {

    @Mock
    private SequenceRepository sequenceRepository;

    @InjectMocks
    private SequenceProvider sequenceProvider;

    @Test
    void testRetry() {

        final var sequenceType = SequenceType.CUSTOMER;

        when(sequenceRepository.findByPrefix(sequenceType.getPrefix()))
                .thenThrow(BusinessExceptionFactory.createBusinessException("Test Exception"));

        Assertions.assertThatThrownBy(() -> sequenceProvider.calculateNext(sequenceType))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Unable to generate next sequence value for prefix: C");

        // Expect 4 calls (1 original call plus 3 retries)
        verify(sequenceRepository, times(4))
                .findByPrefix(sequenceType.getPrefix());
    }
}

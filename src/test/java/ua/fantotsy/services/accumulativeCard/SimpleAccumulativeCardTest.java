package ua.fantotsy.services.accumulativeCard;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.fantotsy.domain.AccumulativeCard;
import ua.fantotsy.repository.accumulativeCard.AccumulativeCardRepository;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleAccumulativeCardTest {
    SimpleAccumulativeCardService accumulativeCardService;
    @Mock
    AccumulativeCardRepository accumulativeCardRepositoryMock;
    @Mock
    AccumulativeCard accumulativeCardMock;

    @Before
    public void setUp() {
        accumulativeCardService = spy(new SimpleAccumulativeCardService(accumulativeCardRepositoryMock));
        doReturn(accumulativeCardMock).when(accumulativeCardService).createNewAccumulativeCard();
    }

    @Test
    public void testAddAndReturnNewAccumulativeCardAddsCard() {
        accumulativeCardService.addAndReturnNewAccumulativeCard();
        verify(accumulativeCardRepositoryMock).save(accumulativeCardMock);
    }
}
package ua.fantotsy.repository.accumulativeCard;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.AccumulativeCard;
import ua.fantotsy.repository.RepositoryTestConfig;

import static org.junit.Assert.assertNotNull;

public class JpaAccumulativeCardRepositoryIT extends RepositoryTestConfig {
    @Autowired
    private AccumulativeCardRepository accumulativeCardRepository;

    @Test
    public void testFind() {

    }

    @Test
    public void testSavePizza() {
        AccumulativeCard accumulativeCard = new AccumulativeCard("1234567887654321");
        accumulativeCard = accumulativeCardRepository.save(accumulativeCard);
        assertNotNull(accumulativeCard.getId());
    }
}
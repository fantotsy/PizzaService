package ua.fantotsy.repository.accumulativeCard;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.AccumulativeCard;
import ua.fantotsy.repository.RepositoryTestConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JpaAccumulativeCardRepositoryIT extends RepositoryTestConfig {
    @Autowired
    private AccumulativeCardRepository accumulativeCardRepository;

    @After
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM accumulative_cards");
    }

    @Test
    public void testFindAccumulativeCardById() {
        jdbcTemplate.update("INSERT INTO accumulative_cards (id, number, balance) VALUES (1, 12345, 100.0)");
        AccumulativeCard accumulativeCard = accumulativeCardRepository.findById(1L);
        long id = accumulativeCard.getId();
        assertEquals(1L, id);
    }

    @Test
    public void testSaveAccumulativeCard() {
        AccumulativeCard accumulativeCard = new AccumulativeCard(12345L);
        accumulativeCard = accumulativeCardRepository.save(accumulativeCard);
        assertNotNull(accumulativeCard.getId());
    }
}
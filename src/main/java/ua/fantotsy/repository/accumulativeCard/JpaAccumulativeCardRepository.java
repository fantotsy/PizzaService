package ua.fantotsy.repository.accumulativeCard;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.fantotsy.domain.AccumulativeCard;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("accumulativeCardRepository")
public class JpaAccumulativeCardRepository implements AccumulativeCardRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AccumulativeCard findById(Long id) {
        return entityManager.find(AccumulativeCard.class, id);
    }

    @Override
    @Transactional
    public AccumulativeCard save(AccumulativeCard accumulativeCard) {
        return entityManager.merge(accumulativeCard);
    }
}
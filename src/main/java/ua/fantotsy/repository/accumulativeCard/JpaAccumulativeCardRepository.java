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
    public AccumulativeCard save(AccumulativeCard accumulativeCard) {
        Long maxCardNumber = getMaxCardNumber();
        if (maxCardNumber != null) {
            accumulativeCard.setNumber(maxCardNumber + 1);
        }
        return entityManager.merge(accumulativeCard);
    }

    @Override
    public Long getMaxCardNumber() {
        return entityManager.createNamedQuery("AccumulativeCard.getMaxCardNumber", Long.class).getSingleResult();
    }
}
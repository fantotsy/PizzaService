package ua.fantotsy.repository.accumulativeCard;

import org.springframework.stereotype.Repository;
import ua.fantotsy.domain.AccumulativeCard;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryAccumulativeCardRepository implements AccumulativeCardRepository {
    /*Fields*/
    private final List<AccumulativeCard> accumulativeCards;

    /*Constructors*/
    public InMemoryAccumulativeCardRepository() {
        accumulativeCards = new ArrayList<>();
    }

    /*Public Methods*/
    @Override
    public AccumulativeCard findById(Long id) {
        if (accumulativeCards.size() > 0) {
            for (AccumulativeCard accumulativeCard : accumulativeCards) {
                if (accumulativeCard.getAccumulativeCardId().equals(id)) {
                    return accumulativeCard;
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    public AccumulativeCard save(AccumulativeCard accumulativeCard) {
        accumulativeCard.setAccumulativeCardId(getNextId());
        accumulativeCards.add(accumulativeCard);
        return accumulativeCard;
    }

    @Override
    public Long getMaxCardNumber() {
        return null;
    }

    /*Private Methods*/
    private long getNextId() {
        return (accumulativeCards.size() + 1);
    }

}
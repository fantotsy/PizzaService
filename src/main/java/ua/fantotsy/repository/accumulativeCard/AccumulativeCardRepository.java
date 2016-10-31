package ua.fantotsy.repository.accumulativeCard;

import ua.fantotsy.domain.AccumulativeCard;

public interface AccumulativeCardRepository {

    AccumulativeCard findById(Long id);

    AccumulativeCard save(AccumulativeCard accumulativeCard);

    Long getMaxCardNumber();
}
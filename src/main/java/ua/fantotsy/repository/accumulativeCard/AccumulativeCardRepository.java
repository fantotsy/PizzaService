package ua.fantotsy.repository.accumulativeCard;

import ua.fantotsy.domain.AccumulativeCard;

public interface AccumulativeCardRepository {

    AccumulativeCard getAccumulativeCardById(long id);

    void addNewAccumulativeCard(AccumulativeCard accumulativeCard);
}
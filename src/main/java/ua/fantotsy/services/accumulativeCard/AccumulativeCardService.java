package ua.fantotsy.services.accumulativeCard;

import ua.fantotsy.domain.AccumulativeCard;

public interface AccumulativeCardService {

    AccumulativeCard findAccumulativeCardById(Long id);

    AccumulativeCard addNewAccumulativeCard();
}
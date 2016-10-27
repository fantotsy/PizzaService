package ua.fantotsy.services.accumulativeCard;

import ua.fantotsy.domain.AccumulativeCard;

public interface AccumulativeCardService {

    AccumulativeCard getAccumulativeCardById(Long id);

    AccumulativeCard addAndReturnNewAccumulativeCard();
}
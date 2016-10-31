package ua.fantotsy.services.accumulativeCard;

import ua.fantotsy.domain.AccumulativeCard;

public interface AccumulativeCardService {

    AccumulativeCard findById(Long id);

    AccumulativeCard addNewAccumulativeCard();

    Long getMaxCardNumber();
}
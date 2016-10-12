package ua.fantotsy.services.accumulativeCard;

import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.AccumulativeCard;
import ua.fantotsy.repository.accumulativeCard.AccumulativeCardRepository;

public class SimpleAccumulativeCardService implements AccumulativeCardService {
    /*Fields*/
    private AccumulativeCardRepository accumulativeCardRepository;

    /*Constructors*/
    @Autowired
    public SimpleAccumulativeCardService(AccumulativeCardRepository accumulativeCardRepository) {
        this.accumulativeCardRepository = accumulativeCardRepository;
    }

    /*Public Methods*/
    @Override
    public AccumulativeCard getAccumulativeCardById(long id) {
        return accumulativeCardRepository.getAccumulativeCardById(id);
    }

    @Override
    public AccumulativeCard addAndReturnNewAccumulativeCard() {
        AccumulativeCard accumulativeCard = createNewAccumulativeCard();
        accumulativeCardRepository.addNewAccumulativeCard(accumulativeCard);
        return accumulativeCard;
    }

    /*Privet & Protected Methods*/
    protected AccumulativeCard createNewAccumulativeCard() {
        throw new IllegalStateException();
    }
}
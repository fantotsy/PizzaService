package ua.fantotsy.services.accumulativeCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    public AccumulativeCard getAccumulativeCardById(Long id) {
        return accumulativeCardRepository.findById(id);
    }

    @Override
    @Transactional
    public AccumulativeCard addAndReturnNewAccumulativeCard() {
        AccumulativeCard accumulativeCard = createNewAccumulativeCard();
        accumulativeCardRepository.save(accumulativeCard);
        return accumulativeCard;
    }

    /*Privet & Protected Methods*/
    protected AccumulativeCard createNewAccumulativeCard() {
        throw new IllegalStateException();
    }
}
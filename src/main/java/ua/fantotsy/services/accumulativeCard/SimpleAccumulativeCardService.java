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
    public AccumulativeCard findAccumulativeCardById(Long id) {
        return accumulativeCardRepository.findById(id);
    }

    @Override
    @Transactional
    public AccumulativeCard addNewAccumulativeCard() {
        AccumulativeCard accumulativeCard = createNewAccumulativeCard();
        accumulativeCard = accumulativeCardRepository.save(accumulativeCard);
        return accumulativeCard;
    }

    /*Privet & Protected Methods*/
    protected AccumulativeCard createNewAccumulativeCard() {
        throw new IllegalStateException();
    }
}
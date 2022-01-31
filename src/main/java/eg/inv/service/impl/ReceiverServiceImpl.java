package eg.inv.service.impl;

import eg.inv.domain.Receiver;
import eg.inv.repository.ReceiverRepository;
import eg.inv.service.ReceiverService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Receiver}.
 */
@Service
@Transactional
public class ReceiverServiceImpl implements ReceiverService {

    private final Logger log = LoggerFactory.getLogger(ReceiverServiceImpl.class);

    private final ReceiverRepository receiverRepository;

    public ReceiverServiceImpl(ReceiverRepository receiverRepository) {
        this.receiverRepository = receiverRepository;
    }

    @Override
    public Receiver save(Receiver receiver) {
        log.debug("Request to save Receiver : {}", receiver);
        return receiverRepository.save(receiver);
    }

    @Override
    public Optional<Receiver> partialUpdate(Receiver receiver) {
        log.debug("Request to partially update Receiver : {}", receiver);

        return receiverRepository
            .findById(receiver.getId())
            .map(existingReceiver -> {
                if (receiver.getType() != null) {
                    existingReceiver.setType(receiver.getType());
                }
                if (receiver.getName() != null) {
                    existingReceiver.setName(receiver.getName());
                }

                return existingReceiver;
            })
            .map(receiverRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Receiver> findAll() {
        log.debug("Request to get all Receivers");
        return receiverRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Receiver> findOne(Long id) {
        log.debug("Request to get Receiver : {}", id);
        return receiverRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Receiver : {}", id);
        receiverRepository.deleteById(id);
    }
}

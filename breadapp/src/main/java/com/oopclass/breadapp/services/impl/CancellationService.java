package com.oopclass.breadapp.services.impl;

import com.oopclass.breadapp.models.Cancellation;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oopclass.breadapp.repository.CancellationRepository;
import com.oopclass.breadapp.services.ICancellationService;



/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Service
public class CancellationService implements ICancellationService {

    @Autowired
    private CancellationRepository cancelationRepository;

    @Override
    public Cancellation save(Cancellation entity) {
        return cancelationRepository.save(entity);
    }

    @Override
    public Cancellation update(Cancellation entity) {
        return cancelationRepository.save(entity);
    }

    @Override
    public void delete(Cancellation entity) {
        cancelationRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        cancelationRepository.deleteById(id);
    }

    @Override
    public Cancellation find(Long id) {
        return cancelationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cancellation> findAll() {
        return cancelationRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<Cancellation> cancellations) {
        cancelationRepository.deleteInBatch(cancellations);
    }

}

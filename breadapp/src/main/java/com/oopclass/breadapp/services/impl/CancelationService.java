package com.oopclass.breadapp.services.impl;

import com.oopclass.breadapp.models.Cancelation;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oopclass.breadapp.repository.CancelationRepository;
import com.oopclass.breadapp.services.ICancelationService;



/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Service
public class CancelationService implements ICancelationService {

    @Autowired
    private CancelationRepository cancelationRepository;

    @Override
    public Cancelation save(Cancelation entity) {
        return cancelationRepository.save(entity);
    }

    @Override
    public Cancelation update(Cancelation entity) {
        return cancelationRepository.save(entity);
    }

    @Override
    public void delete(Cancelation entity) {
        cancelationRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        cancelationRepository.deleteById(id);
    }

    @Override
    public Cancelation find(Long id) {
        return cancelationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cancelation> findAll() {
        return cancelationRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<Cancelation> cancelations) {
        cancelationRepository.deleteInBatch(cancelations);
    }

}

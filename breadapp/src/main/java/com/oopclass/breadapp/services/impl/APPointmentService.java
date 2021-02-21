package com.oopclass.breadapp.services.impl;

import com.oopclass.breadapp.models.APPointment;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oopclass.breadapp.repository.APPointmentRepository;
import com.oopclass.breadapp.services.IAPPointmentService;



/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Service
public class APPointmentService implements IAPPointmentService {

    @Autowired
    private APPointmentRepository appointmentRepository;

    @Override
    public APPointment save(APPointment entity) {
        return appointmentRepository.save(entity);
    }

    @Override
    public APPointment update(APPointment entity) {
        return appointmentRepository.save(entity);
    }

    @Override
    public void delete(APPointment entity) {
        appointmentRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public APPointment find(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<APPointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<APPointment> appointments) {
        appointmentRepository.deleteInBatch(appointments);
    }

}

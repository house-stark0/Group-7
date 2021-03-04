package com.oopclass.breadapp.services.impl;

import com.oopclass.breadapp.models.Transaction;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oopclass.breadapp.repository.TransactionRepository;
import com.oopclass.breadapp.services.ITransactionService;



/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository trasactionRepository;

    @Override
    public Transaction save(Transaction entity) {
        return trasactionRepository.save(entity);
    }

    @Override
    public Transaction update(Transaction entity) {
        return trasactionRepository.save(entity);
    }

    @Override
    public void delete(Transaction entity) {
        trasactionRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        trasactionRepository.deleteById(id);
    }

    @Override
    public Transaction find(Long id) {
        return trasactionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Transaction> findAll() {
        return trasactionRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<Transaction> trasactions) {
        trasactionRepository.deleteInBatch(trasactions);
    }

}

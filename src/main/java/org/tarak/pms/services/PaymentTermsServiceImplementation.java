package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.PaymentTerms;
import org.tarak.pms.repositories.PaymentTermsRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class PaymentTermsServiceImplementation implements ServiceInterface<PaymentTerms, Integer>
{
    @Autowired
    private PaymentTermsRepository repository;

    @Override
    public List<PaymentTerms> findAll() {
        return repository.findAll();
    }

    @Override
    public PaymentTerms saveAndFlush(PaymentTerms paymentTerms) {
        return repository.saveAndFlush(paymentTerms);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public PaymentTerms findOne(Integer id) {
        return repository.findOne(id);
    }
}

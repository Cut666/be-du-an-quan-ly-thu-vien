package com.ntt.spring.security.login.security.services;

import com.ntt.spring.security.login.models.entity.Product;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.BuySellBook;
import com.ntt.spring.security.login.repository.BuySellBookRepository;
import com.ntt.spring.security.login.security.services.itp.BuySellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class BuySellServiceImpl implements BuySellService {
    @Autowired
    BuySellBookRepository buySellBookRepository;
    @Override
    public Object getAll(Long id) {
        List<BuySellBook> buySellBookList = buySellBookRepository.findByUserId(id);
        Collections.sort(buySellBookList, Comparator.comparingLong(BuySellBook::getId).reversed());
        return buySellBookList;
    }

    @Override
    public Object findCode(Long id, String cccd) {
        List<BuySellBook> buySellBooks = buySellBookRepository.findByUserIdAndCodeContaining(id,cccd);
        return buySellBooks;
    }

    @Override
    public Object getId(int id) {
        return buySellBookRepository.findById(id).get();
    }
}

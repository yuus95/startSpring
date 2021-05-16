package com.example.jpa_shop.service;


import com.example.jpa_shop.domain.Item.Item;
import com.example.jpa_shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



//상품 Repository 를 위임하는 부분
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }


    public List<Item> findItem(){
        return itemRepository.findAll();

    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}

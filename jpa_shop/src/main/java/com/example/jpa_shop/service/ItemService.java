package com.example.jpa_shop.service;


import com.example.jpa_shop.domain.Item.Book;
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


    //변경감지 방법 findItem은 영속성 데이터이므로 save함수를 안불러도 저장이 가능
    @Transactional
    public void updateItem(Long itemId, String name, int price,int stockQuantity){
        //영속성 데이터 -> findItem
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);

    }


    public List<Item> findItem(){
        return itemRepository.findAll();

    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}

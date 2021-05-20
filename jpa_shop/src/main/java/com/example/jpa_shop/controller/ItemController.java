package com.example.jpa_shop.controller;


import com.example.jpa_shop.domain.Item.Book;
import com.example.jpa_shop.domain.Item.Item;
import com.example.jpa_shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form",new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){


        /**
         * book - > 준영속 엔티티  디비에 한번 걸치고 나왔으므로 영속성 컨텍스트가 관리하지 않는다
          준영속 엔티티 수정방법
             변경감지 기능 사용
             병합사용
         */

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);

        return "redirect:/";
    }


    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItem();
        model.addAttribute("items",items);
        return "items/itemList";
    }

    //@Pathvariable --> pathvariable 인식해준다 (itemId)
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){

        Book item =(Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form",form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId,@ModelAttribute("form") BookForm form){

        //          어설프게 엔티티 만들어서 하는방법
        //          Book book = new Book();
        //
        //         book.setId(form.getId());
        //         book.setName(form.getName());
        //         book.setPrice(form.getPrice());
        //         book.setStockQuantity(form.getStockQuantity());
        //         book.setAuthor(form.getAuthor());
        //         book.setIsbn(form.getIsbn());
        //         itemService.saveItem(book);

        //변경감지로 업데이트 처리하기기
       itemService.updateItem(itemId,form.getName(),form.getPrice(),form.getStockQuantity());
         return "redirect:/items";
    }
}

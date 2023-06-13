package com.example.gallery.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gallery.backend.entity.Item;
import com.example.gallery.backend.repository.ItemRepository;

@RestController
public class ItemController {
    
    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/api/items")
    public List<Item> getItems(){
        
        List<Item> items = itemRepository.findAll();

        return items;
        
    }
}

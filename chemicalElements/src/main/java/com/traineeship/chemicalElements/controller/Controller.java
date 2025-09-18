package com.traineeship.chemicalElements.controller;

import com.traineeship.chemicalElements.entity.Element;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    private final List<Element> elements = new ArrayList<>();

    public Controller() {
        initializeElements();
    }

    private void initializeElements() {
        elements.addAll(List.of(
                new Element("Ca", 2.4, 3.0),
                new Element("K", 6.66, 0.1),
                new Element("K", 9.76, 1.00)));
    }



    @GetMapping("/elements")
    public List<Element> getAllElements() {
        return elements;
    }

}

package com.traineeship.chemicalElements.controller;

import com.traineeship.chemicalElements.entity.Element;
import com.traineeship.chemicalElements.service.ElementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/elements")
public class Controller {

    private final ElementService elementService;

    public Controller(ElementService elementService) {
        this.elementService = elementService;
    }

    @GetMapping
    public List<Element> getElements(@RequestParam(required = false) String element) {
        return elementService.getElements(element);
    }
}


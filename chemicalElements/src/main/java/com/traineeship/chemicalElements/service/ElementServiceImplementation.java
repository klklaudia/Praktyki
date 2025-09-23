package com.traineeship.chemicalElements.service;

import com.traineeship.chemicalElements.entity.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElementServiceImplementation implements ElementService {

    private final List<Element> elements = new ArrayList<>();

    public ElementServiceImplementation() {
        initializeElements();
    }

    // hard-coded values for testing
    private void initializeElements() {
        elements.addAll(List.of(
                new Element("Ca", 2.4, 3.0),
                new Element("K", 6.66, 0.1),
                new Element("K", 9.76, 1.00)));
    }

    // returning all rows of chosen chemical element
    public List<Element> getElements(String element) {
        if (element == null)
            return elements;
        return elements.stream()
                .filter(el -> el.getName().equalsIgnoreCase(element))
                .toList();
    }
}


package com.traineeship.chemicalElements.service;

import com.traineeship.chemicalElements.entity.Element;

import java.util.List;

public interface ElementService {

    public List<Element> getElements(String element);
}

package com.traineeship.chemicalElements.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

// a chemical element object
// name + measurements in an array

@Data
@AllArgsConstructor
public class Element {

    private String name;
    private double[] measurements;

}

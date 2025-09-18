package com.traineeship.chemicalElements.entity;

public class Element {

    private String name;
    private double val1;
    private double val2;

    public Element(String name, double val1, double val2) {
        this.name = name;
        this.val1 = val1;
        this.val2 = val2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVal1() {
        return val1;
    }

    public void setVal1(double val1) {
        this.val1 = val1;
    }

    public double getVal2() {
        return val2;
    }

    public void setVal2(double val2) {
        this.val2 = val2;
    }

    @Override
    public String toString() {
        return "Elements{" +
                "name='" + name + '\'' +
                ", val1=" + val1 +
                ", val2=" + val2 +
                '}';
    }
}

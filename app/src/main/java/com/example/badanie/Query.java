package com.example.badanie;

class Query {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    @Override
    public String toString() {
        return "Query{" +
                "name='" + name + '\'' +
                '}';
    }
}

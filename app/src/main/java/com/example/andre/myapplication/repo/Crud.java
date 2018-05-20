package com.example.andre.myapplication.repo;

import java.util.List;
public interface Crud {
    int create (Object item);
    int update(Object item);
    int delete(Object item);
    Object findById(int id);
    List<?> findAll();
}

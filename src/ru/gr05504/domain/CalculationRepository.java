package ru.gr05504.domain;

import java.util.List;

public interface CalculationRepository {
    void save(Calculation calculation);
    void delete(Calculation calculation);
    List<Calculation> findAll();
}
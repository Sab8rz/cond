package com.example.javafxhomework.DAO;

import com.example.javafxhomework.models.Ingredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import java.util.List;

public class IngredientDAO implements DAO<Ingredient> {
    public ObservableList<Ingredient> observableIngredientList = FXCollections.observableList(findAll());

    @Override
    public List<Ingredient> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<Ingredient> ingredientList = session.createQuery("SELECT i FROM Ingredient i", Ingredient.class).getResultList();
            ingredientList.forEach(session::detach);
            return ingredientList;
        }
    }

    @Override
    public void save(Ingredient object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(object);
            observableIngredientList.add(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public void remove(Ingredient object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(object);
            observableIngredientList.remove(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Ingredient object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        }
    }
}
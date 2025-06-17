package com.example.javafxhomework.DAO;

import com.example.javafxhomework.models.Pastry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import java.util.List;

public class PastryDAO implements DAO<Pastry> {
    public ObservableList<Pastry> observablePastryList = FXCollections.observableList(findAll());

    @Override
    public List<Pastry> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<Pastry> pastryList = session.createQuery("SELECT p FROM Pastry p", Pastry.class).getResultList();
            pastryList.forEach(session::detach);
            return pastryList;
        }
    }

    @Override
    public void save(Pastry object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(object);
            observablePastryList.add(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public void remove(Pastry object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(object);
            observablePastryList.remove(object);
            session.getTransaction().commit();
        }
    }

    public static Pastry findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Pastry pastry = session.get(Pastry.class, id);
            session.detach(pastry);
            session.getTransaction().commit();
            return pastry;
        }
    }

    @Override
    public void update(Pastry object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        }
    }
}
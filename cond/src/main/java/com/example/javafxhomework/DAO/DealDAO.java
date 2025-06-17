package com.example.javafxhomework.DAO;

import com.example.javafxhomework.models.Deal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import java.util.List;

public class DealDAO implements DAO<Deal> {
    public ObservableList<Deal> observableDealList = FXCollections.observableList(findAll());

    @Override
    public List<Deal> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<Deal> dealList = session.createQuery("SELECT d FROM Deal d", Deal.class).getResultList();
            dealList.forEach(session::detach);
            return dealList;
        }
    }

    @Override
    public void save(Deal object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(object);
            observableDealList.add(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public void remove(Deal object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(object);
            observableDealList.remove(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Deal object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        }
    }
}
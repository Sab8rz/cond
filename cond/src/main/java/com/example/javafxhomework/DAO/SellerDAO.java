package com.example.javafxhomework.DAO;

import com.example.javafxhomework.models.Seller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import java.util.List;

public class SellerDAO implements DAO<Seller> {
    public ObservableList<Seller> observableSellerList = FXCollections.observableList(findAll());

    @Override
    public List<Seller> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<Seller> sellerList = session.createQuery("SELECT s FROM Seller s", Seller.class).getResultList();
            sellerList.forEach(session::detach);
            return sellerList;
        }
    }

    @Override
    public void save(Seller object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(object);
            observableSellerList.add(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public void remove(Seller object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(object);
            observableSellerList.remove(object);
            session.getTransaction().commit();
        }
    }


    public static Seller findById(int id) {
        Session session = sessionFactory.openSession();
        Seller seller = session.get(Seller.class, id);
        session.close();
        return seller;
    }

    @Override
    public void update(Seller object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        }
    }
}
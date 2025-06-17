package com.example.javafxhomework.HDB;
import com.example.javafxhomework.models.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateDB {
    public static final Configuration configuration = new Configuration()
            .addAnnotatedClass(Pastry.class)
            .addAnnotatedClass(Seller.class)
            .addAnnotatedClass(Deal.class)
            .addAnnotatedClass(Ingredient.class);
    public static final SessionFactory sessionFactory = configuration.buildSessionFactory();
}

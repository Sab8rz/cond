package com.example.javafxhomework.DAO;

import com.example.javafxhomework.HDB.HibernateDB; // для доступа к базе данных
import org.hibernate.SessionFactory; // для работы с базой данных с помощью Hibernate
import java.util.List; // для использования списков объектов

public interface DAO<T> {
    SessionFactory sessionFactory = HibernateDB.sessionFactory; // создание объекта SessionFactory для работы с базой данных
    List<T> findAll(); // метод для получения всех объектов T из базы данных
    void save(T object); // метод для сохранения объекта T в базе данных
    void remove(T object); // метод для удаления объекта T из базы данных
    void update(T object); // метод для изменения объекта T из базы данных
}

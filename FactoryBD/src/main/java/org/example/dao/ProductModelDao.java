package org.example.dao;

import org.example.model.ProductModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;

public class ProductModelDao {
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public ProductModelDao() {

    }

    public List<ProductModel> getAllModels() {
        try (Session session = sessionFactory.openSession()) {
            Query<ProductModel> query = session.createQuery("FROM ProductModel", ProductModel.class);
            return query.list();
        }
    }

    public ProductModel getModelById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(ProductModel.class, id);
        }
    }
}
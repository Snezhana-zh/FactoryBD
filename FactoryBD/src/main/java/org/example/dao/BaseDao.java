package org.example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.selector.spi.StrategyCreator;
import org.hibernate.cfg.Configuration;
import java.util.List;
import org.example.model.Employee;
import org.hibernate.query.Query;

public class BaseDao<T> {
    private static final SessionFactory sessionFactory;
    private final Class<T> entityClass;

    public BaseDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void save(T entity) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            if (entity instanceof Employee) {
                Employee employee = (Employee) entity;
                Query query = session.createNativeQuery(
                        "INSERT INTO employee (name, position) VALUES (:name, cast(:position as employee_position))"
                );
                query.setParameter("name", employee.getName());
                query.setParameter("position", employee.getPosition().name());
                query.executeUpdate();
            }
            else session.save(entity);
            transaction.commit();
        }
        catch(Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<T> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from " + entityClass.getSimpleName(), entityClass).list();
        }
    }

    public void update(T entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            T entity = session.get(entityClass, id);
            if (entity != null) {
                session.delete(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public T getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(entityClass, id);
        }
    }
}

package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryRepository {
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

//    public List<Object[]> getModels(long workshop_id, long category_id) {
//        Transaction transaction = null;
//        try(Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
//
//            Query query = session.createNativeQuery(
//                    "SELECT pm.title AS model_name, pc.title AS category_name\n" +
//                            "FROM product_model pm\n" +
//                            "JOIN product_category pc ON pm.category_id = pc.id\n" +
//                            "JOIN department d ON pm.department_id = d.id\n" +
//                            "JOIN workshop w ON w.department_id = d.id\n" +
//                            "WHERE w.id = :workshop_id AND pc.id = :category_id;"
//            );
//            query.setParameter("workshop_id", workshop_id);
//            query.setParameter("category_id", category_id);
//            query.executeUpdate();
//
//            transaction.commit();
//            return query.getResultList();
//        }
//        catch(Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//        return null;
//    }
    // 1. Получить перечень видов изделий
    public List<Object[]> getProductsByWorkshopAndCategory(long workshopId, Long categoryId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT pm.title, pc.title " +
                    "FROM ProductModel pm " +
                    "JOIN pm.category pc " +
                    "JOIN pm.department d " +
                    "JOIN d.workshops w " +
                    "WHERE w.id = :workshopId " +
                    (categoryId != null ? "AND pc.id = :categoryId" : "");

            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("workshopId", workshopId);
            if (categoryId != null) {
                query.setParameter("categoryId", categoryId);
            }
            return query.getResultList();
        }
    }

    public List<Object[]> getProductsByCompany(long companyId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT pm.title, pc.title " +
                                    "FROM ProductModel pm " +
                                    "JOIN pm.category pc " +
                                    "JOIN pm.department d " +
                                    "WHERE d.company.id = :companyId", Object[].class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        }
    }

    // 2. Получить число и перечень изделий за период
    public List<Object[]> getProductsCountByPeriod(long workshopId, Long categoryId,
                                                   LocalDateTime start, LocalDateTime end) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT COUNT(p.id), pm.title, pc.title " +
                    "FROM Product p " +
                    "JOIN p.model pm " +
                    "JOIN pm.category pc " +
                    "JOIN p.logs pl " +
                    "WHERE pl.workshop.id = :workshopId " +
                    "AND pl.startWork BETWEEN :start AND :end " +
                    (categoryId != null ? "AND pc.id = :categoryId" : "") +
                    " GROUP BY pm.title, pc.title";

            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("workshopId", workshopId);
            query.setParameter("start", start);
            query.setParameter("end", end);
            if (categoryId != null) {
                query.setParameter("categoryId", categoryId);
            }
            return query.getResultList();
        }
    }

    // 3. Получить данные о кадровом составе

    public List<Object[]> getEmployeesByWorkshop(long workshop_id) {
        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT e.position, COUNT(e.id) AS employee_count " +
                    "FROM employee e " +
                    "JOIN engineers eng ON e.id = eng.employee_id " +
                    "JOIN workshop ws ON eng.workshop_id = ws.id " +
                    "WHERE ws.id = :workshop_id " +
                    "GROUP BY e.position;";

            return session.createNativeQuery(sql, Object[].class)
                    .setParameter("workshop_id", workshop_id)
                    .getResultList();
        }
    }
//    public List<Object[]> getEmployeesByWorkshop(long workshopId) {
//        try (Session session = sessionFactory.openSession()) {
//            String hql = "SELECT e.position, COUNT(e.id) " +
//                    "FROM Employee e " +
//                    "JOIN e.workshop w " +
//                    "WHERE w.id = :workshopId " +
//                    "GROUP BY e.position";
//
//            return session.createQuery(hql, Object[].class)
//                    .setParameter("workshopId", workshopId)
//                    .getResultList();
//        }
//    }
//    public List<Object[]> getEmployeesByWorkshop(long workshopId) {
//        try (Session session = sessionFactory.openSession()) {
//            // Для рабочих
//            String hqlWorkers = "SELECT e.position, COUNT(e.id) " +
//                    "FROM Employee e " +
//                    "JOIN e.worker w " +
//                    "JOIN w.brigade b " +
//                    "JOIN b.workshop wsh " +
//                    "WHERE wsh.id = :workshopId " +
//                    "GROUP BY e.position";
//
//            // Для инженеров
//            String hqlEngineers = "SELECT e.position, COUNT(e.id) " +
//                    "FROM Employee e " +
//                    "JOIN e.engineer eng " +
//                    "JOIN eng.workshop wsh " +
//                    "WHERE wsh.id = :workshopId " +
//                    "GROUP BY e.position";
//
//            // Объединяем результаты
//            List<Object[]> workers = session.createQuery(hqlWorkers, Object[].class)
//                    .setParameter("workshopId", workshopId)
//                    .getResultList();
//
//            List<Object[]> engineers = session.createQuery(hqlEngineers, Object[].class)
//                    .setParameter("workshopId", workshopId)
//                    .getResultList();
//
//            // Собираем и объединяем результаты
//            Map<String, Long> resultMap = new HashMap<>();
//
//            addToResultMap(resultMap, workers);
//            addToResultMap(resultMap, engineers);
//
//            return resultMap.entrySet().stream()
//                    .map(e -> new Object[]{e.getKey(), e.getValue()})
//                    .collect(Collectors.toList());
//        }
//    }
//
//    private void addToResultMap(Map<String, Long> map, List<Object[]> data) {
//        for (Object[] row : data) {
//            String position = (String) row[0];
//            Long count = (Long) row[1];
//            map.merge(position, count, Long::sum);
//        }
//    }

    // 4. Получить участки и начальников
    public List<Object[]> getWorkshopsWithHeads(long departmentId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT w.name, e.name " +
                                    "FROM Workshop w " +
                                    "JOIN w.head e " +
                                    "WHERE w.department.id = :departmentId", Object[].class)
                    .setParameter("departmentId", departmentId)
                    .getResultList();
        }
    }

    // 5. Получить перечень работ для изделия
//    public List<Object[]> getProductWorkflow(long productId) {
//        try (Session session = sessionFactory.openSession()) {
//            return session.createQuery(
//                            "SELECT pl.startWork, pl.endWork, " +
//                                    "CASE " +
//                                    "   WHEN pl.workshop IS NOT NULL THEN CONCAT('Производство в цехе: ', w.name) " +
//                                    "   WHEN pl.testlab IS NOT NULL THEN CONCAT('Тестирование в лаборатории: ', tl.title) " +
//                                    "   ELSE 'Неизвестный этап' " +
//                                    "END " +
//                                    "FROM ProductLog pl " +
//                                    "LEFT JOIN pl.workshop w " +
//                                    "LEFT JOIN pl.testlab tl " +
//                                    "WHERE pl.product.id = :productId " +
//                                    "ORDER BY pl.startWork", Object[].class)
//                    .setParameter("productId", productId)
//                    .getResultList();
//        }
//    }
    public List<Object[]> getProductWorkflow(long productId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT pl.startWork, pl.endWork, " +
                                    "COALESCE(" +
                                    "   NULLIF(CONCAT('Производство в цехе: ', w.name), 'Производство в цехе: '), " +
                                    "   COALESCE(" +
                                    "       NULLIF(CONCAT('Тестирование в лаборатории: ', tl.title), 'Тестирование в лаборатории: '), " +
                                    "       'Неизвестный этап'" +
                                    "   )" +
                                    ") " +
                                    "FROM ProductLog pl " +
                                    "LEFT JOIN pl.workshop w " +
                                    "LEFT JOIN pl.testlab tl " +
                                    "WHERE pl.product.id = :productId " +
                                    "ORDER BY pl.startWork", Object[].class)
                    .setParameter("productId", productId)
                    .getResultList();
        }
    }

    // 6. Получить состав бригад
    public List<Object[]> getBrigadeMembers(long workshopId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT b.id, e.name " +
                                    "FROM Brigade b " +
                                    "JOIN b.workers w " +
                                    "JOIN w.employee e " +
                                    "WHERE b.workshop.id = :workshopId", Object[].class)
                    .setParameter("workshopId", workshopId)
                    .getResultList();
        }
    }

    // 7. Получить перечень мастеров
    public List<String> getBrigadeHeads(long departmentId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT e.name " +
                                    "FROM Brigade b " +
                                    "JOIN b.head e " +
                                    "JOIN b.workshop w " +
                                    "WHERE w.department.id = :departmentId", String.class)
                    .setParameter("departmentId", departmentId)
                    .getResultList();
        }
    }

    // 8. Получить текущие изделия
    public List<Object[]> getCurrentProducts(long workshopId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT p.id, pm.title, pc.title " +
                                    "FROM Product p " +
                                    "JOIN p.model pm " +
                                    "JOIN pm.category pc " +
                                    "JOIN p.logs pl " +
                                    "WHERE pl.workshop.id = :workshopId " +
                                    "AND pl.endWork IS NULL", Object[].class)
                    .setParameter("workshopId", workshopId)
                    .getResultList();
        }
    }

    // 9. Получить бригады для изделия
    public List<Object[]> getProductBrigades(long productId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT b.id, e.name, emp.id, emp.name " +
                                    "FROM ProductLog pl " +
                                    "JOIN pl.workshop w " +
                                    "JOIN w.brigades b " +
                                    "JOIN b.head e " +
                                    "JOIN b.workers wr " +
                                    "JOIN wr.employee emp " +
                                    "WHERE pl.product.id = :productId " +
                                    "ORDER BY b.id, emp.name", Object[].class)
                    .setParameter("productId", productId)
                    .getResultList();
        }
    }

    // 10. Получить испытательные лаборатории для изделия
    public List<Object[]> getTestLabsForProduct(long productId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT tl.id, tl.title " +
                                    "FROM ProductLog pl " +
                                    "JOIN pl.testlab tl " +
                                    "WHERE pl.product.id = :productId " +
                                    "AND tl IS NOT NULL", Object[].class)
                    .setParameter("productId", productId)
                    .getResultList();
        }
    }

    // 11. Получить изделия, проходившие испытания
    public List<Object[]> getTestedProducts(long testLabId, Long categoryId,
                                            LocalDateTime start, LocalDateTime end) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT p.id, pm.title, pc.title " +
                    "FROM Product p " +
                    "JOIN p.model pm " +
                    "JOIN pm.category pc " +
                    "JOIN p.logs pl " +
                    "WHERE pl.testlab.id = :testLabId " +
                    "AND pl.startWork BETWEEN :start AND :end " +
                    (categoryId != null ? "AND pc.id = :categoryId" : "");

            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("testLabId", testLabId);
            query.setParameter("start", start);
            query.setParameter("end", end);
            if (categoryId != null) {
                query.setParameter("categoryId", categoryId);
            }
            return query.getResultList();
        }
    }

    // 12. Получить испытателей
    public List<Object[]> getTestersForProduct(long productId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT e.id, e.name " +
                                    "FROM Tester t " +
                                    "JOIN t.employee e " +
                                    "JOIN t.testLab tl " +
                                    "JOIN ProductLog pl ON tl.id = pl.testlab.id " +
                                    "WHERE pl.product.id = :productId", Object[].class)
                    .setParameter("productId", productId)
                    .getResultList();
        }
    }

    // 13. Получить оборудование для испытаний
    public List<Object[]> getTestEquipment(long productId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT tle.id, tle.title " +
                                    "FROM TestLabEquipment tle " +
                                    "JOIN tle.testLab tl " +
                                    "JOIN ProductLog pl ON tl.id = pl.testlab.id " +
                                    "WHERE pl.product.id = :productId", Object[].class)
                    .setParameter("productId", productId)
                    .getResultList();
        }
    }

    // 14. Получить текущие изделия с количеством
    public List<Object[]> getCurrentProductsCount(long companyId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT COUNT(p.id), pm.title, pc.title " +
                                    "FROM Product p " +
                                    "JOIN p.model pm " +
                                    "JOIN pm.category pc " +
                                    "JOIN p.logs pl " +
                                    "JOIN pl.workshop w " +
                                    "JOIN w.department d " +
                                    "WHERE d.company.id = :companyId " +
                                    "AND pl.endWork IS NULL " +
                                    "GROUP BY pm.title, pc.title", Object[].class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        }
    }
}

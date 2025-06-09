package org.example.ui;

import org.example.dao.BaseDao;

import javax.swing.*;
import java.awt.*;

import org.example.model.*;
import org.example.ui.employee.EmployeePanel;
import org.example.ui.employee.EngineerPanel;
import org.example.ui.employee.TestersPanel;
import org.example.ui.employee.WorkersPanel;
import org.example.ui.office.BrigadePanel;
import org.example.ui.office.DepartmentForm;
import org.example.ui.office.DepartmentPanel;
import org.example.ui.office.TestLabPanel;
import org.example.ui.products.*;
import org.hibernate.jdbc.Work;

public class MainFrame extends JFrame {
    // private final ProductDao productDao;
    private final BaseDao<Product> productDao;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        this.productDao = new BaseDao<Product>(Product.class);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Создаем главное меню
        JMenuBar menuBar = new JMenuBar();

        // Меню "Модули"
        JMenu modulesMenu = new JMenu("Modules");
        JMenuItem productsItem = new JMenuItem("Products");
        JMenuItem staffItem = new JMenuItem("Staff");
        JMenuItem productModelsItem = new JMenuItem("Product Models");
        JMenuItem productCatItem = new JMenuItem("Product Category");
        JMenuItem workersItem = new JMenuItem("Workers");
        JMenuItem engineersItem = new JMenuItem("Engineers");
        JMenuItem testersItem = new JMenuItem("Testers");
        JMenuItem brigadeItem = new JMenuItem("Brigade");
        JMenuItem departmentItem = new JMenuItem("Department");
        JMenuItem testLabItem = new JMenuItem("TestLab");

        // Добавляем меню в менюбар
        menuBar.add(modulesMenu);
        modulesMenu.add(productCatItem);
        modulesMenu.add(productModelsItem);
        modulesMenu.add(productsItem);
        modulesMenu.add(staffItem);
        modulesMenu.add(workersItem);
        modulesMenu.add(engineersItem);
        modulesMenu.add(testersItem);
        modulesMenu.add(brigadeItem);
        modulesMenu.add(departmentItem);
        modulesMenu.add(testLabItem);

        setJMenuBar(menuBar);

        // Создаем панель с карточками для переключения между формами
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Добавляем формы на contentPanel
        contentPanel.add(new ProductsPanel(productDao, MainFrame.this), "Products");
        contentPanel.add(new EmployeePanel(new BaseDao<>(Employee.class), MainFrame.this), "Staff");
        contentPanel.add(new ProductModelPanel(new BaseDao<>(ProductModel.class), this), "ProductModels");
        contentPanel.add(new ProductCategoryPanel(new BaseDao<>(ProductCategory.class), this), "ProductCategory");
        contentPanel.add(new WorkersPanel(new BaseDao<>(Worker.class), this), "Workers");
        contentPanel.add(new EngineerPanel(new BaseDao<>(Engineer.class), this), "Engineers");
        contentPanel.add(new TestersPanel(new BaseDao<>(Tester.class), this), "Testers");
        contentPanel.add(new BrigadePanel(new BaseDao<>(Brigade.class), this), "Brigade");
        contentPanel.add(new DepartmentPanel(new BaseDao<>(Department.class), this), "Department");
        contentPanel.add(new TestLabPanel(new BaseDao<>(TestLab.class), this), "TestLab");


        // Обработчики для меню
        productsItem.addActionListener(e -> cardLayout.show(contentPanel, "Products"));
        staffItem.addActionListener(e -> cardLayout.show(contentPanel, "Staff"));
        productModelsItem.addActionListener(e ->
                cardLayout.show(contentPanel, "ProductModels"));
        productCatItem.addActionListener(e ->
                cardLayout.show(contentPanel, "ProductCategory"));
        workersItem.addActionListener(e -> cardLayout.show(contentPanel, "Workers"));
        engineersItem.addActionListener(e -> cardLayout.show(contentPanel, "Engineers"));
        testersItem.addActionListener(e -> cardLayout.show(contentPanel, "Testers"));
        brigadeItem.addActionListener(e -> cardLayout.show(contentPanel, "Brigade"));
        departmentItem.addActionListener(e -> cardLayout.show(contentPanel, "Department"));
        testLabItem.addActionListener(e -> cardLayout.show(contentPanel, "TestLab"));

        // Добавляем компоненты на форму
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        // Показываем панель продуктов по умолчанию
        cardLayout.show(contentPanel, "Products");
    }
}
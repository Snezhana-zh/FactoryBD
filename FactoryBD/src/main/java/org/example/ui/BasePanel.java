package org.example.ui;

import org.example.dao.BaseDao;
import org.example.model.BaseModel;

import javax.swing.*;
import java.awt.*;

public class BasePanel<T extends BaseModel> extends JPanel {
    protected BaseTable<T> table;
    protected BaseDao<T> dao;
    protected JFrame mainFrame;

    public BasePanel(BaseDao<T> dao, JFrame mainFrame, BaseTable<T> table) {
        this.dao = dao;
        this.mainFrame = mainFrame;
        this.table = table;
        initializeUI();
        loadElements();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Создаем панель с кнопками
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(e -> showForm(null));
        editButton.addActionListener(e -> {
            T selected = table.getSelected();
            if (selected != null) {
                showForm(selected);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please select a element to edit");
            }
        });

        deleteButton.addActionListener(e -> {
            T selected = table.getSelected();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(
                        mainFrame,
                        "Are you sure you want to delete this element?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    dao.delete(selected.getId());
                    loadElements();
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please select a element to delete");
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadElements());

        buttonPanel.add(refreshButton);

        // Добавляем компоненты на панель
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadElements() {
        java.util.List<T> elements = dao.getAll();
        table.set(elements);
    }

    protected void showForm(BaseModel element) {
        //form.setVisible(true);
    }
}
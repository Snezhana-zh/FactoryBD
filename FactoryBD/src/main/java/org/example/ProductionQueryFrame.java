package org.example;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProductionQueryFrame extends JFrame {
    private final JTextArea resultArea;
    private final QueryRepository repository;
    private final JTabbedPane tabbedPane;

    public ProductionQueryFrame() {
        this.repository = new QueryRepository();
        setTitle("Production Database Query Tool");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        tabbedPane = new JTabbedPane();
        createProductQueriesTab();
        createEmployeeQueriesTab();
        createTestLabQueriesTab();
        createBrigadeQueriesTab();

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        JPanel controlPanel = new JPanel();
        JButton clearButton = new JButton("Очистить результаты");
        clearButton.addActionListener(e -> resultArea.setText(""));
        controlPanel.add(clearButton);

        mainPanel.add(tabbedPane, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void createProductQueriesTab() {
        JPanel productPanel = new JPanel(new GridLayout(5, 1, 15, 10));
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Запрос 1: Изделия по цеху и категории
        JPanel query1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        query1Panel.add(new JLabel("Цех:"));
        JTextField workshopIdField1 = new JTextField(5);
        query1Panel.add(workshopIdField1);
        query1Panel.add(new JLabel("Категория (опционально):"));
        JTextField categoryIdField1 = new JTextField(5);
        query1Panel.add(categoryIdField1);
        JButton query1Button = new JButton("Получить изделия");
        query1Button.addActionListener(e -> {
            try {
                long workshopId = Long.parseLong(workshopIdField1.getText());
                Long categoryId = categoryIdField1.getText().isEmpty() ? null : Long.parseLong(categoryIdField1.getText());
                List<Object[]> results = repository.getProductsByWorkshopAndCategory(workshopId, categoryId);
                displayResults("Изделия цеха " + workshopId +
                        (categoryId != null ? " категории " + categoryId : "") + "(model, category)", results);
            } catch (NumberFormatException ex) {
                showError("Введите корректные числовые ID");
            }
        });
        query1Panel.add(query1Button);
        productPanel.add(query1Panel);

        // Запрос 2: Изделия за период
        JPanel query2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        query2Panel.add(new JLabel("Участок:"));
        JTextField workshopIdField2 = new JTextField(5);
        query2Panel.add(workshopIdField2);
        query2Panel.add(new JLabel("Категория (опционально):"));
        JTextField categoryIdField2 = new JTextField(5);
        query2Panel.add(categoryIdField2);
        query2Panel.add(new JLabel("Начало периода:"));
        JTextField startDateField = new JTextField(10);
        startDateField.setText(LocalDateTime.now().minusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE));
        query2Panel.add(startDateField);
        query2Panel.add(new JLabel("Конец периода:"));
        JTextField endDateField = new JTextField(10);
        endDateField.setText(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        query2Panel.add(endDateField);
        JButton query2Button = new JButton("Статистика производства");
        query2Button.addActionListener(e -> {
            try {
                long workshopId = Long.parseLong(workshopIdField2.getText());
                Long categoryId = categoryIdField2.getText().isEmpty() ? null : Long.parseLong(categoryIdField2.getText());
                LocalDateTime start = LocalDateTime.parse(startDateField.getText() + "T00:00");
                LocalDateTime end = LocalDateTime.parse(endDateField.getText() + "T23:59");
                List<Object[]> results = repository.getProductsCountByPeriod(workshopId, categoryId, start, end);
                displayResults("Статистика производства цеха " + workshopId + "(count, model, category)", results);
            } catch (Exception ex) {
                showError("Ошибка формата данных: " + ex.getMessage());
            }
        });
        query2Panel.add(query2Button);
        productPanel.add(query2Panel);

        // Запрос 8: Текущие изделия
        JPanel query8Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        query8Panel.add(new JLabel("Участок:"));
        JTextField workshopIdField8 = new JTextField(5);
        query8Panel.add(workshopIdField8);
        JButton query8Button = new JButton("Текущие изделия");
        query8Button.addActionListener(e -> {
            try {
                long workshopId = Long.parseLong(workshopIdField8.getText());
                List<Object[]> results = repository.getCurrentProducts(workshopId);
                displayResults("Текущие изделия на участке " + workshopId + "(id, model, category)", results);
            } catch (NumberFormatException ex) {
                showError("Введите корректный ID");
            }
        });
        query8Panel.add(query8Button);
        productPanel.add(query8Panel);

        // Запрос 5: Работы по изделию
        JPanel query5Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        query5Panel.add(new JLabel("ID изделия:"));
        JTextField productIdField5 = new JTextField(5);
        query5Panel.add(productIdField5);
        JButton query5Button = new JButton("История работ");
        query5Button.addActionListener(e -> {
            try {
                long productId = Long.parseLong(productIdField5.getText());
                List<Object[]> results = repository.getProductWorkflow(productId);
                displayResults("История работ по изделию " + productId + "(start date, end date, status)", results);
            } catch (NumberFormatException ex) {
                showError("Введите корректный ID изделия");
            }
        });
        query5Panel.add(query5Button);
        productPanel.add(query5Panel);

        // Запрос 14: текущие изделия с количеством
        JPanel query14Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        query14Panel.add(new JLabel("ID участка:"));
        JTextField productIdField14 = new JTextField(5);
        query14Panel.add(productIdField14);
        JButton query14Button = new JButton("Получить статистику по количеству");
        query14Button.addActionListener(e -> {
            try {
                long productId = Long.parseLong(productIdField14.getText());
                List<Object[]> results = repository.getCurrentProductsCount(productId);
                displayResults("Текущие изделия с количеством для участка" + productId + "(count, category)", results);
            } catch (NumberFormatException ex) {
                showError("Введите корректный ID");
            }
        });
        query14Panel.add(query14Button);
        productPanel.add(query14Panel);

        tabbedPane.addTab("Изделия", productPanel);
    }

    private void createEmployeeQueriesTab() {
        JPanel employeePanel = new JPanel(new GridLayout(2, 1, 10, 10));
        employeePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Запрос 3: Кадровый состав
        JPanel query3Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        query3Panel.add(new JLabel("Цех:"));
        JTextField workshopIdField3 = new JTextField(5);
        query3Panel.add(workshopIdField3);
        JButton query3Button = new JButton("Кадровый состав");
        query3Button.addActionListener(e -> {
            try {
                long workshopId = Long.parseLong(workshopIdField3.getText());
                List<Object[]> results = repository.getEmployeesByWorkshop(workshopId);
                displayResults("Кадровый состав участка " + workshopId + "(position, count)", results);
            } catch (NumberFormatException ex) {
                showError("Введите корректный ID");
            }
        });
        query3Panel.add(query3Button);
        employeePanel.add(query3Panel);

        // Запрос 4: Участки и начальники
        JPanel query4Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        query4Panel.add(new JLabel("ID отдела:"));
        JTextField departmentIdField4 = new JTextField(5);
        query4Panel.add(departmentIdField4);
        JButton query4Button = new JButton("Участки и начальники");
        query4Button.addActionListener(e -> {
            try {
                long departmentId = Long.parseLong(departmentIdField4.getText());
                List<Object[]> results = repository.getWorkshopsWithHeads(departmentId);
                displayResults("Участки отдела " + departmentId + "(workshop, name)", results);
            } catch (NumberFormatException ex) {
                showError("Введите корректный ID отдела");
            }
        });
        query4Panel.add(query4Button);
        employeePanel.add(query4Panel);

        // Запрос 7: Бригады и начальники
        JPanel query7Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        query7Panel.add(new JLabel("ID участка:"));
        JTextField departmentIdField7 = new JTextField(5);
        query7Panel.add(departmentIdField7);
        JButton query7Button = new JButton("Бригады и начальники");
        query7Button.addActionListener(e -> {
            try {
                long departmentId = Long.parseLong(departmentIdField7.getText());
                List<Object[]> results = repository.getBrigadeHeads(departmentId);
                displayResults("Бригады участка " + departmentId + "(id brigade, head)", results);
            } catch (NumberFormatException ex) {
                showError("Введите корректный ID");
            }
        });
        query7Panel.add(query7Button);
        employeePanel.add(query7Panel);

        tabbedPane.addTab("Персонал", employeePanel);
    }

    private void createTestLabQueriesTab() {
        JPanel testLabPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        testLabPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Запрос 10: Лаборатории для изделия
        JPanel query10Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        query10Panel.add(new JLabel("ID изделия:"));
        JTextField productIdField10 = new JTextField(5);
        query10Panel.add(productIdField10);
        JButton query10Button = new JButton("Испытательные лаборатории");
        query10Button.addActionListener(e -> {
            try {
                long productId = Long.parseLong(productIdField10.getText());
                List<Object[]> results = repository.getTestLabsForProduct(productId);
                displayResults("Лаборатории для изделия " + productId + "(id, title lab)", results);
            } catch (NumberFormatException ex) {
                showError("Введите корректный ID изделия");
            }
        });
        query10Panel.add(query10Button);
        testLabPanel.add(query10Panel);

        // Запрос 11: Изделия в лаборатории
        JPanel query11Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        query11Panel.add(new JLabel("ID лаборатории:"));
        JTextField testLabIdField11 = new JTextField(5);
        query11Panel.add(testLabIdField11);
        query11Panel.add(new JLabel("Категория (опционально):"));
        JTextField categoryIdField11 = new JTextField(5);
        query11Panel.add(categoryIdField11);
        query11Panel.add(new JLabel("Начало периода:"));
        JTextField startDateField11 = new JTextField(10);
        startDateField11.setText(LocalDateTime.now().minusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE));
        query11Panel.add(startDateField11);
        query11Panel.add(new JLabel("Конец периода:"));
        JTextField endDateField11 = new JTextField(10);
        endDateField11.setText(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        query11Panel.add(endDateField11);
        JButton query11Button = new JButton("Изделия в лаборатории");
        query11Button.addActionListener(e -> {
            try {
                long testLabId = Long.parseLong(testLabIdField11.getText());
                Long categoryId = categoryIdField11.getText().isEmpty() ? null : Long.parseLong(categoryIdField11.getText());
                LocalDateTime start = LocalDateTime.parse(startDateField11.getText() + "T00:00");
                LocalDateTime end = LocalDateTime.parse(endDateField11.getText() + "T23:59");
                List<Object[]> results = repository.getTestedProducts(testLabId, categoryId, start, end);
                displayResults("Изделия в лаборатории " + testLabId + "(id, model, category)", results);
            } catch (Exception ex) {
                showError("Ошибка формата данных: " + ex.getMessage());
            }
        });
        query11Panel.add(query11Button);
        testLabPanel.add(query11Panel);

        // Запрос 12: Испытатели
        JPanel query12Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        query12Panel.add(new JLabel("ID изделия:"));
        JTextField productIdField12 = new JTextField(5);
        query12Panel.add(productIdField12);
        JButton query12Button = new JButton("Испытатели");
        query12Button.addActionListener(e -> {
            try {
                long productId = Long.parseLong(productIdField12.getText());
                List<Object[]> results = repository.getTestersForProduct(productId);
                displayResults("Испытатели изделия " + productId + "(id, name)", results);
            } catch (NumberFormatException ex) {
                showError("Введите корректный ID изделия");
            }
        });
        query12Panel.add(query12Button);
        testLabPanel.add(query12Panel);

        tabbedPane.addTab("Испытания", testLabPanel);
    }

    private void createBrigadeQueriesTab() {
        JPanel brigadePanel = new JPanel(new GridLayout(2, 1, 10, 10));
        brigadePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Запрос 6: Состав бригад
        JPanel query6Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        query6Panel.add(new JLabel("Участок:"));
        JTextField workshopIdField6 = new JTextField(5);
        query6Panel.add(workshopIdField6);
        JButton query6Button = new JButton("Состав бригад");
        query6Button.addActionListener(e -> {
            try {
                long workshopId = Long.parseLong(workshopIdField6.getText());
                List<Object[]> results = repository.getBrigadeMembers(workshopId);
                displayResults("Бригады участка " + workshopId + "(id, model, category)", results);
            } catch (NumberFormatException ex) {
                showError("Введите корректный ID");
            }
        });
        query6Panel.add(query6Button);
        brigadePanel.add(query6Panel);

        // Запрос 9: Бригады для изделия
        JPanel query9Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        query9Panel.add(new JLabel("ID изделия:"));
        JTextField productIdField9 = new JTextField(5);
        query9Panel.add(productIdField9);
        JButton query9Button = new JButton("Бригады изделия");
        query9Button.addActionListener(e -> {
            try {
                long productId = Long.parseLong(productIdField9.getText());
                List<Object[]> results = repository.getProductBrigades(productId);
                displayResults("Бригады для изделия " + productId + "(brigade id, head, empl id, empl name)", results);
            } catch (NumberFormatException ex) {
                showError("Введите корректный ID изделия");
            }
        });
        query9Panel.add(query9Button);
        brigadePanel.add(query9Panel);

        tabbedPane.addTab("Бригады", brigadePanel);
    }

    private void displayResults(String title, List<Object[]> results) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(title).append(" ===\n");

        if (results == null || results.isEmpty()) {
            sb.append("Нет данных\n");
        } else {
            for (Object[] row : results) {
                for (Object cell : row) {
                    sb.append(cell != null ? cell.toString() : "null").append("    ");
                }
                sb.append("\n");
            }
        }
        sb.append("\n");
        resultArea.append(sb.toString());
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

}
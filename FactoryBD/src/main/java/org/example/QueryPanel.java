package org.example;

import org.example.model.Employee;
import org.example.model.Workshop;

import javax.swing.*;
import java.util.List;

public class QueryPanel extends JPanel {
    public QueryPanel() {
        QueryRepository rep = new QueryRepository();
        add(new JLabel("workshop_id:"));
        JTextField workshop_id_Field = new JTextField(10);
        add(workshop_id_Field);

        add(new JLabel("category_id:"));
        JTextField category_id_Field = new JTextField(10);
        add(workshop_id_Field);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane);

        JButton query1Button = new JButton("GET");
        query1Button.addActionListener(e -> {
            List<Object[]> results = rep.getProductsByWorkshopAndCategory(
                    Long.parseLong(workshop_id_Field.getText()),
                    Long.parseLong(category_id_Field.getText()));

            StringBuilder sb = new StringBuilder();
            sb.append("Результаты запроса: \n");

            if (results != null && !results.isEmpty()) {
                for (Object[] row : results) {
                    sb.append("Модель: ").append(row[0])
                            .append(", Категория: ").append(row[1])
                            .append("\n");
                }
            } else {
                sb.append("Нет результатов\n");
            }

            resultArea.append(sb.toString() + "\n");
        });
        add(query1Button);
    }
}

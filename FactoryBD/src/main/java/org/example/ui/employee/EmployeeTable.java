package org.example.ui.employee;

import org.example.dao.BaseDao;
import org.example.model.Employee;
import org.example.ui.BaseTable;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class EmployeeTable extends BaseTable<Employee> {
    private DefaultTableModel model;

    public EmployeeTable() {
        model = new DefaultTableModel(
                new Object[]{"ID", "Name", "Position"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
    }

    public void set(List<Employee> models) {
        model.setRowCount(0);

        for (Employee el : models) {

            model.addRow(new Object[]{
                    el.getId(),
                    el.getName(),
                    el.getPosition() != null ? el.getPosition().name() : "N/A",
            });
        }
    }

    public Employee getSelected() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            long id = (long) model.getValueAt(selectedRow, 0);

            return new BaseDao<Employee>(Employee.class).getById(id);
        }
        return null;
    }
}

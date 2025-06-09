package org.example.ui.employee;

import org.example.dao.BaseDao;
import org.example.model.Worker;
import org.example.ui.BaseTable;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class WorkersTable extends BaseTable<Worker> {
    private DefaultTableModel model;

    public WorkersTable() {
        model = new DefaultTableModel(
                new Object[]{"ID", "Employee_ID", "Brigade_ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
    }

    public void set(List<Worker> models) {
        model.setRowCount(0);

        for (Worker el : models) {

            model.addRow(new Object[]{
                    el.getId(),
                    el.getEmployee().getName(),
                    el.getBrigade() != null ? el.getBrigade().getId() : "N/A",
            });
        }
    }

    public Worker getSelected() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            long id = (long) model.getValueAt(selectedRow, 0);

            return new BaseDao<Worker>(Worker.class).getById(id);
        }
        return null;
    }
}

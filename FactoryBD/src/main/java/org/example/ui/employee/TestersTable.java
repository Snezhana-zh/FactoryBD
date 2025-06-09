package org.example.ui.employee;

import org.example.dao.BaseDao;
import org.example.model.Tester;
import org.example.ui.BaseTable;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TestersTable extends BaseTable<Tester> {
    private DefaultTableModel model;

    public TestersTable() {
        model = new DefaultTableModel(
                new Object[]{"ID", "Employee_ID", "TestLab_ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
    }

    public void set(List<Tester> models) {
        model.setRowCount(0);

        for (Tester el : models) {

            model.addRow(new Object[]{
                    el.getId(),
                    el.getEmployee().getName(),
                    el.getTestLab() != null ? el.getTestLab().getId() : "N/A",
            });
        }
    }

    public Tester getSelected() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            long id = (long) model.getValueAt(selectedRow, 0);

            return new BaseDao<Tester>(Tester.class).getById(id);
        }
        return null;
    }
}

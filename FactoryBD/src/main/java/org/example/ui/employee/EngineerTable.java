package org.example.ui.employee;

import org.example.dao.BaseDao;
import org.example.model.Engineer;
import org.example.ui.BaseTable;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class EngineerTable extends BaseTable<Engineer> {
    private DefaultTableModel model;

    public EngineerTable() {
        model = new DefaultTableModel(
                new Object[]{"ID", "Employee_ID", "Workshop_ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
    }

    public void set(List<Engineer> models) {
        model.setRowCount(0);

        for (Engineer el : models) {

            model.addRow(new Object[]{
                    el.getId(),
                    el.getEmployee().getName(),
                    el.getWorkshop() != null ? el.getWorkshop().getName() : "N/A",
            });
        }
    }

    public Engineer getSelected() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            long id = (long) model.getValueAt(selectedRow, 0);

            return new BaseDao<Engineer>(Engineer.class).getById(id);
        }
        return null;
    }
}

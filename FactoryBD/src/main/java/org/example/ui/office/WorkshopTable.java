package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.model.Workshop;
import org.example.ui.BaseTable;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class WorkshopTable extends BaseTable<Workshop> {
    private DefaultTableModel model;

    public WorkshopTable() {
        model = new DefaultTableModel(
                new Object[]{"ID", "Department_ID", "Name", "Head"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
    }

    public void set(List<Workshop> models) {
        model.setRowCount(0);

        for (Workshop el : models) {

            model.addRow(new Object[]{
                    el.getId(),
                    el.getDepartment().getName(),
                    el.getName(),
                    el.getHead().getName()
            });
        }
    }

    public Workshop getSelected() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            long id = (long) model.getValueAt(selectedRow, 0);

            return new BaseDao<Workshop>(Workshop.class).getById(id);
        }
        return null;
    }
}

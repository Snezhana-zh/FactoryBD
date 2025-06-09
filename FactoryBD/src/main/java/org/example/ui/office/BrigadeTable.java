package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.model.Brigade;
import org.example.ui.BaseTable;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BrigadeTable extends BaseTable<Brigade> {
    private DefaultTableModel model;

    public BrigadeTable() {
        model = new DefaultTableModel(
                new Object[]{"ID", "Head", "Workshop_ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
    }

    public void set(List<Brigade> models) {
        model.setRowCount(0);

        for (Brigade el : models) {

            model.addRow(new Object[]{
                    el.getId(),
                    el.getHead().getName(),
                    el.getWorkshop() != null ? el.getWorkshop().getId() : "N/A",
            });
        }
    }

    public Brigade getSelected() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            long id = (long) model.getValueAt(selectedRow, 0);

            return new BaseDao<Brigade>(Brigade.class).getById(id);
        }
        return null;
    }
}

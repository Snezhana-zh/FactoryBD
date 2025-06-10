package org.example.ui.logs;

import org.example.dao.BaseDao;
import org.example.model.ProductLog;
import org.example.ui.BaseTable;

import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProductLogsTable extends BaseTable<ProductLog> {
    private DefaultTableModel model;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ProductLogsTable() {
        model = new DefaultTableModel(
                new Object[]{"ID", "Product_ID", "Start_work", "End_work", "Workshop_ID", "TestLab_ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
    }

    public void set(List<ProductLog> models) {
        model.setRowCount(0);

        for (ProductLog el : models) {

            model.addRow(new Object[]{
                    el.getId(),
                    el.getProduct().getId().toString(),
                    el.getStartWork().format(formatter),
                    el.getEndWork() != null ? el.getEndWork().format(formatter) : "N/A",
                    el.getWorkshop() != null ? el.getWorkshop().getName() : "N/A",
                    el.getTestlab() != null ? el.getTestlab().getTitle() :  "N/A"
            });
        }
    }

    public ProductLog getSelected() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            long id = (long) model.getValueAt(selectedRow, 0);

            return new BaseDao<ProductLog>(ProductLog.class).getById(id);
        }
        return null;
    }
}

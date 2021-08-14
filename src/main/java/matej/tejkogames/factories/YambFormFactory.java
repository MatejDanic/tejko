package matej.tejkogames.factories;

import java.util.ArrayList;
import java.util.List;

import matej.tejkogames.models.yamb.Box;
import matej.tejkogames.models.yamb.BoxType;
import matej.tejkogames.models.yamb.Column;
import matej.tejkogames.models.yamb.ColumnType;
import matej.tejkogames.models.yamb.Form;

public class YambFormFactory {

    public static Form generateYambForm(int numberOfColumns, int numberOfDice) {

        List<Column> columnList = new ArrayList<>();
        for (int i = 1; i <= numberOfColumns; i++) {
            ColumnType columnType = ColumnType.FREE;
            List<Box> boxList = generateBoxList(columnType);

            Column column = new Column(columnType, boxList);
            columnList.add(column);
        }

        return new Form(columnList);
    }

    public static Form generateYambForm() {

        List<Column> columnList = new ArrayList<>();
        for (ColumnType columnType : ColumnType.values()) {
            List<Box> boxList = generateBoxList(columnType);

            Column column = new Column(columnType, boxList);
            columnList.add(column);
        }

        return new Form(columnList);
    }

    private static List<Box> generateBoxList(ColumnType columnType) {
        List<Box> boxList = new ArrayList<>();
        for (BoxType boxType : BoxType.values()) {
            boolean available = (columnType == ColumnType.DOWNWARDS && boxType == BoxType.ONES
                    || columnType == ColumnType.UPWARDS && boxType == BoxType.YAMB || columnType == ColumnType.FREE
                    || columnType == ColumnType.ANNOUNCEMENT);
            Box box = new Box(boxType, available);
            boxList.add(box);
        }
        return boxList;
    }

}

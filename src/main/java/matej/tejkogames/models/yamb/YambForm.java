package matej.tejkogames.models.yamb;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import matej.tejkogames.constants.YambConstants;

public class YambForm {

    private List<Column> columns;

    private int sumOne;

    private int sumTwo;

    private int sumThree;

    private int totalSum;
    
    private int availableBoxes;

    public YambForm(List<Column> columns) {
        this.columns = columns;
        this.sumOne = 0;
        this.sumTwo = 0;
        this.sumThree = 0;
        this.totalSum = 0;
        this.availableBoxes = this.columns.size() * YambConstants.NUMBER_OF_BOXES;
    }

    public YambForm() { }

    public List<Column> getColumns() {
        return this.columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
    
    public int getSumOne() {
        return this.sumOne;
    }

    public void setSumOne(int sumOne) {
        this.sumOne = sumOne;
    }
    
    public int getSumTwo() {
        return this.sumTwo;
    }

    public void setSumTwo(int sumTwo) {
        this.sumTwo = sumTwo;
    }

    public int getSumThree() {
        return this.sumThree;
    }

    public void setSumThree(int sumThree) {
        this.sumThree = sumThree;
    }

    public int getTotalSum() {
        return this.totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    public int getAvailableBoxes() {
        return this.availableBoxes;
    }

    public void setAvailableBoxes(int availableBoxes) {
        this.availableBoxes = availableBoxes;
    }

    @JsonIgnore
    public boolean isAnnouncementRequired() {
        for (Column column : this.columns) {
            if (column.getType() != ColumnType.ANNOUNCEMENT && !column.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public void updateSums() {
        this.sumOne = 0;
        this.sumTwo = 0;
        this.sumThree = 0;
        for (Column column : this.columns) {
            column.updateSums();
            this.sumOne += column.getSumOne();
            this.sumTwo += column.getSumTwo();
            this.sumThree += column.getSumThree();
        }
        this.totalSum = this.sumOne + this.sumTwo + this.sumThree;
    }

    public void updateSums(ColumnType columnType) {
        this.sumOne = 0;
        this.sumTwo = 0;
        this.sumThree = 0;
        Column col = this.getColumnByType(columnType);
        col.updateSums();
        for (Column column : this.columns) {
            this.sumOne += column.getSumOne();
            this.sumTwo += column.getSumTwo();
            this.sumThree += column.getSumThree();
        }
        this.totalSum = this.sumOne + this.sumTwo + this.sumThree;
    }

    public Column getColumnByType(ColumnType type) {
		for (Column column : this.columns) {
			if (column.getType() == type) {
				return column;
			}
		}
		return null;
	}
    
}

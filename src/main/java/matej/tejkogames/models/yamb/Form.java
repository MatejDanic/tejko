package matej.tejkogames.models.yamb;

import java.util.List;

import matej.tejkogames.constants.YambConstants;

public class Form {

    private List<Column> columns;
    private int numberSum;
    private int differenceSum;
    private int labelSum;
    private int finalSum;
    private int availableBoxes;

    public Form(List<Column> columns) {
        this.columns = columns;
        this.numberSum = 0;
        this.differenceSum = 0;
        this.labelSum = 0;
        this.finalSum = 0;
        this.availableBoxes = this.columns.size() * YambConstants.NUMBER_OF_BOXES;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
    
    public int getNumberSum() {
        return this.numberSum;
    }

    public void setNumberSum(int numberSum) {
        this.numberSum = numberSum;
    }
    
    public int getDifferenceSum() {
        return this.differenceSum;
    }

    public void setDifferenceSum(int differenceSum) {
        this.differenceSum = differenceSum;
    }

    public int getLabelSum() {
        return this.labelSum;
    }

    public void setLabelSum(int labelSum) {
        this.labelSum = labelSum;
    }

    public int getFinalSum() {
        return this.finalSum;
    }

    public void setFinalSum(int finalSum) {
        this.finalSum = finalSum;
    }

    public int getAvailableBoxes() {
        return this.availableBoxes;
    }

    public void setAvailableBoxes(int availableBoxes) {
        this.availableBoxes = availableBoxes;
    }

    public boolean isAnnouncementRequired() {
        for (Column column : this.columns) {
            if (column.getType() != ColumnType.ANNOUNCEMENT && !column.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public void updateSums() {
        this.numberSum = 0;
        this.differenceSum = 0;
        this.labelSum = 0;
        for (Column column : this.columns) {
            column.updateSums();
            this.numberSum += column.getNumberSum();
            this.differenceSum += column.getDifferenceSum();
            this.labelSum += column.getLabelSum();
        }
        this.finalSum = this.numberSum + this.differenceSum + this.labelSum;
    }

    public void UpdateSums(String columnTypeString) {
        this.numberSum = 0;
        this.differenceSum = 0;
        this.labelSum = 0;
        Column col = this.getColumnByType(columnTypeString);
        col.updateSums();
        for (Column column : this.columns) {
            this.numberSum += column.getNumberSum();
            this.differenceSum += column.getDifferenceSum();
            this.labelSum += column.getLabelSum();
        }
        this.finalSum = this.numberSum + this.differenceSum + this.labelSum;
    }

    public Column getColumnByType(String columnTypeString) {
		for (Column column : this.columns) {
			if (column.getType().name() == columnTypeString) {
				return column;
			}
		}
		return null;
	}
    
}

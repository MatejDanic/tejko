package matej.tejkogames.models.yamb;

import java.util.List;

public class Column {

    
    private ColumnType type;
    private List<Box> boxes;
    private int numberSum;
    private int differenceSum;
    private int labelSum;

    public Column(ColumnType type, List<Box> boxes) {
        this.type = type;
        this.boxes = boxes;
        this.numberSum = 0;
        this.differenceSum = 0;
        this.labelSum = 0;
    }

    public ColumnType getType() {
        return this.type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }
    
    public List<Box> getBoxes() {
        return this.boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
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

    public Box getBoxByType(String boxTypeString) {
		for (Box box : this.boxes) {
			if (box.getType().toString().equals(boxTypeString)) {
				return box;
			}
		}
		return null;
	}

    public boolean isFinished() {
        for (Box box : this.boxes) {
            if (!box.isFilled()) {
                return false;
            }
        }
        return true;
    }

    public void updateSums() {
        this.numberSum = 0;
        this.differenceSum = 0;
        this.labelSum = 0;
        for (Box box : this.boxes) {
            if (box.getType() == BoxType.ONES || box.getType() == BoxType.TWOS || 
                box.getType() == BoxType.THREES || box.getType() == BoxType.FOURS || 
                box.getType() == BoxType.FIVES || box.getType() == BoxType.SIXES) {
                numberSum += box.getValue();
            } else if (box.getType() == BoxType.TRIPS || box.getType() == BoxType.STRAIGHT || 
                        box.getType() == BoxType.BOAT || box.getType() == BoxType.CARRIAGE || 
                        box.getType() == BoxType.YAMB) {
                labelSum += box.getValue();
            }
        }
        Box ones = this.getBoxByType(BoxType.ONES.toString());
        Box max = this.getBoxByType(BoxType.MAX.toString());
        Box min = this.getBoxByType(BoxType.MIN.toString());
        if (ones.isFilled() && max.isFilled() && min.isFilled()) {
            this.differenceSum = ones.getValue() * (max.getValue() - min.getValue());
        }
    }
    
}

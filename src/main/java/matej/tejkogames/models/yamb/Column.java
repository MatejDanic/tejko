package matej.tejkogames.models.yamb;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Column {

    private ColumnType type;

    private List<Box> boxes;

    private int sumOne;

    private int sumTwo;

    private int sumThree;

    public Column(ColumnType type, List<Box> boxes) {
        this.type = type;
        this.boxes = boxes;
        this.sumOne = 0;
        this.sumTwo = 0;
        this.sumThree = 0;
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

    public Box getBoxByType(BoxType boxType) {
        for (Box box : this.boxes) {
            if (box.getType() == boxType) {
                return box;
            }
        }
        return null;
    }

    @JsonIgnore
    public boolean isFinished() {
        for (Box box : this.boxes) {
            if (!box.isFilled()) {
                return false;
            }
        }
        return true;
    }

    public void updateSums() {
        this.sumOne = 0;
        this.sumTwo = 0;
        this.sumThree = 0;
        for (Box box : this.boxes) {
            if (box.getType() == BoxType.ONES || box.getType() == BoxType.TWOS || box.getType() == BoxType.THREES
                    || box.getType() == BoxType.FOURS || box.getType() == BoxType.FIVES
                    || box.getType() == BoxType.SIXES) {
                sumOne += box.getValue();
            } else if (box.getType() == BoxType.TRIPS || box.getType() == BoxType.STRAIGHT
                    || box.getType() == BoxType.BOAT || box.getType() == BoxType.CARRIAGE
                    || box.getType() == BoxType.YAMB) {
                sumThree += box.getValue();
            }
        }
        Box ones = this.getBoxByType(BoxType.ONES);
        Box max = this.getBoxByType(BoxType.MAX);
        Box min = this.getBoxByType(BoxType.MIN);
        if (ones.isFilled() && max.isFilled() && min.isFilled()) {
            this.sumTwo = ones.getValue() * (max.getValue() - min.getValue());
        }
    }

}

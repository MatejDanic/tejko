package matej.tejkogames.models.yamb;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import matej.tejkogames.factories.YambFormFactory;

@Entity
@Table(name="game_yamb")
public class Yamb {

    private GameType type;
    private int numberOfColumns;
    private int numberOfDice;
    private Form form;
    private BoxType announcement;
    private Set<Dice> dice;
    private int rollCount;

    public Yamb(GameType type, int numberOfColumns, int numberOfDice) {
        if (type == GameType.CUSTOM) {
            this.form = YambFormFactory.generateYambForm(numberOfColumns, numberOfDice);
        } else {
            this.form = YambFormFactory.generateYambForm();
        }
        this.announcement = null;
        this.rollCount = 0;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public BoxType getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(BoxType announcement) {
        this.announcement = announcement;
    }

    public Set<Dice> getDice() {
        return dice;
    }

    public void setDice(Set<Dice> dice) {
        this.dice = dice;
    }

    public int getRollCount() {
        return rollCount;
    }

    public void setRollCount(int rollCount) {
        this.rollCount = rollCount;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfDice() {
        return numberOfDice;
    }

    public void setNumberOfDice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }

    public GameType getType() {
        return type;
    }

    public void setType(GameType type) {
        this.type = type;
    }

}

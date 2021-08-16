package matej.tejkogames.models.general.payload.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import matej.tejkogames.models.yamb.YambType;
import matej.tejkogames.constants.YambConstants;

public class YambRequest {

    @NotBlank
    private YambType type;
    
    @Min(YambConstants.NUMBER_OF_COLUMNS_MIN)
    @Max(YambConstants.NUMBER_OF_COLUMNS_MAX)
    private int numberOfColumns;
    
    @Min(YambConstants.NUMBER_OF_DICE_MIN)
    @Max(YambConstants.NUMBER_OF_DICE_MAX)
    private int numberOfDice;

    public YambType getType() {
        return type;
    }

    public void setType(YambType type) {
        this.type = type;
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
    
}

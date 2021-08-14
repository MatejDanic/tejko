package matej.tejkogames.models.yamb;

import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    
    private int value;
    private int order;
    private boolean held;

    public Dice(int order) {
        this.order = order;
        this.value = 6; 
        this.held = false;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isHeld() {
        return this.held;
    }

    public void setHeld(boolean held) {
        this.held = held;
    }    

	public void roll() {
		this.value = ThreadLocalRandom.current().nextInt(1, 7);
	}
	

}

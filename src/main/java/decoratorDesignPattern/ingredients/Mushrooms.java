package decoratorDesignPattern.ingredients;

import decoratorDesignPattern.Pizza;
import lombok.extern.log4j.Log4j;

@Log4j
public class Mushrooms extends ToppingDecorator {
    public Mushrooms(Pizza newPizza) {
        super(newPizza);

        log.info("Adding Mushrooms");
    }

    public String getDescription() {
        return tempPizza.getDescription() + ", Mushrooms";
    }

    public double getCost() {
        return tempPizza.getCost() + .65;
    }
}

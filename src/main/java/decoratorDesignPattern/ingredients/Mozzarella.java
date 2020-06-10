package decoratorDesignPattern.ingredients;

import decoratorDesignPattern.Pizza;
import lombok.extern.log4j.Log4j;

@Log4j
public class Mozzarella extends ToppingDecorator {
    public Mozzarella(Pizza newPizza) {
        super(newPizza);

        log.info("Adding Dough");
        log.info("Adding Mozzarella");
    }

    public String getDescription() {
        return tempPizza.getDescription() + ", Mozzarella";
    }

    public double getCost() {
        return tempPizza.getCost() + .50;
    }
}

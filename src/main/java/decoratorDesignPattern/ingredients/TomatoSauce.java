package decoratorDesignPattern.ingredients;

import decoratorDesignPattern.Pizza;
import lombok.extern.log4j.Log4j;

@Log4j
public class TomatoSauce extends ToppingDecorator {

    public TomatoSauce(Pizza newPizza) {
        super(newPizza);

        log.info("Adding Sauce");
    }

    public String getDescription() {
        return tempPizza.getDescription() + ", Tomato Sauce";
    }

    public double getCost() {
        return tempPizza.getCost() + .35;
    }
}

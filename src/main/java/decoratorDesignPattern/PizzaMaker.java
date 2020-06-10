package decoratorDesignPattern;

import decoratorDesignPattern.ingredients.Mozzarella;
import decoratorDesignPattern.ingredients.Mushrooms;
import decoratorDesignPattern.ingredients.TomatoSauce;
import lombok.extern.log4j.Log4j;

@Log4j
public class PizzaMaker {
    public static void main(String[] args) {
        Pizza basicPizza = new Mushrooms(new TomatoSauce(new Mozzarella(new PlainPizza())));

        log.info("Ingredients: " + basicPizza.getDescription());
        log.info("Price: " + basicPizza.getCost());
    }
}

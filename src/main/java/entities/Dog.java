package entities;

import interfaces.Animal;

/**
 * This class represents entities.Dog which implements interfaces.Animal
 * @author Diana_Aimbetova
 * @version 4.0
 */
public class Dog implements Animal {
    /**
     * Overrided method which prints info about entities.Dog playing
     */
    @Override
    public void play() {
        System.out.println("entities.Dog is playing");
    }
    /**
     * Overrided method which prints info about voice that entities.Dog makes
     */
    @Override
    public void voice() {
        System.out.println("Woof - woof");
    }
}

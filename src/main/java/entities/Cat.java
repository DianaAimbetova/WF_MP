package entities;

import interfaces.Animal;

/**
 * This class represents entities.Cat which implements interfaces.Animal
 * @author Diana_Aimbetova
 * @version 4.0
 */
public class Cat implements Animal {
    /**
     * Overrided method which prints info about entities.Cat playing
     */
    @Override
    public void play() {
        System.out.println("entities.Cat is playing");
    }
    /**
     * Overrided method which prints info about voice that entities.Cat makes
     */
    @Override
    public void voice() {
        System.out.println("Meow - meow");
    }
}

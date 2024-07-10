package ardev.example;

import ardev.annotation.IntensiveComponent;

/**
 * Пример класса, демонстрирующий использование {@link IntensiveComponent}.
 * Этот класс управляется {@link ardev.core.IntensiveContext}.
 */
@IntensiveComponent
public class SomeClass1 {

    /**
     * Метод, выполняющий действие для демонстрации функциональности.
     */
    public void run() {
        System.out.println("SomeClass1 running");
    }
}
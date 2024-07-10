package ardev;

import ardev.core.IntensiveContext;
import ardev.example.SomeClass1;

/**
 * Главная точка входа в приложение, демонстрирующая использование {@link IntensiveContext}.
 */
public class Main {
    public static void main(String[] args) {
        try {
            IntensiveContext context = new IntensiveContext("ardev.example");
            SomeClass1 class1 = context.getObject(SomeClass1.class);
            class1.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

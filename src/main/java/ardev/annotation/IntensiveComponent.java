package ardev.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, используемая для обозначения классов, которые должны управляться IntensiveContext.
 * Классы, отмеченные этой аннотацией, могут быть автоматически обнаружены и управляемы
 * контейнером внедрения зависимостей.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntensiveComponent {
}
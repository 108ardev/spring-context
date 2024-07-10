package ardev.service;

import ardev.annotation.IntensiveComponent;
import ardev.core.IntensiveContext;

import java.lang.reflect.Field;

/**
 * Реализация интерфейса {@link InjectionService}.
 * Обрабатывает внедрение зависимостей в экземпляры бинов.
 */
public class InjectionServiceImpl implements InjectionService {

    /**
     * Внедряет зависимости в предоставленный экземпляр, используя рефлексию.
     * Зависимости идентифицируются по аннотации {@link IntensiveComponent} на полях.
     *
     * @param <T> тип экземпляра
     * @param instance экземпляр, в который следует внедрить зависимости
     * @param context контекст, управляющий зависимостями
     * @throws Exception если внедрение зависимостей не удалось
     */
    @Override
    public <T> void injectDependencies(T instance, IntensiveContext context) throws Exception {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(IntensiveComponent.class)) {
                Class<?> fieldType = field.getType();
                Object fieldInstance = context.getObject(fieldType);
                field.setAccessible(true);
                field.set(instance, fieldInstance);
            }
        }
    }
}
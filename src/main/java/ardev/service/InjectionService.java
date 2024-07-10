package ardev.service;

import ardev.annotation.IntensiveComponent;
import ardev.core.IntensiveContext;

/**
 * Интерфейс сервиса для инъекции зависимостей в экземпляры бинов.
 */
public interface InjectionService {

    /**
     * Внедряет зависимости в предоставленный экземпляр с помощью рефлексии.
     * Зависимости идентифицируются по аннотации {@link IntensiveComponent} на полях.
     *
     * @param <T> тип экземпляра
     * @param instance экземпляр, в который следует внедрить зависимости
     * @param context контекст, управляющий зависимостями
     * @throws Exception если инъекция зависимостей не удалась
     */
    <T> void injectDependencies(T instance, IntensiveContext context) throws Exception;
}
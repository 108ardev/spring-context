package ardev.core;

import ardev.annotation.IntensiveComponent;
import ardev.factory.DependencyFactory;
import ardev.service.InjectionService;
import ardev.service.InjectionServiceImpl;
import ardev.service.SearchService;
import ardev.service.SearchServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Основной класс фреймворка внедрения зависимостей.
 * Этот класс отвечает за инициализацию и управление бинами, аннотированными {@link IntensiveComponent}.
 */
public class IntensiveContext {
    private final SearchService searchService;
    private final InjectionService injectionService;
    private final DependencyFactory dependencyFactory;
    private final Map<Class<?>, List<Class<?>>> componentClasses = new HashMap<>();
    private final String basePackage;

    /**
     * Конструктор создает новый контекст, который будет сканировать указанный пакет на наличие компонентов.
     *
     * @param basePackage корневой пакет для сканирования компонентов
     * @throws Exception если во время обнаружения компонентов произошла ошибка
     */
    public IntensiveContext(String basePackage) throws Exception {
        this.basePackage = basePackage;
        this.searchService = new SearchServiceImpl();
        this.injectionService = new InjectionServiceImpl();
        this.dependencyFactory = new DependencyFactory();
        discoverComponents();
    }

    /**
     * Открывает компоненты, аннотированные {@link IntensiveComponent}, в указанном пакете.
     *
     * @throws Exception если во время сканирования произошла ошибка
     */
    private void discoverComponents() throws Exception {
        List<Class<?>> classes = searchService.findClasses(basePackage);
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(IntensiveComponent.class)) {
                // Регистрация всех интерфейсов, которые реализует класс
                Class<?>[] interfaces = clazz.getInterfaces();
                if (interfaces.length == 0) {
                    // Если класс не реализует интерфейс, регистрируем сам класс
                    componentClasses.computeIfAbsent(clazz, _ -> new ArrayList<>()).add(clazz);
                } else {
                    // Регистрация всех реализованных интерфейсов
                    for (Class<?> iface : interfaces) {
                        componentClasses.computeIfAbsent(iface, _ -> new ArrayList<>()).add(clazz);
                    }
                }
            }
        }
    }

    /**
     * Получает экземпляр указанного типа.
     *
     * @param <T> параметр типа
     * @param type класс параметра типа
     * @return экземпляр запрошенного типа
     * @throws Exception если тип не найден или не может быть создан
     */
    public <T> T getObject(Class<T> type) throws Exception {
        List<Class<?>> implementations = componentClasses.get(type);

        if (implementations == null) {
            throw new RuntimeException("No component found for: " + type);
        }
        if (implementations.size() > 1) {
            throw new IllegalStateException("Multiple implementations found for interface: " + type.getCanonicalName());
        }

        Class<?> implClass = implementations.getFirst();
        Object instance = dependencyFactory.createInstance(implClass);
        if (!type.isInstance(instance)) {
            throw new IllegalArgumentException("The component does not match the requested type: " + type);
        }
        injectionService.injectDependencies(instance, this);
        return type.cast(instance);
    }
}
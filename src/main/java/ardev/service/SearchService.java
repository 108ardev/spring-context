package ardev.service;

import ardev.utils.FindUtils;

import java.util.List;

/**
 * Интерфейс сервиса для поиска классов в пакете.
 */
public interface SearchService {

    /**
     * Находит все классы в указанном базовом пакете, которые можно загрузить с помощью загрузчика классов.
     * Этот метод использует {@link FindUtils} для выполнения фактического поиска в файловой системе.
     *
     * @param basePackage базовый пакет для поиска
     * @return список классов, найденных в пакете
     * @throws Exception если поиск не удался
     */
    List<Class<?>> findClasses(String basePackage) throws Exception;
}
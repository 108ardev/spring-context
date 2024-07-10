package ardev.service;

import ardev.utils.FindUtils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Реализация интерфейса {@link SearchService}.
 * Отвечает за поиск классов в указанном пакете, используя загрузчик классов.
 */
public class SearchServiceImpl implements SearchService {

    /**
     * Ищет все классы в указанном базовом пакете, которые можно загрузить с помощью загрузчика классов.
     * Этот метод использует {@link FindUtils} для проведения реального поиска в файловой системе.
     *
     * @param basePackage базовый пакет для поиска
     * @return список классов, найденных в пакете
     * @throws Exception если поиск не удался
     */
    @Override
    public List<Class<?>> findClasses(String basePackage) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = basePackage.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(FindUtils.findClasses(directory, basePackage));
        }

        return classes;
    }
}
package ardev.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Утилитный класс для помощи в поиске классов в директории файловой системы.
 * Этот класс используется преимущественно {@link ardev.service.SearchServiceImpl} для сканирования директорий в поисках классов.
 */
public class FindUtils {

    /**
     * Находит все классы в данной директории и названии пакета.
     * Этот метод рекурсивно исследует директории на наличие классов, оканчивающихся на ".class".
     *
     * @param directory директория для поиска
     * @param packageName название пакета для классов, найденных в директории
     * @return список классов, найденных в директории
     * @throws ClassNotFoundException если класс не удается загрузить
     */
    public static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
package ardev.factory;

import java.lang.reflect.Constructor;

public class DependencyFactory {
    public <T> T createInstance(Class<T> implClass) throws Exception {
        Constructor<T> constructor = implClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }
}
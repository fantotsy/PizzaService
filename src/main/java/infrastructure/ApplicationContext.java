package infrastructure;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext implements Context {
    private final Config config;
    private final Map<String, Object> beans = new HashMap<>();

    public ApplicationContext(Config config) {
        this.config = config;
    }

    @Override
    public <T> T getBean(String beanName) {
        if (beans.containsKey(beanName)) {
            return (T) beans.get(beanName);
        }
        Class<?> type = config.getImpl(beanName);
        Constructor<?> constructor = type.getConstructors()[0];
        int numberOfParameters = constructor.getParameterCount();
        if (numberOfParameters == 0) {
            try {
                T bean = (T) type.newInstance();
                return putBeanAndReturnIt(beanName, bean);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] params = new Object[numberOfParameters];
            for (int i = 0; i < numberOfParameters; i++) {
                String parameterBeanName = convertTypeToBeanName(parameterTypes[i]);
                params[i] = getBean(parameterBeanName);
            }
            try {
                T bean = (T) constructor.newInstance(params);
                return putBeanAndReturnIt(beanName, bean);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public String convertTypeToBeanName(Class<?> type) {
        String className = type.getSimpleName();
        char[] classNameArray = className.toCharArray();
        classNameArray[0] = Character.toLowerCase(classNameArray[0]);
        return new String(classNameArray);
    }

    private <T> T putBeanAndReturnIt(String beanName, T bean) {
        beans.put(beanName, bean);
        return bean;
    }
}
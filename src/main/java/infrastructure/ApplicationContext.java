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
        Class<?> type = config.getImpl(beanName);
        Object bean = beans.get(beanName);

        if (bean != null) {
            return (T) bean;
        }

        BeanBuilder<T> builder = new BeanBuilder<>(type);
        builder.createBean();
        bean = builder.build();
        beans.put(beanName, bean);
        return (T) bean;
    }

    class BeanBuilder<T> {
        private final Class<?> type;
        private T bean;

        BeanBuilder(Class<?> type) {
            this.type = type;
        }

        private void createBean() {
            Constructor<?> constructor = type.getConstructors()[0];
            if (constructor.getParameterCount() == 0) {
                try {
                    this.bean = (T) type.newInstance();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                Object[] params = getConstructorParameterBeans(constructor);
                try {
                    this.bean = (T) constructor.newInstance(params);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        private T build() {
            return bean;
        }

        private Object[] getConstructorParameterBeans(Constructor constructor) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            int numberOfParameters = constructor.getParameterCount();
            Object[] params = new Object[numberOfParameters];
            for (int i = 0; i < numberOfParameters; i++) {
                String parameterBeanName = convertTypeToBeanName(parameterTypes[i]);
                params[i] = getBean(parameterBeanName);
            }
            return params;
        }
    }

    public String convertTypeToBeanName(Class<?> type) {
        String className = type.getSimpleName();
        char[] classNameArray = className.toCharArray();
        classNameArray[0] = Character.toLowerCase(classNameArray[0]);
        return new String(classNameArray);
    }
}
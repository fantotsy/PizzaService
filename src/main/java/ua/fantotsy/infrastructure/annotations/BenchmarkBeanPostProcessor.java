package ua.fantotsy.infrastructure.annotations;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class BenchmarkBeanPostProcessor implements BeanPostProcessor {
    private Map<String, List<Method>> methodsWithBenchmark = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (hasMethodAnnotation(Benchmark.class, bean)) {
            Method[] beanMethods = bean.getClass().getMethods();
            List<Method> benchmarkMethods = new ArrayList<>();
            for (Method method : beanMethods) {
                if (method.isAnnotationPresent(Benchmark.class) && method.getAnnotation(Benchmark.class).value()) {
                    benchmarkMethods.add(method);
                }
            }
            methodsWithBenchmark.put(beanName, benchmarkMethods);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (methodsWithBenchmark.containsKey(beanName)) {
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    for (Method beanMethod : methodsWithBenchmark.get(beanName)) {
                        if (isSame(method, beanMethod)) {
                            long start = System.nanoTime();
                            Object result = method.invoke(bean, args);
                            long finish = System.nanoTime();
                            System.out.println("Method: " + method.getName() + ". Time: " + (finish - start));
                            return result;
                        }
                    }
                    return method.invoke(bean, args);
                }
            });
        }
        return bean;
    }

    public boolean isSame(Method method1, Method method2) {
        if (!method1.getName().equals(method2.getName())) {
            return false;
        }
        Class<?>[] parameters1 = method1.getParameterTypes();
        Class<?>[] parameters2 = method2.getParameterTypes();
        if (parameters1.length != parameters2.length) {
            return false;
        }
        for (int i = 0; i < parameters1.length; i++) {
            if (!parameters1[i].getCanonicalName().equals(parameters2[i].getCanonicalName())) {
                return false;
            }
        }
        return true;
    }

    private Class<?>[] getDeclaredClasses(Object bean) {
        List<Class<?>> classes = new ArrayList<>();
        Class<?> clazz = bean.getClass();
        while (clazz != null) {
            Collections.addAll(classes, clazz);
            clazz = clazz.getSuperclass();
        }
        return classes.stream().toArray(Class<?>[]::new);
    }

    public boolean hasMethodAnnotation(Class<? extends Annotation> annotationClass, Object bean) {
        Class<?>[] classes = getDeclaredClasses(bean);
        for (Class item : classes) {
            Method[] methods = item.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(annotationClass)) {
                    return true;
                }
            }
        }
        return false;
    }
}
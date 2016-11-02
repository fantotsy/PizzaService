package ua.fantotsy.infrastructure.annotations;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ua.fantotsy.infrastructure.annotations.Benchmark;
import ua.fantotsy.services.pizza.PizzaService;
import ua.fantotsy.services.pizza.SimplePizzaService;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

//@Component
public class BenchmarkBeanPostProcessor<T> implements BeanPostProcessor {

    Map<String, List<Method>> methodsWithAnnotation = new HashMap<>();
    //String benchMarkBean = null;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (hasMethodAnnotation(Benchmark.class, bean)) {
            Method[] methods = bean.getClass().getMethods();
            List<Method> benchmarkMethods = new ArrayList<>();
            for (Method m : methods) {
                if (m.isAnnotationPresent(Benchmark.class)) {
                    benchmarkMethods.add(m);
                    System.out.println("present");
                }
            }
            methodsWithAnnotation.put(beanName, benchmarkMethods);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (methodsWithAnnotation.containsKey(beanName)) {
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println(method.toString());
                    for (Method beanMethod : methodsWithAnnotation.get(beanName)) {

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
        if(!method1.getName().equals(method2.getName())){
            return false;
        }
        Class<?>[] parameters1 = method1.getParameterTypes();
        Class<?>[] parameters2 = method2.getParameterTypes();
        if (parameters1.length != parameters2.length) {
            return false;
        }
        for (int i = 0; i < parameters1.length; i++) {
            if (!parameters1[i].getCanonicalName().equals(parameters2[i].getCanonicalName())){
                return false;
            }
        }
        return true;
    }

//    public T createBeanProxy(Object bean, String beanName) {
//        if (benchMarkBean != null && benchMarkBean.equals(beanName)) {
//            System.out.println("Bench " + bean);
//            T original = (T) bean;
////            Class<?> type = bean.getClass();
//            Class<?>[] declaredInterfaces = getDeclaredInterfaces(bean);
////            System.out.println("Declared interfaces" + Arrays.toString(declaredInterfaces));
//            bean = (T) Proxy.newProxyInstance(bean.getClass().getClassLoader(), declaredInterfaces,
//                    new InvocationHandler() {
//                        @Override
//                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                            System.out.println("BENCHH");
//                            return proxy;
////                            Method beanMethod = type.getMethod(method.getName(), method.getParameterTypes());
////                            System.out.println(beanMethod);
////                            if (isBenchMarkAnnotationPresentAndTrue(beanMethod)) {
////                                System.out.println("BenchmarkAnnot " + type);
////                                return wrapMethodInBenchmark(original, beanMethod, args);
////                            } else return beanMethod.invoke(original, args);
//                        }
//                    });
//        }
//        return (T) bean;
//    }

    private Class<?>[] getDeclaredInterfaces(Object o) {
        List<Class<?>> interfaces = new ArrayList<>();
        Class<?> klazz = o.getClass();
        while (klazz != null) {
            Collections.addAll(interfaces, klazz.getInterfaces());
            klazz = klazz.getSuperclass();
        }
        return interfaces.stream().toArray(Class<?>[]::new);
    }

    private Class<?>[] getDeclaredClasses(Object o) {

        System.out.println("YES:" + (o instanceof SimplePizzaService));

        List<Class<?>> classes = new ArrayList<>();
        Class<?> klazz = o.getClass();
        while (klazz != null) {
            Collections.addAll(classes, klazz);
            klazz = klazz.getSuperclass();
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

    public boolean isBenchMarkAnnotationPresentAndTrue(Method beanMethod) {
        return beanMethod.isAnnotationPresent(Benchmark.class) && beanMethod.getAnnotation(Benchmark.class).value();
    }


    public <T> Object wrapMethodInBenchmark(T original, Method beanMethod, Object[] args)
            throws InvocationTargetException, IllegalAccessException {
        long start = System.nanoTime();
        Object invoke = beanMethod.invoke(original, args);
        long end = System.nanoTime();
        System.out.println(String.format("Method '%s', execution time %d", beanMethod.getName(), end - start));
        return invoke;
    }
}
package ua.fantotsy.infrastructure.annotations;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ua.fantotsy.infrastructure.annotations.Benchmark;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@Component
public class BenchmarkBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;

    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class type = bean.getClass();

        if (type.isAnnotationPresent(Benchmark.class)) {

            Object proxy = Proxy.newProxyInstance(type.getClassLoader(),type.getInterfaces(),new InvocationHandler() {

                @Override

                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    long before = System.nanoTime();

                    Object retVal = method.invoke(bean, args);

                    long after = System.nanoTime();

                    System.out.println("метод работал: "+(after-before)+" наносекунд");

                    return retVal;

                }

            });

            return proxy;

        } else {

            return bean;

        }
//        Method[] beanMethods = bean.getClass().getMethods();
//        for (Method beanMethod : beanMethods) {
//            Benchmark annotation = beanMethod.getAnnotation(Benchmark.class);
//            if (annotation != null && annotation.value()) {
//                Object targetBean = bean;
//                bean = Proxy.newProxyInstance(bean.getClass().getClassLoader(), getAllDeclaredInterfaces(bean), new InvocationHandler() {
//                    @Override
//                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                        Benchmark annotation = targetBean.getClass().getMethod(method.getName(), method.getParameterTypes()).getAnnotation(Benchmark.class);
//                        if (annotation != null && annotation.value()) {
//                            long startTime = System.nanoTime();
//                            Object result = method.invoke(targetBean, args);
//                            System.out.println(method.getName() + " " + (System.nanoTime() - startTime));
//                            return result;
//                        } else {
//                            return method.invoke(targetBean, args);
//                        }
//                    }
//                });
//            }
//        }
    }

    private Class<?>[] getAllDeclaredInterfaces(Object o) {
        List<Class<?>> interfaces = new ArrayList<>();
        Class<?> clazz = o.getClass();
        while (clazz != null) {
            Collections.addAll(interfaces, clazz.getInterfaces());
            clazz = clazz.getSuperclass();
        }
        return interfaces.toArray(new Class<?>[interfaces.size()]);
    }
}
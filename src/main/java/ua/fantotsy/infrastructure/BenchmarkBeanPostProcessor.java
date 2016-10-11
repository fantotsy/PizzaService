package ua.fantotsy.infrastructure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ua.fantotsy.infrastructure.annotations.BenchMark;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BenchmarkBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] beanMethods = bean.getClass().getMethods();
        for (Method beanMethod : beanMethods) {
            if (beanMethod.isAnnotationPresent(BenchMark.class)) {
                Object targetBean = bean;
                bean = Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.isAnnotationPresent(BenchMark.class)) {
                            long startTime = System.nanoTime();
                            Object result = method.invoke(targetBean, args);
                            System.out.println(method.getName() + " " + (System.nanoTime() - startTime));
                            return result;
                        } else {
                            return method.invoke(targetBean, args);
                        }
                    }
                });
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
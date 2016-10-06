package ua.fantotsy.infrastructure.context;

public interface Context {
    <T> T getBean(String beanName);
}
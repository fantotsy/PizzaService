package infrastructure;

public interface Context {
    <T> T getBean(String beanName);
}
package infrastructure.config;

public interface Config {
    Class<?> getImpl(String name);
}
package ua.fantotsy.infrastructure.config;

public interface Config {
    Class<?> getImpl(String name);
}
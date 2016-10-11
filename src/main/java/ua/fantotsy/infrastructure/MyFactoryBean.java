package ua.fantotsy.infrastructure;

import org.springframework.beans.factory.FactoryBean;
import ua.fantotsy.domain.Pizza;

public class MyFactoryBean implements FactoryBean<Pizza> {

    @Override
    public Pizza getObject() throws Exception {
        return new Pizza("name", 100.0, Pizza.PizzaTypes.MEAT);
    }

    @Override
    public Class<?> getObjectType() {
        return Pizza.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
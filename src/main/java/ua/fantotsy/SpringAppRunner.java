package ua.fantotsy;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.fantotsy.repository.pizza.PizzaRepository;

import java.util.Arrays;

public class SpringAppRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        PizzaRepository pizzaRepository = (PizzaRepository) context.getBean("pizzaRepository");
        System.out.println(pizzaRepository.getPizzaById(1));

        context.close();
    }
}
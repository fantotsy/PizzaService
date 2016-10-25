package ua.fantotsy;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.repository.pizza.JpaPizzaRepository;
import ua.fantotsy.repository.pizza.PizzaRepository;
import ua.fantotsy.services.order.OrderService;

import java.util.Arrays;

public class SpringJpaAppRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext("repoContext.xml");
        System.out.println(Arrays.toString(repoContext.getBeanDefinitionNames()));

        ConfigurableApplicationContext appContext =
                new ClassPathXmlApplicationContext(new String[]{"appContext.xml"}, repoContext);
        System.out.println(Arrays.toString(appContext.getBeanDefinitionNames()));

        PizzaRepository pizzaRepository = (PizzaRepository) appContext.getBean("pizzaRepository");
        Pizza pizza = new Pizza("Sea", 200.0, Pizza.PizzaTypes.SEA);

        pizza = pizzaRepository.save(pizza);
        System.out.println(pizza);

        repoContext.close();
        appContext.close();
    }
}
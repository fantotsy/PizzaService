package ua.fantotsy.services.pizza;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.repository.pizza.PizzaRepository;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SimplePizzaServiceTest {
    SimplePizzaService pizzaService;
    @Mock
    PizzaRepository pizzaRepositoryMock;
    @Mock
    Pizza pizzaMock;

    @Before
    public void setUp() {
        pizzaService = spy(new SimplePizzaService(pizzaRepositoryMock));
    }

    @Test(expected = RuntimeException.class)
    public void testAddNewPizzaNotAllowedPrice() {
        pizzaService.addNewPizza("Name", 0.0, Pizza.PizzaType.MEAT);
    }

    @Test
    public void testAddNewPizzaAddsPizza() {
        doReturn(pizzaMock).when(pizzaService).createNewPizza();
        pizzaService.addNewPizza("Name", 100.0, Pizza.PizzaType.SEA);
        verify(pizzaRepositoryMock).save(pizzaMock);
    }
}
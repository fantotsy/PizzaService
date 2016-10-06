package ua.fantotsy.services;

public class Test1SomeService implements SomeService {

    @Override
    public String getString() {
        return "Test1";
    }

    public void destroy() {
        System.out.println("Destroy");
    }
}
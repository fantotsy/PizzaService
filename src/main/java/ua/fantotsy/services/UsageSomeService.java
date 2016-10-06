package ua.fantotsy.services;

public class UsageSomeService {
    private SomeService someService;

    public UsageSomeService(SomeService someService) {
        this.someService = someService;
    }

    public void init() {
        System.out.println("Call: " + someService.getString());
    }
}
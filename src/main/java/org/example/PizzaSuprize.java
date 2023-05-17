package org.example;

public class PizzaSuprize {
    private boolean isValid = true;
    private Employee _employee;

    public PizzaSuprize(Employee _employee) {
        this._employee = _employee;
    }

    public void activateSurprise() {
        if(isValid) {
            System.out.printf("-=* Dear %s %s, you got a surprise! Congratulations! *=- \n", _employee.get_firstName(), _employee.get_lastName());
            isValid = false;
        } else {
            System.out.printf("%s %s, Your surprise expired!!!!!!!!!!!!!! \n", _employee.get_firstName(), _employee.get_lastName());
        }

    }
}

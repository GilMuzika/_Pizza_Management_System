package org.example;

public class PizzaHut extends PizzaStore {


    public PizzaHut(String _id, String _phoneNumber) {
        super(_id, _phoneNumber);
    }

    @Override
    protected int calculateRank() {
        int ranksSum = super.calculateRank();
        if(_numberOfEmployees != 0)
            return ranksSum / _numberOfEmployees;
        else
            return 0;
    }
}

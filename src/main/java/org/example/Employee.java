package org.example;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Employee {
    @Getter
    private String _id;
    @Getter
    private String _firstName;
    @Getter
    private String _lastName;
    @Getter
    private String _address;
    @Getter
    @Setter
    private int _salary = 0;
    @Getter
    @Setter
    private LocalDateTime _startDate;
    @Getter
    private String _phoneNumber;
    @Getter
    public EmployeeRank _rank;
    @Getter
    private PizzaSuprize _surprise;

    public Employee(String _id, String _firstName, String _lastName, String _address, String phoneNumber) throws Exception {
        this._id = _id;
        this._firstName = _firstName;
        this._lastName = _lastName;
        this._address = _address;
        this._phoneNumber = phoneNumber;

        this._rank = new EmployeeRank();
    }


    public void setRank(Class<?> caller) throws Exception {
        StackWalker instance = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        Class<?> callerClass = instance.getCallerClass();
        if(!callerClass.equals(PizzaStore.class))
            throw new RuntimeException("Only a pizza store can set an employee rank");

        this._rank._rankGiver = "by " + caller.getSimpleName();
        Random r = new Random();
        this._rank._actualRank = r.nextInt(1, 100);
        this._rank._rankValid = true;
        long randomDay = ThreadLocalRandom.current().nextLong(LocalDate.now().toEpochDay() - 365, LocalDate.now().toEpochDay());
        this._rank._rankingTime = LocalDate.ofEpochDay(randomDay).atStartOfDay();
    }

    public void takeSurpriseFromPizzaStore(PizzaSuprize s) {
        StackWalker instance = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        Class<?> callerClass = instance.getCallerClass();
        if(!callerClass.getName().equals(PizzaStore.class.getName()))
            throw new RuntimeException("Only pizza store can give or take surprises");

        _surprise = s;
    }
    public void giveSurpriseToPizzaStore() {
        StackWalker instance = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        Class<?> callerClass = instance.getCallerClass();
        if(!callerClass.getName().equals(PizzaStore.class.getName()))
            throw new RuntimeException("Only pizza store can give or take surprises");

        _surprise = null;
    }

    public class EmployeeRank {

        @Getter
        @Setter
        private int _actualRank;
        @Getter
        @Setter
        private LocalDateTime _rankingTime;
        @Getter
        @Setter
        private boolean _rankValid;
        private String _rankGiver = "at birth";

        public EmployeeRank() {
            this._actualRank = 1;
            this._rankValid = true;
            this._rankingTime = LocalDateTime.now();
        }

        @Override
        public String toString() {
            return String.format("The rank of employee %s %s is: %s, given %s, the rank is %s, the ranking time: %s, ",
                    _firstName, _lastName, this._actualRank, _rankGiver, this._rankValid ? "valid" : "invalid", this._rankingTime
                    );
        }
    }


}

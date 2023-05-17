package org.example;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class PizzaStore {
    @Getter
    protected String _id;
    @Getter
    protected String _name;
    protected int _numberOfEmployees;
    @Getter
    protected ArrayList<Employee> _storeEmployees = new ArrayList<>();
    @Getter
    protected String _phoneNumber;
    @Getter
    protected int _rank;

    public PizzaStore() {
        _numberOfEmployees = _storeEmployees.size();
        _name = this.getClass().getSimpleName();
        _rank = calculateRank();
    }

    public PizzaStore(String _id, String _phoneNumber) {
        this();
        this._id = _id;
        this._phoneNumber = _phoneNumber;
    }

    protected int calculateEmployeeExpenses() {
        int expenses = 0;
        for(Employee one : _storeEmployees) {
            expenses += one.get_salary();
        }
        return expenses;
    }

    public void hireEmployee(Employee employee) throws Exception {
        if(_storeEmployees.contains(employee))
            return;
        Random r = new Random();

        employee.set_startDate(LocalDateTime.now());
        employee.set_salary(r.nextInt(100, 200));
        employee.setRank(this.getClass());
        _storeEmployees.add(employee);
        _numberOfEmployees = _storeEmployees.size();
        _rank = calculateRank();
    }
    public void resignEmployee(Employee employee){
        if(!_storeEmployees.contains(employee))
            return;

        employee.giveSurpriseToPizzaStore();
        _storeEmployees.remove(employee);
        _numberOfEmployees = _storeEmployees.size();
        _rank = calculateRank();
    }
    public int get_numberOfEmployees() {
        return _storeEmployees.size();
    }

    public void calculateRangedRank(int range) {
        for(Employee one : _storeEmployees) {
           long rankingDay = one.get_rank().get_rankingTime().toLocalDate().toEpochDay();
           long currentDay = LocalDate.now().toEpochDay();
           if(currentDay - rankingDay > range) //inplicit type conversion
               one.get_rank().set_rankValid(false);
           else
               one.get_rank().set_rankValid(true);
        }
    }

    protected int calculateRank() {
        int sum = 0;
        List<Integer> ranks = _storeEmployees.stream().map(x -> x.get_rank().get_actualRank()).collect(Collectors.toList());
        for(int r : ranks) {
            sum += r;
        }
        return sum;
    }

    public void giveSurpriseToTheBest(int rankRange) {
        this.calculateRangedRank(rankRange);
        for(Employee emp : _storeEmployees) {
            if(emp.get_rank().is_rankValid() && emp.get_rank().get_actualRank() > 50) {
                emp.takeSurpriseFromPizzaStore(new PizzaSuprize(emp));
            }
        }
    }
}

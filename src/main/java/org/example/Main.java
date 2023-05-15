package org.example;

import auxiliaryClasses.EmployeeGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");

        EmployeeGenerator generator = new EmployeeGenerator();


        PizzaDominos pizzaDominos = generator.getPizzaStrore(PizzaDominos.class);
        PizzaHut pizzaHut = generator.getPizzaStrore(PizzaHut.class);
        ArrayList<Employee> employees = new ArrayList<>();
        for(int i = 0 ; i < 20; i++) {
            Employee employee1 = generator.getRandomEmployee();
            pizzaDominos.hireEmployee(employee1);

            Employee employee2 = generator.getRandomEmployee();
            pizzaHut.hireEmployee(employee2);

            employees.add(employee1);
            employees.add(employee2);
        }

        pizzaDominos.giveSurpriseToTheBest(1);

        for(Employee one : employees) {
            if(one.get_surprise() != null) {
                one.get_surprise().activateSurprize();
            }
        }

        System.out.println();
        System.out.printf("Pizza Hut: Calculate Employee Expenses = %s₪\n" ,pizzaHut.calculateEmployeeExpenses());
        System.out.printf("Pizza Dominos: Calculate Employee Expenses = %s₪\n" ,pizzaDominos.calculateEmployeeExpenses());

        System.out.println();
        System.out.printf("Pizza Hut Calculate Ranks:  %s\n" ,pizzaHut.calculateRank());
        System.out.printf("Pizza Dominos Calculate Ranks:  %s\n" ,pizzaDominos.calculateRank());
        System.out.println();

        pizzaHut.calculateRangedRank(10);
        pizzaDominos.calculateRangedRank(20);
        for(Employee emlpyee : employees) {
            System.out.println(emlpyee.get_rank());
        }
        Employee e = generator.getRandomEmployee();
        System.out.println(e.get_rank());
    }
}
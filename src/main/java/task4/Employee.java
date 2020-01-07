package task4;

/**
 * Created by Diana Aimbetova on 1/7/2020
 **/

public class Employee {
    String name;
    Integer id,salary,level;

    public Employee(int id, String name, int level
                    ) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee with id=" + id + ", name=" + name + ", salary=" + salary;
    }

}

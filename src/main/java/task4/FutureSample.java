package task4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author Diana_Aimbetova
 */
public class FutureSample {
    public static void main(String[] args) {
        hiredEmployees()
                .thenCompose(employees -> {
                    List<CompletionStage<Employee>> updatedEmployees = employees.stream()
                            .map(employee ->
                                    getSalary(employee.level)
                                            .thenApplyAsync(newSalary -> {
                                                employee.setSalary(newSalary);
                                                return employee;
                                            }))
                            .collect(Collectors.toList());

                    CompletableFuture<Void> finalEmployees = CompletableFuture
                            .allOf(updatedEmployees
                                    .toArray(new CompletableFuture[updatedEmployees.size()]));

                    return finalEmployees.thenApply(v -> updatedEmployees
                            .stream()
                            .map(CompletionStage::toCompletableFuture)
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList()));
                }).whenComplete((employees, th) -> {
            if (th == null) {
                employees.forEach(System.out::println);
            } else {
                throw new RuntimeException(th);
            }
        }).toCompletableFuture()
                .join();

    }

    static CompletionStage<Integer> getSalary(int level) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                simulateDelay();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            switch (level) {
                case 1:
                    return 1000;
                case 2:
                    return 2000;
                case 3:
                    return 3000;
                default:
                    return 500;
            }
        });
    }

    static CompletionStage<List<Employee>> hiredEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        Employee e1 = new Employee(1, "Alex", 1);
        e1.setSalary(500);
        Employee e2 = new Employee(2, "Beth", 2);
        e2.setSalary(500);
        Employee e3 = new Employee(3, "James", 3);
        e3.setSalary(500);
        employeeList.add(e1);
        employeeList.add(e2);
        employeeList.add(e3);
        return CompletableFuture.supplyAsync(() -> employeeList);
    }

    private static void simulateDelay() throws InterruptedException {
        Thread.sleep(5000);
    }

}

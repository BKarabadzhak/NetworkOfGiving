package org.finaltask.networkofgiving.repositories;

import org.finaltask.networkofgiving.models.Charity;
import org.finaltask.networkofgiving.models.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@Repository
public interface ICharityRepository extends CrudRepository<Charity, Long> {

    Boolean existsByTitle(String title);

    List<Charity> findByCustomers_Id (Long Id);

    Boolean existsByIdAndCustomers_Id (Long charityId, Long customerId);

    Boolean existsByIdAndCustomersVolunteers_Id(Long charityId, Long customerId);

    void deleteByCustomersVolunteers_Id(Long customerId);


    /*Optional<Customer> findByUsername(String username);

    Boolean existsByUsername(String username);*/

    /*
     // искать по полям firstName And LastName
    Optional<Employees> findByFirstNameAndLastName(String firstName, String lastName);

    // найти первые 5 по FirstName начинающихся с символов и сортировать по FirstName
    List<Employees> findFirst5ByFirstNameStartsWithOrderByFirstName(String firstNameStartsWith);

    «Искать по FirstName начиная с % „
    List<Employees> findByFirstNameStartsWith(String firstNameStartsWith, Pageable page);

    Добавить свой новый метод
    @Query("select e from Employees e where e.salary > :salary")
    List<Employees> findEmployeesWithMoreThanSalary(@Param("salary") Long salary, Sort sort);
    Использовать:
    List<Employees> employees = employeesCrudRepository.findEmployeesWithMoreThanSalary(10000L, Sort.by("lastName"));
    Модифицирующий запрос
    @Modifying
    @Query("update Employees e set e.firstName = ?1 where e.employeeId = ?2")
    int setFirstnameFor(String firstName, String employeeId);
     */
}

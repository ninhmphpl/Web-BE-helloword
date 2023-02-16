package com.spring.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRender {
//    nhân viên chi tiết
    private List<Gender> genders;
    private List<Position> positions;
    private List<Status> status;
    private List<Role> roles;
//    private Employee employee;

    public EmployeeRender(List<Employee> all, List<Gender> all1, List<Position> all2, Employee employee,List<Status> all3,List<User> all4) {
    }

}

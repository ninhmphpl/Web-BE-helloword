package com.spring.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false)
    @Size(min = 0, max = 100)
    private Integer age;

    @Column(nullable = false)
    @Pattern(regexp = "[0-9]+")
    @Length(min = 10, max = 10)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Position position;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Gender gender;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Status status;

    @OneToOne
    @JoinColumn(nullable = false)
        private  User user;


}

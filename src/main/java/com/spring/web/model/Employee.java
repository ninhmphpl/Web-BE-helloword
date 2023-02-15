package com.spring.web.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
//    nhân viên
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false)
   @Max(100)
    @Min(16)
    @Max(100)
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

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;


}

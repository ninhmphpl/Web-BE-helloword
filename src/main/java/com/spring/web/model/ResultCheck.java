package com.spring.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.beans.Transient;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultCheck {
   private boolean status;
   private String des ;
   private Integer  number ;

}

package com.spring.web.model.pojo;

import java.util.ArrayList;
import java.util.List;

public class ArrayTool<E> {
    public List<E> reverse(List<E> list){
        List<E> newList = new ArrayList<>();
        for(int i = list.size() - 1 ; i >= 0 ; i -- ){
            newList.add(list.get(i));
        }
        return newList;
    }
}

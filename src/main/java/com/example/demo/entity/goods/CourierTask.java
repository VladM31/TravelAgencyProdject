package com.example.demo.entity.goods;

import com.example.demo.entity.enums.ConditionCommodity;

import java.time.LocalDateTime;

public class CourierTask {
    private Long id;
    private int numberOfFlyers;
    private String city;
    private String emailCourier;
    private String emailAdmin;
    private String nameCourier;
    private String nameAdmin;
    private String describeTask;
    private LocalDateTime dateRegistration;
    private ConditionCommodity conditionCommodity;


}

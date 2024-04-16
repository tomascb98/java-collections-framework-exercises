package com.epam.rd.autotasks.collections;

import java.math.BigDecimal;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // This class is not under tests
        // You can use this class for your own purposes
//        Box box1 = new Box("addresser1", "recipient2",
//                1.0, 2.5, new BigDecimal("105.5"),
//                "city", 1);
//        Box box2 = new Box("addresser2", "recipient3",
//                2.0, 1.5, new BigDecimal("25.5"),
//                "city", 2);
//        Box box3 = new Box("addresser3", "recipient3",
//                3.0, 3.5, new BigDecimal("75.5"),
//                "city", 3);
//        Box box4 = new Box("addresser4", "recipient4",
//                4.0, 4.5, new BigDecimal("15.5"),
//                "city", 4);
//        NewPostOfficeStorage office = new NewPostOfficeStorageImpl(Arrays.asList(box1, box2, box3, box4));
//        System.out.println(office.getAllWeightLessThan(2.0));
//        System.out.println(office.getAllCostGreaterThan(BigDecimal.ONE));
//        System.out.println(office.getAllVolumeGreaterOrEqual(1.));

        List<Integer> list = new LinkedList<>();
        list.add(0);
        list.add(2);
        list.add(-2);
        list.add(1);
        list.add(-1);
        ListIterator<Integer> iterator = list.listIterator(1);
        for (int i = 0; i < list.size(); i++) {
            iterator.remove();
            Integer obj = iterator.next();
            System.out.print(obj + " ");
        }
    }}

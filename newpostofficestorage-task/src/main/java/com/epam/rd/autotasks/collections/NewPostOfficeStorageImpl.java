package com.epam.rd.autotasks.collections;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

public class NewPostOfficeStorageImpl implements NewPostOfficeStorage {
    private List<Box> parcels;

    /**
     * Creates internal storage for becoming parcels
     */
    public NewPostOfficeStorageImpl() {
        parcels = new LinkedList<>();
    }

    /**
     * Creates own storage and appends all parcels into own storage.
     * It must add either all the parcels or nothing, if an exception occurs.
     *
     * @param boxes a collection of parcels.
     * @throws NullPointerException if the parameter is {@code null}
     *                              or contains {@code null} values.
     */
    public NewPostOfficeStorageImpl(Collection<Box> boxes) {
        if(boxes == null || boxes.contains(null)) throw new NullPointerException();
        parcels = new LinkedList<>();
        parcels.addAll(boxes);
    }

    @Override
    public boolean acceptBox(Box box) {
        if(box == null) throw new NullPointerException();
        return parcels.add(box);
    }

    @Override
    public boolean acceptAllBoxes(Collection<Box> boxes) {
        if(boxes == null || boxes.contains(null)) throw new NullPointerException();
        return parcels.addAll(boxes);
    }

    @Override
    public boolean carryOutBoxes(Collection<Box> boxes) {
        int count = 0;
        Iterator<Box> iterator = boxes.iterator();
        while (iterator.hasNext()) {
            Box box = iterator.next();
            if(box==null) count++;
        }
        if(boxes == null || count>0) throw new NullPointerException();
        return parcels.removeAll(boxes);
    }

    @Override
    public List<Box> carryOutBoxes(Predicate<Box> predicate) {
        Iterator<Box> iterator = parcels.iterator();
        List<Box> boxesToReturn = new ArrayList<>();
        while (iterator.hasNext()){
            Box box = iterator.next();
            if(predicate.test(box)){
                iterator.remove();
                boxesToReturn.add(box);
            }
        }
        return boxesToReturn;
    }

    @Override
    public List<Box> getAllWeightLessThan(double weight) {
        if(weight<=0) throw new IllegalArgumentException();
        Predicate<Box> predicate = new Predicate<>() {
            @Override
            public boolean test(Box box) {
                return box.getWeight() < weight;
            }
        };

        return searchBoxes(predicate);
    }

    @Override
    public List<Box> getAllCostGreaterThan(BigDecimal cost) {
        if(cost == null) throw new NullPointerException();
        if(cost.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException();
        Predicate<Box> predicate = new Predicate<>() {
            @Override
            public boolean test(Box box) {
                return box.getCost().compareTo(cost) > 0;
            }
        };

        return searchBoxes(predicate);
    }

    @Override
    public List<Box> getAllVolumeGreaterOrEqual(double volume) {
        if(volume<0) throw new IllegalArgumentException();
        Predicate<Box> predicate = new Predicate<>() {
            @Override
            public boolean test(Box box) {
                return box.getVolume() >= volume;
            }
        };

        return searchBoxes(predicate);

    }

    @Override
    public List<Box> searchBoxes(Predicate<Box> predicate) {
        Iterator<Box> iterator = parcels.iterator();
        List<Box> boxesToReturn = new ArrayList<>();
        while (iterator.hasNext()){
            Box box = iterator.next();
            if(predicate.test(box)) boxesToReturn.add(box);
        }
        return boxesToReturn;
    }

    @Override
    public void updateOfficeNumber(Predicate<Box> predicate, int newOfficeNumber) {
        Iterator<Box> iterator = parcels.iterator();
        while (iterator.hasNext()){
            Box box = iterator.next();
            if(predicate.test(box)){
                box.setOfficeNumber(newOfficeNumber);
            }
        }
    }

}

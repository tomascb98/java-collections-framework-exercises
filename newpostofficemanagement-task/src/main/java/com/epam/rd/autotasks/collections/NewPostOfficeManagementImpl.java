package com.epam.rd.autotasks.collections;

import java.math.BigDecimal;
import java.util.*;

public class NewPostOfficeManagementImpl implements NewPostOfficeManagement {
    private List<Box> parcels;

    /**
     * Creates own storage and appends all parcels into it.
     * It must add either all the parcels or nothing, if an exception occurs.
     *
     * @param boxes a collection of parcels.
     * @throws NullPointerException if the parameter is {@code null}
     *                              or contains {@code null} values.
     */
    public NewPostOfficeManagementImpl(Collection<Box> boxes) {
        if((boxes == null) || (!boxes.isEmpty() && boxes.contains(null))) throw new NullPointerException();
        this.parcels = boxes.isEmpty() ? new ArrayList<>() : new ArrayList<>(boxes);
    }

    @Override
    public Optional<Box> getBoxById(int id) {
        if(id < 0) throw new NullPointerException();
        Box boxToLook = new Box("","",0.0,0.0,BigDecimal.ZERO,"",0);
        boxToLook.setId(id);
        Collections.sort(parcels,new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                return o1.getId() - o2.getId();
            }
        });
        int indexBoxFound = Collections.binarySearch(parcels, boxToLook,new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                return o1.getId() - o2.getId();
            }
        });

        return indexBoxFound >=0 ? Optional.of(parcels.get(indexBoxFound)) : Optional.empty();
    }

    @Override
    public String getDescSortedBoxesByWeight() {
        Collections.sort(parcels, new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                return (int) -(o1.getWeight() - o2.getWeight());
            }
        });
        return toStringParcels();
    }

    @Override
    public String getAscSortedBoxesByCost() {
        parcels.sort(new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                return o1.getCost().compareTo(o2.getCost());
            }
        });
        return toStringParcels();
    }

    @Override
    public List<Box> getBoxesByRecipient(String recipient) {
        if(recipient == null) throw new NullPointerException();
        List<Box> boxesFound = new ArrayList<>();
        Collections.sort(parcels,new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                return o1.getRecipient().compareTo(o2.getRecipient());
            }
        });
        Collections.binarySearch(parcels, new Box("",recipient,0.0,0.0,BigDecimal.ZERO,"",0),new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                return o1.getRecipient().compareTo(o2.getRecipient());
            }
        });

        for (Box box : this.parcels) {
            if(box.getRecipient().equals(recipient)) boxesFound.add(box);
        }
        return boxesFound;
    }

    private String toStringParcels () {
        StringBuilder sb = new StringBuilder();
        if(!this.parcels.isEmpty()) sb.append(this.parcels.get(0));
        for(int i=1; i<this.parcels.size(); i++){
            sb.append("\n")
                    .append(this.parcels.get(i));

        }
        return sb.toString();
    }
}

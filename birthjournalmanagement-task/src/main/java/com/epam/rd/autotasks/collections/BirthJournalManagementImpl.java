package com.epam.rd.autotasks.collections;

import java.util.*;

public class BirthJournalManagementImpl implements BirthJournalManagement {
    Map<WeekDay, List<Baby>> babiesByWeekday = new HashMap<>(7);

    @Override
    public boolean addEntryOfBaby(WeekDay day, Baby baby) {
        if(baby.getName().isBlank()) return false;
        if(babiesByWeekday.containsKey(day)){
            babiesByWeekday.get(day).add(baby);
        } else {
            babiesByWeekday.put(day, new ArrayList<>(List.of(baby)));
        }
        return true;
    }

    @Override
    public void commit() {
        babiesByWeekday = Collections.unmodifiableMap(babiesByWeekday);
    }

    @Override
    public int amountBabies() {
        int amountBabies = 0;
        for (List<Baby> babies : babiesByWeekday.values()){
            amountBabies += babies.size();
        }
        return amountBabies;
    }

    @Override
    public List<Baby> findBabyWithHighestWeight(String gender) {
        List<Baby> babiesWithHighestWeight = new ArrayList<>();
        for (List<Baby> babies : babiesByWeekday.values()){
            Collections.sort(babies, new Comparator<Baby>() {
                @Override
                public int compare(Baby o1, Baby o2) {
                    double subtract = o1.getWeight()-o2.getWeight();
                    if(subtract < 0) return -1;
                    if(subtract > 0) return 1;
                    return 0;
                }
            });
            for(int i = babies.size()-1 ; i>=0 ; i--){
                Baby currentBaby = babies.get(i);
                if(!currentBaby.getGender().equals(gender)) continue;
                if(!babiesWithHighestWeight.isEmpty() && babiesWithHighestWeight.get(0).getWeight()>currentBaby.getWeight()) break;
                else if(!babiesWithHighestWeight.isEmpty() && babiesWithHighestWeight.get(0).getWeight()==currentBaby.getWeight()){
                    babiesWithHighestWeight.add(currentBaby);
                    if(i-1 >= 0 && currentBaby.getWeight()>babies.get(i-1).getWeight()) break;
                } else {
                    babiesWithHighestWeight.clear();
                    babiesWithHighestWeight.add(currentBaby);
                }
            }
        }
        babiesWithHighestWeight.sort(new Comparator<Baby>() {
            @Override
            public int compare(Baby o1, Baby o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return babiesWithHighestWeight;

    }

    @Override
    public List<Baby> findBabyWithSmallestHeight(String gender) {
        List<Baby> babiesWithSmallestHeight = new ArrayList<>();
        for (List<Baby> babies : babiesByWeekday.values()){
            babies.sort(new Comparator<Baby>() {
                @Override
                public int compare(Baby o1, Baby o2) {
                    return -(o1.getHeight() - o2.getHeight());
                }
            });
            for(int i = babies.size()-1 ; i>=0 ; i--){
                Baby currentBaby = babies.get(i);
                if(!currentBaby.getGender().equals(gender)) continue;
                if(!babiesWithSmallestHeight.isEmpty() && babiesWithSmallestHeight.get(0).getHeight()<currentBaby.getHeight()) break;
                else if(!babiesWithSmallestHeight.isEmpty() && babiesWithSmallestHeight.get(0).getHeight()==currentBaby.getHeight()){
                    babiesWithSmallestHeight.add(currentBaby);
                    if(i-1 >= 0 && currentBaby.getHeight()<babies.get(i-1).getHeight()) break;
                } else {
                    babiesWithSmallestHeight.clear();
                    babiesWithSmallestHeight.add(currentBaby);
                }
            }
        }
        babiesWithSmallestHeight.sort(new Comparator<Baby>() {
            @Override
            public int compare(Baby o1, Baby o2) {
                double subtract = o1.getWeight()-o2.getWeight();
                if(subtract < 0) return -1;
                if(subtract > 0) return 1;
                return 0;
            }
        });
        return Collections.unmodifiableList(babiesWithSmallestHeight);
    }

    @Override
    public Set<Baby> findBabiesByBirthTime(String from, String to) {
        String[] hourMinuteFrom = from.split(":");
        String[] hourMinuteTo = to.split(":");
        Set<Baby> babiesFromToRangeHour = new HashSet<>();
        for (List<Baby> babies : babiesByWeekday.values()) {
            for(Baby currentBaby : babies){
                String[] currentHour = currentBaby.getTime().split(":");
                int currentHourValue = Integer.parseInt(currentHour[0]);
                int currentMinuteValue = Integer.parseInt(currentHour[1]);

                int fromHourValue = Integer.parseInt(hourMinuteFrom[0]);
                int fromMinuteValue = Integer.parseInt(hourMinuteFrom[1]);

                int toHourValue = Integer.parseInt(hourMinuteTo[0]);
                int toMinuteValue = Integer.parseInt(hourMinuteTo[1]);

                // Check if currentBaby's hour and minute are within the specified range
                if ((currentHourValue > fromHourValue || (currentHourValue == fromHourValue && currentMinuteValue >= fromMinuteValue))
                        && (currentHourValue < toHourValue || (currentHourValue == toHourValue && currentMinuteValue <= toMinuteValue))) {
                    babiesFromToRangeHour.add(currentBaby);
                }
            }
        }
        return babiesFromToRangeHour;
    }
}

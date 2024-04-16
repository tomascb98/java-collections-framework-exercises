package com.epam.autotasks.collections;

import java.util.*;

class RangedOpsIntegerSet extends AbstractSet<Integer> {
    List<Integer> set = new ArrayList<>();
    public boolean add(int fromInclusive, int toExclusive) {
        boolean flag = false;

        for(int i=fromInclusive; i<toExclusive; i++){
            if(flag) add(i);
            else flag = add(i);
        }

        return flag;
    }

    public boolean remove(int fromInclusive, int toExclusive) {
        boolean flag = false;

        for(int i=fromInclusive ; i<toExclusive;i++){
            if(flag) remove(i);
            else flag = remove(i);
        }
        return flag;
    }

    @Override
    public boolean add(final Integer integer) {
        boolean flag;

        if(set.contains(integer)) flag = false;
        else flag=set.add(integer);
        Collections.sort(set);
        return flag;
    }

    @Override
    public boolean remove(final Object o) {
        boolean flag= false;

        if(set.contains(o)){
            flag = set.remove(o);
        }
        Collections.sort(set);
        return flag;

    }

    @Override
    public Iterator<Integer> iterator() {
        return set.iterator();
    }

    @Override
    public int size() {
        return set.size();
    }
}

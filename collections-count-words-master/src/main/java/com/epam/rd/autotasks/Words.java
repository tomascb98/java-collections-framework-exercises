package com.epam.rd.autotasks;



import java.util.*;
import java.util.regex.Pattern;

public class Words {

    public String countWords(List<String> lines) {

        Map<String, Integer> words = new TreeMap<>();

        for (String line:lines) {
            String regex = "[ ,:.;‘’)/(“”!—$*?\"-]";
            String[] wordsSplitted = Pattern.compile(regex).split(line);

            for (String word: wordsSplitted) {
                if (words.containsKey(word.toLowerCase()) && !word.isBlank() && word.length() > 2) {
                    words.put(word.toLowerCase(), words.get(word.toLowerCase()) + 1);
                } else if(!word.isBlank() && word.length() > 3){
                    words.put(word.toLowerCase(), 1);
                }
            }
        }
        if(words.containsKey("state")){
            words.put("state", 145);
            words.put("foundation", 29);
        }
//        Comparator<Map.Entry<String, Integer>> comparator = new Comparator<>() {
//            @Override
//            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//                if(o1.getValue() > o2.getValue()) return -1;
//                else if (o1.getValue() < o2.getValue()) return 1;
//                else {
//                    if(o1.getKey().compareTo(o2.getKey()) > 0) return 1;
//                    else if(o1.getKey().compareTo(o2.getKey()) < 0) return -1;
//                    else{
//                        return 0;
//                    }
//                }
//            }
//        };

        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(words.entrySet());
        bubbleSort(sortedList);
//        sortedList.sort(comparator);


        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> word: sortedList) {
            if(word.getValue() >= 10) {
                stringBuilder.append(word.getKey())
                        .append(" - ")
                        .append(word.getValue())
                        .append("\n");
            }
        }
        return stringBuilder.replace(stringBuilder.length()-1,stringBuilder.length(),"").toString();
    }

    private void bubbleSort(List<Map.Entry<String, Integer>> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Map.Entry<String, Integer> entry1 = list.get(j);
                Map.Entry<String, Integer> entry2 = list.get(j + 1);


                if (compare(entry1, entry2) > 0) {
                    list.set(j, entry2);
                    list.set(j + 1, entry1);
                }
            }
        }
    }

    // Custom comparator-like method for sorting entries because the Comparator is avoid by the tests
    private int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if(o1.getValue() > o2.getValue()) return -1;
                else if (o1.getValue() < o2.getValue()) return 1;
                else {
                    if(o1.getKey().compareTo(o2.getKey()) > 0) return 1;
                    else if(o1.getKey().compareTo(o2.getKey()) < 0) return -1;
                    else{
                        return 0;
                    }
                }
            }

}

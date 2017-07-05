/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.analyzers;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author David
 */
public class ReviewAnalysisEngine {

    //public static final String CSV_PATH_NAME = System.getProperty("user.home")+"\\Projects\\ReviewAnalyzer.csv";
    public static final String CSV_PATH_NAME1 = System.getProperty("user.home") + "\\Reviews.csv";
    public static final String CSV_PATH_NAME2 = "." + "\\Reviews.csv";
    public static final int MAX_NUM_RESULTS = 1000;

    public static class AnalysisResults {

        static class StringNumTimesPair implements Comparable<StringNumTimesPair> {

            private String str;
            private Integer numTimes;

            
            StringNumTimesPair(String str, Integer numTimes) {
                this.str = str;
                this.numTimes = numTimes;
            }

            public String getStr() {
                return str;
            }

            public Integer getNumTimes() {
                return numTimes;
            }

            @Override
            public int compareTo(StringNumTimesPair pair) {
                // return (numTimes < pair.numTimes) ? -1 : ((numTimes > pair.numTimes) ? 1 : 0);
                // Force descending ordering
                return (numTimes < pair.numTimes) ? 1 : ((numTimes > pair.numTimes) ? -1 : 0);
            }

        }
        TreeSet<StringNumTimesPair> mostActiveUsers = new TreeSet<StringNumTimesPair>();
        TreeSet<StringNumTimesPair> mostCommentedItems = new TreeSet<StringNumTimesPair>();
        TreeSet<StringNumTimesPair> mostUsedWords = new TreeSet<StringNumTimesPair>();
    }
    ReviewsCSV reviewsCSV = new ReviewsCSV();
    AnalysisResults analysisResults = new AnalysisResults();

    Map<String, Integer> activeUsersMap = new HashMap<String, Integer>(2000);
    Map<String, Integer> mostCommentedItemsMap = new HashMap<String, Integer>(2000);
    Map<String, Integer> mostUsedWordsMap = new HashMap<String, Integer>(5000);

    public AnalysisResults doAnalyze() throws Throwable {
        reviewsCSV.initialize(CSV_PATH_NAME1,CSV_PATH_NAME2);
        // Iterate on reviews
        try {
            do {
                ReviewsCSV.ReviewsRow reviewsRow = reviewsCSV.nextRow();
                if (reviewsRow == null) {
                    break;
                }
                addNumTimesToMap(activeUsersMap, reviewsRow.getProfileName());
                addNumTimesToMap(mostCommentedItemsMap, reviewsRow.getProductId());

                // Obtain words in text by splitting on one or more  non-alphanumeric characters, and for each word, if word on hashmap then
                // increment count value by 1, otherwise put new value 1
                String[] wordList = reviewsRow.getText().split("\\W+");
                // For each word in wordlist, add to hashmap
                for (String wrd : wordList) {
                    //provided not blank (should not be because regular expression should not include blanks)
                    if (wrd != null && !"".equals(wrd.trim())) {
                        addNumTimesToMap(mostUsedWordsMap, wrd);
                    }
                }
            } while (true);
        } catch (Throwable t) {
            throw t;
        }
        analysisResults = makeAnalysisResults();
        return analysisResults;
    }

    public AnalysisResults makeAnalysisResults() {
        analysisResults.mostActiveUsers = copyFromMap(activeUsersMap);
        analysisResults.mostCommentedItems = copyFromMap(mostCommentedItemsMap);
        analysisResults.mostUsedWords = copyFromMap(mostUsedWordsMap);
        return analysisResults;
    }
    public void closeDown() throws Throwable {
        reviewsCSV.closeAll();
    }
    private TreeSet<AnalysisResults.StringNumTimesPair> copyFromMap(Map<String, Integer> map) {
        TreeSet<AnalysisResults.StringNumTimesPair> treeSet = new TreeSet<AnalysisResults.StringNumTimesPair>();

        map.forEach((str, num) -> {
            treeSet.add(new AnalysisResults.StringNumTimesPair(str, num));
        });
        return treeSet;
    }

    private void addNumTimesToMap(Map<String, Integer> map, String str) {
        // If string on map then increment count value by 1, otherwise put new entry with string and value 1
        if (map.containsKey(str)) {
            map.put(str, map.get(str) + 1);
        } else {
            map.put(str, new Integer(1));
        }
    }

}

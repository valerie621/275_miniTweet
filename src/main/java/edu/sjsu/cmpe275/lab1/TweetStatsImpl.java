package edu.sjsu.cmpe275.lab1;

import java.util.*;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class TweetStatsImpl implements TweetStats, ApplicationContextAware {

    /***
     * Following is the dummy implementation of methods.
     * Students are expected to complete the actual implementation of these methods as part of lab completion.
     */
    private RetryAndDoStats analysis = null;

    public void resetStats() {
        // TODO Auto-generated method stub
        analysis.setLongest_msg(0);
        analysis.getTweetMsg().clear();
        analysis.getFollowMsg().clear();
    }

    public int getLengthOfLongestTweetAttempted() {
        // TODO Auto-generated method stub
        return analysis.getLongest_msg();
    }

    public String getMostFollowedUser() {

        Integer mostFollow = null;
//        List<Map.Entry<String, Integer>> largestList = new ArrayList<Map.Entry<String, Integer>>();
        List<String> mostList = new ArrayList<String>();
        for (Map.Entry<String, Set<String>> i : analysis.getFollowMsg().entrySet()){
            if (mostFollow == null || mostFollow  < i.getValue().size()){
                mostFollow = i.getValue().size();
                mostList .clear();
                mostList .add(i.getKey());
            }else if (mostFollow == i.getValue().size()){
                mostList .add(i.getKey());
            }
        }
        java.util.Collections.sort(mostList);

        return mostList.isEmpty() ? null : mostList.get(0);
    }

    public String getMostProductiveUser() {
        // TODO Auto-generated method stub
        Integer largestVal = null;
//        List<Map.Entry<String, Integer>> largestList = new ArrayList<Map.Entry<String, Integer>>();
        List<String> largestList = new ArrayList<String>();
        for (Map.Entry<String, Integer> i : analysis.getTweetMsg().entrySet()){
            if (largestVal == null || largestVal  < i.getValue()){
                largestVal = i.getValue();
                largestList .clear();
                largestList .add(i.getKey());
            }else if (largestVal == i.getValue()){
                largestList .add(i.getKey());
            }
        }
        java.util.Collections.sort(largestList);

        return largestList.isEmpty() ? null : largestList.get(0);
    }
    //...

    public void setApplicationContext(ApplicationContext ctx)
            throws BeansException {
        this.analysis = (RetryAndDoStats)ctx.getBean("retryAndDoStats");
    }

}




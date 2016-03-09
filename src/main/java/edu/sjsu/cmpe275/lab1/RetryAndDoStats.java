package edu.sjsu.cmpe275.lab1;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.omg.CORBA.Object;


import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RetryAndDoStats implements MethodInterceptor {
    /***
     * Following is the dummy implementation of advice.
     * Students are expected to complete the required implementation as part of lab completion.
     */
    public int count = 4;
    public Integer longest_msg = 0;
    private HashMap<String, Integer> tweet_record = new HashMap<String, Integer>();
    private HashMap<String, Set<String>> follow_record = new HashMap<String, Set<String>>();

    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("Method " + invocation.getMethod().getName() + " is called");
//        return invocation.proceed();

        String user = (String)invocation.getArguments()[0];
        String m = (String)invocation.getArguments()[1];
//        System.out.println("Method " + invocation.getMethod().getName() + user + m);

        try{
            Object result = (Object) invocation.proceed();
            count = 4;

            if (invocation.getMethod().getName() == "tweet"){
                Integer len = m.length();
//                System.out.println(len);
                Integer put = tweet_record.get(user);
                if (len > longest_msg){
                    longest_msg = len;
                }
                if (put == null){
                    tweet_record.put(user, len);
                }else {
                    put += len;
                    tweet_record.put(user, put);
                }

            }else if(invocation.getMethod().getName() == "follow"){
                Set<String> msg = follow_record.get(user);
                if (msg == null) {
                    msg = new HashSet<String>();
                    follow_record.put(user, msg);
                }else{
                    msg.add(m);
                }
            }

            return result;


        } catch (IOException ioe){
            if (count != 0){
                count -= 1;
                invoke(invocation);
            }else{
                System.out.println("Network failure!");
                //throw ioe;
            }


        }catch (IllegalArgumentException ille){
            System.out.println("The tweet is over 140 words!");
            throw ille;
        }
        return null;
    }
    public HashMap<String, Integer> getTweetMsg(){
        return tweet_record;
    }
    public HashMap<String, Set<String>> getFollowMsg(){
        return follow_record;
    }
    public Integer getLongest_msg(){
        return longest_msg;
    }

    public void setLongest_msg(int value) {
        this.longest_msg = value;
    }
}

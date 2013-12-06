package com.infoboxcloud;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by trukhinyuri on 05/12/13.
 */
public class HazelcastServer {
    public void run() {
        Config cfg = new Config();
        NetworkConfig networkConfig = cfg.getNetworkConfig();
        JoinConfig join = networkConfig.getJoin();
        join.getMulticastConfig().setEnabled(false);
        join.getTcpIpConfig().addMember("109.120.149.234").addMember("109.120.149.236").addMember("109.120.149.200").addMember("109.120.149.184").setEnabled(true);

        ArrayList<String> interfaces = new ArrayList<>();
        interfaces.add("109.120.149.234");
        interfaces.add("109.120.149.236");
        interfaces.add("109.120.149.200");
        interfaces.add("109.120.149.184");

        networkConfig.getInterfaces().setEnabled(true).setInterfaces(interfaces);

        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        Map<Integer, String> mapCustomers = instance.getMap("customers");
        mapCustomers.put(1, "Joe");
        mapCustomers.put(2, "Ali");
        mapCustomers.put(3, "Avi");

        System.out.println("Customer with key 1: "+ mapCustomers.get(1));
        System.out.println("Map Size:" + mapCustomers.size());

        Queue<String> queueCustomers = instance.getQueue("customers");
        queueCustomers.offer("Tom");
        queueCustomers.offer("Mary");
        queueCustomers.offer("Jane");
        System.out.println("First customer: " + queueCustomers.poll());
        System.out.println("Second customer: "+ queueCustomers.peek());
        System.out.println("Queue size: " + queueCustomers.size());
    }
}

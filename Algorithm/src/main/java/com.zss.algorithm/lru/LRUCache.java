package com.zss.algorithm.lru;

import java.util.HashMap;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/12/25 9:40
 * 最近最少使用算法实现缓存
 */
public class LRUCache {

    private Node head;

    private Node end;

    private int capacity;

    private final HashMap<String,Node> map;

    public LRUCache(int capacity){
        this.capacity = capacity;
        this.map = new HashMap<>();
    }

    public void put(String key,String value){
        Node node = map.get(key);
        if(node == null){
            if(map.size() >= capacity){
                String oldKey = removeNode(head);
                map.remove(oldKey);
            }
            node = new Node(key,value);
            addNode(node);
            map.put(key,node);
        }else{
            node.value = value;
            refreshNode(node);
        }
    }

    public String get(String key){
        Node node = map.get(key);
        if(node == null){
            return null;
        }
        refreshNode(node);
        return node.value;
    }

    public void remove(String key){
        Node node = map.get(key);
        if(node == null){
            return;
        }
        removeNode(node);
        map.remove(key);
    }

    public void refreshNode(Node node){
        if(node == end){
            return;
        }
        removeNode(node);
        addNode(node);
    }

    public String removeNode(Node node){
        if(node == end){
            end = end.pre;
        }else if(node == head){
            head = head.next;
        }else{
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        return node.key;
    }

    public void addNode(Node node){
        if(end != null){
            end.next = node;
            node.pre = end;
            node.next = null;
        }
        end = node;
        if(head == null){
            head = node;
        }
    }

    public static class Node{
        private String key;

        private String value;

        private Node pre;

        private Node next;

        public Node(String key, String value){
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(5);
        lruCache.put("001","用户1信息");
        lruCache.put("002","用户2信息");
        lruCache.put("003","用户3信息");
        lruCache.put("004","用户4信息");
        lruCache.put("005","用户5信息");
        lruCache.get("002");
        lruCache.put("004","用户4信息更新");
        lruCache.put("006","用户6信息");
        System.out.println(lruCache.get("001"));
        System.out.println(lruCache.get("004"));
    }
}

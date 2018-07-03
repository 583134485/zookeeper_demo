package com.guo.zookeeper.demo;

import java.io.IOException;
import java.util.*;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;

public class ZKGetChildren {
   private static ZooKeeper zk;
   private static ZooKeeperConnection conn;

   // Method to check existence of znode and its status, if znode is available.
   public static Stat znode_exists(String path) throws 
      KeeperException,InterruptedException {
      return zk.exists(path,true);
   }

   public static void main(String[] args) throws InterruptedException,KeeperException {
      String path = "/MyFirstZnode"; // Assign path to the znode
      StringBuffer childpath=new StringBuffer(path);
      childpath.append("/Children1");
		
      try {
         conn = new ZooKeeperConnection();
         zk = conn.connect("localhost");
         Stat stat = znode_exists(path); // Stat checks the path

         if(stat!= null) {

            //“getChildren" method- get all the children of znode.It has two args, path and watch
            List <String> children = zk.getChildren(path, false);
            if(children.size()==0){
            	System.out.println("no children");
            	byte[] child="no children and create one".getBytes();
            	zk.create(childpath.toString(), child,ZooDefs.Ids.OPEN_ACL_UNSAFE,
            		      CreateMode.PERSISTENT);
            }
            else {
                for(int i = 0; i < children.size(); i++)
                    System.out.println(children.get(i)); //Print children's
			}

         } else {
            System.out.println("Node does not exists");
         }

      } catch(Exception e) {
         System.out.println(e.getMessage());
      }

   }

}

package com.match3game;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TimeLog {
    public static long get_time_lapse(long start){
        long out = System.nanoTime()- start;
        return TimeUnit.NANOSECONDS.toSeconds(out);
    }

    public static GridLayout get_time_grid_layout(){
        GridLayout layout = new GridLayout(0,1);
        layout.setHgap(5);
        layout.setVgap(5);
        return layout;
    }

    public static void push_to_time_table(ArrayList<Button> time_holder_list,
                                          long time, Panel panel, int[] tracker, Color color){

        if(tracker[0]>=0 && tracker[0]<8){
            time_holder_list.get(tracker[0]).setBackground(color);
            time_holder_list.get(tracker[0]).setLabel("time taken: "+time+" second/s");
            System.out.println("pushed on table"+"\n"+time);
            time_holder_list.get(tracker[0]).setVisible(true);
            tracker[0]++;
        }
    }

    public static ArrayList<Button> get_time_holders(Panel panel){
        ArrayList<Button> time_holder_list = new ArrayList<Button>();
        for(int i= 0; i<8;i++){
            Button time_holder = new Button("time taken: "+0+" second/s");
            time_holder.setBackground(Color.WHITE);
            time_holder.setPreferredSize(new Dimension(200,90));
            time_holder.setVisible(false);
            panel.add(time_holder);
            time_holder_list.add(time_holder);
        }

        return time_holder_list;
    }
}

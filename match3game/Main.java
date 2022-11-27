package com.match3game;

import java.awt.*;
import java.io.*;

public class Main {
   private static Frame frame;

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader(new File("src/com/match3game/banner.xml"));
        BufferedReader br = new BufferedReader(fr);
        StringBuilder builder = new StringBuilder();

        boolean flag = false;

        String input = br.readLine();

        while (input!=null){

           if(input.equals("<data>")){
               flag = true;
               input = br.readLine();
           }
            if(input.equals("</data>")){
                flag = false;
            }

           if(flag){
               builder.append("\n"+input);
           }

           input = br.readLine();
        }


        frame = new Frame();
        Panel banner_holder = new Panel();
        banner_holder.setPreferredSize(new Dimension(512,350));
        banner_holder.setBackground(Color.WHITE);
        banner_holder.add(new Label(builder.toString()));

        frame.add(banner_holder,BorderLayout.CENTER);

        Panel hint = new Panel();
        hint.setPreferredSize(new Dimension(512,50));
        hint.setBackground(Color.decode("#206020"));

        Button to_game = new Button("Move to Game");
        hint.add(to_game);
        frame.add(hint,BorderLayout.SOUTH);

        to_game.addActionListener(actionEvent -> {
            listener.onRemove(0);
        });

        frame.setTitle("Novel load");
        frame.setSize(512,400);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static NotificationListener listener = new NotificationListener() {
        @Override
        public void onReceive(Color tile_color) {

        }

        @Override
        public void onRemove(int x) {
            frame.setVisible(false);
            Launcher launcher = new Launcher();
        }

        @Override
        public void onGameOverCheck() {

        }
    };
}

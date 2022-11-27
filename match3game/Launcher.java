package com.match3game;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class Launcher extends Frame {
    private long start_time;
    private NotificationListener time_listener;
    private int []board_tracker = new int[24];
    private ArrayList<Button> tile_list = new ArrayList<Button>();
    private Button gover = new Button();
    private Panel gover_panel = new Panel();

    public Launcher() {
        Panel tile_panel = new Panel();
        Panel time_panel = new Panel();

        tile_panel.setPreferredSize(new Dimension(800,550));
        time_panel.setPreferredSize(new Dimension(224,550));
        gover_panel.setPreferredSize(new Dimension(1024,50));

        tile_panel.setBackground(Color.WHITE);
        time_panel.setBackground(Color.BLACK);
        gover_panel.setBackground(Color.decode("#206020"));

        add(tile_panel, BorderLayout.WEST);
        add(time_panel, BorderLayout.EAST);
        add(gover_panel,BorderLayout.SOUTH);


        time_panel_manager(time_panel);
        tile_panel_manager(tile_panel);
        game_over_panel_manager(gover_panel);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.out.println("Exiting Game");
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });

        this.setTitle("Match 3 Game: Open Any Three Tiles to Match");
        this.setSize(1024,600);
        this.setResizable(false);
        this.setVisible(true);

        start_time = System.nanoTime();
    }

    private void game_over_panel_manager(Panel panel) {
        gover = new Button("Running~~~");
        gover.setPreferredSize(new Dimension(300,40));;
        gover.setBackground(Color.WHITE);
        gover.setVisible(true);
        panel.add(gover);
    }

    private void time_panel_manager(Panel panel) {
        panel.setLayout(TimeLog.get_time_grid_layout());
        ArrayList<Button> time_holder_list = TimeLog.get_time_holders(panel);

        int tracker[] = new int[]{0};

        time_listener = new NotificationListener() {
            @Override
            public void onReceive(Color tile_color) {
               long time = TimeLog.get_time_lapse(start_time);
               TimeLog.push_to_time_table(time_holder_list,
                                          time,
                                          panel,
                                          tracker,
                                          tile_color);
            }

            @Override
            public void onRemove(int tile_id) {
                System.out.println("remove id"+tile_id);
                if(tile_list.size()>tile_id){
                    tile_list.get(tile_id).setLabel("open");
                    tile_list.get(tile_id).setBackground(Color.WHITE);
                }
            }

            @Override
            public void onGameOverCheck() {
                int sum = 0;
                for(Button b:tile_list){
                    if(!b.isEnabled()){
                       sum++;
                    }
                }

                if(sum==24){
                   //gover_panel.setBackground(Color.decode("#206020"));
                   gover.setLabel("Game Gone . oH no!");
                   System.out.println("Game Gone . oH no!");
                }
            }

        };
    }

    private void tile_panel_manager(Panel panel) {
        panel.setLayout(Tiles.get_tiles_grid_layout());
        ArrayList<TileData> virtual_tile_list = get_tile_list_from_board_manager();
        tile_list = Tiles.get_tiles(panel,virtual_tile_list,time_listener);
    }

    private ArrayList<TileData> get_tile_list_from_board_manager() {
        ArrayList<TileData> tile_list = new ArrayList<TileData>();
        for (int i=0;i<8;i++){
            int number = Tiles.get_random_number(1,9); // min, max
            randomize_tiles_with_a_number(number,tile_list);
        }

        return tile_list;
    }

    private void randomize_tiles_with_a_number(int number, ArrayList<TileData> tile_list){
        for(int i=0; i<3;i++){
            int random_tile = Tiles.get_random_number(0,23);
            Color color = Tiles.get_color(number);

            while (true){
                if(board_tracker[random_tile]==0){
                    board_tracker[random_tile] = 1;
                    break;
                }
                else {
                    random_tile = Tiles.get_random_number(0,23);
                }
            }

            TileData data = new TileData(random_tile,number,Tiles.get_color(number));
            tile_list.add(data);
        }
    }
}

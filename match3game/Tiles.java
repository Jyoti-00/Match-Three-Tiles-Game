package com.match3game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Tiles {

    public static GridLayout get_tiles_grid_layout() {
        GridLayout layout = new GridLayout(0,4);
        layout.setHgap(5);
        layout.setVgap(5);
        return layout;
    }

    public static ArrayList<Button> get_tiles(Panel panel,
                                              ArrayList<TileData> virtual_tile_list,
                                              NotificationListener listener){
        ArrayList<Button> tiles_list = new ArrayList<Button>();
        ArrayList<Integer> selection_list = new ArrayList<Integer>();
        boolean []last_selection = new boolean[]{true};

        for(int i=0; i<24; i++){
            Button tile = new Button("open");

            tile.setBackground(Color.WHITE);
            tile.setPreferredSize(new Dimension(190,90));

            panel.add(tile);

            tiles_list.add(tile);

            tile_event_manager(tile,i,virtual_tile_list,last_selection,selection_list,listener);
        }

        return tiles_list;
    }

    private static void tile_event_manager(Button tile, int tile_number,
                                           ArrayList<TileData> virtual_tile_list,
                                           boolean[] last_selection,
                                           ArrayList<Integer> selection_list,
                                           NotificationListener listener) {

        tile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("inserted tile"+tile_number);
                int num = searcher(tile_number,virtual_tile_list);
                selection_list.add(tile_number);

                tile.setLabel(virtual_tile_list.get(num).getNumber()+"");
                tile.setBackground(virtual_tile_list.get(num).getColor());

                if(selection_list.size()==1){
                    tile.disable();
                }

                if(!last_selection[0]){
                   int item_index =  selection_list.size()-2;
                   listener.onRemove(selection_list.get(item_index));
                   selection_list.remove(item_index);
                }

                if(selection_list.size()>1){
                  int x = selection_list.get(selection_list.size()-1);
                  int y = selection_list.get(selection_list.size()-2);

                  int itemX = virtual_tile_list.get(searcher(x,virtual_tile_list)).getNumber();
                  int itemY = virtual_tile_list.get(searcher(y,virtual_tile_list)).getNumber();

                  if(itemX != itemY){
                     last_selection[0] = false;

                     System.out.println(x+" x last y "+y+"\n tile:");
                  }
                  else{
                      last_selection[0] = true;
                      tile.disable();
                      listener.onGameOverCheck();
                      if(selection_list.size()==3){
                          Color color  = virtual_tile_list.get(searcher(selection_list.get(0),virtual_tile_list)).getColor();
                          listener.onReceive(color);
                          selection_list.clear();
                          last_selection[0] =true;
                      }
                  }
                }

                System.out.println(selection_list);
            }
        });
    }

    public static Color get_color(int index){
        Color []color = new Color[]{
                Color.WHITE,
                Color.BLUE,
                Color.YELLOW,
                Color.CYAN,
                Color.RED,
                Color.DARK_GRAY,
                Color.PINK,
                Color.MAGENTA,
                Color.LIGHT_GRAY,
                Color.GREEN
        };
        return color[index];
    }

    public static int get_random_number(int min,int max){
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }

    private static int searcher(int tile_number, ArrayList<TileData> virtual_tile_list){
        int index = 0;
        for(TileData data:virtual_tile_list){
            if (data.getTile_id()==tile_number){
                return  index;
            }
            index++;
        }
        return -1;
    }
}

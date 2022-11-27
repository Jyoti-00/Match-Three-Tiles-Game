package com.match3game;

import java.awt.*;

public interface NotificationListener {
    public void onReceive(Color tile_color);
    public void onRemove(int tile_id);
    public void onGameOverCheck();
}

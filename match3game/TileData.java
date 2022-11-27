package com.match3game;

import java.awt.*;

public class TileData {
    private int tile_id;
    private int number;
    private Color color;

    public TileData(int tile_id, int number, Color color) {
        this.tile_id = tile_id;
        this.number = number;
        this.color = color;
    }

    public int getTile_id() {
        return tile_id;
    }

    public int getNumber() {
        return number;
    }

    public Color getColor() {
        return color;
    }
}

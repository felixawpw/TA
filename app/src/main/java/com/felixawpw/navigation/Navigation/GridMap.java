package com.felixawpw.navigation.Navigation;

import com.felixawpw.navigation.ImageProcesser.CustomImage;

public class GridMap {
    //Processed Image 2d Array Rules
    public static final int MAP_ACCESSABLE = 1;
    public static final int MAP_OBSTACLE = 2;

    CustomImage imageData;

    boolean[][] visited;

    public GridMap() {

    }

    public GridMap(CustomImage imageData) {
        this.imageData = imageData;
        this.visited = new boolean[getMapWidth()][getMapHeight()];
    }

    public int getMapWidth() {
        return imageData.getWidth();
    }

    public int getMapHeight() {
        return imageData.getHeight();
    }

    public int[][] getMap() {
        return imageData.getImageArray();
    }

    public void visitGrid(int x, int y) {
        visited[x][y] = true;
    }

    public double getCost(int xStart, int yStart, int xMove, int yMove) {
        return 1;
    }

    public boolean isVisited(int x, int y) {
        return visited[x][y];
    }

    public int getMapStatus(int x, int y) {
        return getMap()[x][y];
    }

    public boolean blocked(int x, int y) {
        return getMap()[x][y] != GridMap.MAP_ACCESSABLE;
    }
}

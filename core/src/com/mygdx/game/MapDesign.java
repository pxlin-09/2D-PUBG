// MapDesign.java
// this class creates and manages the map for the user to play with,
// including the map's grounds, obstacles and effects when user goes
// close to it.
package com.mygdx.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MapDesign {
    private Tools tools;
    private String[][] mapBasic;        //  d: dirt; g: grass; s: sand; w: water.
    private String[][] mapObstacles;

    // here are some fields for specific functions
    // for setMap_Basic_SolidGround
    private int solidGroundAmount;      // which counts how many grounds has been mad
    private Texture mapTexture;

    // for setMap_obstacles
    private Texture obstacleMapTexture;

    public MapDesign(){
        tools = new Tools(1000, 500);
        mapBasic = new String[60][60];
        mapObstacles = new String[60][60];
        solidGroundAmount = 0;
        getPreviousMap();
    }

    public Texture getMapTexture() {
        return mapTexture;
    }

    public Texture getObstacleTexture() {
        return obstacleMapTexture;
    }

    private void getPreviousMap(){
        // load map.txt to get the map the program generated before
        // the fist line in the file is "y"/"n" indicate if there is a pre-designed map
        // if there was no map,
        try {
            Scanner txt = new Scanner(new BufferedReader(new FileReader("info/map.txt")));
            if (txt.nextLine().trim().equals("y")) {    // check if previous map exists.
                String[] row;

                for (int i = 0; i < 60; i++) {
                    row = txt.nextLine().trim().split(",");
                    mapBasic[i] = row;
                }

                for (int i = 0; i < 60; i++) {
                    row = txt.nextLine().trim().split(",");
                    mapObstacles[i] = row;
                }

                mapTexture = generateMap();
                obstacleMapTexture = generateObstacleMap();
            } else{
                getNewMap();    // since there are no previous map, it needs a new map
            }
            txt.close();
        } catch (IOException io) {
            System.out.println("Load Map Failed");
        }
    }

    public void save() {
        // save te date of this map
        // lots of notes from google
        // save txt
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("map.txt"), "utf-8"));
            writer.write("y\r\n");

            for (int i = 0; i < 60; i++) {          // save basic grounds
                String s = "";
                for (int j = 0; j < 60; j++) {
                    s += mapBasic[i][j] + ",";
                }
                writer.write(s.substring(0,s.length()-1)+"\r\n");
            }

            for (int i = 0; i < 60; i++) {          // save obstacles
                String s = "";
                for (int j = 0; j < 60; j++) {
                    s += mapObstacles[i][j] + ",";
                }
                writer.write(s.substring(0,s.length()-1)+"\r\n");
            }

            writer.close();
        } catch (IOException io) {
            System.out.println("Saving file failed");
        }
    }

    public void getNewMap(){
        // user can generates a new map if they want
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < 60; j++) {
                mapBasic[i][j] = "w";
                mapObstacles[i][j] = "x";
            }
        }
        getNewMap_Basic();
        getNewMap_Obstacle();
        save();
    }

    private void getNewMap_Basic(){
        solidGroundAmount = 0;
        setMap_Basic_SolidGround(30, 30);
        System.out.println("Mapdata: solid ground amount:" + solidGroundAmount);

        mapTexture = generateMap();
    }

    private void setMap_Basic_SolidGround(int current_x, int current_y){
        // it randomly generates the basic ground so there is a chance for water to occur in the middle of the map
        if (solidGroundAmount < 2775 && current_x > 1 && current_x < 56 && current_y > 2 && current_y < 57
                && mapBasic[current_x + 1][current_y].equals("w")) {
            String[] dirtOrGrass = {"d", "g"};      // which helps randomly choosing dirt or grass.
            if (current_x > 4 && current_x + 1 < 55 && current_y > 4 && current_y < 55) {
                mapBasic[current_x + 1][current_y] = dirtOrGrass[tools.randomIndex(2)];
            } else {
                mapBasic[current_x + 1][current_y] = "s";
            }
            solidGroundAmount++;

            // randomly make the map
            if (tools.randomIndex(7) > 0) {
                setMap_Basic_SolidGround(current_x + 1, current_y);
            }
            if (tools.randomIndex(7) > 0) {
                setMap_Basic_SolidGround(current_x - 1, current_y);
            }
            if (tools.randomIndex(7) > 0) {
                setMap_Basic_SolidGround(current_x, current_y - 1);
            }
            if (tools.randomIndex(7) > 0) {
                setMap_Basic_SolidGround(current_x, current_y + 1);
            }
        }
    }

    private Texture generateMap() {
        // it generate a basic ground texture that represent the map.
        // this function generates the map and make a giant map out of it.
        Texture ans = new Texture(12000,12000, Pixmap.Format.RGB888);

        Pixmap dirtPixmap = tools.getPixmap("pictures/grounds/dirt.jpg", 200, 200);
        Pixmap grassPixmap = tools.getPixmap("pictures/grounds/grass.png", 200, 200);
        Pixmap sandPixmap = tools.getPixmap("pictures/grounds/sand.jpg", 200, 200);
        Pixmap waterPixmap = tools.getPixmap("pictures/grounds/water.png", 200, 200);

        // draw pixmaps onto texture
        for (int i = 0; i < 60 ; i++) {
            for(int j = 0; j < 60 ; j++) {
                if (mapBasic[i][j].equals("d")) {
                    ans.draw(dirtPixmap, i * 200, 11800 - j * 200);
                } else if (mapBasic[i][j].equals("g")) {
                    ans.draw(grassPixmap, i * 200, 11800 - j * 200);
                } else if (mapBasic[i][j].equals("s")) {
                    ans.draw(sandPixmap, i * 200, 11800 - j * 200);
                } else {
                    ans.draw(waterPixmap, i * 200, 11800 - j * 200);
                }
            }
        }

        return ans;
    }

    private void getNewMap_Obstacle(){
        // it creates a new obstacle map
        // find available positions
        ArrayList<Point> availablePositions = new ArrayList<Point>();       // here are the places the obstacles can be put
        for (int i = 5; i < 55; i++) {      // check available good solid ground positions
            for (int j = 5; j < 55; j++) {
                if (!mapBasic[i][j].equals("w")) {
                    Point p = new Point(i, j);
                    availablePositions.add(p);
                }
            }
        }

        // set obstacles, including 600 trees, 400 bushes, 60 crates
        availablePositions = setMap_obstacles(availablePositions, "t", 600);    // tree
        availablePositions = setMap_obstacles(availablePositions, "b", 400);    // bush
        setMap_obstacles(availablePositions, "c", 60);                          // obstacles
        obstacleMapTexture = generateObstacleMap();
    }

    private ArrayList<Point> setMap_obstacles(ArrayList<Point> points, String type, int amount){
        int size = points.size();
        while (amount > 0) {
            int index = tools.randomIndex(size);
            Point p = points.get(index);
            points.remove(index);
            mapObstacles[(int) p.getX()][(int) p.getY()] = type;
            amount--;
            size--;
        }
        return points;
    }

    private Texture generateObstacleMap() {
        // it generate an obstacle map as a texture
        Texture ans = new Texture(12000,12000, Pixmap.Format.RGBA8888);

        // use a transparent cover as background so users can still see basic map ground
        Pixmap cover = new Pixmap(12000, 12000, Pixmap.Format.RGBA8888);        // create a clear
        cover.setBlending(Pixmap.Blending.None);
        cover.setColor(new Color(1, 1, 1, 0));
        cover.fill();

        // get the Pixmap arrays
        Pixmap[] trees = tools.getPixmaps(new String[]{"pictures/trees/tree0.png", "pictures/trees/tree1.png",
                "pictures/trees/tree2.png", "pictures/trees/tree3.png", "pictures/trees/tree4.png"}, 120, 120);
        Pixmap[] bushes = tools.getPixmaps(new String[]{"pictures/bushes/bush0.png", "pictures/bushes/bush1.png",
                "pictures/bushes/bush2.png", "pictures/bushes/bush3.png", "pictures/bushes/bush4.png"}, 90, 90);
        Pixmap[] crates = tools.getPixmaps(new String[]{"pictures/crates/crate0.png", "pictures/crates/crate1.png",
                "pictures/crates/crate2.png", "pictures/crates/crate3.png", "pictures/crates/crate4.png"}, 60, 60);

        // draw pixmaps to the Texture to edit it.
        for (int i = 5; i < 55; i++) {
            for (int j = 5; j < 55; j++) {
                if (mapObstacles[i][j].equals("t")) {
                    ans.draw(trees[tools.randomIndex(5)], i * 200 + 40, 11840 - j * 200);
                } else if(mapObstacles[i][j].equals("b")){
                    ans.draw(bushes[tools.randomIndex(5)], i * 200 + 55, 11845 - j * 200);
                } else if(mapObstacles[i][j].equals("c")){
                    ans.draw(crates[tools.randomIndex(5)], i * 200 + 70, 11870 - j * 200);
                }
            }
        }
        return ans;
    }

    public int getMovingSpeed(int x, int y) {
        // based on user's position in positive values, return the appropriate moving speed
        int xInArray = (x - x % 200) / 200;
        int yInArray = (y - y % 200) / 200;

        // for speed: water < sand < grass < dirt, the speed rises from 3 to 6
        if (mapBasic[xInArray][yInArray].equals("w")) {
            return 2;       // water is slow
        } else if (mapBasic[xInArray][yInArray].equals("s")) {
            return 4;       // sand is a little faster
        } else if (mapBasic[xInArray][yInArray].equals("g")) {
            return 5;
        } else {
            return 6;
        }
    }

    public boolean validPosition(int x, int y){
        // returns true if user can go to this position, return false if he can not go to this position
        int inBlockX = x % 200;
        int inBlockY = y % 200;
        int xInArray = (x - inBlockX) / 200;
        int yInArray = (y - inBlockY) / 200;

        if (mapObstacles[xInArray][yInArray].equals("t")) {     // tree
            if (inBlockX > 70 && inBlockX < 130) {
                if (inBlockY > 70 && inBlockY < 130) {          // it user hits the center of the obstacle, he cannot go forward
                    return false;
                }
            }
        } else if (mapObstacles[xInArray][yInArray].equals("b")) {
            if (inBlockX > 80 && inBlockX < 120) {
                if (inBlockY > 80 && inBlockY < 120) {
                    return false;
                }
            }
        } else if (mapObstacles[xInArray][yInArray].equals("c")) {
            if (inBlockX > 70 && inBlockX < 130) {
                if (inBlockY > 70 && inBlockY < 130) {
                    return false;
                }
            }
        }
        return true;
    }
}
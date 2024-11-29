package com.badlogic.drop.user;

import java.io.Serializable;
import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    public int current_bird_index = 0 ;
    public Vector2 bird_position;
    public Vector2 bird_velocity;
    private ArrayList<ArrayList<Vector2>> block_position_list;
    private ArrayList<Vector2> pig_positions;
    private ArrayList<Integer> pig_textures_used;
    private ArrayList<ArrayList<Integer>> block_type_list;

    public GameState() {
        this.block_position_list = new ArrayList<>();
        this.pig_positions = new ArrayList<>();
        this.pig_textures_used = new ArrayList<>();
        this.block_type_list = new ArrayList<>();
    }

    // Getters and setters for the arrays
    public ArrayList<ArrayList<Vector2>> getBlock_position_list() {
        return block_position_list;
    }

    public void setBlock_position_list(ArrayList<ArrayList<Vector2>> block_position_list) {
        this.block_position_list = block_position_list;
    }

    public ArrayList<Vector2> getPig_positions() {
        return pig_positions;
    }

    public void setPig_positions(ArrayList<Vector2> pig_positions) {
        this.pig_positions = pig_positions;
    }

    public ArrayList<Integer> getPig_textures_used() {
        return pig_textures_used;
    }

    public void setPig_textures_used(ArrayList<Integer> pig_textures_used) {
        this.pig_textures_used = pig_textures_used;
    }

    public ArrayList<ArrayList<Integer>> getBlock_type_list() {
        return block_type_list;
    }

    public void setBlock_type_list(ArrayList<ArrayList<Integer>> block_type_list) {
        this.block_type_list = block_type_list;
    }
}

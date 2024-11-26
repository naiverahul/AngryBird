package com.badlogic.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class block_struct extends Actor{

    private Texture block_texture;
    private Body block_body;
    private Vector2 initial_position;
    private int health=10;

    public block_struct(BodyDef bd, FixtureDef fd, World lg_world, String block_texture_path, Vector2 block_position) {
        this.initial_position = block_position;
        
    }
    
}

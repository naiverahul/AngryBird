package com.badlogic.drop.physics;

import com.badlogic.drop.MyGame;
import com.badlogic.drop.Pig;
import com.badlogic.drop.block_struct;
import com.badlogic.drop.user.User;
import com.badlogic.gdx.math.Vector2;
// import com.badlogic.gdx.ai.btree.decorator.Random;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;

public class LevelGenerator {
    private final MyGame game_variable;
    private Stage lg_stage;
    private Random lg_random;
    private World lg_world;
    private User user;

    private final int[] min = { 1100, 150 }, max = { 1500, 400 };
    private final int block_height = 6, max_structures = 5;

    private String pigs_base = "Pigimages/pig", block_base_directory = "Structures/";
    private String[] pig_textures = { ".png", "2.png", "3.png", "4.png" }, block_types = { "wood", "stone", "glass" };

    public LevelGenerator(MyGame game, World world, Stage stage) {
        this.game_variable = game;
        this.lg_world = world;
        this.lg_stage = stage;
        this.lg_random = new Random();
        this.user = game.current_user;
    }

    public void generateLevel(BodyDef bd, FixtureDef fd) {
        lg_stage.clear();

        // generate_defense(bd,fd,5);

        // generatng defense
        for (int structure_count = 0; structure_count < max_structures; structure_count++) {
            int baseX = min[0] + lg_random.nextInt(max[0] - min[0]);
            int  structure_height = user.getLevel();
            structure_height += lg_random.nextInt(2);

            for (int i = 0; i < structure_height; i++) {

                // constructing blocks
                {
                    int blockY = block_height * i + min[1];

                    // selecting the blocktyep
                    String block_texture_path = block_base_directory + block_types[i % block_types.length]
                        + "/square.png";
                    Vector2 block_position = new Vector2(baseX, blockY);

                    // creating block and setting size
                    // block_struct block = new block_struct(bd, fd, lg_world, block_texture_path, block_position, block_height);
                    block_struct block = new block_struct(lg_world, block_texture_path, block_position, block_height);

                    block.setSize(block_height, block_height); // square blocks
                    lg_stage.addActor(block);
                }

            }
            // constructing pigs on top
            {
                int pigY = min[1] + (structure_height * block_height);
                Pig pig = new Pig(lg_world, pigs_base + pig_textures[structure_count % pig_textures.length],
                    new Vector2(baseX, pigY));
                lg_stage.addActor(pig);
            }
        }
    }
}

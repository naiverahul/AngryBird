package com.badlogic.drop;

// package Levels;

import Pigs.Pig;
import Defense.DefenseStructure;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.Random;

public class LevelGenerator {
    private ArrayList<Pig> pigs;
    private ArrayList<DefenseStructure> structures;
    private Random random;

    public LevelGenerator() {
        this.pigs = new ArrayList<>();
        this.structures = new ArrayList<>();
        this.random = new Random();
    }

    public void generateRandomLevel() {
        // Generate random pigs
        generatePigs(3 + random.nextInt(3));  // Randomly between 3 and 5 pigs

        // Generate random defense structures
        generateStructures(5 + random.nextInt(6));  // Randomly between 5 and 10 blocks
    }

    private void generatePigs(int numPigs) {
        for (int i = 0; i < numPigs; i++) {
            String name = "Pig" + (i + 1);
            String imagePath = "Pigimages/pig.png"; // Image path for the pig
            int health = 100 + random.nextInt(100);  // Random health between 100 and 200
            int level = 1;  // Default level

            // Random position for the pig (in a reasonable range)
            Vector2 position = new Vector2(800 + random.nextInt(500), 100 + random.nextInt(300));
            
            Pig pig = new Pig(name, imagePath, level, health);
            pigs.add(pig);
        }
    }

    private void generateStructures(int numStructures) {
        String[] materials = {"wood", "stone", "glass"};
        String[] shapes = {"rectangle", "triangle", "circle"};
        
        for (int i = 0; i < numStructures; i++) {
            // Randomly select material and shape
            String material = materials[random.nextInt(materials.length)];
            String shape = shapes[random.nextInt(shapes.length)];
            String imagePath = material + "_" + shape + ".png";  // Example image path: "wood_rectangle.png"

            // Random strength for the structure
            int strength = 50 + random.nextInt(100);  // Strength between 50 and 150

            // Random position for the structure
            Vector2 position = new Vector2(600 + random.nextInt(800), 100 + random.nextInt(300));

            DefenseStructure structure = new DefenseStructure(material + " " + shape, imagePath, strength, position);
            structures.add(structure);
        }
    }

    public ArrayList<Pig> getPigs() {
        return pigs;
    }

    public ArrayList<DefenseStructure> getStructures() {
        return structures;
    }

    public void dispose() {
        // Dispose of pigs and structures
        for (Pig pig : pigs) {
            pig.dispose();
        }
        for (DefenseStructure structure : structures) {
            structure.dispose();
        }
    }
}

// package com.badlogic.drop.saved_files;

// import com.badlogic.gdx.graphics.g2d.Batch;
// import java.util.ArrayList;
// import Pigs.Pig;
// import Defense.DefenseStructure;

// public class GameElements {
//     private ArrayList<Pig> pigs;
//     private ArrayList<DefenseStructure> structures;

//     public GameElements() {
//         pigs = new ArrayList<>();
//         structures = new ArrayList<>();
//     }

//     public void addPig(Pig pig) {
//         pigs.add(pig);
//     }

//     public void addStructure(DefenseStructure structure) {
//         structures.add(structure);
//     }

//     public void render(Batch batch) {
//         for (DefenseStructure structure : structures) {
//             batch.draw(structure.getTexture(), structure.getPosition().x, structure.getPosition().y);
//         }
//         for (Pig pig : pigs) {
//             batch.draw(pig.getTexture(), 800, 100); // Example static position
//         }
//     }

//     public void dispose() {
//         for (Pig pig : pigs) pig.dispose();
//         for (DefenseStructure structure : structures) structure.dispose();
//     }
// }


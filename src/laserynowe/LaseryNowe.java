/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laserynowe;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Adam
 */
public class LaseryNowe {

    public static void main(String[] args) {
        LaserGame Gra = new LaserGame();
        Gra.setVisible(true);
        Gra.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
}

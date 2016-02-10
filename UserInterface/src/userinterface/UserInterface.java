/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Panel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import mechdata.ChoiceData;
import mechdata.Mech;
import mechdata.MechContainer;
import sounds.SoundMaster;

/**
 *
 * @author StevenKiley
 */
public class UserInterface {
    MechContainer userMechs;
    /**
     * @param args the command line arguments
     */
    
    public UserInterface(MechContainer mechs){
        userMechs = mechs; 
    }
    
    public void run() throws IOException, LineUnavailableException {
      SoundMaster.playLaunch();
      Frame frame = new MechJFrame(this.userMechs);
      File path = new File(".\\src\\userinterface\\Images\\Menu\\MechWarrior-Online.png");
      Image img = ImageIO.read(path);
      frame.setIconImage(img);
      frame.setVisible(true);
    }
}

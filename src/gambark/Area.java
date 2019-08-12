/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gambark;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import static gambark.Gambar.LEBAR;
import static gambark.Gambar.TINGGI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author fajar
 */
public class Area{
    public static int map[][];
    public static int lebarBata;
    public static int tinggiBata;
    
    public int x,y;
    
    public static boolean mouse = false;
    
    private BufferStrategy br;
    
    private Graphics gr;
    
    private int nilai = 1;
    private Area sqr;
    
    public Area(int row, int col){
        
        
        map = new int[row][col];
            for(int i = 0; i < map.length; i++){
                    for(int j=0; j < map[0].length; j++){
                            map[i][j] = nilai;
                            
                    }
            }
            lebarBata = 20;
            tinggiBata = 20;

    }
    public void draw(Graphics2D g){
       
         
         
            for(int i = 0; i < map.length; i++){
                    for(int j=0; j < map[0].length; j++){
                            if(map[i][j] == 1){
                               
                                    g.setColor(Color.WHITE);
                                    g.fillRect(j * (lebarBata)+Gambar.margin, i * (tinggiBata)+Gambar.margin, lebarBata, tinggiBata);
                                
                                    if(!Gambar.clear){
                                    g.setStroke(new BasicStroke(3));
                                    g.setColor(Color.black);
                                    g.drawRect(j * (lebarBata)+Gambar.margin, i * (tinggiBata)+Gambar.margin, lebarBata, tinggiBata);
                                    }
                                   
                            }
                            if(map[i][j] <= 0){
                                
                                    g.setColor(Color.black);
                                    g.fillRect(j * (lebarBata)+Gambar.margin, i * (tinggiBata)+Gambar.margin, lebarBata, tinggiBata);
                                
                                    if(!Gambar.clear){
                                    g.setStroke(new BasicStroke(3));
                                    g.setColor(Color.black);
                                    g.drawRect(j * (lebarBata)+Gambar.margin, i * (tinggiBata)+Gambar.margin, lebarBata, tinggiBata);
                                    }
                                
                            }
                            if(map[i][j] == 2){
                                g.clearRect(j * (lebarBata)+Gambar.margin-10, i * (tinggiBata)+Gambar.margin-10, lebarBata*2, tinggiBata*2);
                            }
                            
                    }


            }
           g.dispose();
            
            
            
    }  
    public void setNilaiBata(int nilai, int row, int col){
	map[row][col] = nilai;
    }
   
    
    
}
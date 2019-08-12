/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gambark;

import static gambark.Area.lebarBata;
import static gambark.Area.map;
import static gambark.Area.tinggiBata;
import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import static java.awt.Color.RED;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author fajar
 */
public class Gambar extends Canvas  implements Runnable, MouseListener, KeyListener, MouseMotionListener {

    public static final int LEBAR = 450;
    public static final int TINGGI= 330;
    public static final int SCALE = 2;
    
    public static final String TITLE = "KUFI sqr";
    
    public static int margin = 60;
    private int width,height;
    
    private Thread thread;
    private boolean running = false;
    public static int panjang = 30;
    public static int lebar = 30;
    
    public String p;
    public String l;
    
    public static String tulisan = "WRITE";
    public static boolean clear = false;
    public static boolean map = false;
    public static boolean hover = false;
    
    private static JPanel panel;
    
    private static JFrame Frame;

    private static JButton btnBaru;
    private static JButton btnSimpan;
    
    private static Area sqr;
    
    private BufferedImage image;
    
    private GradientPaint gp;
    
    private int x,y;
    
    public Gambar (int width,int height,GradientPaint gp, String file)
    {           Dimension size = new Dimension(LEBAR*SCALE,TINGGI*SCALE);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
                this.width=width;
                this.height=height;
                this.gp=gp;
                drawImage(file);
                
                      
	}
	
	
	
	public static void main(String[] args){
		Gambar gbr = new Gambar(LEBAR*SCALE,TINGGI*SCALE,new GradientPaint(0,0,Color.BLUE,0,400,Color.CYAN),null);
    
                ImageIcon img = new ImageIcon("C:\\Users\\fajar\\Documents\\NetBeansProjects\\gambark\\src\\gambark\\KUFIicon.png");
                
		Frame = new JFrame(TITLE);
		               
                Frame.getContentPane().add(gbr);
		Frame.pack();
                
		Frame.setResizable(false);
		Frame.setLocationRelativeTo(null);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.setVisible(true);
                Frame.setIconImage(img.getImage());
                
                
                
                gbr.start();
               
}

    
        
        private synchronized void start(){
		if(running) return;
		running = true;
		thread = new Thread(this,"Thread");
		thread.start();
	}
       
        public static int getFrameWidth(){
		return LEBAR*SCALE;
	}
	
	public static int getFrameHeight(){
		return TINGGI*SCALE;
	}
        
        private void init(){
            scan();
        	 addKeyListener(this);
                   addMouseListener(this);
	}
        
        
        
        
        public void scan(){
            lebar = Integer.parseInt(JOptionPane.showInputDialog(null,"Panjang (1-40) *WAJIB diisi & angka"));
            panjang = Integer.parseInt(JOptionPane.showInputDialog(null,"Lebar (1-30) *WAJIB diisi &  angka"));
            if(lebar>=40)         lebar = 40;
            if(panjang>=30)          panjang = 30;
            sqr = new Area(panjang,lebar);
             }
      private synchronized void stop(){
		if(!running) return;
		running  = false;
		
	}
       public void tick(){
//           
        }
        public void run() {
		init();
		requestFocus();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0;
		double ns = 1000000000.0/60.0;
		int frames = 0;
		int ticks = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime = now;
			while(delta>=1){
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis()-timer>1000){
				timer+=1000;
				System.out.println(frames + " fps " + ticks + "ticks");
                                frames = 0;
				ticks = 0;
			}
		}
		stop();
	}
        
        
        
        public void render(){
            BufferStrategy bs = getBufferStrategy(); 
            if(bs==null){
		createBufferStrategy(3);
		return;
            }
            
                try {                
                    image = ImageIO.read(getClass().getResource("KUFI-sqr.png"));
                 } catch (IOException ex) {
                      // handle exception...
                 }
               Graphics g= (Graphics) bs.getDrawGraphics();
               g.setColor(Color.LIGHT_GRAY);
               g.fillRect(9, 10, 385, 30);
               g.fillRect(505, 10, 395, 30);
               
               g.setColor(Color.BLACK);
		g.setFont(new Font("Calibri",Font.PLAIN,18));
                
               	g.drawString("Baru" , 10, 30);
                if(hover) g.setColor(Color.RED);
                g.drawString("| Simpan ", 55, 30);
                g.setColor(Color.BLACK);
                g.drawString("| Bantuan" , 140, 30);
                g.drawImage(image, 400, 1, 100, 50, this);
                g.drawString("Fajaralmunawwar@yahoo.com" , 600, 30);
                sqr.draw((Graphics2D) g);
                map = true;
                
                g.dispose();
                
                bs.show();
        }
        
      //SIMPAN
    
    public static void simpan(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
        fileChooser.addChoosableFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + file.getAbsolutePath());
                new Gambar(lebar*20,panjang*20,new GradientPaint(0,0,Color.BLUE,0,400,Color.CYAN),file.getAbsolutePath());
                JOptionPane.showMessageDialog(null,"Gambar tersimpan!");
                    }    
            }
            
        
    private void drawImage(String file)
    {
        
        // Create a BufferedImage object specifying width,height of the image
        // and also the type (here alpha-red-green-blue)
        BufferedImage bim=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
       
        // Create Graphics object
        // Graphics2D is a sub class of the Graphics class,
        // so this statement is correct
        Graphics2D g2=bim.createGraphics();
       
        // Set the paint
        g2.setPaint(gp);
       
        // You can also use rendering hints
        // to smoothen the edges or the rounded rectangle
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);
       
        // Fill the round rectangle from (0,0) and the paint
        // should spread over the entire width and height
         if(map){
         for(int i = 0; i < Area.map.length; i++){
                    for(int j=0; j < Area.map[0].length; j++){
                            if(Area.map[i][j] == 1){
                               
                                    g2.setColor(Color.WHITE);
                                    g2.fillRect(j * (Area.lebarBata), i * (Area.tinggiBata), Area.lebarBata, Area.tinggiBata);
                                
                                    if(!clear){
                                    g2.setStroke(new BasicStroke(3));
                                    g2.setColor(Color.black);
                                    g2.drawRect(j * (Area.lebarBata), i * (Area.tinggiBata), Area.lebarBata, Area.tinggiBata);
                                    }
                                   
                            }
                            if(Area.map[i][j] <= 0){
                                
                                    g2.setColor(Color.black);
                                    g2.fillRect(j * (Area.lebarBata), i * (Area.tinggiBata), Area.lebarBata, Area.tinggiBata);
                                
                                    if(!Gambar.clear){
                                    g2.setStroke(new BasicStroke(3));
                                    g2.setColor(Color.black);
                                    g2.drawRect(j * (Area.lebarBata), i * (Area.tinggiBata), Area.lebarBata, Area.tinggiBata);
                                    }
                                
                            }
                            if(Area.map[i][j] == 2){
                                g2.clearRect(j * (Area.lebarBata)-10, i * (Area.tinggiBata)-10, Area.lebarBata*2, Area.tinggiBata*2);
                            }
                            
                                    
                                 }
         }   
         }
         // Dispose the Graphics2D object
        g2.dispose();
       
        
        
        // Throws IOException
        try
        {
            // Write the BufferedImage object to a file
            // The type of the image here is made PNG for
            // transparent edges as the image contains a 
            // rounded rectangle
         
            ImageIO.write(bim,"PNG",new File(file+".png"));
        }catch(Exception e){}
    }

            
        
          //mouse
    @Override
        public void mousePressed(MouseEvent e) {
        x = e.getX();
	y = e.getY();
        for(int i = 0; i < lebar; i++){
                    for(int j=0; j < panjang; j++){
                         
                    if(x>=i*Area.lebarBata+margin&&y>=j*Area.tinggiBata+margin&&x<=i*Area.lebarBata+margin+Area.lebarBata&&y<=j*Area.tinggiBata+margin+Area.tinggiBata){
                         if(Area.map[j][i] > 0){
                       System.out.println("CLICK: i:"+i+" j:"+j+" val= 1 ("+x+","+y+")");
                       sqr.setNilaiBata(0,j,i);
                         }else{
                              System.out.println("CLICK: i:"+i+" j:"+j+" val= 0 ("+x+","+y+")");
                      
                             sqr.setNilaiBata(1,j,i);
                         }
                    }
                                     
                }
            }
        
        //new canvas
        if(x>=10&&x<=50&&y>=20&&y<=40) {
             System.out.println("CLICK: ("+x+","+y+")");
             
             for(int i = 0; i < lebar; i++){
                    for(int j=0; j < panjang; j++){
                        sqr.setNilaiBata(2, j, i);
                    }
              }
           
           scan();
            
            }
        if(x>=55&&x<=135&&y>=20&&y<=40) {
             System.out.println("CLICK save: ("+x+","+y+")");
            try {
                simpan();
            } catch (Exception ex) {
                Logger.getLogger(Gambar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            }
        if(x>=138&&x<=210&&y>=20&&y<=40) {
             System.out.println("CLICK bantuan: ("+x+","+y+")");
             
            Tentang ob = new Tentang();
            
            ob.setVisible(true);
            
            
            }
	}
    
    public void mouseDragged(MouseEvent e) {

            }

    public void mouseMoved(MouseEvent e) {
       x = e.getX();
	y = e.getY();
        //BTN simpan
        if(x>=10&&x<=50&&y>=20&&y<=40) {
                hover = true;
            }

            }


    public void mouseClicked(MouseEvent e) {

            }


    public void mouseEntered(MouseEvent e) {
        
       }


    public void mouseExited(MouseEvent e) {
        }
    public void mouseReleased(MouseEvent e) {
         }

    
    @Override
    public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_C){
       
                if(!clear) {
                    clear = true;
                    System.out.println("clear");
                }
                else{
                    clear = false;
                     System.out.println("ruler");
                }
            }   
    if(e.getKeyCode() == KeyEvent.VK_X){
        
   for(int i = 0; i < Area.map.length; i++){
                    for(int j=0; j < Area.map[0].length; j++){
                        sqr.setNilaiBata(1,j,i);
                    }
                    }
    }
    
    }
    @Override
    public void keyTyped(KeyEvent e) {
      }


    @Override
    public void keyReleased(KeyEvent e) {
         }
      
    
   

}

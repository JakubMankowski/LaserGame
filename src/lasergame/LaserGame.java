package lasergame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

final class LaserGame extends JFrame implements KeyListener,ActionListener
{ 
    public class PointOfReflect
    {
        public int currentLaserX, currentLaserY, previousLaserX,previousLaserY;
        public PointOfReflect(int c1, int c2, int p1, int p2)
        {
            currentLaserX = c1;
            currentLaserY = c2;
            previousLaserX = p1;
            previousLaserY = p2;
        }
        public void printPointOfReflect()
        {
            System.out.println(currentLaserX+","+ currentLaserY+"; "+previousLaserX+","+previousLaserY);
        }
    }
    public class WinningPoints
    {
        public int winX, winY;
        public WinningPoints(int x, int y)
        {
            winX = x;
            winY = y;
        }
    }
    
    static int m = 16;
    static int n= 16; 
    static int currentX=5;
    static int currentY=11;
    static boolean win = false;
    static boolean up = false;
    static boolean down = false;
    static boolean left = false;
    static boolean right = false;
    final static int startPointLaserX = 4;
    final static int startPointLaserY = 5;
    final static int startPointLaserPX = 4;
    final static int startPointLaserPY = 6;
    final static int startPointLaserPPX = 5;
    final static int startPointLaserPPY = 6;
    static int currentLaserX;
    static int currentLaserY;
    static int previousLaserX;
    static int previousLaserY;
    static int previouspreviousLaserX;
    static int previouspreviousLaserY;
    static int winningPointX = 12;
    static int winningPointY = 11;
    static boolean paint = true;
    static int moves = 0;
    JLabel mapa[][];
    JPanel panel;
    JPanel menu;
    JButton button1;
    JButton button2;
    JButton button3;
    int mapTable[][];
    ArrayList<PointOfReflect> listOfReflect;
    
    ArrayList<WinningPoints> listWinningPoints;
    PointOfReflect previous = new PointOfReflect(0, 0, 0, 0);
    
    JMenuBar MenuBar;
    JMenu MenuPlik, MenuLevel, MenuOpcje, MenuPomoc;
    JMenuItem /*mOtworz, mZapisz,*/ mWyjscie, mLevel1, mLevel2,  mLevel3, mOProgramie;
    
    LaserGame()
    {
        mapTable = new int[m][n];
        mapa = new JLabel [m][n];
        panel = new JLines();
        
        listOfReflect = new ArrayList<PointOfReflect>();
        listWinningPoints = new ArrayList<WinningPoints>();
        
        
        for (int y = 0;y<n;y++){
            for (int x = 0;x<m;x++){
                mapa[x][y] = new JLabel();
                mapa[x][y].setBackground(Color.BLACK);
                //panel.add(mapa[x][y]);
                mapa[x][y].setEnabled(true);
                mapa[x][y].setOpaque(true);
                //mapa[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                mapa[x][y].setPreferredSize(new Dimension(0,0));      
            }
        }
        panel.setLayout(new GridLayout(n,m));
        for (int y = 0;y<n;y++){
            for (int x = 0;x<m;x++){
                    panel.add(mapa[x][y]);
            }
        }
        add(panel);
        button1 = new JButton("START");
        button2 = new JButton("SETLEVEL");
        button3 = new JButton("QUIT");
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        menu = new JPanel();
        menu.setBounds(panel.getWidth(), 0, panel.getWidth()/5, panel.getHeight());
        menu.setBackground(Color.blue);
        menu.setLayout(new GridLayout(3,0));
        menu.add(button1);
        menu.add(button2);
        menu.add(button3);
        add(menu);
        setLayout(new GridLayout(2,0));
        
        

        
        MenuBar = new JMenuBar();
        MenuPlik = new JMenu("Plik");

        //mOtworz = new JMenuItem("Otwórz");
        //mZapisz = new JMenuItem("Zapisz");
        mWyjscie = new JMenuItem("Wyjście");

        //MenuPlik.add(mOtworz);
        //MenuPlik.add(mZapisz);
        MenuPlik.addSeparator();
        MenuPlik.add(mWyjscie);
        mWyjscie.addActionListener(this);
        
        MenuLevel = new JMenu("SetLevel");

        mLevel1 = new JMenuItem("Level1");
        mLevel2 = new JMenuItem("Level2");
        mLevel3 = new JMenuItem("Level3");
        
        mLevel1.addActionListener(this);
        mLevel2.addActionListener(this);
        mLevel3.addActionListener(this);

        MenuLevel.add(mLevel1);
        MenuLevel.add(mLevel2);
        MenuLevel.add(mLevel3);


        MenuBar.add(Box.createHorizontalStrut(20));
        MenuPomoc = new JMenu("Pomoc");

        mOProgramie = new JMenuItem("O programie");
        MenuPomoc.add(mOProgramie);

        setJMenuBar(MenuBar);
        MenuBar.add(MenuPlik);
        MenuBar.add(MenuLevel);
        MenuBar.add(MenuPomoc);
        
        //initLabel();
        /*for(int i=1;i<n-1;i++)
            for(int j=0 ; j<m-1;j++)
                if((i+j) % 4==0)mapa[i][j].setBackground(Color.gray);*/
        
        
        
        setFocusable(true);
        setSize(600,800);
        setResizable(true);
        addKeyListener(this);
    }
    class JLines extends JPanel{
        public JLines()
        {
        }
        public void drawLine(Graphics g)
        {
            previous = new PointOfReflect(startPointLaserX, startPointLaserY, startPointLaserPX, startPointLaserPY);
            for(PointOfReflect p: listOfReflect)
            {
                g.drawLine((panel.getWidth()/m)*(previous.currentLaserX+previous.previousLaserX+1)/2, 
                        (panel.getHeight()/n)*(previous.currentLaserY+previous.previousLaserY+1)/2, 
                        (panel.getWidth()/m)*(p.currentLaserX+p.previousLaserX+1)/2, 
                        (panel.getHeight()/n)*(p.currentLaserY+p.previousLaserY+1)/2);

                previous = p;

            }
        }
        public void drawTank(Graphics g)
        {
        }
        @Override
        protected void paintChildren(Graphics g) 
        {
            super.paintChildren(g);
            g.setColor(Color.green);
            repaint();
            drawTank(g);
            drawLine(g);
            
        }       
    }
    private void initLabel()
    {
        for (int i = 0;i<m;i++){
            mapa[i][0].setBackground(Color.GRAY);
            mapa[i][n-1].setBackground(Color.GRAY);
        }
        for (int i = 0;i<n;i++){
            mapa[0][i].setBackground(Color.GRAY);
            mapa[m-1][i].setBackground(Color.GRAY);     
        }
    }
    public void zerosLabel()
    {
        for (int i = 0;i<m;i++)
        {
            for (int j = 0;j<n;j++)
            {
                mapa[i][j].setBackground(Color.black);
            }
        }
    }
    private void setLaser(int dcx, int dcy, int dpx, int dpy, int dppx, int dppy)
    {
        currentLaserX +=dcx;
        currentLaserY +=dcy;
        previousLaserX +=dpx;
        previousLaserY +=dpy;
        previouspreviousLaserX +=dppx;
        previouspreviousLaserY +=dppy;
    }
    private void setLaserStartPoint()
    {
        currentLaserX = startPointLaserX;
        currentLaserY = startPointLaserY;
        previousLaserX = startPointLaserPX;
        previousLaserY = startPointLaserPY;
        previouspreviousLaserX = startPointLaserPPX;
        previouspreviousLaserY = startPointLaserPPY;
    }
    private void createOneLaserField() throws ArrayIndexOutOfBoundsException
    {
        if(currentLaserX > previousLaserX && currentLaserY == previousLaserY && previousLaserY > previouspreviousLaserY)
        { 
            if(mapa[currentLaserX][currentLaserY+1].getBackground() == Color.GRAY || mapa[currentLaserX][currentLaserY+1].getBackground() == Color.WHITE)
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,1,1,0,2);
            }
            else
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,1,1,0,0,1);
            }
        }
        else if(currentLaserX > previousLaserX && currentLaserY == previousLaserY && previousLaserY < previouspreviousLaserY)
        {
            if(mapa[currentLaserX][currentLaserY-1].getBackground() == Color.GRAY || mapa[currentLaserX][currentLaserY-1].getBackground() == Color.WHITE)
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,1,-1,0,-2);
            }
            else
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,-1,1,0,0,-1);
            } 
        }
        else if(currentLaserX < previousLaserX && currentLaserY == previousLaserY && previousLaserY > previouspreviousLaserY)
        {
            if(mapa[currentLaserX][currentLaserY+1].getBackground() == Color.GRAY || mapa[currentLaserX][currentLaserY+1].getBackground() == Color.WHITE)
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,-1,1,0,2);
            }
            else
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,1,-1,0,0,1);
            } 
        }
        else if(currentLaserX < previousLaserX && currentLaserY == previousLaserY && previousLaserY < previouspreviousLaserY)
        {
            if(mapa[currentLaserX][currentLaserY-1].getBackground() == Color.GRAY || mapa[currentLaserX][currentLaserY-1].getBackground() == Color.WHITE)
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,-1,-1,0,-2);

            }
            else
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,-1,-1,0,0,-1);
            } 
        }
        else if(currentLaserX == previousLaserX && currentLaserY > previousLaserY && previousLaserX > previouspreviousLaserX)
        {  
            if(mapa[currentLaserX+1][currentLaserY].getBackground() == Color.GRAY || mapa[currentLaserX+1][currentLaserY].getBackground() == Color.WHITE)
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,1,1,2,0);

            }
            else
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(1,0,0,1,1,0);
            }
        }
        else if(currentLaserX == previousLaserX && currentLaserY > previousLaserY && previousLaserX < previouspreviousLaserX)
        {
            if(mapa[currentLaserX-1][currentLaserY].getBackground() == Color.GRAY || mapa[currentLaserX-1][currentLaserY].getBackground() == Color.WHITE)
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,-1,1,-2,0);
            }
            else
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(-1,0,0,1,-1,0);
            } 
        }
        else if(currentLaserX == previousLaserX && currentLaserY < previousLaserY && previousLaserX > previouspreviousLaserX)
        {
            if(mapa[currentLaserX+1][currentLaserY].getBackground() == Color.GRAY || mapa[currentLaserX+1][currentLaserY].getBackground() == Color.WHITE)
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,1,-1,2,0);

            }
            else
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(1,0,0,-1,1,0);
            } 
        }
        else if(currentLaserX == previousLaserX && currentLaserY < previousLaserY && previousLaserX < previouspreviousLaserX)
        {
            if(mapa[currentLaserX-1][currentLaserY].getBackground() == Color.GRAY||mapa[currentLaserX-1][currentLaserY].getBackground() == Color.WHITE)
            {
                // jest odbicie
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,-1,-1,-2,0);
            }
            else
            {
                // nie ma odbicia 
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(-1,0,0,-1,-1,0);            
            } 
        }
    }
    public void createLaserTable()
    {
        PointOfReflect currentPoint = new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY);
        if(!listOfReflect.isEmpty())listOfReflect.clear();
        if(mapa[currentLaserX][currentLaserY].getBackground()==Color.GRAY){}
        else{
            listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
            do
            {
                try{
                createOneLaserField();
                }
                catch(ArrayIndexOutOfBoundsException e)
                {
                    listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                    if(currentLaserX > previousLaserX && currentLaserY == previousLaserY && previousLaserY > previouspreviousLaserY)
                    { 
                        setLaser(0,1,1,0,0,1);
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                    }
                    else if(currentLaserX > previousLaserX && currentLaserY == previousLaserY && previousLaserY < previouspreviousLaserY)
                    {
                        setLaser(0,-1,1,0,0,-1);
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));  
                    }
                    else if(currentLaserX < previousLaserX && currentLaserY == previousLaserY && previousLaserY > previouspreviousLaserY)
                    {
                        setLaser(0,1,-1,0,0,1);
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        
                    }
                    else if(currentLaserX < previousLaserX && currentLaserY == previousLaserY && previousLaserY < previouspreviousLaserY)
                    {
                        setLaser(0,-1,-1,0,0,-1);
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));       
                    }
                    else if(currentLaserX == previousLaserX && currentLaserY > previousLaserY && previousLaserX > previouspreviousLaserX)
                    {  
                        setLaser(1,0,0,1,1,0);
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                    }
                    else if(currentLaserX == previousLaserX && currentLaserY > previousLaserY && previousLaserX < previouspreviousLaserX)
                    {
                        setLaser(-1,0,0,1,-1,0);
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        
                    }
                    else if(currentLaserX == previousLaserX && currentLaserY < previousLaserY && previousLaserX > previouspreviousLaserX)
                    {
                        setLaser(1,0,0,-1,1,0);
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                            
                    }
                    else if(currentLaserX == previousLaserX && currentLaserY < previousLaserY && previousLaserX < previouspreviousLaserX)
                    {
                        setLaser(-1,0,0,-1,-1,0);  
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                                  
                    }
                    setLaserStartPoint();
                    break;
                }
                finally{
                    try{
                    for(WinningPoints w: listWinningPoints)
                        if(currentLaserX==w.winX && currentLaserY == w.winY)
                        {
                            mapa[w.winX][w.winY].setBackground(Color.BLACK);
                            listWinningPoints.remove(w);
                            if(listWinningPoints.isEmpty())
                            {
                                if(!win)
                                {
                                    win();
                                    win = true;
                                }
                                
                            }
                        }
                    }
                    catch(ConcurrentModificationException e)
                    {
                        
                    }
                }
                currentPoint= new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY);


            }while(!ifExist(listOfReflect, currentPoint));
        }
    }
    boolean ifExist(ArrayList<PointOfReflect> list, PointOfReflect current) {
        for (PointOfReflect item : list) {
            if (item.currentLaserX==current.currentLaserX && item.currentLaserY==current.currentLaserY&&item.previousLaserX==current.previousLaserX && item.previousLaserY == current.previousLaserY)
            {
                return true;
            }
        }
        return false;
    }
    boolean Exist(ArrayList<PointOfReflect> list, int currentX, int currentY) {
    for (PointOfReflect item : list) {
        if (item.currentLaserX==currentX && item.currentLaserY==currentY)
        {
            return true;
        }
    }
    return false;
    }
    public boolean checkmove(int x, int y, int dx, int dy){
            if (((x+dx) > -1) && ((x+dx) < n) && ((y+dy) > -1) && ((y+dy) < m)){
                    return true;
            } else {
                    return false;
            }
    }
    private void up() {
        try{
            if(mapa[currentX][currentY-1].getBackground()==Color.BLACK)
            {

                mapa[currentX][currentY-1].setBackground(Color.YELLOW);
                mapa[currentX][currentY].setBackground(Color.BLACK);
                currentY--;
            }
            else if(mapa[currentX][currentY-1].getBackground()==Color.GRAY && mapa[currentX][currentY-2].getBackground()==Color.BLACK)
            {
                mapa[currentX][currentY-2].setBackground(Color.GRAY);
                mapa[currentX][currentY-1].setBackground(Color.YELLOW);
                mapa[currentX][currentY].setBackground(Color.BLACK);
                currentY--;
            }
            else
            {
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {  
        }
    }
    private void down() {
        try{
            if(mapa[currentX][currentY+1].getBackground()==Color.BLACK)
            {
                mapa[currentX][currentY+1].setBackground(Color.YELLOW);
                mapa[currentX][currentY].setBackground(Color.BLACK);
                currentY++;
            }
            else if(mapa[currentX][currentY+1].getBackground()==Color.GRAY && mapa[currentX][currentY+2].getBackground()==Color.BLACK)
            {
                mapa[currentX][currentY+2].setBackground(Color.GRAY);
                mapa[currentX][currentY+1].setBackground(Color.YELLOW);
                mapa[currentX][currentY].setBackground(Color.BLACK);
                currentY++;
            }
            else
            {
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            
        }
    }
    private void left() {
        try{
            if(mapa[currentX-1][currentY].getBackground()==Color.BLACK)
            {
                mapa[currentX-1][currentY].setBackground(Color.YELLOW);
                mapa[currentX][currentY].setBackground(Color.BLACK);
                currentX--;
            }
            else if(mapa[currentX-1][currentY].getBackground()==Color.GRAY && mapa[currentX-2][currentY].getBackground()==Color.BLACK)
            {
                mapa[currentX-2][currentY].setBackground(Color.GRAY);
                mapa[currentX-1][currentY].setBackground(Color.YELLOW);
                mapa[currentX][currentY].setBackground(Color.BLACK);
                currentX--;
            }
            else
            {
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            
        }
    }
    private void right() {
        try{
            if(mapa[currentX+1][currentY].getBackground()==Color.BLACK)
            {
                mapa[currentX+1][currentY].setBackground(Color.YELLOW);
                mapa[currentX][currentY].setBackground(Color.BLACK);
                currentX++;
            }
            else if(mapa[currentX+1][currentY].getBackground()==Color.GRAY && mapa[currentX+2][currentY].getBackground()==Color.BLACK)
            {
                mapa[currentX+2][currentY].setBackground(Color.GRAY);
                mapa[currentX+1][currentY].setBackground(Color.YELLOW);
                mapa[currentX][currentY].setBackground(Color.BLACK);
                currentX++;
            }
            else
            {
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            
        }
    }
    public void win(){
        Component temporaryLostComponent = null;
	JOptionPane.showMessageDialog(temporaryLostComponent, "You scored: "+moves+". Well done!");
    }
    
    @Override
    public void keyPressed(KeyEvent e) {        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (checkmove(currentX,currentY, 1,0) == true)
            {
                moves++;          
                right();
                createLaserTable();
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if (checkmove(currentX,currentY, -1,0) == true){
                moves++;
                left();
                createLaserTable();
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP){
            if (checkmove(currentX,currentY, 0,-1) == true){
                moves++;
                up();
                createLaserTable();
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            if (checkmove(currentX,currentY, 0,1) == true){
                moves++;
                down();
                createLaserTable();
            }
        }	
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object zrodlo = e.getSource();
        if(zrodlo == button1);
        {         
            
        }
        if(zrodlo==button2)
        {
            
        }
        else if(zrodlo==button3)
        {
            dispose();
        }
        else if (zrodlo == mWyjscie) {
            dispose();
        }
        else if(zrodlo == mLevel1)
        {
            zerosLabel();
            mapa[currentX][currentY].setBackground(Color.black);
            setLaserStartPoint();
            currentX=0;
            currentY = 0;
            mapa[previousLaserX][previousLaserY].setBackground(Color.green);
            mapa[3][0].setBackground(Color.white);
            mapa[5][0].setBackground(Color.white);
            mapa[14][0].setBackground(Color.white);
            mapa[1][1].setBackground(Color.white);
            mapa[3][1].setBackground(Color.white);
            mapa[7][1].setBackground(Color.white);
            mapa[9][1].setBackground(Color.white);
            mapa[12][1].setBackground(Color.white);
            mapa[7][2].setBackground(Color.white);
            mapa[9][2].setBackground(Color.white);
            mapa[13][2].setBackground(Color.white);
            mapa[2][3].setBackground(Color.white);
            mapa[3][3].setBackground(Color.white);
            mapa[11][3].setBackground(Color.white);
            mapa[5][4].setBackground(Color.white);
            mapa[7][4].setBackground(Color.white);
            mapa[9][4].setBackground(Color.white);
            mapa[0][5].setBackground(Color.white);
            mapa[2][5].setBackground(Color.white);
            mapa[6][5].setBackground(Color.white);
            mapa[11][5].setBackground(Color.white);
            mapa[12][5].setBackground(Color.white);
            mapa[13][5].setBackground(Color.white);
            mapa[15][5].setBackground(Color.white);
            mapa[0][6].setBackground(Color.white);
            mapa[6][6].setBackground(Color.white);
            mapa[8][6].setBackground(Color.white);
            mapa[0][7].setBackground(Color.white);
            mapa[4][7].setBackground(Color.white);
            mapa[9][7].setBackground(Color.white);
            mapa[11][8].setBackground(Color.white); 
            mapa[14][8].setBackground(Color.white);
            mapa[1][9].setBackground(Color.white);
            mapa[3][9].setBackground(Color.white);
            mapa[7][9].setBackground(Color.white);
            mapa[9][9].setBackground(Color.white);
            mapa[0][10].setBackground(Color.white);
            mapa[12][10].setBackground(Color.white);
            mapa[1][11].setBackground(Color.white);
            mapa[7][11].setBackground(Color.white);
            mapa[13][11].setBackground(Color.white);
            mapa[14][11].setBackground(Color.white);
            mapa[15][11].setBackground(Color.white);

            mapa[3][2].setBackground(Color.gray);
            mapa[14][5].setBackground(Color.gray);
            mapa[7][7].setBackground(Color.gray);
            mapa[7][10].setBackground(Color.gray);
            mapa[11][7].setBackground(Color.gray);
            listWinningPoints.clear();
            listWinningPoints.add(new WinningPoints(13,13));
            listWinningPoints.add(new WinningPoints(14,15));
            for(WinningPoints w: listWinningPoints)
            mapa[w.winX][w.winY].setBackground(Color.red);
        
            mapa[currentX][currentY].setBackground(Color.YELLOW);
            
            createLaserTable();

        }
        else if(zrodlo == mLevel2)
        {
            zerosLabel();
            mapa[currentX][currentY].setBackground(Color.black);
            setLaserStartPoint();
            currentX=1;
            currentY = 5;
            mapa[previousLaserX][previousLaserY].setBackground(Color.green);
            mapa[2][1].setBackground(Color.white);
            mapa[5][1].setBackground(Color.white);
            mapa[14][1].setBackground(Color.white);
            mapa[3][2].setBackground(Color.white);
            mapa[4][2].setBackground(Color.white);
            mapa[9][2].setBackground(Color.white);
            mapa[12][2].setBackground(Color.white);
            mapa[5][3].setBackground(Color.white);
            mapa[9][3].setBackground(Color.white);
            mapa[13][3].setBackground(Color.white);
            mapa[5][4].setBackground(Color.white);
            mapa[7][4].setBackground(Color.white);
            mapa[9][4].setBackground(Color.white);
            mapa[0][5].setBackground(Color.white);
            mapa[2][5].setBackground(Color.white);
            mapa[6][5].setBackground(Color.white);
            mapa[11][5].setBackground(Color.white);
            mapa[12][5].setBackground(Color.white);
            mapa[13][5].setBackground(Color.white);
            mapa[15][5].setBackground(Color.white);
            mapa[0][6].setBackground(Color.white);
            mapa[6][6].setBackground(Color.white);
            mapa[8][6].setBackground(Color.white);
            mapa[0][7].setBackground(Color.white);
            mapa[4][7].setBackground(Color.white);
            mapa[9][7].setBackground(Color.white);
            mapa[11][8].setBackground(Color.white); 
            mapa[14][8].setBackground(Color.white);
            mapa[1][9].setBackground(Color.white);
            mapa[3][9].setBackground(Color.white);
            mapa[7][9].setBackground(Color.white);
            mapa[9][9].setBackground(Color.white);
            mapa[0][10].setBackground(Color.white);
            mapa[12][10].setBackground(Color.white);
            mapa[1][11].setBackground(Color.white);
            mapa[7][11].setBackground(Color.white);
            mapa[13][11].setBackground(Color.white);
            mapa[14][11].setBackground(Color.white);
            mapa[15][11].setBackground(Color.white);

            mapa[3][2].setBackground(Color.gray);
            mapa[14][5].setBackground(Color.gray);
            mapa[7][7].setBackground(Color.gray);
            mapa[7][10].setBackground(Color.gray);
            mapa[11][7].setBackground(Color.gray);
            
            listWinningPoints.clear();
            listWinningPoints.add(new WinningPoints(10,13));
            listWinningPoints.add(new WinningPoints(14,15));
            listWinningPoints.add(new WinningPoints(13,8));
            for(WinningPoints w: listWinningPoints)
            mapa[w.winX][w.winY].setBackground(Color.red);
        
            mapa[currentX][currentY].setBackground(Color.YELLOW);
            
            createLaserTable();

        }
        else if(zrodlo == mLevel3)
        {
            zerosLabel();
            mapa[currentX][currentY].setBackground(Color.black);
            setLaserStartPoint();
            currentX=6;
            currentY = 3;
            mapa[previousLaserX][previousLaserY].setBackground(Color.green);
            
            for(int i=0;i<7;i++)
                for(int j=0;j<12;j++)
                    mapa[2*i+1][j+2].setBackground(Color.gray);
            listWinningPoints.clear();
            for(int i=0;i<14;i++)
                listWinningPoints.add(new WinningPoints(i+1,15));
            for(WinningPoints w: listWinningPoints)
            mapa[w.winX][w.winY].setBackground(Color.red);
            mapa[currentX][currentY].setBackground(Color.YELLOW);
            
            createLaserTable();
                
            
        }
    }
}
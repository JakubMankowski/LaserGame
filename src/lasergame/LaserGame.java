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
    
    static int m = 16;
    static int n= 16; 
    static int currentX=5;
    static int currentY=11;
    static boolean win = false;
    static boolean up = false;
    static boolean down = false;
    static boolean left = false;
    static boolean right = false;
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
    int laserTable[][];
    int mapTable[][];
    ArrayList<PointOfReflect> listOfReflect;
    
    JMenuBar MenuBar;
    JMenu MenuPlik, MenuLevel, MenuOpcje, MenuPomoc;
    JMenuItem mOtworz, mZapisz, mWyjscie, mLevel1, mLevel2,  mLevel3, mOProgramie;
    
    LaserGame()
    {
        
        
        laserTable = new int[m][n];
        mapTable = new int[m][n];
        mapa = new JLabel [m][n];
        panel = new JLines();
        
        listOfReflect = new ArrayList<PointOfReflect>();
        
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

        mOtworz = new JMenuItem("Otwórz");
        mZapisz = new JMenuItem("Zapisz");
        mWyjscie = new JMenuItem("Wyjście");

        MenuPlik.add(mOtworz);
        MenuPlik.add(mZapisz);
        MenuPlik.addSeparator();
        MenuPlik.add(mWyjscie);
        mWyjscie.addActionListener(this);
        
        MenuLevel = new JMenu("SetLevel");

        mLevel1 = new JMenuItem("Level1");
        mLevel2 = new JMenuItem("Level2");
        
        mLevel1.addActionListener(this);
        mLevel2.addActionListener(this);

        MenuLevel.add(mLevel1);
        MenuLevel.add(mLevel2);


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
        //createLaserTable();
        //printLaserTable();
    }
    class JLines extends JPanel{
        public JLines()
        {
        }
        public void paintbottomright(Graphics g, int i, int j)
        {
            g.drawLine(mapa[0][0].getWidth()*i+mapa[0][0].getWidth()/2,mapa[0][0].getHeight()*j+mapa[0][0].getHeight(),mapa[0][0].getWidth()*i+mapa[0][0].getWidth(),mapa[0][0].getHeight()*j+ mapa[0][0].getHeight()/2);
        }
        public void paintbottomleft(Graphics g, int i, int j)
        {
            g.drawLine(mapa[0][0].getWidth()*i+0,mapa[0][0].getHeight()*j+mapa[0][0].getHeight()/2, mapa[0][0].getWidth()*i+mapa[0][0].getWidth()/2,mapa[0][0].getHeight()*j+ mapa[0][0].getHeight());
        }
        public void painttopright(Graphics g, int i, int j)
        {
            g.drawLine(mapa[0][0].getWidth()*i+mapa[0][0].getWidth()/2,mapa[0][0].getHeight()*j+0,mapa[0][0].getWidth()*i+ mapa[0][0].getWidth(), mapa[0][0].getHeight()*j+mapa[0][0].getHeight()/2);
        }
        public void painttopleft(Graphics g, int i, int j)
        {
            g.drawLine(mapa[0][0].getWidth()*i+0,mapa[0][0].getHeight()*j+mapa[0][0].getHeight()/2,mapa[0][0].getWidth()*i+ mapa[0][0].getWidth()/2,mapa[0][0].getHeight()*j+ 0);
        }
        public void drawLine(Graphics g)
        {
            if(paint){
                for(int i=0;i<m;i++){
                    for(int j=0;j<m;j++){
                        int value = laserTable[i][j];
                        while(value>0)
                        {
                            if(value/8==1)
                            {
                                painttopleft(g,i,j);
                                value-=8;
                            }
                            else
                            {
                                if(value/4==1)
                                {
                                    painttopright(g,i,j);
                                    value-=4;
                                }
                                else
                                {
                                    if(value/2==1)
                                    {
                                        paintbottomright(g, i ,j);
                                        value-=2;
                                    }
                                    else
                                    {
                                        if(value == 1)
                                        {
                                            paintbottomleft(g,i,j);
                                            value-=1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
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
    public void zerosLaserTable()
    {
        for (int i = 0;i<m;i++)
        {
            for (int j = 0;j<n;j++)
            {
                laserTable[i][j] = 0;
            }
        }
    }
    public void printLaserTable()
    {
        for (int i = 0;i<m;i++)
        {
            for (int j = 0;j<n;j++)
            {
                System.out.print(laserTable[j][i]+"\t");
            }
            System.out.print("\n");
        }
    }
    private void setLaser(int dcx, int dcy, int dpx, int dpy, int dppx, int dppy, int value)
    {
        laserTable[currentLaserX][currentLaserY]+=value;
        currentLaserX +=dcx;
        currentLaserY +=dcy;
        previousLaserX +=dpx;
        previousLaserY +=dpy;
        previouspreviousLaserX +=dppx;
        previouspreviousLaserY +=dppy;
    }
    private void setLaserStartPoint()
    {
        currentLaserX = 4;
        currentLaserY = 5;
        previousLaserX = 4;
        previousLaserY = 6;
        previouspreviousLaserX = 5;
        previouspreviousLaserY = 6;
    }
    private void createOneLaserField() throws ArrayIndexOutOfBoundsException
    {
        if(currentLaserX > previousLaserX && currentLaserY == previousLaserY && previousLaserY > previouspreviousLaserY)
        { 
            if(mapa[currentLaserX][currentLaserY+1].getBackground() == Color.GRAY || mapa[currentLaserX][currentLaserY+1].getBackground() == Color.WHITE)
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,1,1,0,2,1);

            }
            else
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,1,1,0,0,1,1);
            }
        }
        else if(currentLaserX > previousLaserX && currentLaserY == previousLaserY && previousLaserY < previouspreviousLaserY)
        {
            if(mapa[currentLaserX][currentLaserY-1].getBackground() == Color.GRAY || mapa[currentLaserX][currentLaserY-1].getBackground() == Color.WHITE)
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,1,-1,0,-2,8);
            }
            else
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,-1,1,0,0,-1,8);
            } 
        }
        else if(currentLaserX < previousLaserX && currentLaserY == previousLaserY && previousLaserY > previouspreviousLaserY)
        {
            if(mapa[currentLaserX][currentLaserY+1].getBackground() == Color.GRAY || mapa[currentLaserX][currentLaserY+1].getBackground() == Color.WHITE)
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,-1,1,0,2,2);
            }
            else
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,1,-1,0,0,1,2);
            } 
        }
        else if(currentLaserX < previousLaserX && currentLaserY == previousLaserY && previousLaserY < previouspreviousLaserY)
        {
            if(mapa[currentLaserX][currentLaserY-1].getBackground() == Color.GRAY || mapa[currentLaserX][currentLaserY-1].getBackground() == Color.WHITE)
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,-1,-1,0,-2,4);

            }
            else
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,-1,-1,0,0,-1,4);
            } 
        }
        else if(currentLaserX == previousLaserX && currentLaserY > previousLaserY && previousLaserX > previouspreviousLaserX)
        {  
            if(mapa[currentLaserX+1][currentLaserY].getBackground() == Color.GRAY || mapa[currentLaserX+1][currentLaserY].getBackground() == Color.WHITE)
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,1,1,2,0,4);

            }
            else
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(1,0,0,1,1,0,4);
            }
        }
        else if(currentLaserX == previousLaserX && currentLaserY > previousLaserY && previousLaserX < previouspreviousLaserX)
        {
            if(mapa[currentLaserX-1][currentLaserY].getBackground() == Color.GRAY || mapa[currentLaserX-1][currentLaserY].getBackground() == Color.WHITE)
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,-1,1,-2,0,8);

            }
            else
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(-1,0,0,1,-1,0,8);
            } 
        }
        else if(currentLaserX == previousLaserX && currentLaserY < previousLaserY && previousLaserX > previouspreviousLaserX)
        {
            if(mapa[currentLaserX+1][currentLaserY].getBackground() == Color.GRAY || mapa[currentLaserX+1][currentLaserY].getBackground() == Color.WHITE)
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,1,-1,2,0,2);

            }
            else
            {
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(1,0,0,-1,1,0,2);
            } 
        }
        else if(currentLaserX == previousLaserX && currentLaserY < previousLaserY && previousLaserX < previouspreviousLaserX)
        {
            if(mapa[currentLaserX-1][currentLaserY].getBackground() == Color.GRAY||mapa[currentLaserX-1][currentLaserY].getBackground() == Color.WHITE)
            {
                // jest odbicie
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(0,0,-1,-1,-2,0,1);
            }
            else
            {
                // nie ma odbicia 
                listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                setLaser(-1,0,0,-1,-1,0,1);            
            } 
        }
    }
    public void createLaserTable()
    {
        zerosLaserTable();
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
                    setLaserStartPoint();
                    break;
                }
                if(currentLaserX==winningPointX && currentLaserY == winningPointY)
                {
                    if(!win)
                    {
                        win();
                        win = true;
                    }
                    mapa[winningPointX][winningPointY].setBackground(Color.BLACK);
                    Random generator = new Random();
                    do{
                        winningPointX = generator.nextInt(m-2)+1;
                        winningPointY = generator.nextInt(n-2)+1;
                    }while(Exist(listOfReflect,winningPointX, winningPointY));
                    mapa[winningPointX][winningPointY].setBackground(Color.RED);

                    //win = false;
                    //mapa[winningPointX][winningPointY].setBackground(Color.RED);
                }
                currentPoint= new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY);


            }while(!ifExist(listOfReflect, currentPoint));
            //it =listOfReflect.listIterator();
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
                printLaserTable();
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if (checkmove(currentX,currentY, -1,0) == true){
                moves++;
                left();
                createLaserTable();
                printLaserTable();

            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP){
            if (checkmove(currentX,currentY, 0,-1) == true){
                moves++;
                up();
                createLaserTable();
                printLaserTable();
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            if (checkmove(currentX,currentY, 0,1) == true){
                moves++;
                down();
                createLaserTable();
                printLaserTable();
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
            
            mapa[winningPointX][winningPointY].setBackground(Color.red);
        
            mapa[currentX][currentY].setBackground(Color.YELLOW);
            
            createLaserTable();

        }
        else if(zrodlo == mLevel2)
        {
            zerosLabel();
            mapa[currentX][currentY].setBackground(Color.black);
            setLaserStartPoint();
            currentX=15;
            currentY = 15;
            mapa[previousLaserX][previousLaserY].setBackground(Color.green);
            mapa[2][1].setBackground(Color.white);
            mapa[5][1].setBackground(Color.white);
            mapa[14][1].setBackground(Color.white);
            mapa[3][2].setBackground(Color.white);
            mapa[4][2].setBackground(Color.white);
            mapa[9][2].setBackground(Color.white);
            mapa[12][2].setBackground(Color.white);
            mapa[3][3].setBackground(Color.white);
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
            
            mapa[winningPointX][winningPointY].setBackground(Color.red);
        
            mapa[currentX][currentY].setBackground(Color.YELLOW);
            
            createLaserTable();

        }
    }
}
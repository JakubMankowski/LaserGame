package laserynowe;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

final class LaserGame extends JFrame implements KeyListener
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
    
    static int n= 20; 
    static int m = 20;
    static int currentX=7;
    static int currentY=6;
    static boolean win = false;
    static boolean up = false;
    static boolean down = false;
    static boolean left = false;
    static boolean right = false;
    static int currentLaserX = 1;
    static int currentLaserY = 1;
    static int previousLaserX = 0;
    static int previousLaserY = 1;
    static int previouspreviousLaserX = 0;
    static int previouspreviousLaserY = 0;
    static int winningPointX = 6;
    static int winningPointY = 8;
    static boolean paint = true;
    static int moves = 0;
    JLabel mapa[][];
    JPanel panel;
    JPanel laser;
    int laserTable[][];
    ArrayList<PointOfReflect> listOfReflect;
    //ListIterator<PointOfReflect> it;
    
    LaserGame()
    {
        laserTable = new int[m][n];
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
                mapa[x][y].setPreferredSize(new Dimension(50,50));      
            }
        }
        panel.setLayout(new GridLayout(n,m));
        for (int y = 0;y<n;y++){
            for (int x = 0;x<m;x++){
                    panel.add(mapa[x][y]);
            }
        }
        add(panel);
        initLabel();
        for(int i=1;i<n-1;i++)
            for(int j=0 ; j<m-1;j++)
                if((i+j) % 4==0)mapa[i][j].setBackground(Color.gray);
        
        mapa[winningPointX][winningPointY].setBackground(Color.red);
        
        mapa[currentX][currentY].setBackground(Color.YELLOW);
        
        setFocusable(true);
        setSize(500,500);
        addKeyListener(this);
        createLaserTable();
        printLaserTable();
    }
    class JLines extends JPanel{
        public JLines()
        {

        }
        public Graphics getGraph()
        {
            return this.getGraphics();
        }
        public void paintbottomright(Graphics g, int i, int j) // topleft
        {
            g.drawLine(mapa[0][0].getWidth()*i+mapa[0][0].getWidth()/2,mapa[0][0].getHeight()*j+mapa[0][0].getHeight(),mapa[0][0].getWidth()*i+mapa[0][0].getWidth(),mapa[0][0].getHeight()*j+ mapa[0][0].getHeight()/2); // dolny prawy

        }
        public void paintbottomleft(Graphics g, int i, int j)
        {
            g.drawLine(mapa[0][0].getWidth()*i+0,mapa[0][0].getHeight()*j+mapa[0][0].getHeight()/2, mapa[0][0].getWidth()*i+mapa[0][0].getWidth()/2,mapa[0][0].getHeight()*j+ mapa[0][0].getHeight()); // dolny lewy
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
        @Override
        protected void paintChildren(Graphics g) {
            super.paintChildren(g);
            g.setColor(Color.red);
            repaint();
            drawLine(g);
            
        }
            /*@Override
            public void paintComponent(Graphics g) {
                //Graphics2D g2 = (Graphics2D)g;
                super.paintComponent(g);
                g.setColor(Color.GREEN);
                paintbottomright(g);
                //repaint();
                for(ListIterator<PointOfReflect> it = listOfReflect.listIterator();it.hasNext();)
                {
                    PointOfReflect current = it.next();
                    PointOfReflect previous = it.previous();
                    current.printPointOfReflect();          
                    if(current.currentLaserX<current.previousLaserX)
                    g.drawLine(mapa[0][0].getWidth()*current.currentLaserX, mapa[0][0].getHeight()*current.currentLaserY,mapa[0][0].getWidth()/2*current.previousLaserX,mapa[0][0].getHeight()/2*current.previousLaserY);
                }
        }*/
            
            
    }
            
    
    /*public final void paintComponent(Graphics g) {
        //super.paintComponent(g);
        g.setColor(Color.RED);
        for(ListIterator<PointOfReflect> it = listOfReflect.listIterator();it.hasNext();)
        {
            PointOfReflect current = it.next();
            current.printPointOfReflect();
            g.drawLine(mapa[0][0].getWidth()*(current.currentLaserX+current.previousLaserX)/2, mapa[0][0].getHeight()*(current.currentLaserY+current.previousLaserY)/2,0,0);

        }
    }*/
        
    
    
    
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
    public void createLaserTable()
    {
        zerosLaserTable();
        PointOfReflect currentPoint = new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY);
        if(!listOfReflect.isEmpty())
        listOfReflect.clear();
        if(mapa[currentLaserX][currentLaserY].getBackground()==Color.GRAY){}
        else{
            listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
            do
            {
                if(currentLaserX > previousLaserX && currentLaserY == previousLaserY && previousLaserY > previouspreviousLaserY)
                {
                    laserTable[currentLaserX][currentLaserY]+=1;  
                    if(mapa[currentLaserX][currentLaserY+1].getBackground() == Color.GRAY)
                    {
                        // jest odbicie
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        System.out.println(currentLaserX+""+currentLaserY+""+previousLaserX+""+previousLaserY+" Laser1");
                        previousLaserX++;
                        previousLaserY++;
                        previouspreviousLaserY+=2;
                        
                    }
                    else
                    {
                        // nie ma odbicia
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        currentLaserY++;
                        previousLaserX++;
                        previouspreviousLaserY++;
                        System.out.println("Claser 1");

                    }
                }
                else if(currentLaserX > previousLaserX && currentLaserY == previousLaserY && previousLaserY < previouspreviousLaserY)
                {
                    laserTable[currentLaserX][currentLaserY]+=8;
                    if(mapa[currentLaserX][currentLaserY-1].getBackground() == Color.GRAY)
                    {
                        // jest odbicie
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        System.out.println(currentLaserX+""+currentLaserY+""+previousLaserX+""+previousLaserY+" Laser8");
                        previousLaserX++;
                        previousLaserY--;
                        previouspreviousLaserY-=2;
                    }
                    else
                    {
                        // nie ma odbicia 
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        currentLaserY--;
                        previousLaserX++;
                        previouspreviousLaserY--;
                        System.out.println("Claser 8");
                    } 
                }
                else if(currentLaserX < previousLaserX && currentLaserY == previousLaserY && previousLaserY > previouspreviousLaserY)
                {
                    laserTable[currentLaserX][currentLaserY]+=2;
                    if(mapa[currentLaserX][currentLaserY+1].getBackground() == Color.GRAY)
                    {
                        // jest odbicie
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        System.out.println(currentLaserX+""+currentLaserY+""+previousLaserX+""+previousLaserY+" Laser2");
                        previousLaserY++;
                        previousLaserX--;
                        previouspreviousLaserY+=2;

                    }
                    else
                    {
                        // nie ma odbicia 
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        currentLaserY++;
                        previousLaserX--;
                        previouspreviousLaserY++;
                        System.out.println("Claser 2");
                    } 
                }
                else if(currentLaserX < previousLaserX && currentLaserY == previousLaserY && previousLaserY < previouspreviousLaserY)
                {
                    laserTable[currentLaserX][currentLaserY]+=4;
                    if(mapa[currentLaserX][currentLaserY-1].getBackground() == Color.GRAY)
                    {
                        // jest odbicie
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        System.out.println(currentLaserX+""+currentLaserY+""+previousLaserX+""+previousLaserY+" Laser4");
                        previousLaserX--;
                        previousLaserY--;
                        previouspreviousLaserY-=2;

                    }
                    else
                    {
                        // nie ma odbicia 
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        currentLaserY--;
                        previousLaserX--;
                        previouspreviousLaserY--;
                        System.out.println("Claser 4");
                    } 
                }
                else if(currentLaserX == previousLaserX && currentLaserY > previousLaserY && previousLaserX > previouspreviousLaserX)
                {
                    laserTable[currentLaserX][currentLaserY]+=4;  
                    if(mapa[currentLaserX+1][currentLaserY].getBackground() == Color.GRAY)
                    {
                        // jest odbicie
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        System.out.println(currentLaserX+""+currentLaserY+""+previousLaserX+""+previousLaserY+"LASER4");
                        previousLaserX++;
                        previousLaserY++;
                        previouspreviousLaserX+=2;

                    }
                    else
                    {
                        // nie ma odbicia
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        currentLaserX++;
                        previousLaserY++;
                        previouspreviousLaserX++;
                        System.out.println("CLASER 4");

                    }
                }
                else if(currentLaserX == previousLaserX && currentLaserY > previousLaserY && previousLaserX < previouspreviousLaserX)
                {
                    laserTable[currentLaserX][currentLaserY]+=8;
                    if(mapa[currentLaserX-1][currentLaserY].getBackground() == Color.GRAY)
                    {
                        // jest odbicie
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        System.out.println(currentLaserX+""+currentLaserY+""+previousLaserX+""+previousLaserY+"LASER8");
                        previousLaserX--;
                        previousLaserY++;
                        previouspreviousLaserX-=2;

                    }
                    else
                    {
                        // nie ma odbicia 
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        currentLaserX--;
                        previousLaserY++;
                        previouspreviousLaserX--;
                        System.out.println("CLASER 8");
                    } 
                }
                else if(currentLaserX == previousLaserX && currentLaserY < previousLaserY && previousLaserX > previouspreviousLaserX)
                {
                    laserTable[currentLaserX][currentLaserY]+=2;
                    if(mapa[currentLaserX+1][currentLaserY].getBackground() == Color.GRAY)
                    {
                        // jest odbicie
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        System.out.println(currentLaserX+""+currentLaserY+""+previousLaserX+""+previousLaserY+" LASER2");
                        previousLaserX++;
                        previousLaserY--;
                        previouspreviousLaserX+=2;

                    }
                    else
                    {
                        //nie ma odbicia
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        currentLaserX++;
                        previousLaserY--;
                        previouspreviousLaserX++;
                        System.out.println("CLASER 2");
                    } 
                }
                else if(currentLaserX == previousLaserX && currentLaserY < previousLaserY && previousLaserX < previouspreviousLaserX)
                {
                    laserTable[currentLaserX][currentLaserY]+=1;
                    if(mapa[currentLaserX-1][currentLaserY].getBackground() == Color.GRAY)
                    {
                        // jest odbicie
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        System.out.println(currentLaserX+""+currentLaserY+""+previousLaserX+""+previousLaserY+"LASER1");
                        previousLaserX--;
                        previousLaserY--;
                        previouspreviousLaserX-=2;
                    }
                    else
                    {
                        // nie ma odbicia 
                        listOfReflect.add(new PointOfReflect(currentLaserX, currentLaserY, previousLaserX, previousLaserY));
                        currentLaserX--;
                        previousLaserY--;
                        previouspreviousLaserX--;
                        
                        System.out.println("CLASER 1");
                        
                    } 
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
		if (((x+dx-1) > -1) && ((x+dx+1) < n) && ((y+dy-1) > -1) && ((y+dy+1) < m)){
			return true;
		} else {
			return false;
		}
	}
        private void up() {
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

    private void down() {
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
    
    private void left() {
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

    private void right() {
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
    public void win()
    {
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
                if (checkmove(currentX,currentY, -1,0) == true)
                {
                    moves++;
                    left();
                    createLaserTable();
                    printLaserTable();
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP){
                if (checkmove(currentX,currentY, 0,-1) == true)
                {
                    moves++;
                    up();
                    createLaserTable();
                    printLaserTable();
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN){
                if (checkmove(currentX,currentY, 0,1) == true)
                {
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
}

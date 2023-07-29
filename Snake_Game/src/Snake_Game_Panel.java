import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Random;
import javax.swing.Timer;

public class Snake_Game_Panel extends JPanel implements ActionListener {



    static final int width=600;
    static final int height=600;

    static final int object_size=25;

    static final int Game_Object= (width*height)/object_size;

    static final int delay =75;

    final int X[]=new int[Game_Object];
    final int Y[]=new int[Game_Object];

    int bodyParts=6;

    int TargetEaten;
    int TargetX;

    int TargetY;


    char direction='R';

    boolean run=false;

    Timer timer;
    Random random;
    private JButton restartButton;

    private boolean gameOver;





    Snake_Game_Panel() {


        random=new Random();
        this.setPreferredSize(new Dimension(width,height));
        this.start();
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        this.addKeyListener( new MyKeyAdapter());





        random = new Random();
        initializeRestartButton();
        restartButton.setVisible(false); // Initially hide the restart button
        this.add(restartButton);



    }


    public void start() {

        newTarget();

        run=true;
        timer=new Timer(80,this);//Speed Control

        timer.start();






    }

    @Override
    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        draw(graphics);

    }


    public void draw(Graphics graphics) {

        if(run){
            //Creating a Grid


          /*  for (int i = 0; i < height / object_size; i++) {
                graphics.drawLine(i * object_size, 0, i * object_size, height);

                graphics.drawLine(0, i * object_size, width, i * object_size);
            }*/


            graphics.setColor(Color.GREEN);

            graphics.fillOval(TargetX, TargetY, object_size, object_size);


            //Snake body
            for (int i = 0; i < bodyParts; i++) {

                if (i == 0) {
                    graphics.setColor(Color.blue);

                    graphics.fillRect(X[i], Y[i], object_size, object_size);
                } else {
                    graphics.setColor(Color.CYAN);

                    graphics.fillRect(X[i], Y[i], object_size, object_size);
                }

            }


            graphics.setColor(Color.red);
            graphics.setFont(new Font("SansSerif",Font.BOLD,30));
            FontMetrics fontMetrics=getFontMetrics(graphics.getFont());

            //Aligning to Center
            graphics.drawString("Game Score :"+ TargetEaten,(width-fontMetrics.stringWidth("Game Score :"+ TargetEaten))/2,graphics.getFont().getSize());
        }


        else
        {
            gameOver(graphics);
        }
    }




    public void newTarget()
    {

        TargetX=random.nextInt((int)(width/object_size))*object_size;
        TargetY=random.nextInt((int)(height/object_size))*object_size;

    }

    public void moveSnake() {


        for(int i=bodyParts;i>0;i--)
        {
            X[i]=X[i-1];
            Y[i]=Y[i-1];

        }


        //For direction
        switch (direction)
        {

            case 'U':
                Y[0]=Y[0]-object_size;
                break;

            case 'D':
                Y[0]=Y[0]+object_size;
                break;


            case 'L':
                X[0]=X[0]-object_size;
                break;
            case 'R':
                X[0]=X[0]+object_size;
                break;

        }


    }

    public void checkTarget() {

        if((X[0]==TargetX && Y[0]==TargetY))
        {
            bodyParts++;
            TargetEaten++;
            newTarget();
        }

    }

    //With walls or with itself
    public void checkCollisions() {

        //Check for if snake head collides with body
        for(int i=bodyParts;i>0;i--)
        {
            if((X[0]==X[i] && Y[0]==Y[i]))
            {
                run=false;//game over
            }
        }


        //Check for left border
      if(X[0]<0)  {

          run=false;

        }

       //Check for right border
        if(X[0]>width)  {

            run=false;

        }

        //Check for top border

        if(Y[0]<0)
        {
            run=false;
        }

        //check for bottom border
        if(Y[0]>height)
        {
            run=false;
        }



        if (!run) {
            gameOver = true;
            timer.stop();
            repaint();
        }




    }

    public void gameOver(Graphics graphics) {


        if (gameOver)

        {      graphics.setColor(Color.red);
        graphics.setFont(new Font("SansSerif", Font.BOLD, 30));
        FontMetrics fontMetrics1 = getFontMetrics(graphics.getFont());

        //Aligning to Center
        graphics.drawString("Game Score :" + TargetEaten, (width - fontMetrics1.stringWidth("Game Score :" + TargetEaten)) / 2, graphics.getFont().getSize());


        graphics.setColor(Color.red);
        graphics.setFont(new Font("SansSerif", Font.BOLD, 50));
        FontMetrics fontMetrics = getFontMetrics(graphics.getFont());

        //Aligning to Center
        graphics.drawString("Game Over!!", (width - fontMetrics.stringWidth("Game Over!!")) / 2, height / 2);






            // Draw the restart button
            restartButton.setBounds((width - 100) / 2, height / 2 + 50, 100, 40); // Adjust the position and size as needed
            restartButton.setVisible(true);

    }

        else {
            restartButton.setVisible(false);
        }
    }



  @Override
    public void actionPerformed(ActionEvent e) {


        if(run)
        {
            moveSnake();
            checkTarget();
           checkCollisions();

        }


        repaint();

    }


    private void initializeRestartButton() {
        restartButton = new JButton("Restart \uD83D\uDD04");
        restartButton.setBackground(Color.GREEN);



        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameOver = false;
                restartGame();
            }
        });
    }


    public  void restartGame()
    {

        restartButton.setVisible(false);

        // Reset the snake's position
        X[0] = width / 2;
        Y[0] = height / 2;
        // Reset the target and number of eaten targets
        newTarget();
        TargetEaten = 0;
        // Reset the number of body parts
        bodyParts = 6;
        // Reset the direction and run state
        direction = 'R';
        run = true;
        // Start the timer again
        timer.start();
        // Request focus to the panel to ensure keyboard events work
        requestFocusInWindow();
        // Repaint the panel to update the changes
        repaint();
    }

    //Receiving Keyboard Events
    public class MyKeyAdapter extends KeyAdapter
    {

        public void keyPressed(KeyEvent e)
        {


            switch (e.getKeyCode())
            {

                case KeyEvent.VK_LEFT :
                    if(direction!='R') //for not going in opposite direction
                    {
                        direction='L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if(direction!='L')
                    {
                        direction='R';
                    }
                    break;


                case KeyEvent.VK_UP :
                    if(direction!='D')
                    {
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U')
                    {
                        direction='D';
                    }
                    break;
            }
        }

    }
}

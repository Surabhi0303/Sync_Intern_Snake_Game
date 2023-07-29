import javax.swing.*;

public class Snake_Game_Frame extends JFrame {


    Snake_Game_Frame()
    {

        this.add(new Snake_Game_Panel());
        this.setTitle("Snake Game using Java");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}

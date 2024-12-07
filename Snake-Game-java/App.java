import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.*;

public class App extends JFrame{
    
    public static void main(String[] args) throws Exception {
        int height=600;
        int width=600;

        JFrame f= new JFrame();
        f.setSize(width,height);
        f.setTitle("Snake Game");
        f.setLayout(new BorderLayout());
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setVisible(true);
        
        SnakeGame gameboard=new SnakeGame(width, height);

        f.add(gameboard);
        f.pack();
        gameboard.requestFocus();
    }
    
}

package uk.ac.dundee;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.GridLayout;

public class Snake extends JFrame {

    public Snake() {

        add(new Board());
        getContentPane().setLayout(new GridLayout(10, 10, 0, 0));
        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame ex = new Snake();
                ex.setVisible(true);
            }
        });
    }
}

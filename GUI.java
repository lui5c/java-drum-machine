import javax.swing.*;
import java.awt.*;

/** with help from:
 *  - https://www.javatpoint.com/
 * 
 */

public class GUI extends JFrame{
    JFrame f;
    Font helvetica = new Font("Helvetica", Font.BOLD, 24);
    Font consolas = new Font("Consolas", Font.BOLD, 36);

    public JPanel getGUIRow(String labelString){
        JPanel row = new JPanel();
        JLabel label = new JLabel(labelString);
        label.setFont(helvetica);
        label.setMaximumSize(new Dimension(200, 200));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField text = new JTextField();
        text.setFont(consolas); //monospace for timing
        text.setSize(200, 30);
        row.add(label);
        row.add(text);
        row.setLayout(new GridLayout());
        row.setBackground(Color.WHITE);
        return row;
    }

    public GUI(){
        JPanel BPMRow = getGUIRow("bpm:");
        JPanel hatRow = getGUIRow("hat pattern:");
        JPanel snareRow = getGUIRow("snare pattern:");
        JPanel kickRow = getGUIRow("kick pattern:");

        add(BPMRow);
        add(hatRow);
        add(snareRow);
        add(kickRow);

        getContentPane().setBackground(Color.WHITE);
        setSize(500, 400);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setVisible(true);
    }
    public static void main(String[] args){
        new GUI();
    }
}

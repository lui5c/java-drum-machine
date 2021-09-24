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

    public JPanel getTopJPanel(){
        JPanel row = new JPanel();

        JButton reset = new JButton("STOP");
        reset.setHorizontalAlignment(SwingConstants.CENTER);
        reset.setFont(helvetica);
        reset.setBackground(Color.orange);

        JButton go = new JButton("GO");
        go.setHorizontalAlignment(SwingConstants.CENTER);
        go.setFont(helvetica);
        go.setBackground(Color.green);

        row.add(go);
        row.add(reset);
        row.setLayout(new GridLayout());
        row.setBackground(Color.WHITE);
        return row;
    }

    public JPanel getGUIRow(String labelString, String placeholder){
        JPanel row = new JPanel();
        JLabel label = new JLabel(labelString);
        label.setFont(helvetica);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField text;
        if (placeholder != null){
            text = new JTextField(placeholder);
        } else {
            text = new JTextField();
        }

        text.setFont(consolas); //monospace for timing
        text.setSize(200, 30);
        row.add(label);
        row.add(text);
        row.setLayout(new GridLayout());
        row.setBackground(Color.WHITE);
        return row;
    }

    public GUI(){
        JPanel topRow = getTopJPanel();
        JPanel BPMRow = getGUIRow("bpm:", "120");
        JPanel hatRow = getGUIRow("hat pattern:", "oxo");
        JPanel snareRow = getGUIRow("snare pattern:", "oox");
        JPanel kickRow = getGUIRow("kick pattern:", "xoo");

        add(topRow);
        add(BPMRow);
        add(hatRow);
        add(snareRow);
        add(kickRow);

        getContentPane().setBackground(Color.WHITE);
        setSize(500, 400);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public static void main(String[] args){
        new GUI();
    }
}

import javax.swing.*;
import java.awt.*;

import java.util.HashMap;
/** with help from:
 *  - https://www.javatpoint.com/
 * 
 */

public class GUI extends JFrame{
    JFrame f;
    Font helvetica = new Font("Helvetica", Font.BOLD, 24);
    Font consolas = new Font("Consolas", Font.BOLD, 36);
    String hatpattern = "oox";
    String snarepattern = "oxo";
    String kickpattern = "xoo";
    String bpm = "120";
    JButton reset;
    JButton go;

    public int getNSinterval(){
        // takes in a BPM value and returns the nanosecond amount
        // between beats. 
        double BPM = (double) Integer.valueOf(bpm);
        double secPerBeat = Math.pow(BPM / (double) 60, (double) -1);
        int ns = (int) (secPerBeat * Math.pow((double) 10, (double) 9));
        return ns;
    }

    public HashMap<String, String> getConfig(){
        /**
        * "bpm"    : "120"
        * "hat"    : "oox"
        * "snare"  : "oxo"
        * "kick"   : "xoo"
        */
        HashMap<String, String> config = new HashMap<String, String>();
        bpm = ((JTextField) ((JPanel) getContentPane()
                    .getComponents()[1])
                    .getComponents()[1])
                    .getText();
        hatpattern = ((JTextField) ((JPanel) getContentPane()
                    .getComponents()[2])
                    .getComponents()[1])
                    .getText();
        snarepattern = ((JTextField) ((JPanel) getContentPane()
                    .getComponents()[3])
                    .getComponents()[1])
                    .getText();
        kickpattern = ((JTextField) ((JPanel) getContentPane()
                    .getComponents()[4])
                    .getComponents()[1])
                    .getText();

        config.put("interval", String.valueOf(getNSinterval()));
        config.put("hat", hatpattern);
        config.put("snare", snarepattern);
        config.put("kick", kickpattern);
        
        return config;
    }

    public JPanel getTopJPanel(){
        JPanel row = new JPanel();

        reset = new JButton("STOP");
        reset.setHorizontalAlignment(SwingConstants.CENTER);
        reset.setFont(helvetica);
        reset.setBackground(Color.orange);

        go = new JButton("GO");
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
        JPanel BPMRow = getGUIRow("bpm:", bpm);
        JPanel hatRow = getGUIRow("hat pattern:", hatpattern);
        JPanel snareRow = getGUIRow("snare pattern:", snarepattern);
        JPanel kickRow = getGUIRow("kick pattern:", kickpattern);

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
}

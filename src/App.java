import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {


        JFrame flagFrame = new JFrame("US Flag");
        flagFrame.setSize(800, 600);
        flagFrame.add (new FlagPanel());
        flagFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        flagFrame.setVisible(true);



    }
}

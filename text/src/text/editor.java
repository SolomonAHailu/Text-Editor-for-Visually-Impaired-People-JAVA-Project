import javax.swing.*;
import java.awt.*;
import java.awt.Event.*;
/*public class editor extends JFrame{
   /* JTextArea textarea;
    JPanel panel1;
    JScrollPane panel2;
    JPanel panel3;
    JPanel panel4;
    JPanel panel5;
    
  public editor() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(1370,750);
    setVisible(true);
    setLocationRelativeTo(null); 
    setLayout(new BorderLayout());
  buildPanel1();
  buildPanel2();
  buildpanel3();
  buildpanel4();
  buildpanel5();
  add(panel1,BorderLayout.NORTH);
  add(panel2,BorderLayout.CENTER);
 add(panel3, BorderLayout.EAST);
 add(panel4, BorderLayout.WEST);
  add(panel5, BorderLayout.SOUTH);
  
  }
  private void buildtextarea(){
       textarea=new JTextArea();
    textarea.setPreferredSize(new Dimension(700,450));
    textarea.setLineWrap(true);
    textarea.setWrapStyleWord(true);
    textarea.setFont(new Font("Arial",Font.PLAIN,18));
  }
  private void buildPanel2(){
      panel2=new JScrollPane();
      buildtextarea();
      panel2.setContent(textarea);
      panel2.setVerticalScrollBarPolicy(500);
  }
  private void buildPanel1(){
      panel1=new JPanel();
     panel1.setPreferredSize(new Dimension(1366,100));
      panel1.setBackground(Color.RED);
  }
   private void buildpanel3(){
      panel3=new JPanel();
      panel3.setPreferredSize(new Dimension(300,500));
       panel3.setBackground(Color.BLUE);
  }
    private void buildpanel4(){
      panel4=new JPanel();
     panel4.setPreferredSize(new Dimension(300,500));
     panel4.setBackground(Color.green);
  }
     private void buildpanel5(){
      panel5=new JPanel();
     panel5.setPreferredSize(new Dimension(1366,100));
      panel5.setBackground(Color.yellow);
  }
 /* public static void main(String[] args){
    new editor();
  }
    JScrollPane scroll;
    JTextArea text;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    Toolkit tool= Toolkit.getDefaultToolkit();
    int width=(int)tool.getScreenSize().getWidth();
    int height=(int)tool.getScreenSize().getHeight();
    Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
    int taskbar=scnMax.bottom;
    public editor(){
        setSize(width,height-taskbar);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
       
       // buildScrollpane();
       // add(scroll,BorderLayout.CENTER);
        buildPanel1();
        buildPanel2();
        buildpanel3();
        add(panel1,BorderLayout.NORTH);
        add(panel2,BorderLayout.SOUTH);
        add(panel3,BorderLayout.CENTER);
         setVisible(true);
        
    }
   /* private void buildtext(){
        text=new JTextArea();
        text.setPreferredSize(new Dimension(800,600));
    }
    private void buildScrollpane(){
      text=new JTextArea();
        text.setSize(new Dimension(500,1000));
        scroll=new JScrollPane(text);
        scroll.setSize(new Dimension (width,600));
        
        
        //scroll.setHorizontalScrollBarPolicy(800);
     // scroll.setVerticalScrollBarPolicy(600);
        
        
    }
    private void buildPanel1(){
        panel1=new JPanel();
        panel1.setPreferredSize(new Dimension(width,150));
        panel1.setBackground(Color.GRAY);
    }
    private void buildPanel2(){
        panel2=new JPanel();
        panel2.setPreferredSize(new Dimension(width,20));
        panel2.setBackground(Color.yellow);
    }
   private void buildpanel3(){
       panel3=new JPanel();
       buildScrollpane();
       panel3.setLayout(new BorderLayout());
       panel3.add(scroll,BorderLayout.CENTER);
   }
}*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class editor extends JFrame implements ActionListener{
    JTextArea textArea;
    JScrollPane scrollPane;
    JSpinner fontSizeSpinner;
    JLabel fontLabel;
    JButton fontColorButton;
    JComboBox fontBox;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem,saveItem,exitItem;

    public void TextEditor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("TEXT EDITOR");
        this.setSize(500,500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(450,450));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial",Font.PLAIN,20));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450,450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fontLabel = new JLabel("FONT");

        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50,25));
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)fontSizeSpinner.getValue()));
            }
        });

        fontColorButton = new JButton("COLOR");
        fontColorButton.addActionListener(this);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        fontBox = new JComboBox(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");

//---------------------menuBar-----------------
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
// ---------------------menuBar-----------------

        this.setJMenuBar(menuBar);
        this.add(fontLabel);
        this.add(fontSizeSpinner);
        this.add(fontColorButton);
        this.add(fontBox);
        this.add(scrollPane);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==fontColorButton){
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null,"Choose Color",Color.black);

            textArea.setForeground(color);

        }
        if (e.getSource()==fontBox){
            textArea.setFont(new Font((String) fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
        }
        if (e.getSource()==openItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files","txt");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showOpenDialog(null);
            if (response==JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;
                try {
                    fileIn = new Scanner(file);
                    if(file.isFile()){
                        while(fileIn.hasNextLine()){
                            String line = fileIn.nextLine()+"\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                finally
                {
                    fileIn.close();
                }
            }
        }
        if (e.getSource()==saveItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response = fileChooser.showSaveDialog(null);
            if (response==JFileChooser.APPROVE_OPTION){
                File file;
                PrintWriter fileOut = null;
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileOut = new PrintWriter(file);
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }finally{
                    fileOut.close();
                }
            }
        }
        if (e.getSource()==exitItem){
            System.exit(0);
        }

    }
}


package text;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class build extends JFrame implements ActionListener {

    private JFrame frame;
    private JPanel panel2, panel3, panel4;
    private JScrollPane scroll;
    private JTextArea text = new JTextArea();
    private JSpinner fontSizeSpinner;
    private JComboBox fontBox;
    private JMenuItem openItem, saveItem, exitItem;
    private JMenuItem boldItem, italicItem;
    private JRadioButtonMenuItem large, small, defaultItem;
    private JButton read, help;
    private JMenuItem cut, copy, paste, delete;

    Toolkit tool = Toolkit.getDefaultToolkit();
    int width = (int) tool.getScreenSize().getWidth();
    int height = (int) tool.getScreenSize().getHeight();
    Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
    int taskbar = scnMax.bottom;

    public build() {
        frame = new JFrame();
        frame.setTitle("Text Editor for Visually Impaired");
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.setSize(width, height - taskbar);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        buildpanel4();
        buildPanel2();
        buildpanel3();
        frame.add(panel3, BorderLayout.CENTER);
        frame.add(panel4, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.SOUTH);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //addMouseListener(new listener("close"));
                buildvoice("are you sure you don't want to save please choose your option on the center of the window");
                JDialog dialog = new JDialog(frame, "option");
                dialog.setBounds(550, 300, 350, 150);
                dialog.addMouseListener(new listener("dialog box"));
                JPanel panel = new JPanel();
                JPanel panel5 = new JPanel();
                JPanel panel6 = new JPanel();
                panel.setLayout(new GridLayout(3, 1));
                JButton button1 = new JButton("Save");
                JButton button2 = new JButton("Don't save");
                JButton button3 = new JButton("Cancel");
                JLabel label = new JLabel("    are you sure you don't want to save any changes    ");
                //_____________________________________________________________________________
                panel.add(label);
                panel.add(panel5);
                panel.add(panel6);
                panel6.setLayout(new FlowLayout());
                panel6.add(button1);
                panel6.add(button2);
                panel6.add(button3);
                dialog.add(panel);
                dialog.setVisible(true);
                //_____________________________________________________________________
                button1.addMouseListener(new listener(button1.getText()));
                button2.addMouseListener(new listener(button2.getText()));
                button3.addMouseListener(new listener(button3.getText()));
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser("C:\\Users\\kibrom\\Documents");
                        fileChooser.setCurrentDirectory(new File("C:\\Users\\kibrom\\Documents"));
                        fileChooser.addMouseListener(new listener("save dialog"));

                        int response = fileChooser.showSaveDialog(null);
                        if (response == JFileChooser.APPROVE_OPTION) {
                            File file;
                            PrintWriter fileOut = null;
                            file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                            try {
                                fileOut = new PrintWriter(file);
                                fileOut.println(text.getText());

                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            } finally {
                                assert fileOut != null;
                                fileOut.close();
                                System.exit(0);
                            }
                        } else if (response == JFileChooser.CANCEL_OPTION) {
                            fileChooser.setVisible(false);
                            dialog.setVisible(false);
                        }
                    }
                });
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
                button3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.setVisible(false);
                    }
                });
            }
        });
    }

    private void buildPanel2() {
        panel2 = new JPanel();
        panel2.setBackground(Color.GRAY);
        panel2.setPreferredSize(new Dimension(width, 20));
    }

    private void buildScroll() {
        scroll = new JScrollPane();
        scroll.setPreferredSize(new Dimension(300, 700));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setTabSize(5);
        scroll.setViewportView(text);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        text.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                buildvoice("page area");
            }
        });
        text.addKeyListener(new KeyAdapter() {
            Voice voice;
            VoiceManager free;
            private void charvoice(char ex) {
                System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
                free = VoiceManager.getInstance();
                voice = free.getVoice("kevin16");
                if (voice != null) {
//the Voice class allocate() method allocates this voice
                    voice.allocate();
                }
                voice.setRate(150);
//sets the baseline pitch (150) in hertz
                voice.setPitch(130);
//sets the volume (10) of the voice
                voice.setVolume(10);
                voice.speak(String.valueOf(ex));
            }

            private void makeVoice(String word) {
                System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
                free = VoiceManager.getInstance();
                voice = free.getVoice("kevin16");
                if (voice != null) {
//the Voice class allocate() method allocates this voice
                    voice.allocate();
                }
                assert voice != null;
                voice.setRate(150);
//sets the baseline pitch (150) in hertz
                voice.setPitch(130);
//sets the volume (10) of the voice
                voice.setVolume(10);
                voice.speak(word);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case 92:
                        makeVoice("back slash");
                        break;
                    case 93:
                        makeVoice("close bracket");
                        break;
                    case 9:
                        makeVoice("tab");
                        break;
                    case 19:
                        makeVoice("pause");
                        break;
                    case 27:
                        makeVoice("escape");
                        break;
                    case 3:
                        makeVoice("cancel");
                        break;
                    case 12:
                        makeVoice("clear");
                        break;
                    case 32:
                        makeVoice("space");
                        break;
                    case 33:
                        makeVoice("page up");
                        break;
                    case 34:
                        makeVoice("page down");
                        break;
                    case 35:
                        makeVoice("end");
                        break;
                    case 36:
                        makeVoice("home");
                        break;
                    case 37:
                        makeVoice("left");
                        break;
                    case 38:
                        makeVoice("up");
                        break;
                    case 39:
                        makeVoice("right");
                        break;
                    case 40:
                        makeVoice("down");
                        break;
                    case 45:
                        makeVoice("minus");
                        break;
                    case 59:
                        makeVoice("semi colon");
                        break;
                    case 91:
                        makeVoice("open bracket");
                        break;
                    case 112:
                        makeVoice("F 1");
                        break;
                    case 113:
                        makeVoice("F 2");
                        break;
                    case 114:
                        makeVoice("F 3");
                        break;
                    case 115:
                        makeVoice("F 4");
                        break;
                    case 116:
                        makeVoice("F 5");
                        break;
                    case 117:
                        makeVoice("F 6");
                        break;
                    case 118:
                        makeVoice("F 7");
                        break;
                    case 119:
                        makeVoice("F 8");
                        break;
                    case 120:
                        makeVoice("F 9");
                        break;
                    case 121:
                        makeVoice("F 10");
                        break;
                    case 122:
                        makeVoice("F 11");
                        break;
                    case 123:
                        makeVoice("F 12");
                        break;
                    case 127:
                        makeVoice("delete");
                    case 144:
                        makeVoice("num lock");
                        break;
                    case 145:
                        makeVoice("scroll lock");
                        break;
                    case 150:
                        makeVoice("ampersand");
                        break;
                    case 151:
                        makeVoice("asterisk");
                        break;
                    case 152:
                        makeVoice("double quote");
                        break;
                    case 153:
                        makeVoice("less");
                        break;
                    case 154:
                        makeVoice("print screen");
                        break;
                    case 155:
                        makeVoice("insert");
                        break;
                    case 156:
                        makeVoice("help");
                        break;
                    case 157:
                        makeVoice("meta");
                        break;
                    case 160:
                        makeVoice("greater");
                        break;
                    case 161:
                        makeVoice("left brace");
                        break;
                    case 162:
                        makeVoice("right brace");
                        break;
                    case 192:
                        makeVoice("back quote");
                        break;
                    case 513:
                        makeVoice("colon");
                        break;
                    case 516:
                        makeVoice("euro");
                        break;
                    case 517:
                        makeVoice("exclamation mark");
                        break;
                    case 518:
                        makeVoice("inverted exclamation mark");
                        break;
                    case 519:
                        makeVoice("left parenthesis");
                        break;
                    case 521:
                        makeVoice("plus");
                        break;
                    case 522:
                        makeVoice("right parenthesis");
                        break;
                    case 523:
                        makeVoice("underscore");
                        break;
                    case 524:
                        makeVoice("windows");
                        break;
                    case 65485:
                        makeVoice("copy");
                        break;
                    case 65487:
                        makeVoice("paste");
                        break;
                    case 65488:
                        makeVoice("find");
                        break;
                    case 65489:
                        makeVoice("cut");
                        break;
                    case 65483:
                        makeVoice("undo");
                        break;
                    case 46:
                        makeVoice("full stop");
                        break;
                    case 44:
                        makeVoice("comma");
                        break;
                    case 109:
                        makeVoice("minus");
                        break;
                    case 111:
                        makeVoice("divide");
                        break;
                    case 129:
                        makeVoice("single quote");
                        break;
                    case 17:
                        makeVoice("control");
                        break;
                    case 16:
                        makeVoice("shift");
                        break;
                    case 20:
                        makeVoice("capslock");
                        break;
                    case 10:
                        makeVoice("enter");
                        break;
                    case 18:
                        makeVoice("alter");
                        break;
                    case 8:
                        makeVoice("back space");
                        break;
                    default:
                        charvoice(e.getKeyChar());
                }
            }
        });
    }

    private void buildpanel3() {
        panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        panel4.setPreferredSize(new Dimension(200, 400));
        panel5.setPreferredSize(new Dimension(200, 400));
        panel3.setBackground(Color.LIGHT_GRAY);
        panel3.setLayout(new BorderLayout());
        panel3.setSize(WIDTH, height - taskbar - 165);
        buildScroll();
        panel3.add(scroll, BorderLayout.CENTER);
        panel3.add(panel4, BorderLayout.EAST);
        panel3.add(panel5, BorderLayout.WEST);
    }

    public void buildpanel4() {
        Color color = new Color(0, 85, 153);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel1.setLayout(new GridLayout(1, 7));
        panel4 = new JPanel();
        panel4.setPreferredSize(new Dimension(width, 30));
        panel4.setLayout(new GridLayout(1, 3));
        panel2.setBackground(color);
        panel3.setBackground(color);
        panel1.setBackground(color);
        panel4.add(panel1);
        panel4.add(panel2);
        panel4.add(panel3);
        //____________________________________________________________________
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(Color.WHITE);
        menuBar.setBackground(color);
        menuBar.setPreferredSize(new Dimension(50, 30));

        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        fileMenu.addMouseListener(new listener(fileMenu.getText()));
        openItem.addMouseListener(new listener(openItem.getText()));
        saveItem.addMouseListener(new listener(saveItem.getText()));
        exitItem.addMouseListener(new listener(exitItem.getText()));

        menuBar.add(fileMenu);
        //__________________________________________________________________________
        JMenuBar homebar = new JMenuBar();
        homebar.setPreferredSize(new Dimension(100, 30));
        JMenu home = new JMenu("Home");
        boldItem = new JMenuItem("Bold");
        italicItem = new JMenuItem("italic");
        ButtonGroup group = new ButtonGroup();
        large = new JRadioButtonMenuItem("large");
        small = new JRadioButtonMenuItem("small");
        defaultItem = new JRadioButtonMenuItem("default", true);
        group.add(large);
        group.add(defaultItem);
        group.add(small);
        home.setForeground(Color.WHITE);
        homebar.setBackground(color);
        JMenu fontSize = new JMenu("fontSize");
        home.add(boldItem);
        home.add(italicItem);
        home.addSeparator();
        home.add(fontSize);
        fontSize.add(large);
        fontSize.add(defaultItem);
        fontSize.add(small);
        homebar.add(home);

        defaultItem.addActionListener(this);
        large.addActionListener(this);
        small.addActionListener(this);
        italicItem.addActionListener(this);
        boldItem.addActionListener(this);

        home.addMouseListener(new listener(home.getText()));
        fontSize.addMouseListener(new listener(fontSize.getText()));
        large.addMouseListener(new listener(large.getText()));
        small.addMouseListener(new listener(small.getText()));
        defaultItem.addMouseListener(new listener(defaultItem.getText()));
        boldItem.addMouseListener(new listener(boldItem.getText()));
        italicItem.addMouseListener(new listener(italicItem.getText()));
        //___________________________________________________________________________
        read = new JButton("read");
        read.setBackground(color);
        read.setForeground(Color.WHITE);
        read.addMouseListener(new listener("read"));
        read.addActionListener(this);
        //__________________________________________________________________
        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addMouseListener(new listener("font size"));
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                text.setFont(new Font(text.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));
            }
        });
        //_____________________________________________________________________
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox = new JComboBox(fonts);
        fontBox.setSelectedItem("Arial");
        fontBox.setEditable(true);
        fontBox.insertItemAt("Arial", 0);
        fontBox.insertItemAt("Calibri", 1);
        fontBox.insertItemAt("Cambria", 2);
        fontBox.insertItemAt("Times New Romans", 3);
        fontBox.insertItemAt("Tahoma", 4);
        fontBox.getEditor().getEditorComponent().addMouseListener(new listener("fonts"));
        fontBox.addActionListener(this);
        //_________________________________________-----------------_______________________________________
        JMenuBar editbar = new JMenuBar();
        editbar.setPreferredSize(new Dimension(100, 30));
        JMenu edit = new JMenu("Edit");
        edit.setForeground(Color.WHITE);
        editbar.setBackground(color);
        copy = new JMenuItem("Copy");
        cut = new JMenuItem("Cut");
        delete = new JMenuItem("Delete");
        paste = new JMenuItem("Paste");
        edit.add(copy);
        edit.add(cut);
        edit.add(paste);
        edit.add(delete);
        editbar.add(edit);
        edit.addMouseListener(new listener(edit.getText()));
        copy.addMouseListener(new listener(copy.getText()));
        cut.addMouseListener(new listener(cut.getText()));
        paste.addMouseListener(new listener(paste.getText()));
        delete.addMouseListener(new listener(delete.getText()));
        copy.addActionListener(this);
        cut.addActionListener(this);
        paste.addActionListener(this);
        delete.addActionListener(this);
        //___________________________________________________________________________________
        help = new JButton("Help");
        help.setBackground(color);
        help.setForeground(Color.WHITE);
        help.addMouseListener(new listener("help"));
        help.addActionListener(this);
        //______________________________________________________________
        panel1.add(menuBar);
        panel1.add(homebar);
        panel1.add(editbar);
        panel1.add(read);
        panel1.add(fontSizeSpinner);
        panel1.add(fontBox);
        panel1.add(help);
    }

    public void buildvoice(String words) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager free = VoiceManager.getInstance();
        Voice voice = free.getVoice("kevin16");
        if (voice != null) {
//the Voice class allocate() method allocates this voice
            voice.allocate();
        }
        assert voice != null;
        voice.setRate(150);
//sets the baseline pitch (150) in hertz
        voice.setPitch(130);
//sets the volume (10) of the voice
        voice.setVolume(10);
        voice.speak(words);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == openItem) {
            JFileChooser fileChooser = new JFileChooser("C:\\Users\\kibrom\\Documents");
            // fileChooser.setCurrentDirectory(new File("C:\\Users\\kibrom\\Documents"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            fileChooser.setFileFilter(filter);
            fileChooser.addMouseListener(new listener("open dialog"));
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;
                try {
                    fileIn = new Scanner(file);
                    if (file.isFile()) {
                        while (fileIn.hasNextLine()) {
                            String line = fileIn.nextLine() + "\n";
                            text.append(line);
                        }
                        String str = file.getName();
                        frame.setTitle(str);
                    }
                } catch (FileNotFoundException ex) {
                    buildvoice("you did't select any file ");
                } finally {
                    assert fileIn != null;
                    fileIn.close();
                }
            }
        }
        //____________________________________________________________________________________
        if (e.getSource() == help) {
            help.transferFocusBackward();
            String exx = text.getText();
            text.setText("");
            File file = new File("C:\\Users\\kibrom\\Documents\\NetBeansProjects\\text\\help.txt");
            Scanner fileIn = null;
            try {
                fileIn = new Scanner(file);
                if (file.isFile()) {
                    while (fileIn.hasNextLine()) {
                        String line = fileIn.nextLine() + "\n";
                        text.append(line);
                    }
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                assert fileIn != null;
                fileIn.close();
            }
            String exe = text.getText();
            buildvoice(exe);
            text.setText(exx);
        }
        //_____________________________________________________________________________________
        if (e.getSource() == saveItem) {
            JFileChooser fileChooser = new JFileChooser("C:\\Users\\kibrom\\Documents");
            fileChooser.setCurrentDirectory(new File("C:\\Users\\kibrom\\Documents"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
            fileChooser.setFileFilter(filter);
            fileChooser.addMouseListener(new listener("save dialog"));
            int response = fileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file;
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                PrintWriter fileOut = null;
                try {
                    fileOut = new PrintWriter(file);
                    fileOut.println(text.getText());
                    String str = file.getName();
                    frame.setTitle(str);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } finally {
                    fileOut.close();
                }
            }
        }
        //_________________________________________________________________________________________
        if (e.getSource() == exitItem) {
            System.exit(0);
        }

        if (e.getSource() == read) {
            read.transferFocusBackward();
            if (e.getSource() == read) {
                final String talkTalk = "kevin";
                Voice italk;
                VoiceManager iSpeak = VoiceManager.getInstance();
                italk = iSpeak.getVoice(talkTalk);
                italk.allocate();
                if (text.getSelectedText() != null) {
                    italk.speak(text.getSelectedText());
                } else if (text.getText() != null) {
                    italk.speak(text.getText());
                } else if (text.getText() == "") {
                    String myString = new String("Sorry, there is no any text");
                    italk.speak(myString);
                }
            }
        }
        //______________________________________________________________________________________
        if (e.getSource() == fontBox) {
            String str = (String) fontBox.getSelectedItem();
            text.setFont(new Font(str, Font.PLAIN, (int) fontSizeSpinner.getValue()));
        }
        if (e.getSource() == boldItem) {
            text.setFont(new Font(text.getFont().getFamily(), Font.BOLD, (int) fontSizeSpinner.getValue()));
        }
        if (e.getSource() == italicItem) {
            text.setFont(new Font(text.getFont().getFamily(), Font.ITALIC, (int) fontSizeSpinner.getValue()));
        }
        if (e.getSource() == large) {
            text.setFont(new Font(text.getFont().getFamily(), Font.PLAIN, 40));
        }
        if (e.getSource() == small) {
            text.setFont(new Font(text.getFont().getFamily(), Font.PLAIN, 10));
        }
        if (e.getSource() == defaultItem) {
            text.setFont(new Font(text.getFont().getFamily(), Font.PLAIN, 20));
        }
        if (e.getSource() == copy) {
            text.copy();
        }
        if (e.getSource() == cut) {
            text.cut();
        }
        if (e.getSource() == paste) {
            text.paste();
        }
        if (e.getSource() == delete) {
            text.setText("");
        }
    }
}

class listener extends MouseAdapter {
    private String words;

    public listener(String words) {
        this.words = words;
    }

    private void buildvoice(String words) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager free = VoiceManager.getInstance();
        //= VoiceManager.getInstance().getVoice("kevin");
        Voice voice = free.getVoice("kevin16");
        if (voice != null) {
//the Voice class allocate() method allocates this voice
            voice.allocate();
        }
        assert voice != null;
        voice.setRate(150);
//sets the baseline pitch (150) in hertz
        voice.setPitch(130);
//sets the volume (10) of the voice
        voice.setVolume(10);
        voice.speak(words);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        buildvoice(words);
    }
}
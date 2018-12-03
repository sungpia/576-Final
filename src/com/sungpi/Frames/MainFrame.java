package com.sungpi.Frames;

import com.sungpi.Caches.AuthoringToolCache;
import com.sungpi.Caches.CaptureToolCache;
import com.sungpi.Caches.PrimaryVideoPositionCache;
import com.sungpi.Frames.Listeners.MainFrameListener;
import com.sungpi.ImportFileUtils.VideoFrameView;
import com.sungpi.ImportFileUtils.VideoPreview;
import com.sungpi.InteractiveVideoPlayerUtils.InteractiveVideoPlayerOption;
import com.sungpi.InteractiveVideoReader;
import com.sungpi.Windows.CaptureWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class MainFrame extends JFrame implements ActionListener {
    static private String newline ="\n";
    private JTextArea log;
    private JFileChooser fc;

    JDesktopPane jDesktopPane;
    AuthoringToolFrame authoringToolFrame = new AuthoringToolFrame();
    InteractiveVideoPlayerFrame interactiveVideoPlayerFrame = new InteractiveVideoPlayerFrame();
    LoggerFrame loggerFrame = new LoggerFrame();
    MetaDataDisplayFrame metaDataDisplayFrame = new MetaDataDisplayFrame();

    public MainFrame() {
        super("CSCI-576 Final Project");

//        int padding = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        //Set up the GUI.
        jDesktopPane = new JDesktopPane(); // A specialized Layered Pane.
        createFrame(); // Create First Windows.
        setContentPane(jDesktopPane);
        setJMenuBar(createMenuBar());

        // Make dragging a little faster but perhaps uglier -> JDesktopPane.OUTLINE_DRAG_MODE
        jDesktopPane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);

        addComponentListener(new MainFrameListener());
        PrimaryVideoPositionCache.getInstance().setMainFrame(this);
        PrimaryVideoPositionCache.getInstance().setMainFrameX((int) this.getBounds().getLocation().getX());
        PrimaryVideoPositionCache.getInstance().setMainFrameY((int) this.getBounds().getLocation().getY());
        System.out.println(PrimaryVideoPositionCache.getInstance().getMainFrameX());
        System.out.println(PrimaryVideoPositionCache.getInstance().getMainFrameY());
    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Authoring Tool");
        menu.setMnemonic(1);
        menuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("Import Primary Video");
        menuItem.setActionCommand("Import Primary Video");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Import Secondary Video");
        menuItem.setActionCommand("Import Secondary Video");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Create New Hyperlink");
        menuItem.setActionCommand("Create New Hyperlink");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Connect Video");
        menuItem.setActionCommand("Connect Video");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // Set up the menu bar.
        menu = new JMenu("Video Player");
        menu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(menu);

        // TODO(sungwonc): Set up the menu items; examples below commented.
        menuItem = new JMenuItem("Import Video");
        menuItem.setActionCommand("Import Video");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Play Video");
        menuItem.setActionCommand("Play Video");
        menuItem.addActionListener(this);
        menu.add(menuItem);
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
//        menuItem.addActionListener(this);
//        menu.add(menuItem);
//
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
//        menuItem.addActionListener(this);
//        menu.add(menuItem);
//
        return menuBar;
    }

    //React to menu selections.
    public void actionPerformed(ActionEvent e) {
        if ("Import Video".equals(e.getActionCommand())) { //new
            importVideofor("Interactive Video Player");
        } else if ("Play Video".equals(e.getActionCommand())) {
            interactiveVideoPlayerFrame.play();
        } else if ("Import Primary Video".equals(e.getActionCommand())) {
            try {
                importVideofor("Authoring Tool: Primary");
            } catch (Exception exception) {
                final JDialog dialog = new JDialog(this,
                        "Error");

                //Add contents to it. It must have a close button,
                //since some L&Fs (notably Java/Metal) don't provide one
                //in the window decorations for dialogs.
                JLabel label = new JLabel("<html><p align=center>"
                        + "Try Importing Again.<br>");

                label.setHorizontalAlignment(JLabel.CENTER);
                Font font = label.getFont();
                label.setFont(label.getFont().deriveFont(font.PLAIN,
                        14.0f));

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dialog.setVisible(false);
                        dialog.dispose();
                    }
                });
                JPanel closePanel = new JPanel();
                closePanel.setLayout(new BoxLayout(closePanel,
                        BoxLayout.LINE_AXIS));
                closePanel.add(Box.createHorizontalGlue());
                closePanel.add(closeButton);
                closePanel.setBorder(BorderFactory.
                        createEmptyBorder(0,0,5,5));

                JPanel contentPane = new JPanel(new BorderLayout());
                contentPane.add(label, BorderLayout.CENTER);
                contentPane.add(closePanel, BorderLayout.PAGE_END);
                contentPane.setOpaque(true);
                dialog.setContentPane(contentPane);

                //Show it.
                dialog.setSize(new Dimension(300, 150));
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
                AuthoringToolCache.getInstance().setPrimaryVideoFolder(null);
            }
        } else if ("Import Secondary Video".equals(e.getActionCommand())) {
            try {
                importVideofor("Authoring Tool: Secondary");
            } catch (Exception exception) {
                final JDialog dialog = new JDialog(this,
                        "Error");

                //Add contents to it. It must have a close button,
                //since some L&Fs (notably Java/Metal) don't provide one
                //in the window decorations for dialogs.
                JLabel label = new JLabel("<html><p align=center>"
                        + "Try Importing Again.<br>");

                label.setHorizontalAlignment(JLabel.CENTER);
                Font font = label.getFont();
                label.setFont(label.getFont().deriveFont(font.PLAIN,
                        14.0f));

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dialog.setVisible(false);
                        dialog.dispose();
                    }
                });
                JPanel closePanel = new JPanel();
                closePanel.setLayout(new BoxLayout(closePanel,
                        BoxLayout.LINE_AXIS));
                closePanel.add(Box.createHorizontalGlue());
                closePanel.add(closeButton);
                closePanel.setBorder(BorderFactory.
                        createEmptyBorder(0,0,5,5));

                JPanel contentPane = new JPanel(new BorderLayout());
                contentPane.add(label, BorderLayout.CENTER);
                contentPane.add(closePanel, BorderLayout.PAGE_END);
                contentPane.setOpaque(true);
                dialog.setContentPane(contentPane);

                //Show it.
                dialog.setSize(new Dimension(300, 150));
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);

                AuthoringToolCache.getInstance().setSecondaryVideoFolder(null);
            }
        } else if ("Create New Hyperlink".equals(e.getActionCommand())) {
            if ( AuthoringToolCache.getInstance().getPrimaryVideoFolder() == null ||
                    AuthoringToolCache.getInstance().getSecondaryVideoFolder() == null) {
                final JDialog dialog = new JDialog(this,
                        "Error");

                //Add contents to it. It must have a close button,
                //since some L&Fs (notably Java/Metal) don't provide one
                //in the window decorations for dialogs.
                JLabel label = new JLabel("<html><p align=center>"
                        + "Import Primary/Secondary Video.<br>");

                label.setHorizontalAlignment(JLabel.CENTER);
                Font font = label.getFont();
                label.setFont(label.getFont().deriveFont(font.PLAIN,
                        14.0f));

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dialog.setVisible(false);
                        dialog.dispose();
                    }
                });
                JPanel closePanel = new JPanel();
                closePanel.setLayout(new BoxLayout(closePanel,
                        BoxLayout.LINE_AXIS));
                closePanel.add(Box.createHorizontalGlue());
                closePanel.add(closeButton);
                closePanel.setBorder(BorderFactory.
                        createEmptyBorder(0,0,5,5));

                JPanel contentPane = new JPanel(new BorderLayout());
                contentPane.add(label, BorderLayout.CENTER);
                contentPane.add(closePanel, BorderLayout.PAGE_END);
                contentPane.setOpaque(true);
                dialog.setContentPane(contentPane);

                //Show it.
                dialog.setSize(new Dimension(300, 150));
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            }
            else if (CaptureToolCache.getInstance().isWindowActivated()) {
                final JDialog dialog = new JDialog(this,
                        "Error");

                //Add contents to it. It must have a close button,
                //since some L&Fs (notably Java/Metal) don't provide one
                //in the window decorations for dialogs.
                JLabel label = new JLabel("<html><p align=center>"
                        + "Snipping tool already open.<br>");

                label.setHorizontalAlignment(JLabel.CENTER);
                Font font = label.getFont();
                label.setFont(label.getFont().deriveFont(font.PLAIN,
                        14.0f));

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dialog.setVisible(false);
                        dialog.dispose();
                    }
                });
                JPanel closePanel = new JPanel();
                closePanel.setLayout(new BoxLayout(closePanel,
                        BoxLayout.LINE_AXIS));
                closePanel.add(Box.createHorizontalGlue());
                closePanel.add(closeButton);
                closePanel.setBorder(BorderFactory.
                        createEmptyBorder(0,0,5,5));

                JPanel contentPane = new JPanel(new BorderLayout());
                contentPane.add(label, BorderLayout.CENTER);
                contentPane.add(closePanel, BorderLayout.PAGE_END);
                contentPane.setOpaque(true);
                dialog.setContentPane(contentPane);

                //Show it.
                dialog.setSize(new Dimension(300, 150));
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            } else {
                CaptureWindow captureWindow = new CaptureWindow();
                captureWindow.showCaptureWindow();
                CaptureToolCache.getInstance().setWindowActivated(true);
            }
        }
        else
        { //quit
            quit();
        }
    }


    public void importVideofor(String purpose) {
        JFileChooser fc = new JFileChooser();

        // TODO(sungwonc): comment this.
        fc.setCurrentDirectory(new File("."));
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //Add a custom file filter and disable the default
        //(Accept All) file filter.
//        fc.addChoosableFileFilter(new VideoFilter());
        fc.setAcceptAllFileFilterUsed(false);

        //Add custom icons for file types.
        fc.setFileView(new VideoFrameView());

        //Add the preview pane.
        fc.setAccessory(new VideoPreview(fc));

        // Show it.
        int returnVal = fc.showDialog(MainFrame.this, "Import");

        // Process the results.
        InteractiveVideoReader interactiveVideoReader = new InteractiveVideoReader();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            System.out.println("imported folder: " + file.getPath());
            if ("Interactive Video Player".equals(purpose)) {
                InteractiveVideoPlayerOption.getInstance().setInteractiveVideoFile(file);
            } else if ("Authoring Tool: Primary".equals(purpose)) {
                AuthoringToolCache.getInstance().setPrimaryVideoFolder(file);
                interactiveVideoReader.readFileAndConstructFileArray(1);
                File f = AuthoringToolCache.getInstance().getPrimaryVideoFile(0);
                authoringToolFrame.setFrame(f, 1);
            } else if ("Authoring Tool: Secondary".equals(purpose)) {
                AuthoringToolCache.getInstance().setSecondaryVideoFolder(file);
                interactiveVideoReader.readFileAndConstructFileArray(2);
                File f = AuthoringToolCache.getInstance().getSecondaryVideoFile(0);
                authoringToolFrame.setFrame(f, 2);
            }
        } else {

        }

        fc.setSelectedFile(null);
    }

    // Create a new internal frame.
    protected void createFrame() {
        authoringToolFrame.setVisible(true);
        jDesktopPane.add(authoringToolFrame);

        interactiveVideoPlayerFrame.setVisible(true);
        jDesktopPane.add(interactiveVideoPlayerFrame);

        loggerFrame.setVisible(true);
        jDesktopPane.add(loggerFrame);

        metaDataDisplayFrame.setVisible(true);
        jDesktopPane.add(metaDataDisplayFrame);


        try {
            interactiveVideoPlayerFrame.setSelected(true);
            authoringToolFrame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }



    // Quit the application.
    protected void quit() { System.exit(0); }

    private static void createAndShowGUI() {
        // Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Create and set up the window.
        MainFrame mainFrame = new MainFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display the window.
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // Scheduling a job for the event-dispatching thread:
        // Creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() { createAndShowGUI(); }
        });
    }
}

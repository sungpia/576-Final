package com.sungpi.Frames;

import com.sungpi.Caches.AuthoringToolCache;
import com.sungpi.Caches.CaptureToolCache;
import com.sungpi.Caches.MetaDataCache;
import com.sungpi.Caches.PrimaryVideoPositionCache;
import com.sungpi.MetaData;
import com.sungpi.Windows.CaptureWindowListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class CaptureFrame extends JFrame implements ActionListener, AWTEventListener {
    //Create a frame with a button.
    JButton button = new JButton("link");
    Container contentPane = getContentPane();
    BufferedImage bufferedImage = new BufferedImage(CaptureToolCache.getInstance().getFrameWidth(),
            CaptureToolCache.getInstance().getFrameHeight(), BufferedImage.TYPE_INT_RGB);
    JLabel label;

    public CaptureFrame() {
        super("LINK");
        addComponentListener(new CaptureWindowListener());
        setDefaultLookAndFeelDecorated(true);
        setSize(new Dimension(160, 160));
        setAlwaysOnTop(true);
        setOpacity(0.5f);
        setVisible(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //This button lets you close even an undecorated window.


        //Place the button near the bottom of the window.
        contentPane.setLayout(new BoxLayout(contentPane,
                BoxLayout.PAGE_AXIS));
        Color white = new Color(255, 255, 255);
        Color red = new Color(255, 0, 0);
        for (int i = 0; i < CaptureToolCache.getInstance().getFrameHeight(); ++i) {
            for (int j = 0; j < CaptureToolCache.getInstance().getFrameWidth(); ++j) {
                if (i < 4 || j < 4 || i > CaptureToolCache.getInstance().getFrameHeight() - 4 || j > CaptureToolCache.getInstance().getFrameWidth() - 4) {
                    bufferedImage.setRGB(i, j, red.getRGB());
                } else {
                    bufferedImage.setRGB(i, j, white.getRGB());
                }
            }
        }
        ImageIcon img = new ImageIcon(bufferedImage);
        label = new JLabel(img);
        contentPane.add(label);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
//            contentPane.add(Box.createVerticalGlue()); //takes all extra space
        contentPane.add(button);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); //horizontally centered
//            button.setBackground(new Color(Color.BITMASK));
//            contentPane.add(Box.createVerticalStrut(5)); //spacer
        button.setOpaque(true);
        button.setActionCommand("link");
        button.addActionListener(this);
        WindowAdapter windowAdapter = new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {
                System.out.println("Window opened: "
                        + windowEvent.getWindow().getName());
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {
                System.out.println("Window closed: "
                        + windowEvent.getWindow().getName());

                CaptureToolCache.getInstance().setWindowActivated(false);
            }
        };
        this.addWindowListener(windowAdapter);
        CaptureToolCache.getInstance().setCaptureLabel(label);
    }

    public void redraw() {
        Color white = new Color(255, 255, 255);
        Color red = new Color(255, 0, 0);

        bufferedImage = new BufferedImage(CaptureToolCache.getInstance().getFrameWidth(),
                CaptureToolCache.getInstance().getFrameHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < CaptureToolCache.getInstance().getFrameHeight(); ++i) {
            for (int j = 0; j < CaptureToolCache.getInstance().getFrameWidth(); ++j) {
                if (i < 3 || j < 3 || j > CaptureToolCache.getInstance().getFrameWidth() - 4 || i > CaptureToolCache.getInstance().getFrameHeight() -4) {
                    bufferedImage.setRGB(j, i, red.getRGB());
                } else {
                    bufferedImage.setRGB(j, i, white.getRGB());
                }
            }
        }
        ImageIcon img = new ImageIcon(bufferedImage);
        img.getImage().flush();
        label.setIcon(img);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        CaptureToolCache.getInstance().setCaptureLabel(label);
    }

    public void setCaptureRectangleWidth() {

    }

    public int getCaptureRectangleWidth() {
        return 1;
    }

    public void setCaptureRectangleHeight() {

    }

    public int getCaptureRectangleHeight() {
        return 1;
    }

    //Make the button do the same thing as the default close operation
    //(DISPOSE_ON_CLOSE).
    public void actionPerformed(ActionEvent e) {
        if ("link".equals(e.getActionCommand())) {
            JLabel primaryVideo = PrimaryVideoPositionCache.getInstance().getPrimaryVideo();
            AuthoringToolFrame authoringToolFrame = PrimaryVideoPositionCache.getInstance().getAuthoringToolFrame();
            MainFrame mainFrame = PrimaryVideoPositionCache.getInstance().getMainFrame();
            PrimaryVideoPositionCache.getInstance().setAuthoringToolFrameX((int) authoringToolFrame.getBounds().getLocation().getX());
            PrimaryVideoPositionCache.getInstance().setAuthoringToolFrameY((int) authoringToolFrame.getBounds().getLocation().getY());
            PrimaryVideoPositionCache.getInstance().setPrimaryVideoX((int) primaryVideo.getBounds().getLocation().getX());
            PrimaryVideoPositionCache.getInstance().setPrimaryVideoY((int) primaryVideo.getBounds().getLocation().getY());
            PrimaryVideoPositionCache.getInstance().setMainFrameX((int) mainFrame.getBounds().getLocation().getX());
            PrimaryVideoPositionCache.getInstance().setMainFrameY((int) mainFrame.getBounds().getLocation().getY());
            CaptureToolCache captureToolCache = CaptureToolCache.getInstance();
            PrimaryVideoPositionCache primaryVideoPositionCache = PrimaryVideoPositionCache.getInstance();
            int capx1 = captureToolCache.getCaptureLabelX();
            int capy1 = captureToolCache.getCaptureLabelY();
            int capx2 = capx1 + captureToolCache.getFrameWidth();
            int capy2 = capy1 + captureToolCache.getFrameHeight();
            int vidx1 = primaryVideoPositionCache.getAdjustedX();
            int vidy1 = primaryVideoPositionCache.getAdjustedY();
            int vidx2 = vidx1 + 352;
            int vidy2 = vidy1 + 288;
            System.out.println("link (x, y) to (x, y): (" +
                    capx1 +
                    ", " +
                    capy1+
                    ") to (" +
                    capx2 +
                    ", " +
                    capy2 +
                    ")"
            );
            System.out.println("primary (x, y) to (x, y): (" +
                    vidx1 + ", " + vidy1 + ") to (" +
                            vidx2 + ", " +
                    vidy2 + ")");
//            System.out.println()

            if ((capx1 >= vidx1 && capy1 >= vidy1) && (capx2 <= vidx2 && capy2 <= vidy2)) {
                //save
                AuthoringToolCache authoringToolCache = AuthoringToolCache.getInstance();
                System.out.println("creating link");
                MetaData metaData = new MetaData(
                        authoringToolCache.getPrimaryVideoFolder().getAbsolutePath(),
                        authoringToolCache.getVideo1Frame(),
                        authoringToolCache.getVideo1Frame() + 90,
                        capx1 - vidx1,
                        capy1 - vidy1,
                        captureToolCache.getFrameWidth(),
                        captureToolCache.getFrameHeight(),
                        authoringToolCache.getSecondaryVideoFolder().getAbsolutePath(),
                        authoringToolCache.getVideo2Frame()
                );

                System.out.println(metaData.toString());
                MetaDataCache.getInstance().addMetaData(metaData);
            } else {
                System.out.println("ERORRR");
                final JDialog dialog = new JDialog(primaryVideoPositionCache.getMainFrame(),
                        "Error");

                //Add contents to it. It must have a close button,
                //since some L&Fs (notably Java/Metal) don't provide one
                //in the window decorations for dialogs.
                JLabel label = new JLabel("<html><p align=center>"
                        + "Hyperlink should be inside the primary video frame.<br>");

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

        }
        CaptureToolCache.getInstance().setWindowActivated(false);
        setVisible(false);
        dispose();
    }

    public void eventDispatched(AWTEvent event) {
        switch (event.getID()){
            case WindowEvent.WINDOW_OPENED:
//                doSomething();
                break;
            case WindowEvent.WINDOW_CLOSED:
                CaptureToolCache.getInstance().setWindowActivated(false);
                break;
        }
    }
}
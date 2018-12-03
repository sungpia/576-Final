package com.sungpi.Frames;

import com.sungpi.Caches.MetaDataCache;
import com.sungpi.ImportFileUtils.ImportFileUtil;
import com.sungpi.InteractiveVideoPlayer;
import com.sungpi.MetaData;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * This application is intended to demonstrate the loading of image files into icons
 * for use in a Swing user interface. It creates a toolbar with a thumbnail preview
 * of each image.  Clicking on the thumbnail will show the full image
 * in the main display area.
 *
 * MetaDataDisplayFrame.java requires the following files: <br>
 * The following files are copyright 2006 spriggs.net and licensed under a
 * Creative Commons License (http://creativecommons.org/licenses/by-sa/3.0/)
 * <br>
 * images/sunw01.jpg <br>
 * images/sunw02.jpg <br>
 * images/sunw03.jpg <br>
 * images/sunw04.jpg <br>
 * images/sunw05.jpg <br>
 *
 * @author Collin Fagan
 * @date 7/25/2007
 * @version 2.0
 */
public class MetaDataDisplayFrame extends JInternalFrame {

//    BorderLayout c = new GridBagConstraints();
    class MissingIcon implements Icon{

        private int width = 32;
        private int height = 32;

        private BasicStroke stroke = new BasicStroke(4);

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setColor(Color.WHITE);
            g2d.fillRect(x +1 ,y + 1,width -2 ,height -2);

            g2d.setColor(Color.BLACK);
            g2d.drawRect(x +1 ,y + 1,width -2 ,height -2);

            g2d.setColor(Color.RED);

            g2d.setStroke(stroke);
            g2d.drawLine(x +10, y + 10, x + width -10, y + height -10);
            g2d.drawLine(x +10, y + height -10, x + width -10, y + 10);

            g2d.dispose();
        }

        public int getIconWidth() {
            return width;
        }

        public int getIconHeight() {
            return height;
        }

    }

    private JLabel photographLabel = new JLabel();
    private JToolBar buttonBar = new JToolBar();
    private JToolBar editBar = new JToolBar();
    private String imagedir = "images/";

    private List<ThumbnailAction> thumbnailActionsList = new ArrayList<>();

    private com.sungpi.Frames.MetaDataDisplayFrame.MissingIcon placeholderIcon = new com.sungpi.Frames.MetaDataDisplayFrame.MissingIcon();

    /**
     * List of all the descriptions of the image files. These correspond one to
     * one with the image file names
     */
    private String[] imageCaptions = { "Original SUNW Logo", "The Clocktower",
            "Clocktower from the West", "The Mansion", "Sun Auditorium", "The Clocktower",
            "Clocktower from the West", "The Mansion", "The Clocktower",
            "Clocktower from the West", "The Mansion", "The Mansion", "The Mansion", "The Mansion"};

    /**
     * List of all the image files to load.
     */
    private String[] imageFileNames = { "sunw01.jpg", "sunw02.jpg",
            "sunw03.jpg", "sunw04.jpg", "sunw05.jpg", "sunw02.jpg",
            "sunw03.jpg", "sunw04.jpg", "sunw05.jpg", "sunw02.jpg",
            "sunw03.jpg", "sunw04.jpg", "sunw05.jpg"};

    /**
     * Main entry point to the demo. Loads the Swing elements on the "Event
     * Dispatch Thread".
     *
     * @param args
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                com.sungpi.Frames.MetaDataDisplayFrame app = new com.sungpi.Frames.MetaDataDisplayFrame();
                app.setVisible(true);
            }
        });
    }

    /**
     * Default constructor for the demo.
     */
    public MetaDataDisplayFrame() {
        super("Hyperlinks",
                false,
                false,
                false,
                false);


        setSize(650, 450);
        setLocation(800, 0);
//
//        BorderLayout bLayout = new BorderLayout();
//        getContentPane().setLayout(bLayout);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout gLayout = new BorderLayout();
        getContentPane().setLayout(gLayout);
        // A label for displaying the pictures
        photographLabel.setVerticalTextPosition(JLabel.BOTTOM);
        photographLabel.setHorizontalTextPosition(JLabel.CENTER);
        photographLabel.setHorizontalAlignment(JLabel.CENTER);
        photographLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // We add two glue components. Later in process() we will add thumbnail buttons
        // to the toolbar inbetween thease glue compoents. This will center the
        // buttons in the toolbar.
        buttonBar.setAutoscrolls(true);
        buttonBar.setFloatable(false);
        editBar.setBorderPainted(true);
//        buttonBar.set
        buttonBar.setPreferredSize(new Dimension(550, 50));
        buttonBar.add(Box.createGlue());
        buttonBar.add(Box.createGlue());
        getContentPane().add(buttonBar, BorderLayout.PAGE_END);
        getContentPane().add(photographLabel, BorderLayout.CENTER);

        editBar.setAutoscrolls(true);
        editBar.setPreferredSize(new Dimension( 250, 450));
        editBar.setFloatable(false);
        editBar.setBorderPainted(true);
        getContentPane().add(editBar, BorderLayout.EAST);


        JPanel panel = new JPanel();

        JLabel nameLabel = new JLabel("Link Name: ");
        JTextField nameText = new JTextField("");
        nameText.setPreferredSize(new Dimension(150, 30));
        panel.add(nameLabel);
        panel.add(nameText);

        JLabel linkeeLabel = new JLabel("Linkee: ");
        JTextField linkeeText = new JTextField("");
        linkeeText.setPreferredSize(new Dimension(150, 30));
        linkeeText.setEditable(false);
        panel.add(linkeeLabel);
        panel.add(linkeeText);


        editBar.add(panel);
        MetaDataCache.getInstance().setMetaDataDisplayFrame(this);
    }

    public void generateThumbAction() {
        System.out.println("Generating Thumbaction");
        ArrayList<MetaData> metaDatas = MetaDataCache.getInstance().getMetaDatas();
        MetaData metaData = metaDatas.get(metaDatas.size()-1);
        ImageIcon icon;
        icon = createImageIcon(metaData);

        com.sungpi.Frames.MetaDataDisplayFrame.ThumbnailAction thumbAction;
        String desc = metaData.getLinkee().substring(metaData.getLinkee().lastIndexOf('/')+1) + " -> " +
                metaData.getLinked().substring(metaData.getLinked().lastIndexOf('/')+1);

        if(icon != null){

            ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), 32, 32));
            ImageIcon linkedPhoto = new ImageIcon(getScaledImage(icon.getImage(), 176, 144));
            thumbAction = new com.sungpi.Frames.MetaDataDisplayFrame.ThumbnailAction(icon, linkedPhoto, thumbnailIcon, desc);

        } else {
            // the image failed to load for some reason
            // so load a placeholder instead
            thumbAction = new com.sungpi.Frames.MetaDataDisplayFrame.ThumbnailAction(placeholderIcon, placeholderIcon, placeholderIcon, desc);
        }

        JButton thumbButton = new JButton(thumbAction);
        thumbButton.setVisible(true);
        thumbButton.setSize(32, 32);
//        thumbBut
        thumbnailActionsList.add(thumbAction);
        buttonBar.add(thumbButton, buttonBar.getComponentCount() - 1);
        buttonBar.revalidate();
        buttonBar.repaint();
    }

    protected String getImageFileName(String folder, int frameNumber) {
        File dir = new File(folder);
        String filename = Integer.toString(frameNumber+1);
        while(filename.length() < 4) {
            filename = "0" + filename;
        }
        filename = dir.getAbsolutePath() + folder.substring(folder.lastIndexOf('/')) +filename;
        return filename + ".rgb";
    }
    /**
     * SwingWorker class that loads the images a background thread and calls publish
     * when a new one is ready to be displayed.
     *
     * We use Void as the first SwingWroker param as we do not need to return
     * anything from doInBackground().
     */
    public SwingWorker<Void, com.sungpi.Frames.MetaDataDisplayFrame.ThumbnailAction> loadimages = new SwingWorker<Void, com.sungpi.Frames.MetaDataDisplayFrame.ThumbnailAction>() {


        /**
         * Creates full size and thumbnail versions of the target image files.
         */
        @Override
        protected Void doInBackground() throws Exception {
            for (int i = 0; i < MetaDataCache.getInstance().getMetaDatas().size(); i++) {
                System.out.println("loading " + i);
                MetaData metaData = MetaDataCache.getInstance().getMetaDatas().get(i);
                ImageIcon icon;
                icon = createImageIcon(metaData);

                com.sungpi.Frames.MetaDataDisplayFrame.ThumbnailAction thumbAction;
                if(icon != null){

                    ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), 32, 32));
                    String desc = metaData.getLinkee().substring(metaData.getLinkee().lastIndexOf('/')) +
                            metaData.getLinked().substring(metaData.getLinked().lastIndexOf('/'));
                    thumbAction = new com.sungpi.Frames.MetaDataDisplayFrame.ThumbnailAction(icon, icon, thumbnailIcon, desc);

                } else {
                    // the image failed to load for some reason
                    // so load a placeholder instead
                    thumbAction = new com.sungpi.Frames.MetaDataDisplayFrame.ThumbnailAction(placeholderIcon, placeholderIcon, placeholderIcon, imageCaptions[0]);
                }
                publish(thumbAction);
            }
            // unfortunately we must return something, and only null is valid to
            // return when the return type is void.
            return null;
        }

        /**
         * Process all loaded images.
         */
        @Override
        protected void process(List<com.sungpi.Frames.MetaDataDisplayFrame.ThumbnailAction> chunks) {
            for (com.sungpi.Frames.MetaDataDisplayFrame.ThumbnailAction thumbAction : chunks) {
                JButton thumbButton = new JButton(thumbAction);
                // add the new button BEFORE the last glue
                // this centers the buttons in the toolbar
                buttonBar.add(thumbButton, buttonBar.getComponentCount() - 1);
            }
        }
    };

    public ImageIcon getFrameFromFile(File file, int frameNumber, MetaData metaData){
        ImageIcon imageIcon = null;
        try {
            InputStream is = new FileInputStream(file);

            long len = file.length();
            byte[] bytes = new byte[388800];

            int offset = 0;
            int numRead = 0;

            while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }

            BufferedImage bufferedImage = new BufferedImage(352, 288, BufferedImage.TYPE_INT_RGB);
            //read image
            int ind = 0;
            for(int y = 0; y < 288; y++){
                for(int x = 0; x < 352; x++){
                    byte r = bytes[ind];
                    byte g = bytes[ind + 288 * 352];
                    byte b = bytes[ind + 288 * 352 * 2];

                    int pix = 0xff000000 | (((r) & 0xff) << 16) | (((g) & 0xff) << 8) | ((b) & 0xff);
                    bufferedImage.setRGB(x,y,pix);
                    ind++;
                }
            }

            for(int y = metaData.getDisplacementY(); y < metaData.getDisplacementY() + metaData.getDimensionY(); ++y) {
                for(int x = metaData.getDisplacementX(); x < metaData.getDisplacementX() + metaData.getDimensionX(); ++x) {
                    if ( y <= metaData.getDisplacementY() + 2 ||
                            y > metaData.getDisplacementY() + metaData.getDimensionY() - 3  ||
                            x <= metaData.getDisplacementX() + 2 ||
                            x > metaData.getDisplacementX() + metaData.getDimensionX() - 3) {
                        bufferedImage.setRGB(x, y, new Color(255, 0, 0 ).getRGB());
                    }
                }
            }
            imageIcon = new ImageIcon(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageIcon;
    }

    /**
     * Creates an ImageIcon if the path is valid.
     * @param metaData MetaData
     */
    protected ImageIcon createImageIcon(MetaData metaData) {
        ImageIcon imageIcon = getFrameFromFile(new File(getImageFileName(metaData.getLinkee(), metaData.getLinkeeStartFrame())), metaData.getLinkeeStartFrame(), metaData);
        if (imageIcon != null) {
            return imageIcon;
        } else {
            System.err.println("Couldn't find file: " + metaData.getLinkee());
            return null;
        }
    }

    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     * @param srcImg - source image to scale
     * @param w - desired width
     * @param h - desired height
     * @return - the new resized image
     */
    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    /**
     * Action class that shows the image specified in it's constructor.
     */
    public class ThumbnailAction extends AbstractAction{

        /**
         *The icon if the full image we want to display.
         */
        private Icon displayPhoto;
        private Icon linkedPhoto;

        /**
         * @param Icon - The full size photo to show in the button.
         * @param Icon - The thumbnail to show in the button.
         * @param String - The descriptioon of the icon.
         */
        public ThumbnailAction(Icon photo, Icon linkedPhoto, Icon thumb, String desc){
            displayPhoto = photo;

            // The short description becomes the tooltip of a button.
            putValue(SHORT_DESCRIPTION, desc);

            // The LARGE_ICON_KEY is the key for setting the
            // icon when an Action is applied to a button.
            putValue(LARGE_ICON_KEY, thumb);
        }

        /**
         * Shows the full image in the main area and sets the application title.
         */
        public void actionPerformed(ActionEvent e) {
            photographLabel.setIcon(displayPhoto);
            setTitle("Hyperlink: " + getValue(SHORT_DESCRIPTION).toString());
        }
    }

    public List<ThumbnailAction> getThumbnailActionsList() {
        return thumbnailActionsList;
    }
}
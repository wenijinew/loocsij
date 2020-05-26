/**
 * 
 */
package org.loocsij.gui.awt.demo;

import java.awt.Button;
import java.awt.Color;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import org.loocsij.gui.awt.*;
import org.loocsij.gui.util.GUIUtil;
import org.loocsij.img.ImagePanel;

/**
 * @author wengm
 * 
 */
public class ImageViewer extends Window implements ItemListener, ActionListener {
    String path = "C:\\Documents and Settings\\wengm\\My Documents\\My Pictures";

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * TextFiled - used to specify the root directory of image files
     */
    private TextField tfImageRoot;

    /**
     * Button - used to set initial state - get all supported image files' names
     * and add into list.
     */
    private Button btConfiguration;

    /**
     * ImagePanel - used to show current selected image
     */
    private ImagePanel ipImageShower;

    /**
     * ListPanel - used to store all supported image files' names
     */
    private ListPanel lpImageNames;

    /**
     * ConfPanel - used to config the initial state
     */
    private Panel confPanel;

    private String imageRoot;


    /**
     * 
     */
    //public ImageViewer() {
    //    // control panel - set root directory of images
    //    tfImageRoot = new TextField(path);
    //    btConfiguration = new Button("set");
    //    btConfiguration.addActionListener(this);
    //    this.confPanel = new Panel();
    //    confPanel.add(tfImageRoot);
    //    confPanel.add(btConfiguration);
    //    this.add(confPanel, BorderLayout.NORTH);

    //    // image shower - show image
    //    try {
    //        ipImageShower = new ImagePanel();
    //    } catch (ImageFormatException e) {
    //        // TODO Auto-generated catch block
    //        e.printStackTrace();
    //    } 
    //    this.add(ipImageShower, BorderLayout.CENTER);

    //    // list - store image file's name
    //    lpImageNames = new ListPanel(null, this.getHeight() / 10);
    //    lpImageNames.getList().addItemListener(this);
    //    this.add(lpImageNames, BorderLayout.WEST);

    //    // display window
    //    GUIUtil.setLocation(this);
    //    this.setVisible(true);
    //}

    Hashtable cache = null;

    /**
     * get all supported image files' names and add them into list
     */
    public void actionPerformed(ActionEvent e) {
        imageRoot = this.tfImageRoot.getText();
        String[] files = new File(imageRoot).list();
        if (files == null) {
            return;
        }
        List list = lpImageNames.getList();
        list.removeAll();
        cache = new Hashtable();
        BufferedImage bi = null;
        String imageFilePath = null;
        for (int i = 0; i < files.length; i++) {
            if (GUIUtil.isSupportedImage(files[i])) {
                list.add(files[i]);
                imageFilePath = imageRoot
                        + System.getProperty("file.separator") + files[i];
                bi = GUIUtil.loadBufferedImage(imageFilePath);
                if (bi != null) {
                    bi.getGraphics().drawImage(bi, 0, 0, null);
                    cache.put(String.valueOf(files[i]), bi);
                } else {
                    System.out.println(imageFilePath);
                }
            }
        }

        this.setBackground(Color.blue);
    }

    public void itemStateChanged(ItemEvent e) {
        this.confPanel.setBackground(GUIUtil.getRandomColor(100, 100));
        this.lpImageNames.setBackground(GUIUtil.getRandomColor(150, 100));
        BufferedImage bi = (BufferedImage) cache.get(this.lpImageNames
                .getList().getSelectedItem());
        ipImageShower.setBufferedImage(bi);
        ipImageShower.repaint();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new ImageViewer();
    }

}

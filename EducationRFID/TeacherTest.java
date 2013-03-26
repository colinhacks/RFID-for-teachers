import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TeacherTest {
    public static void main(String[] args) {
    	HashMap shared = new HashMap();
        TeacherWindow carl = new TeacherWindow("Manus for teachers", shared);
        carl.setSize(500, 300);
        carl.setVisible(true);
        carl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
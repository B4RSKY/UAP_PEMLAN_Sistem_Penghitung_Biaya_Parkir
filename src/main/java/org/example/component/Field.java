package org.example.component;

import org.example.util.Style;
import javax.swing.*;
import java.awt.*;

public class Field extends JTextField {
    public Field(int cols) {
        super(cols);
        setFont(new Font("Segoe UI", Font.PLAIN, 16));
        setForeground(Color.BLACK);
        setCaretColor(Color.BLACK);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, Style.PRIMARY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        setBackground(Color.WHITE);
    }
}
package org.example.component;

import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {
    private Color color;
    public CardPanel(Color color) {
        this.color = color;
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(220, 220, 220));
        g2.fillRoundRect(3, 3, getWidth()-6, getHeight()-6, 15, 15);

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth()-6, getHeight()-6, 15, 15);

        g2.setColor(color);
        g2.fillRoundRect(0, 0, 8, getHeight()-6, 15, 15);
        g2.fillRect(4, 0, 8, getHeight()-6);

        super.paintComponent(g);
    }
}
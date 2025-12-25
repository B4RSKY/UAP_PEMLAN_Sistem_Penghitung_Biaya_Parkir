package org.example.component;

import org.example.util.Style;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton {
    private Color color;
    private boolean isHovered = false;

    public Button(String text, Color color) {
        super(text);
        this.color = color;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        setForeground(Color.BLACK);
        setFont(Style.FONT_BOLD);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
            public void mouseExited(MouseEvent e) { isHovered = false; repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isHovered) g2.setColor(color.brighter());
        else g2.setColor(color);

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        super.paintComponent(g);
    }
}
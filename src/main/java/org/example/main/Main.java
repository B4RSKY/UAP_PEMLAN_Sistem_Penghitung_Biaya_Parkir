package org.example.main;

import org.example.model.ParkingData;
import org.example.util.FileManager;
import org.example.util.Style;
import org.example.component.Button;
import org.example.component.Field;
import org.example.component.CardPanel;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class Main extends JFrame {
    private List<ParkingData> parkingList;
    private CardLayout cardLayout = new CardLayout();
    private JPanel contentPanel = new JPanel(cardLayout);

    private JLabel lblAktif, lblSelesai, lblUang;
    private DefaultTableModel modelAktif, modelHistory;
    private Map<String, Integer> daftarTarif = new HashMap<>();

    public Main() {
        daftarTarif.put("Motor", 2000);
        daftarTarif.put("Mobil", 5000);
        daftarTarif.put("Truk", 10000);
        daftarTarif.put("Bus", 15000);
        parkingList = FileManager.load();

        setTitle("SkyPark - Sistem Parkir Gacor");
        setSize(1200, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.add(createSidebar(), BorderLayout.WEST);

        contentPanel.add(createDashboard(), "DASHBOARD");
        contentPanel.add(createInputForm(), "INPUT");
        contentPanel.add(createTablePage("DATA"), "DATA");
        contentPanel.add(createTablePage("HISTORY"), "HISTORY");

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230,230,230)));

        JLabel dateLbl = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")));
        dateLbl.setFont(Style.FONT_PLAIN);
        dateLbl.setForeground(Color.BLACK);
        topBar.add(dateLbl);

        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.add(topBar, BorderLayout.NORTH);
        rightContainer.add(contentPanel, BorderLayout.CENTER);

        mainContainer.add(rightContainer, BorderLayout.CENTER);
        add(mainContainer);

        updateDashboard();
    }

    private JPanel createSidebar() {
        JPanel p = new JPanel();
        p.setBackground(Style.BG_SIDEBAR);
        p.setPreferredSize(new Dimension(250, 0));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220,220,220)));

        JLabel logo = new JLabel("SKYPARK");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logo.setForeground(Style.SIDE_BAR);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLogo = new JLabel("Sistem Parkir");
        subLogo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLogo.setForeground(Color.white);
        subLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel btnContainer = new JPanel();
        btnContainer.setLayout(new BoxLayout(btnContainer, BoxLayout.Y_AXIS));
        btnContainer.setOpaque(false);

        btnContainer.add(Box.createRigidArea(new Dimension(0, 30)));
        btnContainer.add(createSideBtn("Dashboard", "DASHBOARD"));
        btnContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        btnContainer.add(createSideBtn("Input Masuk", "INPUT"));
        btnContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        btnContainer.add(createSideBtn("Data Parkir", "DATA"));
        btnContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        btnContainer.add(createSideBtn("Riwayat Transaksi", "HISTORY"));

        p.add(Box.createRigidArea(new Dimension(0, 40)));
        p.add(logo);
        p.add(subLogo);
        p.add(Box.createRigidArea(new Dimension(0, 40)));
        p.add(btnContainer);
        p.add(Box.createVerticalGlue());

        JButton btnExit = createSideBtn("Keluar Aplikasi", "EXIT");
        btnExit.setForeground(Style.DANGER);
        p.add(btnExit);
        p.add(Box.createRigidArea(new Dimension(0, 20)));

        return p;
    }

    private JButton createSideBtn(String text, String cardName) {
        JButton b = new JButton(text);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(220, 50));
        b.setBackground(Color.WHITE);
        b.setForeground(Style.SIDE_BAR);
        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(new EmptyBorder(0, 25, 0, 0));

        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if(!cardName.equals("EXIT")) {
                    b.setOpaque(true);
                    b.setBackground(new Color(121, 121, 121));
                }
            }
            public void mouseExited(MouseEvent e) {
                if(!cardName.equals("EXIT")) b.setOpaque(false);
                b.setBackground(Color.WHITE);
            }
        });

        b.addActionListener(e -> {
            if(cardName.equals("EXIT")) System.exit(0);
            updateDashboard();
            updateTableData();
            cardLayout.show(contentPanel, cardName);
        });
        return b;
    }

    private JPanel createDashboard() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Style.BG_MAIN);
        p.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Dashboard Overview");
        title.setFont(Style.FONT_HEADER);
        title.setForeground(Style.TEXT_MAIN);

        JPanel statsGrid = new JPanel(new GridLayout(1, 3, 20, 0));
        statsGrid.setOpaque(false);

        lblAktif = new JLabel("0");
        lblSelesai = new JLabel("0");
        lblUang = new JLabel("Rp 0");

        statsGrid.add(createStatCard("Kendaraan Parkir", lblAktif, Style.SUCCESS));
        statsGrid.add(createStatCard("Total Transaksi", lblSelesai, Style.ACCENT));
        statsGrid.add(createStatCard("Pendapatan Total", lblUang, Style.WARNING));

        JPanel topContainer = new JPanel(new BorderLayout(0, 20));
        topContainer.setOpaque(false);
        topContainer.add(title, BorderLayout.NORTH);
        topContainer.add(statsGrid, BorderLayout.CENTER);

        p.add(topContainer, BorderLayout.NORTH);
        return p;
    }

    private JPanel createStatCard(String title, JLabel valueLbl, Color color) {
        CardPanel card = new CardPanel(color);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(Style.TEXT_SECONDARY);

        valueLbl.setFont(new Font("Segoe UI", Font.BOLD, 40));
        valueLbl.setForeground(Style.TEXT_MAIN);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(valueLbl, BorderLayout.CENTER);
        return card;
    }

    private JPanel createInputForm() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Style.BG_MAIN);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220), 1),
                new EmptyBorder(30, 50, 30, 50)
        ));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Registrasi Masuk");
        title.setFont(Style.FONT_HEADER);
        title.setForeground(Style.PRIMARY);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        Field txtPlat = new Field(15);
        JComboBox<String> cbJenis = new JComboBox<>(new String[]{"Motor", "Mobil", "Truk", "Bus"});
        cbJenis.setBackground(Color.WHITE);
        cbJenis.setForeground(Color.BLACK);

        JLabel lblInfo = new JLabel("Tarif: Rp 2,000 / Jam");
        lblInfo.setFont(Style.FONT_BOLD);
        lblInfo.setForeground(Style.WARNING);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);

        cbJenis.addActionListener(e ->
                lblInfo.setText("Tarif: Rp " + String.format("%,d", daftarTarif.get(cbJenis.getSelectedItem())) + " / Jam")
        );

        JLabel l1 = new JLabel("Plat Nomor:"); l1.setForeground(Color.BLACK);
        JLabel l2 = new JLabel("Jenis:"); l2.setForeground(Color.BLACK);

        Button btnSimpan = new Button("SIMPAN DATA", Style.SUCCESS);
        btnSimpan.setPreferredSize(new Dimension(200, 45));

        g.gridx=0; g.gridy=0; g.gridwidth=2; card.add(title, g);
        g.gridy=1; g.gridwidth=1; card.add(l1, g);
        g.gridx=1; card.add(txtPlat, g);
        g.gridx=0; g.gridy=2; card.add(l2, g);
        g.gridx=1; card.add(cbJenis, g);
        g.gridx=0; g.gridy=3; g.gridwidth=2; card.add(lblInfo, g);
        g.gridy=4; card.add(btnSimpan, g);

        btnSimpan.addActionListener(e -> {
            try {
                String plat = txtPlat.getText().trim().toUpperCase();
                if(plat.isEmpty()) throw new Exception("Plat harus diisi!");

                for(ParkingData p : parkingList) {
                    if(p.getPlatNomor().equalsIgnoreCase(plat) && p.getStatus().equals("AKTIF")) {
                        throw new Exception("Kendaraan dengan plat " + plat + " masih parkir (AKTIF)!");
                    }
                }

                parkingList.add(new ParkingData(plat, (String)cbJenis.getSelectedItem(), LocalDateTime.now()));
                FileManager.save(parkingList);
                JOptionPane.showMessageDialog(this, "Berhasil Disimpan!");
                txtPlat.setText("");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        wrapper.add(card);
        return wrapper;
    }

    private JPanel createTablePage(String type) {
        JPanel p = new JPanel(new BorderLayout(0, 15));
        p.setBackground(Style.BG_MAIN);
        p.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel head = new JPanel(new BorderLayout());
        head.setOpaque(false);
        JLabel title = new JLabel(type.equals("DATA") ? "Data Parkir Aktif" : "Riwayat Transaksi");
        title.setFont(Style.FONT_HEADER);
        title.setForeground(Style.PRIMARY);

        JTextField txtSearch = new Field(15);
        txtSearch.setText("Cari data...");
        txtSearch.setForeground(Color.GRAY);

        txtSearch.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if(txtSearch.getText().equals("Cari data...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if(txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Cari data...");
                    txtSearch.setForeground(Color.GRAY);
                }
            }
        });

        head.add(title, BorderLayout.WEST);
        head.add(txtSearch, BorderLayout.EAST);

        String[] cols = type.equals("DATA")
                ? new String[]{"Plat Nomor", "Jenis", "Masuk", "Status"}
                : new String[]{"Plat Nomor", "Jenis", "Masuk", "Keluar", "Biaya"};

        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        if(type.equals("DATA")) modelAktif = model; else modelHistory = model;

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(Style.FONT_PLAIN);
        table.setForeground(Color.BLACK);
        table.setShowVerticalLines(false);

        table.getTableHeader().setBackground(new Color(230, 230, 230));
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setFont(Style.FONT_BOLD);
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        table.setSelectionBackground(new Color(220, 240, 255));
        table.setSelectionForeground(Color.BLACK);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            private void filter() {
                String text = txtSearch.getText();
                if (text.equals("Cari data...") || text.isEmpty()) sorter.setRowFilter(null);
                else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        if (type.equals("DATA")) {
            Button btnOut = new Button("Checkout / Keluar", Style.SUCCESS);
            Button btnDel = new Button("Hapus Data", Style.DANGER);
            Button btnUpd = new Button("Update Data", Style.WARNING);

            btnOut.addActionListener(e -> {
                int r = table.getSelectedRow();
                if(r == -1) {
                    JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else {
                    prosesKeluar((String)table.getValueAt(table.convertRowIndexToModel(r), 0));
                }
            });

            btnDel.addActionListener(e -> {
                int r = table.getSelectedRow();
                if(r == -1) {
                    JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else {
                    String plat = (String)table.getValueAt(table.convertRowIndexToModel(r), 0);
                    int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data " + plat + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if(confirm == JOptionPane.YES_OPTION) {
                        parkingList.removeIf(d -> d.getPlatNomor().equals(plat) && d.getStatus().equals("AKTIF"));
                        FileManager.save(parkingList);
                        updateTableData();
                        updateDashboard();
                    }
                }
            });

            btnUpd.addActionListener(e -> {
                int r = table.getSelectedRow();
                if(r == -1) {
                    JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else {
                    String plat = (String)table.getValueAt(table.convertRowIndexToModel(r), 0);
                    showUpdateDialog(plat);
                }
            });

            btnPanel.add(btnDel);
            btnPanel.add(btnUpd);
            btnPanel.add(btnOut);
        }

        p.add(head, BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER);
        p.add(btnPanel, BorderLayout.SOUTH);
        return p;
    }

    private void showUpdateDialog(String currentPlat) {
        ParkingData target = null;
        for (ParkingData d : parkingList) {
            if (d.getPlatNomor().equals(currentPlat) && d.getStatus().equals("AKTIF")) {
                target = d;
                break;
            }
        }
        if (target == null) return;

        JTextField txtEditPlat = new JTextField(target.getPlatNomor());
        JComboBox<String> cbEditJenis = new JComboBox<>(new String[]{"Motor", "Mobil", "Truk", "Bus"});
        cbEditJenis.setSelectedItem(target.getJenisKendaraan());

        Object[] message = {
                "Plat Nomor:", txtEditPlat,
                "Jenis Kendaraan:", cbEditJenis
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Update Data Parkir", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String newPlat = txtEditPlat.getText().trim().toUpperCase();
            if(!newPlat.isEmpty()) {
                target.setPlatNomor(newPlat);
                target.setJenisKendaraan((String)cbEditJenis.getSelectedItem());

                FileManager.save(parkingList);
                updateTableData();
                JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            } else {
                JOptionPane.showMessageDialog(this, "Plat nomor tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void prosesKeluar(String plat) {
        for (ParkingData d : parkingList) {
            if (d.getPlatNomor().equals(plat) && d.getStatus().equals("AKTIF")) {
                LocalDateTime now = LocalDateTime.now();
                d.setWaktuKeluar(now);
                d.setStatus("SELESAI");
                long jam = Math.max(1, java.time.Duration.between(d.getWaktuMasuk(), now).toHours());
                d.setBiaya(jam * daftarTarif.get(d.getJenisKendaraan()));
                FileManager.save(parkingList);
                JOptionPane.showMessageDialog(this, "Biaya: Rp " + String.format("%,.0f", d.getBiaya()));
                updateDashboard();
                updateTableData();
                return;
            }
        }
    }

    private void updateDashboard() {
        long aktif = parkingList.stream().filter(p -> p.getStatus().equals("AKTIF")).count();
        long selesai = parkingList.stream().filter(p -> p.getStatus().equals("SELESAI")).count();
        double total = parkingList.stream().mapToDouble(ParkingData::getBiaya).sum();
        lblAktif.setText(String.valueOf(aktif));
        lblSelesai.setText(String.valueOf(selesai));
        lblUang.setText(String.format("Rp %,.0f", total));
    }

    private void updateTableData() {
        modelAktif.setRowCount(0);
        modelHistory.setRowCount(0);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        for (ParkingData d : parkingList) {
            if (d.getStatus().equals("AKTIF"))
                modelAktif.addRow(new Object[]{d.getPlatNomor(), d.getJenisKendaraan(), d.getWaktuMasuk().format(f), "PARKIR"});
            else
                modelHistory.addRow(new Object[]{d.getPlatNomor(), d.getJenisKendaraan(), d.getWaktuMasuk().format(f), d.getWaktuKeluar().format(f), "Rp " + (long)d.getBiaya()});
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
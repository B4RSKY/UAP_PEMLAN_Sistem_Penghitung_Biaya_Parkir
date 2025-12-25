package org.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParkingData {
    private String platNomor, jenisKendaraan, status;
    private LocalDateTime waktuMasuk, waktuKeluar;
    private double biaya;

    public ParkingData(String platNomor, String jenisKendaraan, LocalDateTime waktuMasuk) {
        this.platNomor = platNomor;
        this.jenisKendaraan = jenisKendaraan;
        this.waktuMasuk = waktuMasuk;
        this.status = "AKTIF";
    }

    public String getPlatNomor() {
        return platNomor; }
    public void setPlatNomor(String p) {
        this.platNomor = p; }

    public String getJenisKendaraan() {
        return jenisKendaraan; }
    public void setJenisKendaraan(String j) {
        this.jenisKendaraan = j; }

    public LocalDateTime getWaktuMasuk() {
        return waktuMasuk; }

    public LocalDateTime getWaktuKeluar() {
        return waktuKeluar; }
    public void setWaktuKeluar(LocalDateTime t) {
        this.waktuKeluar = t; }

    public double getBiaya() { return biaya; }
    public void setBiaya(double b) {
        this.biaya = b; }

    public String getStatus() {
        return status; }
    public void setStatus(String s) {
        this.status = s; }

    public String toCSV() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String out = waktuKeluar != null ? waktuKeluar.format(f) : "null";
        return platNomor + "," + jenisKendaraan + "," + waktuMasuk.format(f) + "," + out + "," + biaya + "," + status;
    }

    public static ParkingData fromCSV(String line) {
        try {
            String[] p = line.split(",");
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ParkingData d = new ParkingData(p[0], p[1], LocalDateTime.parse(p[2], f));
            if (!p[3].equals("null")) d.setWaktuKeluar(LocalDateTime.parse(p[3], f));
            d.setBiaya(Double.parseDouble(p[4]));
            d.setStatus(p[5]);
            return d;

        } catch (Exception e) {
            return null; }
    }
}
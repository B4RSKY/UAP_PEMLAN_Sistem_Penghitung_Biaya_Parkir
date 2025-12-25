package org.example.util;

import org.example.model.ParkingData;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String FILE = "parking_db.csv";

    public static void save(List<ParkingData> list) {
        try (PrintWriter w = new PrintWriter(new FileWriter(FILE))) {
            for (ParkingData d : list) w.println(d.toCSV());
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static List<ParkingData> load() {
        List<ParkingData> list = new ArrayList<>();
        File f = new File(FILE);
        if (!f.exists()) return list;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null) {
                ParkingData d = ParkingData.fromCSV(line);
                if (d != null) list.add(d);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }
}
package logic;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import common.GameInterface;

public class Utils {
    public static Player player=null;
    public static GameInterface game=null;
    public static PlayerStats stats=null;
    public static String filePath=null;

    public static void saveStats() throws IOException {
        File f =new File(filePath);
        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(stats);
        os.close();
        fos.close();
    }

    public static void readStats() throws IOException, ClassNotFoundException {
        File myStatsFile = new File(filePath);

            if(!myStatsFile.exists()){
                myStatsFile.createNewFile();
                Utils.stats = new PlayerStats();
                saveStats();
            }else{
                FileInputStream fin = new FileInputStream(myStatsFile);
                ObjectInputStream in = new ObjectInputStream(fin);
                Utils.stats = (PlayerStats) in.readObject();
                fin.close();
                in.close();
            }

    }



}

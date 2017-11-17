package orz.an.log.etl.load2hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LoadDataToHive {

    private static final String LOAD_CMD =
            "load data inpath '%s' overwrite into table record partition(partition_date='%s',hour_minute='%s')";

    private static final String driverName = "org.apache.hive.jdbc.HiveDriver";

    private static void loadData(String dataDir, String date, String hour_minute) throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Connection con =
                DriverManager.getConnection("jdbc:hive2://slave:10000/default", "hadoop", "hadoop");
        Statement stmt = con.createStatement();
        String sql = String.format(LOAD_CMD, dataDir, date, hour_minute);
        stmt.execute(sql);
    }

    public static void main(String[] args) throws SQLException {
        if (args.length != 3) {
            throw new IllegalArgumentException("need 3 args");
        }
        LoadDataToHive.loadData(args[0], args[1], args[2]);
    }

}

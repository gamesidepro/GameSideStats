package Utils;


import gamesidestats.ConfigurationManager;
import gamesidestats.GameSideStats;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.api.entity.living.player.Player;

/**
 *
 * @author Twelvee
 */
public class DataBase {

    public String user = ConfigurationManager.getInstance().getConfig().getNode("login").getString();
    public String password = ConfigurationManager.getInstance().getConfig().getNode("password").getString();
    public Logger logger = GameSideStats.instance.getLogger();
    
    public String connectionString = "localhost:3306";

    private Connection connection;

    private final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private int dbConnectionTimeOut = 10000;

    public DataBase() {

    }

    public DataBase(String dbUser, String dbPassword, String connectionHost, int dbConnectionTimeOut) {
        user = dbUser;
        password = dbPassword;
        connectionString = connectionHost;
        this.dbConnectionTimeOut = dbConnectionTimeOut;
    }

    public String[] getArrayFromDB(String table, String[] args) {
        return getArrayFromDB(table, args, "");
    }

    /**
     *  класс возвращает нам массив из наших столбцов, который мы указали 
     * @param table name of table
     * @param args columns
     * @param where
     * @return
     */
    public String[] getArrayFromDB(String table, String[] args, String where) {
        String[] result = new String[args.length];
        String query = "SELECT ";
        for (String arg : args)
            query += arg + ", ";
        query = query.substring(0, query.length() - 2) + " FROM " + table;
        if (!where.equals(""))
            query = query + " WHERE " + where;
        query = query + ";";
        ResultSet resultSet = getResultSet(query);
        try {
            if (resultSet.next()) {
                for (int i = 0; i < args.length; i++)
                    result[i] = resultSet.getString(args[i]);
            } else
                for (int i = 0; i < args.length; i++)
                    result[i] = "null";
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    // возвращает все данные  в виде ArrayList<Map<String,Object>>
    public ArrayList getEntities(String table){
        String baseQuery = "SELECT * FROM " + table;
        ArrayList entities = new ArrayList<>();
        try {
            entities = (ArrayList)
                    loadObjectFromResultSet(getResultSet(baseQuery));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  entities;
    }

    public String checkIfPremOur(Player player) throws SQLException{
        String id;
        Statement st = DriverManager.getConnection("jdbc:mysql://localhost:3306/gameside", user, password).createStatement();
        String sql = ("SELECT `until` FROM `gs_prems` WHERE `username`='"+player.getName()+"'");
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()) { 
         id = rs.getString("until"); 
         return id;
        }else{
            return null;
        }
    }
    
    public String getCurrentUserGroup(Player player) throws SQLException{
        String id;
        Statement st = DriverManager.getConnection("jdbc:mysql://localhost:3306/gameside", user, password).createStatement();
        String sql = ("SELECT `pex_name` FROM `gs_prems` WHERE `username`='"+player.getName()+"'");
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()) { 
         id = rs.getString("pex_name"); 
         return id;
        }else{
            return null;
        }
    }
    
    public ArrayList checkIfUserExist(String table, String username){
        String baseQuery = "SELECT id FROM `" + table + "` WHERE `player_name`='"+username+"'";
        ArrayList entities = new ArrayList<>();
        try {
            entities = (ArrayList)
                    loadObjectFromResultSet(getResultSet(baseQuery));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  entities;
    }
    
    public Object loadObjectFromResultSet(ResultSet resultSet) throws Exception
    {
        ArrayList<Object> objectArrayList = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while(resultSet.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 1; i < columnCount -1 ; i++) {
                String columnName = metaData.getColumnName(i);
                Object objectValue = resultSet.getObject(i);
                map.put(columnName, objectValue);
            }
            objectArrayList.add(map); // после того, как всю строку считали, сохраняем в массив и заново 
        }
        return objectArrayList;
    }


    public void connect() {
        try {
            // Create MySQL Connection
            Class.forName(DB_DRIVER);
            // https://helpx.adobe.com/coldfusion/kb/mysql-error-java-sql-sqlexception.html 0000-00-00 date exception
            setConnection(DriverManager.getConnection("jdbc:mysql://localhost:3306/gameside", user, password));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet getResultSet(String sqlQuery) {
        ResultSet resultSet = null;
        try {
            Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/gameside", user, password).createStatement();
            resultSet = statement.executeQuery(sqlQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultSet;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
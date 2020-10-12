package async;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class SqlStore implements Store {
    private final Connection cn;
    private final Map<String, Integer> countryMap;

    public SqlStore(Connection connection) {
        this.cn = connection;
        this.countryMap = new HashMap<>();
    }

    public static Connection init() {
        try (InputStream in = SqlStore
                .class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void saveCountry(String countryName) {
        try (PreparedStatement ps = cn.prepareStatement(
                "INSERT INTO country(name) VALUES(?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, countryName);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                countryMap.put(countryName, rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(User user) {
        try (PreparedStatement ps = cn.prepareStatement(
                "INSERT INTO users (firstname, lastname, country, rating) VALUES(?, ?, ?, ?)")) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, countryMap.get(user.getCountry()));
            ps.setInt(4, user.getRating());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = cn.prepareStatement(
                "SELECT firstname, lastname, country.name as countryName, "
                        + "rating FROM users JOIN country ON users.country = country.id")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getString("firstname"), rs.getString("lastname"),
                        rs.getString("countryName"), rs.getInt("rating")));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findByName(String name) {
        User user = null;
        try (PreparedStatement ps = cn
                .prepareStatement(
                        "SELECT firstname, lastname, c.name as country, rating FROM users as u " +
                                "INNER JOIN country as c ON c.id = u.country WHERE u.firstname = ?")) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("firstname"), rs.getString("lastname"),
                        rs.getString("country"), rs.getInt("rating"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }
}

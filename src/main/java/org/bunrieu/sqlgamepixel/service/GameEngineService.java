package org.bunrieu.sqlgamepixel.service;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameEngineService {
    //inject JdbcTemplate dependency by constructor
    private final JdbcTemplate jdbcTemplate;
    public GameEngineService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //init lv1
    public void startLevel1() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS doors;");

        jdbcTemplate.execute("CREATE TABLE doors (id INT PRIMARY KEY, status VARCHAR(255), room_name VARCHAR(255));");
        //
        jdbcTemplate.execute("INSERT INTO doors (id, status, room_name) VALUES (1, 'locked', 'Tan''s Room');");
        System.out.println("create Level 1 in H2 Database");
    }

//execute player command
    public String executePlayerCommand(String sql) {
        try {
            //base security
            if (sql.toUpperCase().contains("DROP") || sql.toUpperCase().contains("TRUNCATE")) {
                return "ERROR,cmt not allowed!";
            }

            int rowsAffected = jdbcTemplate.update(sql);
            return "Suscess!excuted: " + rowsAffected;

        } catch (Exception e) {
            // IF SQL IS WRONG
            return "ERROR SQL: " + e.getMessage();
        }
    }
}
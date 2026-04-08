package org.bunrieu.sqlgamepixel.service;

import org.bunrieu.sqlgamepixel.dto.GameStateResponse;
import org.bunrieu.sqlgamepixel.dto.PlayerDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameEngineService {

    private final JdbcTemplate jdbcTemplate;
    private PlayerDto player = new PlayerDto();
    private Map<Integer, Boolean> levels = new HashMap<>();

    public GameEngineService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        // unlock
        for (int i = 1; i <= 16; i++) {
            levels.put(i, i == 1);
        }
    }
    // LV1
    public void startLevel1() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS QUAIVAT;");
        jdbcTemplate.execute("DROP TABLE IF EXISTS KHUVUC;");
        //build table
        jdbcTemplate.execute("CREATE TABLE KHUVUC (MaKhuVuc VARCHAR(10) PRIMARY KEY, TenKhuVuc VARCHAR(255) NOT NULL);");
        jdbcTemplate.execute("CREATE TABLE QUAIVAT (MaQuai INT PRIMARY KEY, TenQuai VARCHAR(255) NOT NULL, NgayThucTinh DATE, DacTinh VARCHAR(255), MaKhuVuc VARCHAR(10), FOREIGN KEY (MaKhuVuc) REFERENCES KHUVUC(MaKhuVuc));");

        //insert data
        jdbcTemplate.execute("INSERT INTO KHUVUC VALUES ('Z1', 'Rung Suong Mu'), ('Z2', 'Ham Bang Gia'), ('Z3', 'Lau Dai Lua');");
        jdbcTemplate.execute("INSERT INTO QUAIVAT VALUES " +
                "(1, 'Pink Monster', '2020-01-01', 'Thich an dau tay', 'Z1')," +
                "(2, 'Owlet Bao To', '2021-05-15', 'Tao loc xoay cap 12', 'Z1')," +
                "(3, 'Dude Tron Tim', '2022-10-10', 'Tang hinh trong bong toi', 'Z2')," +
                "(4, 'Slime Doc', '2021-09-05', 'Phun axit', 'Z2')," +
                "(5, 'Rong Dat Nhi', '2019-04-17', 'Dao ham tau thoat', 'Z3');");

        // set player position
        this.player.setPosX(0);
        this.player.setCurrentLevel(1);
        System.out.println("Backend: Level 1 - Monster Dungeon initialized.");
    }

    public GameStateResponse runCommand(String sql) {
        GameStateResponse response = new GameStateResponse();

        if (sql == null || sql.trim().isEmpty()) {
            response.setMessage(">> ERROR: No SQL command provided!");
            return response;
        }

        try {
            String upperSql = sql.trim().toUpperCase().replaceAll("\\s+", " ").replace(";", "");
            if (upperSql.startsWith("SELECT")) {
                List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
                response.setDataTable(result);
                response.setMessage(">> SYSTEM: Executed successfully. " + result.size() + " rows returned.");
            } else {
                int rows = jdbcTemplate.update(sql);
                response.setMessage(">> SYSTEM: Command executed! Rows affected: " + rows);
                response.setDataTable(jdbcTemplate.queryForList("SELECT * FROM QUAIVAT"));
            }
            if (player.getCurrentLevel() == 1) {
                if (upperSql.equals("SELECT * FROM QUAIVAT")) {
                    response.setMessage(">> SYSTEM: LEVEL 1 CLEARED! Port Z1 opened. \n" + response.getMessage());
                    levels.put(2, true); // Unlock level 2
                    player.setPosX(1);   //
                }
            }
        } catch (Exception e) {
            response.setMessage(">> SQL ERROR: " + e.getMessage());
        }
        response.setPlayer(this.player);
        response.setLevelProgress(this.levels);
        return response;
    }
}
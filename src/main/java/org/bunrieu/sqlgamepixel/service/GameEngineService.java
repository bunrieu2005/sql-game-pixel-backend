package org.bunrieu.sqlgamepixel.service;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
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
        for (int i = 1; i <= 16; i++) {
            levels.put(i, i == 1);
        }
    }

    public void startLevel1() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS QUAIVAT;");
        jdbcTemplate.execute("DROP TABLE IF EXISTS KHUVUC;");

        jdbcTemplate.execute("CREATE TABLE KHUVUC (MaKhuVuc VARCHAR(10) PRIMARY KEY, TenKhuVuc VARCHAR(255) NOT NULL);");
        jdbcTemplate.execute("CREATE TABLE QUAIVAT (MaQuai INT PRIMARY KEY, TenQuai VARCHAR(255) NOT NULL, NgayThucTinh DATE, DacTinh VARCHAR(255), MaKhuVuc VARCHAR(10), FOREIGN KEY (MaKhuVuc) REFERENCES KHUVUC(MaKhuVuc));");

        jdbcTemplate.execute("INSERT INTO KHUVUC VALUES ('Z1', 'Rung Suong Mu'), ('Z2', 'Ham Bang Gia'), ('Z3', 'Lau Dai Lua');");
        jdbcTemplate.execute("INSERT INTO QUAIVAT VALUES " +
                "(1, 'Pink Monster', '2020-01-01', 'Thich an dau tay', 'Z1')," +
                "(2, 'Owlet Bao To', '2021-05-15', 'Tao loc xoay cap 12', 'Z1')," +
                "(3, 'Dude Tron Tim', '2022-10-10', 'Tang hinh trong bong toi', 'Z2')," +
                "(4, 'Slime Doc', '2021-09-05', 'Phun axit', 'Z2')," +
                "(5, 'Rong Dat Nhi', '2019-04-17', 'Dao ham tau thoat', 'Z3');");

        this.player.setPosX(0);
        this.player.setCurrentLevel(1);
    }

    public GameStateResponse runCommand(String sql) {
        GameStateResponse response = new GameStateResponse();

        if (sql == null || sql.trim().isEmpty()) {
            response.setMessage(">> ERROR: Command cannot be empty!");
            return response;
        }

        try {
            String upperSql = sql.trim().toUpperCase();
            if (upperSql.startsWith("SELECT")) {
                List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
                response.setDataTable(result);
                response.setMessage(">> SYSTEM: Data retrieved successfully. " + result.size() + " rows.");
            } else {
                response.setMessage(">> ERROR: Only SELECT is allowed in Chapter 1.");
                return response;
            }


            int currentLv = player.getCurrentLevel();
            boolean isPassed = validateLevelLogic(currentLv, sql);

            if (isPassed) {
                int nextLv = currentLv + 1;
                levels.put(nextLv, true);
                player.setCurrentLevel(nextLv);
                response.setMessage(response.getMessage() + "\n>> SYSTEM: LEVEL CLEARED!");
            }

        } catch (Exception e) {
            response.setMessage(">> SQL ERROR: " + e.getMessage());
        }

        response.setPlayer(this.player);
        response.setLevelProgress(this.levels);
        return response;
    }


    private boolean validateLevelLogic(int level, String sql) {
        try {
            Statement stmt = CCJSqlParserUtil.parse(sql);
            if (!(stmt instanceof Select)) return false;

            PlainSelect plainSelect = (PlainSelect) ((Select) stmt).getSelectBody();
            if (!plainSelect.getFromItem().toString().equalsIgnoreCase("QUAIVAT")) return false;

            switch (level) {
                case 1:
                    return plainSelect.getSelectItems().get(0).toString().equals("*");
                case 2:
                    String columns = plainSelect.getSelectItems().toString().toUpperCase();
                    return columns.contains("TENQUAI") && columns.contains("DACTINH") && plainSelect.getSelectItems().size() == 2;
                case 3:
                    if (plainSelect.getWhere() == null) return false;
                    return plainSelect.getWhere().toString().replaceAll("\\s+", "").toUpperCase().equals("MAQUAI=3");
                case 4:
                    if (plainSelect.getWhere() == null) return false;
                    String where4 = plainSelect.getWhere().toString().toUpperCase();
                    return where4.contains("Z1") && where4.contains("AND") && where4.contains("2021");
                case 5:
                    if (plainSelect.getOrderByElements() == null || plainSelect.getLimit() == null) return false;
                    String orderBy = plainSelect.getOrderByElements().get(0).toString().toUpperCase();
                    return orderBy.contains("NGAYTHUCTINH") && !orderBy.contains("DESC") && plainSelect.getLimit().getRowCount().toString().equals("1");
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
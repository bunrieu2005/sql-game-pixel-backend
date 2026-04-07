package org.bunrieu.sqlgamepixel.service;
import org.bunrieu.sqlgamepixel.dto.GameStateResponse;
import org.bunrieu.sqlgamepixel.dto.PlayerDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class GameEngineService {
    private final JdbcTemplate jdbcTemplate;
    private PlayerDto player = new PlayerDto(); // create player
    private Map<Integer, Boolean> levels = new HashMap<>();

    public GameEngineService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        // only level 1 is unlock
        for (int i = 1; i <= 16; i++) levels.put(i, i == 1);
    }
    public GameStateResponse runCommand(String sql) {
        GameStateResponse response = new GameStateResponse();
        try {
            jdbcTemplate.execute(sql);
            response.setMessage("SUCCESS: Command executed!");
            // 2. Win check for Level 1
            Integer openDoors = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM doors WHERE status = 'open'", Integer.class);
            if (openDoors != null && openDoors > 0) {
                response.setMessage("WIN! Level 2 Unlocked!");
                levels.put(2, true); // lv2
                player.setPosX(1);
            }
        } catch (Exception e) {
            response.setMessage("SQL ERROR: " + e.getMessage());
        }
        try {
            response.setDataTable(jdbcTemplate.queryForList("SELECT * FROM doors"));
        } catch (Exception e) {
        }
        response.setPlayer(this.player);
        response.setLevelProgress(this.levels);
        return response;
    }
    // Thêm hàm này vào trong GameEngineService
    public void startLevel1() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS doors;");
        jdbcTemplate.execute("CREATE TABLE doors (id INT PRIMARY KEY, status VARCHAR(255), room_name VARCHAR(255));");
        jdbcTemplate.execute("INSERT INTO doors (id, status, room_name) VALUES (1, 'locked', 'Tan''s Room');");
        this.player.setPosX(0);
        this.player.setCurrentLevel(1);
        System.out.println("Backend: Level 1 has been initialized.");
    }
}

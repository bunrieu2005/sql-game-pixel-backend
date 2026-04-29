package org.bunrieu.sqlgamepixel.service;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.statement.select.Join;
import org.bunrieu.sqlgamepixel.dto.GameStateResponse;
import org.bunrieu.sqlgamepixel.dto.PlayerDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameEngineService {

    private final JdbcTemplate jdbcTemplate;
    private PlayerDto player = new PlayerDto();
    private int currentChapter = 1;
    private Map<Integer, Boolean> levelProgress = new LinkedHashMap<>();

    public GameEngineService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        // 16 levels: 5 per chapter (chapters 1-3), 1 for final boss
        for (int i = 1; i <= 16; i++) {
            levelProgress.put(i, i == 1);
        }
    }

    // ================= CHAPTER SETUP =================

    public void startChapter(int chapter) {
        this.currentChapter = chapter;
        switch (chapter) {
            case 1: setupChapter1(); break;
            case 2: setupChapter2(); break;
            case 3: setupChapter3(); break;
            default: setupChapter1(); break;
        }
    }

    // CHAPTER 1: QUAIVAT — SELECT, WHERE, AND/OR, LIKE, ORDER BY
    private void setupChapter1() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS QUAIVAT;");
        jdbcTemplate.execute("DROP TABLE IF EXISTS KHUVUC;");

        jdbcTemplate.execute("""
            CREATE TABLE KHUVUC (
                MaKhuVuc VARCHAR(10) PRIMARY KEY,
                TenKhuVuc VARCHAR(255) NOT NULL,
                CapDo INT DEFAULT 1
            )
        """);
        jdbcTemplate.execute("""
            CREATE TABLE QUAIVAT (
                MaQuai INT PRIMARY KEY,
                TenQuai VARCHAR(255) NOT NULL,
                NgayThucTinh DATE,
                DacTinh VARCHAR(255),
                CapDo INT,
                MaKhuVuc VARCHAR(10),
                FOREIGN KEY (MaKhuVuc) REFERENCES KHUVUC(MaKhuVuc)
            )
        """);

        jdbcTemplate.execute("INSERT INTO KHUVUC VALUES ('Z1', 'Rung Suong Mu', 1), ('Z2', 'Ham Bang Gia', 2), ('Z3', 'Lau Dai Lua', 3), ('Z4', 'Thung Lung Giet', 2), ('Z5', 'Dao Xam Phu', 1);");
        jdbcTemplate.execute("""
            INSERT INTO QUAIVAT VALUES
            (1, 'Pink Monster',  '2020-01-01', 'Thich an dau tay', 3, 'Z1'),
            (2, 'Owlet Bao To',  '2021-05-15', 'Tao loc xoay cap 12', 4, 'Z1'),
            (3, 'Dude Tron Tim', '2022-10-10', 'Tang hinh trong bong toi', 5, 'Z2'),
            (4, 'Slime Doc',     '2021-09-05', 'Phun axit axit', 2, 'Z2'),
            (5, 'Rong Dat Nhi',  '2019-04-17', 'Dao ham tau thoat', 6, 'Z3'),
            (6, 'Ma Dien Chay',  '2020-07-20', 'Toc do dien chieu', 3, 'Z4'),
            (7, 'Quy Than Nhan', '2023-01-01', 'Hut mau nguoi choi', 4, 'Z3'),
            (8, 'Gau Non Ngu',   '2018-11-30', 'Ngu say that suot', 1, 'Z5'),
            (9, 'Khi To Nhan',  '2022-03-15', 'Luc vat kim cuong', 5, 'Z4'),
            (10,'Rong Lua Phu',  '2021-12-25', 'Thoi lua bang than', 7, 'Z3')
        """);

        player.setPosX(0);
        player.setCurrentLevel(1);
    }

    // CHAPTER 2: SINHVIEN + MONHOC — ORDER BY, LIMIT, OFFSET, DISTINCT
    private void setupChapter2() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS SINHVIEN;");
        jdbcTemplate.execute("DROP TABLE IF EXISTS MONHOC;");

        jdbcTemplate.execute("""
            CREATE TABLE MONHOC (
                MaMon VARCHAR(10) PRIMARY KEY,
                TenMon VARCHAR(255) NOT NULL,
                SoTinChi INT,
                Khoa VARCHAR(100)
            )
        """);
        jdbcTemplate.execute("""
            CREATE TABLE SINHVIEN (
                MaSV VARCHAR(10) PRIMARY KEY,
                HoTen VARCHAR(255) NOT NULL,
                NgaySinh DATE,
                GioiTinh VARCHAR(10),
                DiemTB FLOAT,
                MaMon VARCHAR(10),
                FOREIGN KEY (MaMon) REFERENCES MONHOC(MaMon)
            )
        """);

        jdbcTemplate.execute("INSERT INTO MONHOC VALUES ('CS101', 'Co So Du Lieu', 3, 'CNTT'), ('MA101', 'Toan Roi Rac', 4, 'CNTT'), ('EN101', 'Tieng Anh', 3, 'Ngoai Ngu'), ('PH101', 'Vat Ly Dai Cuong', 4, 'Khoa Hoc'), ('TH101', 'Tin Hoc Co So', 3, 'CNTT');");
        jdbcTemplate.execute("""
            INSERT INTO SINHVIEN VALUES
            ('SV001', 'Nguyen Van A',   '2003-05-10', 'Nam',   8.5, 'CS101'),
            ('SV002', 'Tran Thi B',     '2002-08-20', 'Nu',    9.0, 'MA101'),
            ('SV003', 'Le Van C',       '2004-01-15', 'Nam',   6.5, 'CS101'),
            ('SV004', 'Pham Thi D',     '2003-11-30', 'Nu',    7.5, 'EN101'),
            ('SV005', 'Hoang Van E',    '2002-04-05', 'Nam',   8.0, 'PH101'),
            ('SV006', 'Nguyen Thi F',   '2004-07-22', 'Nu',    5.5, 'MA101'),
            ('SV007', 'Tran Van G',     '2003-09-18', 'Nam',   7.0, 'TH101'),
            ('SV008', 'Le Thi H',       '2002-12-01', 'Nu',    9.5, 'CS101'),
            ('SV009', 'Pham Van I',     '2004-03-25', 'Nam',   6.0, 'EN101'),
            ('SV010','Dinh Thi K',      '2003-06-14', 'Nu',    8.2, 'PH101'),
            ('SV011', 'Bui Van L',      '2002-10-08', 'Nam',   7.8, 'MA101'),
            ('SV012', 'Doan Thi M',     '2004-02-28', 'Nu',    4.5, 'TH101')
        """);

        player.setPosX(0);
        player.setCurrentLevel(6);
    }

    // CHAPTER 3: SANPHAM + LOAISANPHAM — COUNT, SUM, AVG, MAX, MIN, GROUP BY, HAVING
    private void setupChapter3() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS SANPHAM;");
        jdbcTemplate.execute("DROP TABLE IF EXISTS LOAISANPHAM;");

        jdbcTemplate.execute("""
            CREATE TABLE LOAISANPHAM (
                MaLoai VARCHAR(10) PRIMARY KEY,
                TenLoai VARCHAR(255) NOT NULL,
                MoTa VARCHAR(500)
            )
        """);
        jdbcTemplate.execute("""
            CREATE TABLE SANPHAM (
                MaSP VARCHAR(10) PRIMARY KEY,
                TenSP VARCHAR(255) NOT NULL,
                Gia INT,
                SoLuongTon INT,
                XuatXu VARCHAR(100),
                MaLoai VARCHAR(10),
                FOREIGN KEY (MaLoai) REFERENCES LOAISANPHAM(MaLoai)
            )
        """);

        jdbcTemplate.execute("INSERT INTO LOAISANPHAM VALUES ('DT', 'Dien Thoai', 'Cac loai dien thoai di dong'), ('LT', 'Laptop', 'May tinh xach tay'), ('PK', 'Phu Kien', 'Phu kien cong nghe'), ('TM', 'Thiet Bi Mang', 'Router, switch'), ('TV', 'Tu Vi', 'Do dien tu gia dung');");
        jdbcTemplate.execute("""
            INSERT INTO SANPHAM VALUES
            ('SP001', 'iPhone 15',       22000000, 50,  'My',     'DT'),
            ('SP002', 'Samsung Galaxy',   18000000, 30,  'Han Quoc','DT'),
            ('SP003', 'MacBook Air',      28000000, 20,  'My',     'LT'),
            ('SP004', 'Dell XPS 13',      25000000, 15,  'My',     'LT'),
            ('SP005', 'AirPods Pro',      5500000,  80,  'My',     'PK'),
            ('SP006', 'Sac nhanh Anker',  450000,   200, 'Trung Quoc','PK'),
            ('SP007', 'TP-Link Router',   800000,   60,  'Trung Quoc','TM'),
            ('SP008', 'iPad Air',         15000000, 25,  'My',     'TM'),
            ('SP009', 'Sony WH-1000XM5', 7000000,  40,  'Nhat Ban','PK'),
            ('SP010', 'Asus ROG',         35000000, 10,  'Trung Quoc','LT'),
            ('SP011', 'Oppo Reno',        12000000, 45,  'Trung Quoc','DT'),
            ('SP012', 'Router Xiaomi',    600000,   90,  'Trung Quoc','TM'),
            ('SP013', 'Ban phim co',      1200000,  120, 'Trung Quoc','PK'),
            ('SP014', 'Man hinh LG 27"',  9000000,  35,  'Han Quoc','TM'),
            ('SP015', 'Dien thoai Nokia', 1500000,  10,  'Phan Lan','DT')
        """);

        player.setPosX(0);
        player.setCurrentLevel(11);
    }

    // ================= GAME STATE =================

    public GameStateResponse getGameState() {
        GameStateResponse res = new GameStateResponse();
        res.setPlayer(this.player);
        res.setLevelProgress(toList(this.levelProgress));
        res.setMessage(">> SYSTEM: Game state retrieved.");
        return res;
    }

    // ================= SQL EXECUTION =================

    public GameStateResponse runCommand(String sql) {
        GameStateResponse response = new GameStateResponse();

        if (sql == null || sql.trim().isEmpty()) {
            response.setMessage(">> ERROR: Command cannot be empty!");
            return finishResponse(response);
        }

        try {
            String upperSql = sql.trim().toUpperCase();

            if (upperSql.startsWith("SELECT")) {
                List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
                response.setDataTable(result);
                response.setMessage(">> SYSTEM: Data retrieved successfully. " + result.size() + " rows returned.");
            } else {
                // INSERT/UPDATE/DELETE — execute and report
                int affected = jdbcTemplate.update(sql);
                response.setMessage(">> SYSTEM: " + affected + " row(s) affected.");
            }

            // Validate level
            int currentLv = player.getCurrentLevel();
            int chapter = getChapterForLevel(currentLv);

            boolean isPassed = validateLevelLogic(currentLv, sql, chapter);

            if (isPassed) {
                int nextLv = currentLv + 1;
                levelProgress.put(nextLv, true);
                player.setCurrentLevel(nextLv);

                if (nextLv <= 16) {
                    response.setMessage(response.getMessage() + "\n>> SYSTEM: LEVEL CLEARED! Advancing to Level " + nextLv + "...");
                } else {
                    response.setMessage(response.getMessage() + "\n>> SYSTEM: CONGRATULATIONS! You have mastered all chapters!");
                }
            }

        } catch (Exception e) {
            String msg = e.getMessage() != null ? e.getMessage() : "Unknown error";
            if (msg.length() > 120) msg = msg.substring(0, 120);
            response.setMessage(">> SQL ERROR: " + msg);
        }

        return finishResponse(response);
    }

    private GameStateResponse finishResponse(GameStateResponse response) {
        response.setPlayer(this.player);
        response.setLevelProgress(toList(this.levelProgress));
        return response;
    }

    // ================= LEVEL VALIDATION =================

    private boolean validateLevelLogic(int level, String sql, int chapter) {
        try {
            Statement stmt = CCJSqlParserUtil.parse(sql);
            String upperSql = sql.trim().toUpperCase();

            if (chapter == 1) return validateChapter1(level, stmt, upperSql);
            if (chapter == 2) return validateChapter2(level, stmt, upperSql);
            if (chapter == 3) return validateChapter3(level, stmt, upperSql);

            return false;
        } catch (JSQLParserException e) {
            return false;
        }
    }

    // ---- Chapter 1: QUAIVAT table ----
    private boolean validateChapter1(int level, Statement stmt, String upperSql) {
        if (!(stmt instanceof Select)) return false;
        PlainSelect ps = getPlainSelect((Select) stmt);
        if (ps == null) return false;
        String from = ps.getFromItem().toString().toUpperCase().replaceAll("\\s+", "");

        switch (level) {
            case 1: // SELECT * FROM QUAIVAT
                return from.equals("QUAIVAT") && ps.getSelectItems().stream()
                        .anyMatch(s -> s.toString().equalsIgnoreCase("*"));
            case 2: // SELECT TenQuai, DacTinh FROM QUAIVAT
                if (!from.equals("QUAIVAT")) return false;
                String cols = ps.getSelectItems().toString().toUpperCase().replaceAll("\\s+", "");
                return cols.contains("TENQUAI") && cols.contains("DACTINH") && ps.getSelectItems().size() == 2;
            case 3: // WHERE MaQuai = 3
                if (!from.equals("QUAIVAT")) return false;
                if (ps.getWhere() == null) return false;
                String where3 = ps.getWhere().toString().toUpperCase().replaceAll("[\\s'\"`]", "");
                return where3.contains("MAQUAI=3") || where3.contains("MAQUAI = 3");
            case 4: // WHERE MaKhuVuc = 'Z1' AND NgayThucTinh > '2021-01-01'
                if (!from.equals("QUAIVAT")) return false;
                if (ps.getWhere() == null) return false;
                String where4 = ps.getWhere().toString().toUpperCase().replaceAll("[\\s'\"`]", "");
                return where4.contains("Z1") && (where4.contains("AND") || where4.contains("&&"));
            case 5: // ORDER BY NgayThucTinh ASC LIMIT 1
                if (!from.equals("QUAIVAT")) return false;
                if (ps.getOrderByElements() == null || ps.getLimit() == null) return false;
                String orderBy = ps.getOrderByElements().get(0).toString().toUpperCase();
                boolean hasAsc = !orderBy.contains("DESC");
                boolean hasDateCol = orderBy.contains("NGAYTHUCTINH") || orderBy.contains("NGAY");
                boolean hasLimit1 = ps.getLimit().getRowCount() != null
                        && ps.getLimit().getRowCount().toString().equals("1");
                return hasDateCol && hasAsc && hasLimit1;
            default:
                return false;
        }
    }

    // ---- Chapter 2: SINHVIEN + MONHOC table ----
    private boolean validateChapter2(int level, Statement stmt, String upperSql) {
        if (!(stmt instanceof Select)) return false;
        PlainSelect ps = getPlainSelect((Select) stmt);
        if (ps == null) return false;
        String from = ps.getFromItem().toString().toUpperCase().replaceAll("\\s+", "");

        switch (level) {
            case 6: // ORDER BY DiemTB DESC LIMIT 1 → top student
                if (!from.equals("SINHVIEN")) return false;
                if (ps.getOrderByElements() == null || ps.getLimit() == null) return false;
                String order6 = ps.getOrderByElements().get(0).toString().toUpperCase();
                return order6.contains("DIEMTB") && order6.contains("DESC")
                        && ps.getLimit().getRowCount() != null
                        && ps.getLimit().getRowCount().toString().equals("1");
            case 7: // ORDER BY DiemTB ASC LIMIT 1 → lowest student
                if (!from.equals("SINHVIEN")) return false;
                if (ps.getOrderByElements() == null || ps.getLimit() == null) return false;
                String order7 = ps.getOrderByElements().get(0).toString().toUpperCase();
                return order7.contains("DIEMTB") && !order7.contains("DESC");
            case 8: // DISTINCT GioiTinh
                if (!from.equals("SINHVIEN")) return false;
                String cols8 = ps.getSelectItems().toString().toUpperCase();
                return cols8.contains("DISTINCT") && cols8.contains("GIOITINH");
            case 9: // LIMIT 3 OFFSET 2  (skip 2, take 3)
                if (!from.equals("SINHVIEN")) return false;
                if (ps.getLimit() == null) return false;
                if (ps.getLimit().getOffset() == null) return false;
                String limit9 = ps.getLimit().getRowCount().toString();
                String offset9 = ps.getLimit().getOffset().toString();
                return limit9.equals("3") && offset9.equals("2");
            case 10: { // Complex: WHERE + ORDER BY + LIMIT
                if (!from.equals("SINHVIEN")) return false;
                if (ps.getWhere() == null) return false;
                if (ps.getOrderByElements() == null || ps.getLimit() == null) return false;
                String where10 = ps.getWhere().toString().toUpperCase();
                String order10 = ps.getOrderByElements().get(0).toString().toUpperCase();
                return where10.contains("DIEMTB") && order10.contains("DIEMTB")
                        && !order10.contains("DESC");
            }
            default:
                return false;
        }
    }

    // ---- Chapter 3: SANPHAM + LOAISANPHAM table ----
    private boolean validateChapter3(int level, Statement stmt, String upperSql) {
        if (!(stmt instanceof Select)) return false;
        PlainSelect ps = getPlainSelect((Select) stmt);
        if (ps == null) return false;
        String from = ps.getFromItem().toString().toUpperCase().replaceAll("\\s+", "");

        switch (level) {
            case 11: // COUNT(*)
                if (!from.equals("SANPHAM")) return false;
                String cols11 = ps.getSelectItems().toString().toUpperCase();
                return cols11.contains("COUNT") || cols11.contains("COUNT(*)");
            case 12: // SUM(Gia) or AVG(Gia)
                if (!from.equals("SANPHAM")) return false;
                String cols12 = ps.getSelectItems().toString().toUpperCase();
                return cols12.contains("SUM") || cols12.contains("AVG");
            case 13: // MAX(Gia) or MIN(Gia)
                if (!from.equals("SANPHAM")) return false;
                String cols13 = ps.getSelectItems().toString().toUpperCase();
                return cols13.contains("MAX") || cols13.contains("MIN");
            case 14: // GROUP BY MaLoai
                if (!from.equals("SANPHAM")) return false;
                if (ps.getGroupBy() == null) return false;
                String group14 = ps.getGroupBy().toString().toUpperCase();
                return group14.contains("MALOAI");
            case 15: // GROUP BY + HAVING COUNT(*)
                if (!from.equals("SANPHAM")) return false;
                if (ps.getGroupBy() == null) return false;
                if (ps.getHaving() == null) return false;
                String having15 = ps.getHaving().toString().toUpperCase();
                return having15.contains("COUNT");
            case 16: // JOIN LOAISANPHAM
                if (ps.getJoins() == null || ps.getJoins().isEmpty()) return false;
                Join join = ps.getJoins().get(0);
                String joinStr = join.toString().toUpperCase();
                return joinStr.contains("LOAISANPHAM") || joinStr.contains("LOAI");
            default:
                return false;
        }
    }

    // ================= HELPERS =================

    private PlainSelect getPlainSelect(Select select) {
        SelectBody body = select.getSelectBody();
        if (body instanceof PlainSelect) return (PlainSelect) body;
        if (body instanceof SetOperationList) {
            List<SelectBody> selects = ((SetOperationList) body).getSelects();
            if (!selects.isEmpty() && selects.get(0) instanceof PlainSelect) {
                return (PlainSelect) selects.get(0);
            }
        }
        return null;
    }

    private int getChapterForLevel(int level) {
        if (level <= 5) return 1;
        if (level <= 10) return 2;
        if (level <= 16) return 3;
        return 1;
    }

    private List<Map<String, Boolean>> toList(Map<Integer, Boolean> map) {
        List<Map<String, Boolean>> list = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> e : map.entrySet()) {
            Map<String, Boolean> m = new LinkedHashMap<>();
            m.put("level_" + e.getKey(), e.getValue());
            list.add(m);
        }
        return list;
    }
}

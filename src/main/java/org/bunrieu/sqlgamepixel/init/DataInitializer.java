package org.bunrieu.sqlgamepixel.init;

import org.bunrieu.sqlgamepixel.entity.Achievement;
import org.bunrieu.sqlgamepixel.entity.Character;
import org.bunrieu.sqlgamepixel.entity.DailyQuest;
import org.bunrieu.sqlgamepixel.entity.Item;
import org.bunrieu.sqlgamepixel.entity.Skin;
import org.bunrieu.sqlgamepixel.repository.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Order(1)
public class DataInitializer implements ApplicationRunner {

    private final CharacterRepository characterRepository;
    private final SkinRepository skinRepository;
    private final ItemRepository itemRepository;
    private final DailyQuestRepository dailyQuestRepository;
    private final AchievementRepository achievementRepository;

    public DataInitializer(
            CharacterRepository characterRepository,
            SkinRepository skinRepository,
            ItemRepository itemRepository,
            DailyQuestRepository dailyQuestRepository,
            AchievementRepository achievementRepository) {
        this.characterRepository = characterRepository;
        this.skinRepository = skinRepository;
        this.itemRepository = itemRepository;
        this.dailyQuestRepository = dailyQuestRepository;
        this.achievementRepository = achievementRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        initCharacters();
        initSkins();
        initItems();
        initDailyQuests();
        initAchievements();
    }

    private void initCharacters() {
        if (characterRepository.count() > 0) return;

        List.of(
            char_("warrior",   "Chiến Binh",  "fire",      "Lửa",       "Trọng kiếm",
                "Đại diện cho sức mạnh và thể lực phi thường. Thuộc tính lửa mang lại sát thương cao.",
                "SỨC MẠNH PHI THƯỜNG",   100, 30, 85, 70, 40, 15, "#FF4500", 0),
            char_("assassin",  "Thích Khách", "poison",    "Độc tố",    "Đoản kiếm",
                "Kết hợp giữa sức mạnh và sự khéo léo trong chiến đấu. Tấn công nhanh, tích lũy độc tố.",
                "BÓNG TỐI CHẾT CHÓC",     70, 40, 75, 40, 90, 35, "#32CD32", 500),
            char_("mage",      "Pháp Sư",    "ice",       "Băng giá",  "Gậy phép",
                "Đại diện cho sức mạnh tinh thần và khả năng chống chọi với bão tố. Phép thuật đa dạng.",
                "PHÁP THUẬT THƯỢNG ĐỈNH", 65, 60, 90, 35, 55, 20, "#00BFFF", 750),
            char_("marksman",  "Xạ Thủ",     "lightning", "Sấm sét",   "Súng trường",
                "Vận dụng giữa sức mạnh tinh thần và sự khéo léo. Tấn công từ xa với độ chính xác cao.",
                "BẮN TỈA VÔ ĐỊCH",         60, 35, 95, 30, 85, 25, "#FFD700", 1000)
        ).forEach(characterRepository::save);
    }

    private void initSkins() {
        if (skinRepository.count() > 0) return;

        List.of(
            skin("warrior_s1",  "warrior",  "Áo Choàng Đỏ", "#CC2200", 200, "Áo choàng lửa rực rỡ."),
            skin("warrior_s2",  "warrior",  "Giáp Vàng",    "#FFD700", 400, "Giáp kim loại sáng chói."),
            skin("assassin_s1", "assassin", "Bóng Đêm",     "#1A1A2E", 200, "Kẻ săn mồi trong bóng tối."),
            skin("assassin_s2", "assassin", "Áo Choàng Đỏ", "#8B0000", 400, "Sát thủ đỏ thẫm."),
            skin("mage_s1",     "mage",     "Áo Phù Thủy",  "#6A0DAD", 200, "Hào quang ma thuật tím."),
            skin("mage_s2",     "mage",     "Băng Giới",    "#ADD8E6", 400, "Ma thuật băng giá."),
            skin("marksman_s1", "marksman", "Săn Lùi",      "#228B22", 200, "Dân săn chuyên nghiệp."),
            skin("marksman_s2", "marksman", "Thợ Săn",       "#D2691E", 400, "Gille tiêu diệt quái vật.")
        ).forEach(skinRepository::save);
    }

    private void initItems() {
        if (itemRepository.count() > 0) return;

        List.of(
            item("potion_hp",   "Bình Máu",     "HP",   50,  100, "Hồi 50 HP ngay lập tức."),
            item("potion_mp",   "Bình Mana",    "MP",   30,  100, "Hồi 30 MP ngay lập tức."),
            item("atk_boost",   "Thuốc Tăng ATK","ATK", 10,  200, "Tăng 10 ATK trong 1 trận."),
            item("def_boost",   "Thuốc Tăng DEF","DEF", 10,  200, "Tăng 10 DEF trong 1 trận."),
            item("bomb",         "Bom Phá",     "BOMB",  1,  300, "Gây 100 sát thương lên tất cả kẻ địch."),
            item("full_restore", "Thần Lực",    "FULL",  0,  500, "Hồi đầy HP và MP.")
        ).forEach(itemRepository::save);
    }

    private void initDailyQuests() {
        if (dailyQuestRepository.count() > 0) return;

        List.of(
            dq("dq1", "Tân Binh",    "Hoàn thành 1 level",  1, 100, 1),
            dq("dq2", "Chiến Binh",  "Hoàn thành 3 level",  3, 300, 2),
            dq("dq3", "Chiến Tướng", "Hoàn thành 5 level",  5, 500, 3)
        ).forEach(dailyQuestRepository::save);
    }

    private void initAchievements() {
        if (achievementRepository.count() > 0) return;

        List.of(
            ach("ach_c1",   "Chương 1 Hoàn Thành", "Hoàn thành Chương 1",        "chapter",  200, "LEVEL_COMPLETE",  5),
            ach("ach_c2",   "Chương 2 Hoàn Thành", "Hoàn thành Chương 2",        "chapter",  200, "LEVEL_COMPLETE",  10),
            ach("ach_c3",   "Chương 3 Hoàn Thành", "Hoàn thành Chương 3",        "chapter",  500, "LEVEL_COMPLETE",  16),
            ach("ach_all",  "Bậc Thầy SQL",        "Hoàn thành tất cả 16 level", "crown",   1000, "LEVEL_COMPLETE",  16),
            ach("ach_char3","Sư Tử Đoàn Kết",       "Sở hữu 3 nhân vật",          "team",     300, "CHARACTERS_OWNED", 3),
            ach("ach_char4","Bộ Tứ Chiến Binh",     "Sở hữu tất cả 4 nhân vật",  "team",     500, "CHARACTERS_OWNED", 4),
            ach("ach_100g", "Ngân Hàng Nhỏ",        "Tích lũy 1000 gold",         "gold",     100, "TOTAL_GOLD",      1000),
            ach("ach_500g", "Đại Gia",              "Tích lũy 5000 gold",          "gold",     200, "TOTAL_GOLD",      5000),
            ach("ach_skin3","Nhà Thiết Kế",           "Sở hữu 5 skin",               "skin",     200, "SKINS_OWNED",     5),
            ach("ach_first","Khởi Đầu",              "Hoàn thành level đầu tiên",    "star",     50,  "LEVEL_COMPLETE",  1)
        ).forEach(achievementRepository::save);
    }

    // Factory methods
    private static Character char_(String id, String name, String element, String elLabel,
                                   String weapon, String desc, String classLabel,
                                   int hp, int mp, int atk, int def, int speed, int crit, String color, int price) {
        Character c = new Character();
        c.setId(id); c.setName(name); c.setElement(element); c.setElementLabel(elLabel);
        c.setWeapon(weapon); c.setDescription(desc); c.setClassLabel(classLabel);
        c.setBaseHp(hp); c.setBaseMp(mp); c.setBaseAtk(atk); c.setBaseDef(def);
        c.setBaseSpeed(speed); c.setBaseCrit(crit); c.setAvatarColor(color); c.setPrice(price);
        return c;
    }

    private static Skin skin(String id, String charId, String name, String color, int price, String desc) {
        Skin s = new Skin();
        s.setId(id); s.setCharacterId(charId); s.setName(name);
        s.setColor(color); s.setPrice(price); s.setDescription(desc);
        return s;
    }

    private static Item item(String id, String name, String effectType, int effectValue, int price, String desc) {
        Item i = new Item();
        i.setId(id); i.setName(name); i.setEffectType(effectType);
        i.setEffectValue(effectValue); i.setPrice(price); i.setDescription(desc);
        return i;
    }

    private static DailyQuest dq(String id, String title, String desc, int target, int reward, int sort) {
        DailyQuest q = new DailyQuest();
        q.setId(id); q.setTitle(title); q.setDescription(desc);
        q.setTarget(target); q.setGoldReward(reward); q.setSortOrder(sort);
        return q;
    }

    private static Achievement ach(String id, String name, String desc, String icon, int reward,
            String condType, int condValue) {
        Achievement a = new Achievement();
        a.setId(id); a.setName(name); a.setDescription(desc); a.setIcon(icon);
        a.setGoldReward(reward); a.setConditionType(condType); a.setConditionValue(condValue);
        return a;
    }
}

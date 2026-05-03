package org.bunrieu.sqlgamepixel.service;

import org.bunrieu.sqlgamepixel.dto.*;
import org.bunrieu.sqlgamepixel.entity.Character;
import org.bunrieu.sqlgamepixel.entity.OwnedCharacter;
import org.bunrieu.sqlgamepixel.entity.OwnedSkin;
import org.bunrieu.sqlgamepixel.entity.Player;
import org.bunrieu.sqlgamepixel.entity.Skin;
import org.bunrieu.sqlgamepixel.entity.Item;
import org.bunrieu.sqlgamepixel.entity.PlayerItem;
import org.bunrieu.sqlgamepixel.entity.GoldTransaction;
import org.bunrieu.sqlgamepixel.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ShopService {

    private final PlayerRepository playerRepository;
    private final GoldTransactionRepository goldTransactionRepository;
    private final CharacterRepository characterRepository;
    private final OwnedCharacterRepository ownedCharacterRepository;
    private final SkinRepository skinRepository;
    private final OwnedSkinRepository ownedSkinRepository;
    private final ItemRepository itemRepository;
    private final PlayerItemRepository playerItemRepository;

    public ShopService(
            PlayerRepository playerRepository,
            GoldTransactionRepository goldTransactionRepository,
            CharacterRepository characterRepository,
            OwnedCharacterRepository ownedCharacterRepository,
            SkinRepository skinRepository,
            OwnedSkinRepository ownedSkinRepository,
            ItemRepository itemRepository,
            PlayerItemRepository playerItemRepository) {
        this.playerRepository = playerRepository;
        this.goldTransactionRepository = goldTransactionRepository;
        this.characterRepository = characterRepository;
        this.ownedCharacterRepository = ownedCharacterRepository;
        this.skinRepository = skinRepository;
        this.ownedSkinRepository = ownedSkinRepository;
        this.itemRepository = itemRepository;
        this.playerItemRepository = playerItemRepository;
    }

    // ── Shop Characters ─────────────────────────────────────────────────────

    public List<ShopCharacterDto> getShopCharacters(String username) {
        int playerId = playerRepository.findByUsername(username)
            .map(Player::getId).orElse(-1);

        List<Character> characters = characterRepository.findAllOrdered();
        List<OwnedCharacter> owned = playerId > 0
            ? ownedCharacterRepository.findByPlayerId(playerId) : List.of();

        Set<String> ownedIds = new HashSet<>();
        Map<String, String> equippedSkin = new HashMap<>();
        for (OwnedCharacter oc : owned) {
            ownedIds.add(oc.getCharacterId());
            if (oc.getEquippedSkinId() != null) {
                equippedSkin.put(oc.getCharacterId(), oc.getEquippedSkinId());
            }
        }

        List<ShopCharacterDto> result = new ArrayList<>();
        for (Character c : characters) {
            ShopCharacterDto sc = toShopCharacterDto(c);
            sc.setOwned(ownedIds.contains(c.getId()));
            sc.setEquippedSkinId(equippedSkin.get(c.getId()));
            result.add(sc);
        }
        return result;
    }

    @Transactional
    public String buyCharacter(String username, String characterId) {
        Player player = playerRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Player not found"));

        if (ownedCharacterRepository.findByPlayerIdAndCharacterId(player.getId(), characterId).isPresent()) {
            return "Bạn đã sở hữu nhân vật này rồi!";
        }

        int price = characterRepository.findById(characterId)
            .map(Character::getPrice).orElse(0);

        if (price > 0 && player.getGold() < price) {
            return "Vàng không đủ! Bạn cần " + price + " gold.";
        }

        if (price > 0) {
            player.setGold(player.getGold() - price);
            playerRepository.save(player);
            logTransaction(player.getId(), -price, "Buy character: " + characterId);
        }

        OwnedCharacter oc = new OwnedCharacter();
        oc.setPlayerId(player.getId());
        oc.setCharacterId(characterId);
        ownedCharacterRepository.save(oc);

        return "Mua nhân vật thành công!";
    }

    // ── Skins ──────────────────────────────────────────────────────────────

    public List<SkinDto> getSkinsForCharacter(String characterId) {
        return skinRepository.findByCharacterId(characterId).stream()
            .map(this::toSkinDto)
            .toList();
    }

    public List<SkinDto> getOwnedSkins(String username) {
        int playerId = playerRepository.findByUsername(username)
            .map(Player::getId).orElse(-1);
        if (playerId < 0) return List.of();
        return ownedSkinRepository.findByPlayerId(playerId).stream()
            .map(os -> skinRepository.findById(os.getSkinId()).orElse(null))
            .filter(Objects::nonNull)
            .map(this::toSkinDto)
            .toList();
    }

    @Transactional
    public String buySkin(String username, String skinId) {
        Player player = playerRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Player not found"));

        Skin skin = skinRepository.findById(skinId)
            .orElseThrow(() -> new RuntimeException("Skin not found"));

        if (ownedCharacterRepository.findByPlayerIdAndCharacterId(player.getId(), skin.getCharacterId()).isEmpty()) {
            return "Bạn cần sở hữu nhân vật này trước!";
        }

        if (ownedSkinRepository.findByPlayerIdAndSkinId(player.getId(), skinId).isPresent()) {
            return "Bạn đã sở hữu skin này rồi!";
        }

        int price = skin.getPrice();
        if (price > 0 && player.getGold() < price) {
            return "Vàng không đủ! Bạn cần " + price + " gold.";
        }

        if (price > 0) {
            player.setGold(player.getGold() - price);
            playerRepository.save(player);
            logTransaction(player.getId(), -price, "Buy skin: " + skinId);
        }

        OwnedSkin os = new OwnedSkin();
        os.setPlayerId(player.getId());
        os.setSkinId(skinId);
        ownedSkinRepository.save(os);

        return "Mua skin thành công!";
    }

    @Transactional
    public String equipSkin(String username, String characterId, String skinId) {
        int playerId = playerRepository.findByUsername(username)
            .map(Player::getId).orElse(-1);

        if (ownedSkinRepository.findByPlayerIdAndSkinId(playerId, skinId).isEmpty()) {
            return "Bạn chưa sở hữu skin này!";
        }

        ownedCharacterRepository.findByPlayerIdAndCharacterId(playerId, characterId)
            .ifPresent(oc -> {
                oc.setEquippedSkinId(skinId);
                ownedCharacterRepository.save(oc);
            });

        return "Trang bị skin thành công!";
    }

    // ── Items ──────────────────────────────────────────────────────────────

    public List<Item> getShopItems() {
        return itemRepository.findAll();
    }

    @Transactional
    public String buyItem(String username, String itemId, int quantity) {
        Player player = playerRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Player not found"));

        Item item = itemRepository.findById(itemId)
            .orElse(null);
        if (item == null) return "Vật phẩm không tồn tại!";

        int totalPrice = item.getPrice() * quantity;
        if (player.getGold() < totalPrice) {
            return "Vàng không đủ! Bạn cần " + totalPrice + " gold.";
        }

        player.setGold(player.getGold() - totalPrice);
        playerRepository.save(player);
        logTransaction(player.getId(), -totalPrice, "Buy item: " + itemId + " x" + quantity);

        PlayerItem pi = playerItemRepository.findByPlayerIdAndItemId(player.getId(), itemId)
            .orElseGet(() -> {
                PlayerItem newPi = new PlayerItem();
                newPi.setPlayerId(player.getId());
                newPi.setItemId(itemId);
                newPi.setQuantity(0);
                return newPi;
            });
        pi.setQuantity(pi.getQuantity() + quantity);
        playerItemRepository.save(pi);

        return "Mua vật phẩm thành công!";
    }

    // ── Inventory ─────────────────────────────────────────────────────────

    public InventoryDto getInventory(String username) {
        int playerId = playerRepository.findByUsername(username)
            .map(Player::getId).orElse(-1);
        if (playerId < 0) return new InventoryDto();

        InventoryDto inv = new InventoryDto();

        List<String> chars = ownedCharacterRepository.findByPlayerId(playerId).stream()
            .map(OwnedCharacter::getCharacterId).toList();
        inv.setOwnedCharacterIds(chars);

        List<String> skins = ownedSkinRepository.findByPlayerId(playerId).stream()
            .map(OwnedSkin::getSkinId).toList();
        inv.setOwnedSkinIds(skins);

        Map<String, Integer> items = new LinkedHashMap<>();
        for (PlayerItem pi : playerItemRepository.findByPlayerId(playerId)) {
            if (pi.getQuantity() > 0) {
                items.put(pi.getItemId(), pi.getQuantity());
            }
        }
        inv.setItems(items);

        return inv;
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    private void logTransaction(int playerId, int amount, String reason) {
        GoldTransaction tx = new GoldTransaction();
        tx.setPlayerId(playerId);
        tx.setAmount(amount);
        tx.setReason(reason);
        goldTransactionRepository.save(tx);
    }

    private ShopCharacterDto toShopCharacterDto(Character c) {
        ShopCharacterDto dto = new ShopCharacterDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setElement(c.getElement());
        dto.setElementLabel(c.getElementLabel());
        dto.setWeapon(c.getWeapon());
        dto.setDescription(c.getDescription());
        dto.setClassLabel(c.getClassLabel());
        dto.setAvatarColor(c.getAvatarColor());
        dto.setPrice(c.getPrice());

        CharacterDto.StatsDto stats = new CharacterDto.StatsDto();
        stats.setHp(c.getBaseHp());
        stats.setMp(c.getBaseMp());
        stats.setAtk(c.getBaseAtk());
        stats.setDef(c.getBaseDef());
        stats.setSpeed(c.getBaseSpeed());
        stats.setCrit(c.getBaseCrit());
        dto.setStats(stats);
        return dto;
    }

    private SkinDto toSkinDto(Skin s) {
        SkinDto dto = new SkinDto();
        dto.setId(s.getId());
        dto.setCharacterId(s.getCharacterId());
        dto.setName(s.getName());
        dto.setColor(s.getColor());
        dto.setPrice(s.getPrice());
        dto.setDescription(s.getDescription());
        return dto;
    }
}

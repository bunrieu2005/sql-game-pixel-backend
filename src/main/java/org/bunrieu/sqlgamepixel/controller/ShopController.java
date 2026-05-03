package org.bunrieu.sqlgamepixel.controller;

import org.bunrieu.sqlgamepixel.dto.*;
import org.bunrieu.sqlgamepixel.entity.Item;
import org.bunrieu.sqlgamepixel.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/shop")
@CrossOrigin(origins = "http://localhost:4200")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/characters")
    public List<ShopCharacterDto> getCharacters(
        @RequestParam(defaultValue = "player1") String username
    ) {
        return shopService.getShopCharacters(username);
    }

    @PostMapping("/buy-character")
    public Map<String, Object> buyCharacter(
        @RequestParam(defaultValue = "player1") String username,
        @RequestBody Map<String, String> body
    ) {
        String result = shopService.buyCharacter(username, body.get("characterId"));
        return Map.of("message", result);
    }

    @GetMapping("/skins/{characterId}")
    public List<SkinDto> getSkins(@PathVariable String characterId) {
        return shopService.getSkinsForCharacter(characterId);
    }

    @PostMapping("/buy-skin")
    public Map<String, Object> buySkin(
        @RequestParam(defaultValue = "player1") String username,
        @RequestBody Map<String, String> body
    ) {
        String result = shopService.buySkin(username, body.get("skinId"));
        return Map.of("message", result);
    }

    @PostMapping("/equip-skin")
    public Map<String, Object> equipSkin(
        @RequestParam(defaultValue = "player1") String username,
        @RequestBody Map<String, String> body
    ) {
        String result = shopService.equipSkin(
            username, body.get("characterId"), body.get("skinId")
        );
        return Map.of("message", result);
    }

    @GetMapping("/items")
    public List<Item> getItems() {
        return shopService.getShopItems();
    }

    @PostMapping("/buy-item")
    public Map<String, Object> buyItem(
        @RequestParam(defaultValue = "player1") String username,
        @RequestBody Map<String, Object> body
    ) {
        String itemId = (String) body.get("itemId");
        int quantity = body.containsKey("quantity") ? ((Number) body.get("quantity")).intValue() : 1;
        String result = shopService.buyItem(username, itemId, quantity);
        return Map.of("message", result);
    }

    @GetMapping("/inventory")
    public InventoryDto getInventory(
        @RequestParam(defaultValue = "player1") String username
    ) {
        return shopService.getInventory(username);
    }
}

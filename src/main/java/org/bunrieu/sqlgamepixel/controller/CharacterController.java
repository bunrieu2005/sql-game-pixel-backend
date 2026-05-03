package org.bunrieu.sqlgamepixel.controller;

import org.bunrieu.sqlgamepixel.dto.CharacterDto;
import org.bunrieu.sqlgamepixel.service.CharacterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
@CrossOrigin(origins = "http://localhost:4200")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public List<CharacterDto> getAllCharacters() {
        return characterService.getAllCharacters();
    }

    @GetMapping("/{id}")
    public CharacterDto getCharacter(@PathVariable String id) {
        return characterService.getCharacterById(id);
    }
}

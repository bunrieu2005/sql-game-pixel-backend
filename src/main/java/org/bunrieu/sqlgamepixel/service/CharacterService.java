package org.bunrieu.sqlgamepixel.service;

import org.bunrieu.sqlgamepixel.dto.CharacterDto;
import org.bunrieu.sqlgamepixel.entity.Character;
import org.bunrieu.sqlgamepixel.repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<CharacterDto> getAllCharacters() {
        return characterRepository.findAllOrdered().stream()
            .map(this::toDto)
            .toList();
    }

    public CharacterDto getCharacterById(String id) {
        return characterRepository.findById(id)
            .map(this::toDto)
            .orElseThrow();
    }

    public int getPriceById(String characterId) {
        return characterRepository.findById(characterId)
            .map(Character::getPrice)
            .orElse(0);
    }

    private CharacterDto toDto(Character c) {
        CharacterDto dto = new CharacterDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setElement(c.getElement());
        dto.setElementLabel(c.getElementLabel());
        dto.setWeapon(c.getWeapon());
        dto.setDescription(c.getDescription());
        dto.setClassLabel(c.getClassLabel());
        dto.setAvatarColor(c.getAvatarColor());

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
}

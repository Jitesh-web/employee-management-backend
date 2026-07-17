package net.javaguides.ems.mapper;

import net.javaguides.ems.dto.SkillDto;
import net.javaguides.ems.entity.Skill;

public class SkillMapper {

    public static SkillDto mapToSkillDto(Skill skill) {

        if (skill == null) {
            return null;
        }

        SkillDto dto = new SkillDto();
        dto.setId(skill.getId());
        dto.setSkillName(skill.getSkillName());

        return dto;
    }

    public static Skill mapToSkill(SkillDto dto) {

        if (dto == null) {
            return null;
        }

        Skill skill = new Skill();
        skill.setId(dto.getId());
        skill.setSkillName(dto.getSkillName());

        return skill;
    }
}

package com.learnings.learningproject.services.dtos.implementations;

import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.dtos.GroupDto;
import com.learnings.learningproject.services.dtos.IGroupDtoConverter;
import org.springframework.stereotype.Service;

@Service
public class GroupDtoConverterImpl implements IGroupDtoConverter {

    @Override
    public GroupDto convertToDto(Group entity) {
        GroupDto dto = new GroupDto(entity.getId(), entity.getAssociationId(), entity.getName(), entity.getDescription());
        dto.addResourceLinkRef();
        dto.addSelfLinkRef();
        return dto;
    }

    @Override
    public Group convertToEntity(GroupDto groupDto) {
        return new Group(groupDto.getName(), groupDto.getDescription(), groupDto.getAssociationId());
    }
}

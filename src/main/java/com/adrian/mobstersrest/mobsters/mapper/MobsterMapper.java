package com.adrian.mobstersrest.mobsters.mapper;

import com.adrian.mobstersrest.mobsters.domain.Mobster;
import com.adrian.mobstersrest.mobsters.model.MobsterDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MobsterMapper {

    MobsterMapper INSTANCE = Mappers.getMapper(MobsterMapper.class);

    MobsterDto mobsterToMobsterDto(Mobster mobster);
}

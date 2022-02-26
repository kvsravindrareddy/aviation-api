package com.ravindra.mapper;

import com.ravindra.data.AviationData;
import com.ravindra.entity.AviationEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AviationMapper {

    List<AviationEntity> aviationRequestListToEntityList(List<AviationData> aviationDataList);

    List<AviationData> aviationEntityListToDataList(List<AviationEntity> aviationEntityList);
}

package com.ProjX.projxpersitance.mappers;

public interface GenericMapper<Enitity,Dto> {

    Enitity toEntity(Dto dto);

    Dto toDto(Enitity enitity);
}

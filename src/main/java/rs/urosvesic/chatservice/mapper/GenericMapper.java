package rs.urosvesic.chatservice.mapper;


import rs.urosvesic.chatservice.collection.MyCollection;
import rs.urosvesic.chatservice.dto.Dto;

/**
 * @author UrosVesic
 */

public interface GenericMapper<D extends Dto, E extends MyCollection> {

    E toEntity(D dto);

    D toDto(E entity);
}

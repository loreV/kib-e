package org.kibe.common.data.mapper;

import org.kibe.common.data.PlantSubject;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.inject.Inject;

import static org.kibe.common.data.PlantSubject.*;


public class PlantSubjectMapper implements Mapper<PlantSubject> {

    private final PlantMapper plantMapper;

    @Inject
    public PlantSubjectMapper(final PlantMapper plantMapper){
        this.plantMapper = plantMapper;
    }

    @Override
    public PlantSubject mapToObject(final Document document) {
        return new PlantSubject(
                document.getObjectId(ID_FIELD),
                document.getString(PUID_FIELD),
                document.getDate(DATETIME_FIELD),
                plantMapper.mapToObject(document.get(PLANT, Document.class))
        );
    }

    @Override
    public Document mapToDoc(final PlantSubject plantSubject) {
        final ObjectId objectId = (plantSubject.get_id() == null) ? new ObjectId() : plantSubject.get_id();
        return new Document(ID_FIELD, objectId)
                .append(PUID_FIELD, plantSubject.getPuid())
                .append(DATETIME_FIELD, plantSubject.getDatetime())
                .append(PLANT, plantSubject.getPlant());
    }
}

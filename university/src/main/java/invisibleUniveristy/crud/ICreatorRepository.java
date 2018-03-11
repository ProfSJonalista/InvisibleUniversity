package invisibleUniveristy.crud;

import invisibleUniveristy.invention.Creator;

import java.util.List;

public interface ICreatorRepository {
    void initDatabase();
    List<Creator> getAllCreators();
    void add(Creator creator);
    Creator getCreatorById(Long id);
    void deleteById(Long id);
    void updateById(Long id);
}

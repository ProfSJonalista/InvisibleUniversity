package invisibleuniversity.service;

import invisibleuniversity.domain.Creator;
import invisibleuniversity.domain.Invention;

import java.util.List;

public interface SellingManager {
    void addCreator(Creator creator);
    List<Creator> getAllCreators();
    void deleteCreator(Creator creator);
    Creator findCreatorByNickname(String nickname);
    void updateCreator(Creator creator);

    Long addNewInvention(Invention invention);
    List<Invention> getSoldInventions();
    List<Invention> getAvailableInventions();
    void disposeInvention(Creator creator, Invention invention);
    Invention findInventionById(Long id);

    List<Invention> getOwnedInventions(Creator creator);
    void sellInvention(Long creatorId, Long inventionId);
}

package invisibleuniversity.service;

import invisibleuniversity.domain.Creator;
import invisibleuniversity.domain.Invention;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
@Transactional
public class SellingManagerHibernateImpl implements SellingManager{

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addCreator(Creator creator) {
        creator.setId(null);
        sessionFactory.getCurrentSession().persist(creator);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Creator> getAllCreators() {
        return sessionFactory.getCurrentSession().getNamedQuery("Creator.findAll").list();
    }

    @Override
    public void deleteCreator(Creator creator) {
        creator = (Creator) sessionFactory.getCurrentSession().get(Creator.class, creator.getId());

        for (Invention invention : creator.getInventions()) {
            invention.setSold(false);
            sessionFactory.getCurrentSession().update(invention);
        }

        sessionFactory.getCurrentSession().delete(creator);
    }

    @Override
    public Creator findCreatorByNickname(String nickname) {
        return (Creator) sessionFactory
                .getCurrentSession()
                .getNamedQuery("Creator.findByNickname")
                .setString("nickname", nickname)
                .uniqueResult();
    }

    @Override
    public void updateCreator(Creator creator) {
        sessionFactory.getCurrentSession().update(creator);
    }

    @Override
    public Long addNewInvention(Invention invention) {
        invention.setId(null);
        return (Long) sessionFactory.getCurrentSession().save(invention);
    }

    @Override
    public List<Invention> getSoldInventions() {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Invention> getAvailableInventions() {
        return sessionFactory
                .getCurrentSession()
                .getNamedQuery("Invention.unsold")
                .list();
    }

    @Override
    public void disposeInvention(Creator creator, Invention invention) {
        creator = (Creator) sessionFactory.getCurrentSession().get(Creator.class, creator.getId());
        invention = (Invention) sessionFactory.getCurrentSession().get(Invention.class, invention.getId());

        Invention toRemove = null;

        for(Invention anInvention : creator.getInventions()){
            if(anInvention.getId().compareTo(invention.getId()) == 0){
                toRemove = anInvention;
                break;
            }
        }

        if(toRemove != null)
            creator.getInventions().remove(toRemove);

        invention.setSold(false);
    }

    @Override
    public Invention findInventionById(Long id) {
        return (Invention) sessionFactory.getCurrentSession().get(Invention.class, id);
    }

    @Override
    public List<Invention> getOwnedInventions(Creator creator) {
        creator = (Creator) sessionFactory.getCurrentSession().get(Creator.class, creator.getId());

        return new ArrayList<>(creator.getInventions());
    }

    @Override
    public void sellInvention(Long creatorId, Long inventionId) {
        Creator creator = (Creator) sessionFactory.getCurrentSession().get(Creator.class, creatorId);
        Invention invention = (Invention) sessionFactory.getCurrentSession().get(Invention.class, inventionId);

        invention.setSold(true);

        if(creator.getInventions() == null) {
            creator.setInventions(new LinkedList<Invention>());
        }

        creator.getInventions().add(invention);
    }
}

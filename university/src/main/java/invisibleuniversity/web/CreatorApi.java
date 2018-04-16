package invisibleuniversity.web;

import invisibleuniversity.domain.Creator;
import invisibleuniversity.service.CreatorRepositoryFactory;
import invisibleuniversity.service.ICreatorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@RestController
public class CreatorApi {
    @Autowired
    ICreatorRepository creatorRepository;

    @RequestMapping("/")
    public String index() {
        //creatorRepository = CreatorRepositoryFactory.getInstance();
        return "This is non rest, just checking if everything works.";
    }

    @RequestMapping(value = "/creators", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Creator> getCreators() throws SQLException {
        List<Creator> creators = new LinkedList<>();
        creators.addAll(creatorRepository.getAllCreators());

        return creators;
    }

    @RequestMapping(value= "/creator/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Creator getCreator(@PathVariable("id") Long id) throws SQLException{
        return creatorRepository.getCreatorById(id);
    }

    @RequestMapping(value = "/creator/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Long deleteCreator(@PathVariable("id") Long id){
        return (long) creatorRepository.deleteById(id);
    }

    @RequestMapping(value = "/creator", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long addAccount(@RequestBody Creator c) {
        return (long) creatorRepository.add(c);
    }

    @RequestMapping(value = "/creator", method = RequestMethod.PUT)
    public Long updateAccount(@RequestBody Creator c) throws SQLException {
        return (long) creatorRepository.updateById(c);
    }
}

package invisibleUniveristy.web;

import invisibleUniveristy.domain.Creator;
import invisibleUniveristy.service.Creator.ICreatorRepository;

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
        return "This is non rest, just checking if everything works.";
    }

    @RequestMapping(value = "/creators", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Creator> getCreators(@RequestParam("filter") String f) throws SQLException {
        List<Creator> creators = new LinkedList<>();
        for(Creator c : creatorRepository.getAllCreators()){
            if(c.getSurname().contains(f)){
                creators.add(c);
            }
        }

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
        return new Long(creatorRepository.deleteById(id));
    }
}

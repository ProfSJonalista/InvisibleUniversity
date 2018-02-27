package invisibleUniveristy;

import invisibleUniveristy.invention.Invention;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import org.junit.Test;

public class InvensionFinderTest{
    
    @Test
    public void invensionFindTest(){
        ArrayList<Invention> inventionList = new ArrayList<Invention>();

        inventionList.add(new Invention("Death claws", "Claws filled up with death", true));
        inventionList.add(new Invention("Puppy boots", "Boots shaped up like puppies paws", false));
        inventionList.add(new Invention("Gamepad", "Electronic device which is used to play games", false));

        InventionFinder inventionFinder = new InventionFinder(inventionList);
        Integer result = inventionFinder.addAllNonLethalInventions();

        assertNotNull(result);
        assertEquals(result, result > 0);
    }
}
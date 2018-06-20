import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void shouldReturn3ResultsFromStringMatch() throws FileNotFoundException {
        String input = "also";
        int expectedInHitchhikers = 3;
        int actualInHitchhikers = Main.searchString(input, "hitchhikers.txt");
        Assert.assertEquals(actualInHitchhikers, expectedInHitchhikers);
    }
    @Test
    void shouldReturn3ResultsFromRegexMatch() throws FileNotFoundException {
        String input = "against";
        int expectedInFrenchArmedForces = 3;
        int actualInFrenchArmedForces = Main.searchRegex(input, "french_armed_forces.txt");
        Assert.assertEquals(actualInFrenchArmedForces, expectedInFrenchArmedForces);
    }
    @Test
    void shouldReturn2ResultsFromIndex() throws FileNotFoundException {
        String input = "other";
        int expectedFromWarpDrive = 2;
        int actualFromWarpDrive = Main.searchIndex(input, "warp_drive.txt");
        Assert.assertEquals(actualFromWarpDrive, expectedFromWarpDrive);
    }

    @Test
    void shouldReturn132FromStringMatch(){

    }

}
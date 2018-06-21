import org.junit.jupiter.api.Assertions;

import org.junit.*;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


class MainTest {
    @org.junit.jupiter.api.Test
    void shouldReturn3ResultsFromStringMatch() throws FileNotFoundException {
        String input = "also";
        int expectedInHitchhikers = 3;
        int actualInHitchhikers = Main.searchString(input, "hitchhikers.txt");
        Assertions.assertEquals(actualInHitchhikers, expectedInHitchhikers);

    }
    @org.junit.jupiter.api.Test
    void shouldReturn3ResultsFromRegexMatch() throws FileNotFoundException {
        String input = "against";
        int expectedInFrenchArmedForces = 3;
        int actualInFrenchArmedForces = Main.searchRegex(input, "french_armed_forces.txt");
        Assertions.assertEquals(actualInFrenchArmedForces, expectedInFrenchArmedForces);
    }
    @org.junit.jupiter.api.Test
    void shouldReturn2ResultsFromIndex() throws FileNotFoundException {
        String input = "other";
        int expectedFromWarpDrive = 2;
        int actualFromWarpDrive = Main.searchIndex(input, "warp_drive.txt");
        Assertions.assertEquals(actualFromWarpDrive, expectedFromWarpDrive);
    }



}
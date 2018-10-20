package batmudgoalsplugin;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mythicscape.batclient.interfaces.ParsedResult;

import batmudgoalsplugin.data.BatMUDGoalsPluginData;

@ExtendWith(MockitoExtension.class)
public class TestBatMUDGoalsPlugin {

    @Mock
    private ClientGUIModel model;

    private BatMUDGoalsPlugin plugin;

    @BeforeEach
    public void setup() throws Exception {
        plugin = new BatMUDGoalsPlugin(new BatMUDGoalsPluginData(), model);
        plugin.initializeCommandProcessors();

        plugin.trigger(new ParsedResult(",-------------------------------------------------.\n"));
        plugin.trigger(new ParsedResult("| Cost of training Attack                         |\n"));
        plugin.trigger(new ParsedResult("|-------------------------------------------------|\n"));
        plugin.trigger(new ParsedResult("| Percent     Exp        | Percent     Exp        |\n"));
        plugin.trigger(new ParsedResult("|=================================================|\n"));
        plugin.trigger(new ParsedResult("|    1% =            80  |   51% =          9046  |\n"));
        plugin.trigger(new ParsedResult("|    2% =            82  |   52% =          9700  |\n"));
        plugin.trigger(new ParsedResult("|    3% =            86  |   53% =         10395  |\n"));
        plugin.trigger(new ParsedResult("|    4% =            91  |   54% =         11135  |\n"));
        plugin.trigger(new ParsedResult("|    5% =            99  |   55% =         11921  |\n"));
        plugin.trigger(new ParsedResult("|    6% =           109  |   56% =         12756  |\n"));
        plugin.trigger(new ParsedResult("|    7% =           121  |   57% =         13642  |\n"));
        plugin.trigger(new ParsedResult("|    8% =           137  |   58% =         14584  |\n"));
        plugin.trigger(new ParsedResult("|    9% =           155  |   59% =         15583  |\n"));
        plugin.trigger(new ParsedResult("|   10% =           177  |   60% =         16643  |\n"));
        plugin.trigger(new ParsedResult("|   11% =           203  |   86% =         17768  |\n"));
        plugin.trigger(new ParsedResult("|   1% to 86% =         200000000  |\n"));

        plugin.trigger(new ParsedResult("| Attack                      |  1 |  85 | 100 |       22015 |\n"));

        plugin.trigger("ranger info");
        plugin.trigger(new ParsedResult("Name: Rangers\n"));
        plugin.trigger(new ParsedResult("Command: ranger\n"));
        plugin.trigger(new ParsedResult("Creators: Duke\n"));
        plugin.trigger(new ParsedResult("Your level: 2\n"));
        plugin.trigger(new ParsedResult("Maximum level: 35\n"));

        plugin.trigger(new ParsedResult("Description:\n"));
        plugin.trigger(
                new ParsedResult("The mighty barbarian guild is a loosely run group of battle hardened warriors.\n"));
        plugin.trigger(new ParsedResult(" Through intense training in the wilds, members become both mentally and\n"));
        plugin.trigger(new ParsedResult("physically tough.  These ferocious warriors excel at many combat skills.\n"));

        plugin.trigger(new ParsedResult("Joining requirements:\n"));
        plugin.trigger(new ParsedResult(" Background must be nomad (passed)\n"));
        plugin.trigger(new ParsedResult("Abilities gained when joining:\n"));
        plugin.trigger(new ParsedResult(" In the name of Groo wear the shrunken skull necklace with pride.\n"));
        plugin.trigger(new ParsedResult(" May train skill Attack to 20%\n"));
        plugin.trigger(new ParsedResult(" May train skill Push to 40%\n"));
        plugin.trigger(new ParsedResult(" May train skill Alcohol tolerance to 3%\n"));
        plugin.trigger(new ParsedResult(" May train skill Consider to 10%\n"));
        plugin.trigger(new ParsedResult(" May train skill Hunting to 20%\n"));
        plugin.trigger(new ParsedResult(" May train skill Fishing to 20%\n"));
        plugin.trigger(new ParsedResult(" May train skill Torch creation to 15%\n"));
        plugin.trigger(new ParsedResult(" May train skill Looting and burning to 30%\n"));
        plugin.trigger(new ParsedResult(" May train skill Vandalism to 10%\n"));
        plugin.trigger(new ParsedResult(" May train skill Axes to 10%\n"));

        plugin.trigger(new ParsedResult("Abilities and requirements at each level:\n"));
        plugin.trigger(new ParsedResult(" Level 2:\n"));
        plugin.trigger(new ParsedResult("  Abilities:\n"));
        plugin.trigger(new ParsedResult("   May train skill Attack to 57%\n"));
        plugin.trigger(new ParsedResult("   May train skill Push to 100%\n"));
        plugin.trigger(new ParsedResult("   May train skill Bash to 10%\n"));

        plugin.trigger(new ParsedResult("Abilities and requirements at each level:\n"));
        plugin.trigger(new ParsedResult(" Level 3:\n"));
        plugin.trigger(new ParsedResult("  Abilities:\n"));
        plugin.trigger(new ParsedResult("   May train skill Attack to 90%\n"));
        plugin.trigger(new ParsedResult("   May train skill Push to 100%\n"));
        plugin.trigger(new ParsedResult("   May train skill Bash to 10%\n"));

        plugin.trigger("barbarian info");
        plugin.trigger(new ParsedResult("Name: Barbarian Guild\n"));
        plugin.trigger(new ParsedResult("Command: barbarian\n"));
        plugin.trigger(new ParsedResult("Creators: Duke\n"));
        plugin.trigger(new ParsedResult("Your level: 1\n"));
        plugin.trigger(new ParsedResult("Maximum level: 35\n"));

        plugin.trigger(new ParsedResult("Description:\n"));
        plugin.trigger(
                new ParsedResult("The mighty barbarian guild is a loosely run group of battle hardened warriors.\n"));
        plugin.trigger(new ParsedResult(" Through intense training in the wilds, members become both mentally and\n"));
        plugin.trigger(new ParsedResult("physically tough.  These ferocious warriors excel at many combat skills.\n"));

        plugin.trigger(new ParsedResult("Joining requirements:\n"));
        plugin.trigger(new ParsedResult(" Background must be nomad (passed)\n"));
        plugin.trigger(new ParsedResult("Abilities gained when joining:\n"));
        plugin.trigger(new ParsedResult(" In the name of Groo wear the shrunken skull necklace with pride.\n"));
        plugin.trigger(new ParsedResult(" May train skill Attack to 10%\n"));
        plugin.trigger(new ParsedResult(" May train skill Push to 40%\n"));
        plugin.trigger(new ParsedResult(" May train skill Alcohol tolerance to 3%\n"));
        plugin.trigger(new ParsedResult(" May train skill Consider to 10%\n"));
        plugin.trigger(new ParsedResult(" May train skill Hunting to 20%\n"));
        plugin.trigger(new ParsedResult(" May train skill Fishing to 20%\n"));
        plugin.trigger(new ParsedResult(" May train skill Torch creation to 15%\n"));
        plugin.trigger(new ParsedResult(" May train skill Looting and burning to 30%\n"));
        plugin.trigger(new ParsedResult(" May train skill Vandalism to 10%\n"));
        plugin.trigger(new ParsedResult(" May train skill Axes to 10%\n"));

        plugin.trigger(new ParsedResult("Abilities and requirements at each level:\n"));
        plugin.trigger(new ParsedResult(" Level 2:\n"));
        plugin.trigger(new ParsedResult("  Abilities:\n"));
        plugin.trigger(new ParsedResult("   May train skill Attack to 57%\n"));
        plugin.trigger(new ParsedResult("   May train skill Push to 100%\n"));
        plugin.trigger(new ParsedResult("   May train skill Bash to 10%\n"));

    }

    @Test
    public void testSkillNotInLibrary() throws Exception {
        plugin.trigger("goal looting and burning");
        verifyPrint("looting and burning not in library");
    }

    private void verifyPrint(String expected) {
        verify(model).printText("generic", String.format("%s%n", expected));
    }

    @Test
    public void testSetGoalSuccesfully() throws Exception {
        plugin.trigger("goal attack");
        verifyPrint("Next goal: attack");
    }

    @Test
    public void testGoalSetListGoals() throws Exception {
        plugin.trigger(new ParsedResult(",-------------------------------------------------.\n"));
        plugin.trigger(new ParsedResult("| Cost of training Looting and burning                         |\n"));
        plugin.trigger(new ParsedResult("|-------------------------------------------------|\n"));
        plugin.trigger(new ParsedResult("| Percent     Exp        | Percent     Exp        |\n"));
        plugin.trigger(new ParsedResult("|=================================================|\n"));
        plugin.trigger(new ParsedResult("|    1% =            80  |   51% =          9046  |\n"));
        plugin.trigger(new ParsedResult("|    2% =            82  |   52% =          9700  |\n"));
        plugin.trigger(new ParsedResult("|    3% =            86  |   53% =         10395  |\n"));
        plugin.trigger("goal attack");
        plugin.trigger("goal");

        verifyPrint("attack (*)");
        verifyPrint("looting and burning");
    }

    @Test
    public void testParseCostTrain() throws Exception {
        plugin.trigger("goal attack");
        plugin.trigger(new ParsedResult("Exp: 2 Money: 211.10 Bank: 64440.00 Exp pool: 100.0\n"));
        verifyPrint("Goal attack: 82 You need: " + Integer.toString(82 - 2));

        plugin.trigger("goal");
        verifyPrint("attack (*)");

    }

    @Test
    public void testExpOutputWithZeroValues() throws Exception {
        plugin.trigger("goal attack");
        plugin.trigger(new ParsedResult("Exp: 2 Money: 0 Bank: 0 Exp pool: 0\n"));
        verifyPrint("Goal attack: 82 You need: " + Integer.toString(82 - 2));
    }

    @Test
    public void testEnoughExp() throws Exception {
        plugin.trigger("goal attack");
        plugin.trigger(new ParsedResult("Exp: 100000 Money: 0 Bank: 0 Exp pool: 0\n"));
        verifyPrint("Next goal: attack");
        verifyPrint("Goal attack: 82 You have enough to advance in: barbarian, ranger");
    }

    @Test
    public void testNeedAnotherLevel() throws Exception {
        plugin.trigger(new ParsedResult("| Attack                      |  85 |  85 | 85 |       22015 |\n"));
        plugin.trigger("goal attack");
        plugin.trigger(new ParsedResult("Exp: 12920 Money: 211.10 Bank: 64440.00 Exp pool: 100.0\n"));
        verifyPrint("Goal attack: needs level");
    }

    @Test
    public void testSkillIsFull() throws Exception {
        plugin.trigger(new ParsedResult("| Attack                      |  100 |  85 | 100 |       (n/a) |\n"));
        plugin.trigger("goal attack");
        plugin.trigger(new ParsedResult("Exp: 12920 Money: 211.10 Bank: 64440.00 Exp pool: 100.0\n"));
        verifyPrint("Goal attack: full");

    }

    @Test
    public void testTrainOutput() throws Exception {
        plugin.trigger(new ParsedResult("You now have 'Attack' at 100% without special bonuses.\n"));
        plugin.trigger("goal attack");
        plugin.trigger(new ParsedResult("Exp: 12920 Money: 211.10 Bank: 64440.00 Exp pool: 100.0\n"));
        verifyPrint("Goal attack: full");
    }

    @Test
    public void testGuildinfo() throws Exception {

        plugin.trigger("train");
        plugin.trigger(new ParsedResult("| Skills available at level  1  | Cur | Rac | Max | Exp         |"));
        plugin.trigger(new ParsedResult("| Attack                        |   0 |  85 | 10  |       22015 |"));

        plugin.trigger("goal attack");
        plugin.trigger(new ParsedResult("Exp: 12920 Money: 211.10 Bank: 64440.00 Exp pool: 100.0\n"));

        verifyPrint("Next goal: attack");
        verifyPrint("Goal attack: 80 You have enough to advance in: barbarian, ranger");
    }

    @Test
    public void testGuildinfoCanAdvanceInOneOfGuilds() throws Exception {

        plugin.trigger("train");
        plugin.trigger(new ParsedResult("| Skills available at level  1  | Cur | Rac | Max | Exp         |"));
        // Attack at max in barbarians but this will still allow improvement in
        // Rangers
        plugin.trigger(new ParsedResult("| Attack                        |   10 |  85 | 10  |       22015 |"));

        plugin.trigger("goal attack");
        plugin.trigger(new ParsedResult("Exp: 12920 Money: 211.10 Bank: 64440.00 Exp pool: 100.0\n"));

        verifyPrint("Goal attack: 203 You have enough to advance in: ranger");
    }
}

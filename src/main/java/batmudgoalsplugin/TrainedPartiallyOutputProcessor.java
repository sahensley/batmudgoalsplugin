package batmudgoalsplugin;

import java.util.regex.Matcher;

import batmudgoalsplugin.data.BatMUDGoalsPluginData;

/**
 * Partially training causes player to use 250k of experience to improve a skill
 * or a spell partially and gets charged a total of 300k exp.
 */
class TrainedPartiallyOutputProcessor extends AbstractOutputProcessor {

    private final BatMUDGoalsPluginData data;

    public TrainedPartiallyOutputProcessor(BatMUDGoalsPluginData data) {
        super(
                "You partially (?:train|study) ([\\w\\s]+) which cost you 300000 experience.\\s*");
        this.data = data;
    }

    @Override
    protected void process(Matcher m) {
        data.trainPartially(normalizeSkillName(m.group(1)));
    }
}

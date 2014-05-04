package batmudgoalsplugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import batmudgoalsplugin.data.BatMUDGoalsPluginData;
import batmudgoalsplugin.data.SkillMaxInfo;

import com.mythicscape.batclient.interfaces.BatClientPlugin;
import com.mythicscape.batclient.interfaces.BatClientPluginCommandTrigger;
import com.mythicscape.batclient.interfaces.BatClientPluginTrigger;
import com.mythicscape.batclient.interfaces.BatClientPluginUtil;
import com.mythicscape.batclient.interfaces.ClientGUI;
import com.mythicscape.batclient.interfaces.ParsedResult;

/**
 * Plugin for BatClient. Player can set a goal of improving a skill in her
 * guild. Experience needed to reach the next percent is then shown upon 'exp'
 * command.
 * 
 * @author Jogo
 */
public class BatMUDGoalsPlugin extends BatClientPlugin implements
		BatClientPluginCommandTrigger, BatClientPluginTrigger,
		BatClientPluginUtil {

	private BatMUDGoalsPluginData data;
	private Collection<AbstractCommandProcessor> commandProcessors;
	private Collection<AbstractCommandProcessor> outputProcessors;

	@SuppressWarnings("serial")
	public BatMUDGoalsPlugin() {
		data = new BatMUDGoalsPluginData();
		final PlayerLevelOutputProcessor playerLevelOutputProcessor = new PlayerLevelOutputProcessor(
				data);
		final InfoCommandSkillMaxOutputProcessor infoCommandSkillMaxOutputProcessor = new InfoCommandSkillMaxOutputProcessor(
				data);
		final PercentCostOutputProcessor percentCostOutputProcessor = new PercentCostOutputProcessor(
				data);

		commandProcessors = new ArrayList<AbstractCommandProcessor>() {
			{
				add(new GuildCommandProcessor(playerLevelOutputProcessor,
						infoCommandSkillMaxOutputProcessor));
				add(new GoalCommandWithoutParametersProcessor(getClientGUI(),
						data));
				add(new GoalCommandProcessor(getClientGUI(), data));
			}
		};
		outputProcessors = new ArrayList<AbstractCommandProcessor>() {
			{
				add(new TrainCommandOutputProcessor(data));
				add(percentCostOutputProcessor);
				add(new TrainedSkillOutputProcessor(data));
				add(new CostOfTrainingSkillNameOutputProcessor(
						percentCostOutputProcessor, data));
				add(new ExpCommandOutputProcessor(getClientGUI(), data));
				add(playerLevelOutputProcessor);
				add(new InfoCommandFirstLevelProcessor(
						infoCommandSkillMaxOutputProcessor, data));
				add(new InfoCommandLevelNumberProcessor(
						infoCommandSkillMaxOutputProcessor, data));
				add(infoCommandSkillMaxOutputProcessor);
			}
		};
	}

	/*
	 * @see
	 * com.mythicscape.batclient.interfaces.BatClientPluginCommandTrigger#trigger
	 * (java.lang.String)
	 */
	@Override
	public String trigger(String input) {
		for (AbstractCommandProcessor cp : commandProcessors) {
			if (cp.receive(input)) {
				return "";
			}
		}
		return null;
	}

	/*
	 * Catch output from 'cost train skill' and 'train' commands (non-Javadoc)
	 * 
	 * @see
	 * com.mythicscape.batclient.interfaces.BatClientPluginTrigger#trigger(com
	 * .mythicscape.batclient.interfaces.ParsedResult)
	 */
	@Override
	public ParsedResult trigger(ParsedResult input) {
		String originalText = input.getOriginalText();
		for (AbstractCommandProcessor op : outputProcessors) {
			op.receive(originalText);
		}

		return input; // return input to be processed by the client
	}

	/**
	 * Processes output from 'exp' command.
	 * 
	 * Exp command output is something like this: <br>
	 * Exp: 135670 Money: 0.00 Bank: 644404.00 Exp pool: 0<br>
	 * Takes exp amount, outputs goal skill, needed exp and amount missing from
	 * next percent
	 */
	private class ExpCommandOutputProcessor extends AbstractCommandProcessor {
		public ExpCommandOutputProcessor(ClientGUI clientGUI,
				BatMUDGoalsPluginData data) {
			super(
					"Exp: (\\d+) Money: (\\d+)\\.?(\\d*) Bank: (\\d+)\\.?(\\d*) Exp pool: (\\d+)\\.?(\\d*)\\s+",
					clientGUI, data);
		}

		private String concatGuildNames(Collection<String> guilds) {
			StringBuilder sb = new StringBuilder();
			for (String s : guilds) {
				sb.append(s);
				sb.append(", ");
			}
			return sb.toString().substring(0, sb.length() - 2).toLowerCase();
		}

		@Override
		protected boolean process(Matcher m) {
			if (data.isGoalSkillMaxed()) {
				printMessage("Goal %s: full", data.getGoalSkill());
			} else {
				// get skillmaxinfo for this skill
				Collection<SkillMaxInfo> skillmaxinfo = new ArrayList<SkillMaxInfo>();
				Collection<String> guilds = new HashSet<String>();
				for (SkillMaxInfo s : data.getSkillMaxes()) {
					if (data.isGoalSkill(s.skill)
							&& s.level <= data.getGuildLevel(s.guild)
							&& s.max >= data.getGoalPercent()) {
						skillmaxinfo.add(s);
						guilds.add(s.guild);
					}
				}

				if (skillmaxinfo.isEmpty()) {
					printMessage("Goal %s: needs level", data.getGoalSkill());
				} else {
					int neededExp = data.getImproveGoalSkillCost();
					int currentExp = Integer.parseInt(m.group(1));
					if (currentExp < neededExp) {
						printMessage("Goal %s: %d You need: %d",
								data.getGoalSkill(), neededExp, neededExp
										- currentExp);
					} else {
						printMessage(
								"Goal %s: %d You have enough to advance in: %s",
								data.getGoalSkill(), neededExp,
								concatGuildNames(guilds));
					}
				}

			}
			return false;
		}
	}

	private void printMessage(String message) {
		getClientGUI().printText("generic", String.format("%s\n", message));
	}

	/**
	 * Processes output from 'cost train <skill>' command. Stores the skill name
	 * from the outputted table.
	 */
	private class CostOfTrainingSkillNameOutputProcessor extends
			AbstractCommandProcessor {

		private PercentCostOutputProcessor op;

		public CostOfTrainingSkillNameOutputProcessor(
				PercentCostOutputProcessor op, BatMUDGoalsPluginData data) {
			super("\\|\\s+Cost\\s+of\\s+training\\s+([^\\|]+)\\s+\\|\\s+",
					null, data);
			this.op = op;
		}

		@Override
		protected boolean process(Matcher m) {
			String skill = m.group(1).toLowerCase().trim();
			op.setSkill(skill);
			return false;
		}
	}

	@Override
	public String getName() {
		return "BatMUDGoalsPlugin";
	}

	/*
	 * Load state from persistent storage (non-Javadoc)
	 * 
	 * @see com.mythicscape.batclient.interfaces.BatClientPlugin#loadPlugin()
	 */
	@Override
	public void loadPlugin() {
		try {
			data = (BatMUDGoalsPluginData) generateJAXBContext()
					.createUnmarshaller().unmarshal(createPersistenceFile());
		} catch (Exception e) {
			printMessage(e.toString());
			e.printStackTrace();
		}
	}

	/*
	 * Persist current goal and stored skill information (non-Javadoc)
	 * 
	 * @see
	 * com.mythicscape.batclient.interfaces.BatClientPluginUtil#clientExit()
	 */
	@Override
	public void clientExit() {
		try {
			generateJAXBContext().createMarshaller().marshal(data,
					createPersistenceFile());
		} catch (JAXBException | IOException e) {
			printMessage(e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * If file exists opens the file, otherwise a new one is created
	 * 
	 * @return {@link File} used to store plugin's state
	 * @throws IOException
	 */
	private File createPersistenceFile() throws IOException {
		File file = new File(getClientGUI().getBaseDirectory()
				+ "/conf/batmudgoalsplugin/BatMUDGoalsInfo.xml");
		if (!file.exists())
			file.createNewFile();
		return file;
	}

	private JAXBContext generateJAXBContext() throws JAXBException {
		JAXBContext ctx = JAXBContext.newInstance(BatMUDGoalsPluginData.class);
		return ctx;
	}
}

package com.github.pocketkid2.survivalgames.commands;

import java.util.List;

public abstract class SubCommand {

	protected int minArguments;
	protected int maxArguments;
	protected List<String> aliases;

	public abstract void init();

	public abstract void execute(String[] arguments);

	public int getMinArguments() {
		return minArguments;
	}

	public int getMaxArguments() {
		return maxArguments;
	}

	public List<String> getAliases() {
		return aliases;
	}
}

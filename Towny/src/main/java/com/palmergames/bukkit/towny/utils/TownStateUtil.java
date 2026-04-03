package com.palmergames.bukkit.towny.utils;

import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.metadata.StringDataField;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class TownStateUtil {

	public static final String STATE_METADATA_KEY = "towny_state";
	private static final String UNITED_STATES_NAME = "United States";
	private static final StringDataField STATE_DATA_FIELD = new StringDataField(STATE_METADATA_KEY);
	private static final Map<String, String> STATE_NAME_TO_CODE = new HashMap<>();
	private static final Map<String, String> STATE_LOOKUP = new HashMap<>();

	static {
		registerState("Alabama", "AL");
		registerState("Alaska", "AK");
		registerState("Arizona", "AZ");
		registerState("Arkansas", "AR");
		registerState("California", "CA");
		registerState("Colorado", "CO");
		registerState("Connecticut", "CT");
		registerState("Delaware", "DE");
		registerState("Florida", "FL");
		registerState("Georgia", "GA");
		registerState("Hawaii", "HI");
		registerState("Idaho", "ID");
		registerState("Illinois", "IL");
		registerState("Indiana", "IN");
		registerState("Iowa", "IA");
		registerState("Kansas", "KS");
		registerState("Kentucky", "KY");
		registerState("Louisiana", "LA");
		registerState("Maine", "ME");
		registerState("Maryland", "MD");
		registerState("Massachusetts", "MA");
		registerState("Michigan", "MI");
		registerState("Minnesota", "MN");
		registerState("Mississippi", "MS");
		registerState("Missouri", "MO");
		registerState("Montana", "MT");
		registerState("Nebraska", "NE");
		registerState("Nevada", "NV");
		registerState("New Hampshire", "NH");
		registerState("New Jersey", "NJ");
		registerState("New Mexico", "NM");
		registerState("New York", "NY");
		registerState("North Carolina", "NC");
		registerState("North Dakota", "ND");
		registerState("Ohio", "OH");
		registerState("Oklahoma", "OK");
		registerState("Oregon", "OR");
		registerState("Pennsylvania", "PA");
		registerState("Rhode Island", "RI");
		registerState("South Carolina", "SC");
		registerState("South Dakota", "SD");
		registerState("Tennessee", "TN");
		registerState("Texas", "TX");
		registerState("Utah", "UT");
		registerState("Vermont", "VT");
		registerState("Virginia", "VA");
		registerState("Washington", "WA");
		registerState("West Virginia", "WV");
		registerState("Wisconsin", "WI");
		registerState("Wyoming", "WY");
		registerState("Puerto Rico", "PR");
		registerState("Washington, DC", "DC", "Washington DC", "District of Columbia");
	}

	private TownStateUtil() {
	}

	private static void registerState(String name, String code, String... aliases) {
		STATE_NAME_TO_CODE.put(name, code);
		STATE_LOOKUP.put(normalizeState(name), name);
		STATE_LOOKUP.put(normalizeState(code), name);
		for (String alias : aliases)
			STATE_LOOKUP.put(normalizeState(alias), name);
	}

	private static String normalizeState(String input) {
		return input.toUpperCase(Locale.ROOT).replace(".", "").replace(",", "").replaceAll("\\s+", " ").trim();
	}

	public static String resolveStateName(String input) {
		if (input == null)
			return "";
		return STATE_LOOKUP.getOrDefault(normalizeState(input), "");
	}

	public static boolean canUseState(Town town) {
		return town.hasNation() && UNITED_STATES_NAME.equalsIgnoreCase(town.getNationOrNull().getName().replace('_', ' '));
	}

	public static boolean hasState(Town town) {
		return !getState(town).isEmpty();
	}

	public static String getState(Town town) {
		if (!town.hasMeta(STATE_METADATA_KEY))
			return "";

		return MetaDataUtil.getString(town, STATE_DATA_FIELD).trim();
	}

	public static void setState(Town town, String state) {
		MetaDataUtil.setString(town, STATE_DATA_FIELD, state.trim(), true);
	}

	public static void clearState(Town town) {
		town.removeMetaData(STATE_METADATA_KEY, true);
	}

	public static boolean isValidStateName(String input) {
		return !resolveStateName(input).isEmpty();
	}

	public static String getStateCode(Town town) {
		String state = getState(town);
		if (state.isEmpty())
			return "";

		return STATE_NAME_TO_CODE.getOrDefault(state, "");
	}

	public static String getTownNameWithState(Town town) {
		String townName = town.getName().replace("_", " ");
		if (!canUseState(town) || !hasState(town))
			return townName;

		return getState(town) + " " + townName;
	}

	public static String getTownNameWithStateCode(Town town) {
		String townName = town.getName().replace("_", " ");
		if (!canUseState(town) || !hasState(town))
			return townName;

		return getStateCode(town) + " " + townName;
	}

	public static String getTownTagWithState(Town town) {
		if (!canUseState(town) || !hasState(town))
			return town.getTag();

		if (!town.hasTag())
			return getState(town);

		return getState(town) + " " + town.getTag();
	}

	public static String getTownTagOverrideWithState(Town town) {
		if (!canUseState(town) || !hasState(town))
			return town.hasTag() ? town.getTag() : town.getName().replace("_", " ");

		String townPart = town.hasTag() ? town.getTag() : town.getName().replace("_", " ");
		return getState(town) + " " + townPart;
	}
}

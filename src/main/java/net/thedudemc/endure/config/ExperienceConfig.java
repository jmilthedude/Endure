package net.thedudemc.endure.config;

import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.dudeconfig.config.option.Option;
import net.thedudemc.dudeconfig.config.option.OptionMap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ExperienceConfig extends Config {
    @Override
    public String getName() {
        return "Experience";
    }

    @Override
    public OptionMap getDefaults() {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        int xpNeeded = 0;
        for (int i = 1; i < 58; i++) {
            if (i == 1) xpNeeded = 100;
            else if (i == 2) xpNeeded += 2550;
            else if (i == 3) xpNeeded += 1250;
            else if (i <= 5) xpNeeded += 850;
            else if (i <= 8) xpNeeded += 750;
            else if (i == 9) xpNeeded += 300;
            else if (i <= 18) xpNeeded += 450;
            else if (i <= 56) xpNeeded += 150;
            else xpNeeded += 100;

            treeMap.put(i, xpNeeded);
        }
        OptionMap map = OptionMap.create();
        map.put("xpRequired", Option.of(treeMap));
        return map;
    }

    public int getExperienceNeeded(int level) {
        HashMap<?, ?> map = this.getOption("xpRequired").getMapValue();

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getKey() instanceof String) {
                int cfgLevel = Integer.parseInt((String) entry.getKey());
                if (entry.getValue() instanceof Number) {
                    if ((cfgLevel - 1) == level) return ((Number) entry.getValue()).intValue();
                }
            }
        }

        return Integer.MAX_VALUE;
    }
}

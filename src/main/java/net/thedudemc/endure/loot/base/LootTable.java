package net.thedudemc.endure.loot.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import net.thedudemc.endure.Endure;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class LootTable {

    @Expose protected WeightedList<LootItem> loot = new WeightedList<>();

    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    protected String root = "plugins/" + Endure.getPluginName() + "/loot/";
    protected String extension = ".json";

    public abstract String getName();

    protected abstract void reset();

    private File getDataFile() {
        return new File(this.root + this.getName() + this.extension);
    }

    public LootTable read() {
        try {
            return GSON.fromJson(new FileReader(this.getDataFile()), this.getClass());
        } catch (FileNotFoundException e) {
            this.generate();
        }

        return this;
    }

    public void generate() {
        this.reset();

        try {
            this.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() throws IOException {
        File dir = new File(this.root);
        if (!dir.exists() && !dir.mkdirs()) return;
        if (!this.getDataFile().exists() && !this.getDataFile().createNewFile()) return;
        FileWriter writer = new FileWriter(this.getDataFile());
        GSON.toJson(this, writer);
        writer.flush();
        writer.close();
    }

    public List<LootItem> getLoot(int amount) {
        List<LootItem> toReturn = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            toReturn.add(this.loot.getRandom(new Random()));
        }
        return toReturn;
    }


}

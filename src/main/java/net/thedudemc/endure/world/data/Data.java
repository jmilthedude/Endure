package net.thedudemc.endure.world.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.thedudemc.endure.Endure;

import java.io.*;

public abstract class Data {

    private static Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    protected String root = "plugins/" + Endure.getPluginName() + "/data/";
    protected String extension = ".json";

    private boolean dirty = true;

    public abstract String getName();

    protected abstract void reset();

    private File getDataFile() {
        return new File(this.root + this.getName() + this.extension);
    }

    public Data read() {
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

    public void save() {
        if (!this.isDirty()) return;
        try {
            this.write();
            this.dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDirty() {
        return dirty;
    }

    public void markDirty() {
        dirty = true;
    }
}

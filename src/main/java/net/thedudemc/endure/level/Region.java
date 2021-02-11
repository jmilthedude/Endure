package net.thedudemc.endure.level;

import org.bukkit.Location;
import org.bukkit.World;

public class Region {

    private Location center;
    private Area area;


    public Region(World world) {
        this.center = new Location(world, 0, 0, 0);
        this.area = new Area(-100, -100, 100, 100);
    }
}

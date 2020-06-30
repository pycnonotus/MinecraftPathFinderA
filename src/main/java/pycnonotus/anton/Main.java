package pycnonotus.anton;

import org.bukkit.plugin.java.JavaPlugin;
import pycnonotus.anton.Commands.PathFinder;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("blop blop blop 222");
        this.getCommand("path").setExecutor(new PathFinder());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package dev.rollczi.litekarma;

import org.bukkit.plugin.java.JavaPlugin;

public class LiteKarmaPlugin extends JavaPlugin {

    private LiteKarma liteKarma;

    @Override
    public void onEnable() {
        this.liteKarma = new LiteKarma(this);
        this.liteKarma.onEnable();
    }

    @Override
    public void onDisable() {
        this.liteKarma.onDisable();
    }

}

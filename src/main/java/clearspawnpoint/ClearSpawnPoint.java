package clearspawnpoint;

import clearspawnpoint.command.CommandClearSpawnPoint;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ClearSpawnPoint.MODID, version = ClearSpawnPoint.VERSION, name = ClearSpawnPoint.NAME, acceptableRemoteVersions = "*")
public class ClearSpawnPoint {
	
    public static final String MODID = "clearspawnpoint";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "ClearSpawnPoint";
    public static final Logger LOGGER = LogManager.getLogger();
	
	@Instance(MODID)
	public static ClearSpawnPoint instance;
	
	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandClearSpawnPoint());
	}
}
package clearspawnpoint.command;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandClearSpawnPoint extends CommandBase {
	
	@Override
	public String getName() {
		return "clearspawnpoint";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "/clearspawnpoint <player> <optional-dimensionid>";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length >= 1 && !args[0].isEmpty()) {
			EntityPlayerMP entityplayermp = server.getPlayerList().getPlayerByUsername(args[0]);
			
			if(entityplayermp == null) {
				throw new PlayerNotFoundException("The player specified was not found");
			}
			else {
				int dimension = 0;
				if(args.length >= 2 && !args[1].isEmpty()) {
					try {
						dimension = Integer.parseInt(args[1]);
					}
					catch(Exception ex) {
						throw new NumberInvalidException("Invalid dimension id");
					}
				}
				
				entityplayermp.setSpawnChunk(null, true, dimension);
				notifyCommandListener(sender, this, "Player spawnpoint cleared for dimension " + dimension);
			}
		}
		else {
			throw new WrongUsageException("A player name must be specified");
		}
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		return args.length >= 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
	}
}
package clearspawnpoint.command;

import net.minecraft.command.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
	public int getRequiredPermissionLevel() {
		return 2;
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "/clearspawnpoint <player> <optional-dimensionid>";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length >= 1 && !args[0].isEmpty()) {
			Entity entity = getEntity(server, sender, args[0]);
			
			if(entity instanceof EntityPlayer) {
				int dimension = 0;
				if(args.length >= 2 && !args[1].isEmpty()) {
					try {
						dimension = Integer.parseInt(args[1]);
					}
					catch(Exception ex) {
						throw new NumberInvalidException("Invalid dimension id");
					}
				}
				
				((EntityPlayer)entity).setSpawnChunk(null, true, dimension);
				notifyCommandListener(sender, this, "Player " + ((EntityPlayer)entity).getName() + "'s spawnpoint cleared for dimension " + dimension);
			}
			else {
				throw new PlayerNotFoundException("Valid player not found for given selector");
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
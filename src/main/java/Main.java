package mission;

import java.io.*;
import java.net.*;
//Java

import io.anuke.arc.*;
import io.anuke.arc.util.*;
//Arc

import io.anuke.mindustry.core.*;
import io.anuke.mindustry.core.GameState.*;
import io.anuke.mindustry.content.*;
import io.anuke.mindustry.entities.*;
import io.anuke.mindustry.entities.type.*;
import io.anuke.mindustry.game.*;
import io.anuke.mindustry.game.EventType;
import io.anuke.mindustry.gen.*;
import io.anuke.mindustry.io.*;
import io.anuke.mindustry.maps.Map;
import io.anuke.mindustry.maps.*;
import io.anuke.mindustry.net.Packets.KickReason;
import io.anuke.mindustry.net.NetConnection;
import io.anuke.mindustry.plugin.*;
import io.anuke.mindustry.plugin.Plugin;
import io.anuke.mindustry.type.*;
import io.anuke.mindustry.Vars;
import io.anuke.mindustry.world.*;
import io.anuke.mindustry.game.EventType.*;
//Mindustry

import static io.anuke.arc.Core.*;
import static io.anuke.mindustry.Vars.*;
import static io.anuke.mindustry.Vars.net;

public class Main extends Plugin{
	@Override
	public void registerClientCommands(CommandHandler handler){

		handler.<Player>register("mission","<mapname>","to mission", (args, player) -> {
			if(!player.isAdmin){
				player.sendMessage("no admins");
			} else {
					netServer.kickAll(KickReason.gameover);
					state.set(State.menu);
					net.closeServer();
					logic.reset();
	            try{
	                if("groundZero".equalsIgnoreCase(args[0])){
	                    playZone(Zones.groundZero);
	                }else if ("craters".equalsIgnoreCase(args[0])){
	                    playZone(Zones.craters);
	                }else if ("frozenForest".equalsIgnoreCase(args[0])){
	                    playZone(Zones.frozenForest);
	                }else if ("ruinousShores".equalsIgnoreCase(args[0])){
	                    playZone(Zones.ruinousShores);
	                }else if ("stainedMountains".equalsIgnoreCase(args[0])){
	                    playZone(Zones.stainedMountains);
	                }else if ("tarFields".equalsIgnoreCase(args[0])){
	                    playZone(Zones.tarFields);
	                }else if ("saltFlats".equalsIgnoreCase(args[0])){
	                    playZone(Zones.saltFlats);
	                }else if ("overgrowth".equalsIgnoreCase(args[0])){
	                    playZone(Zones.overgrowth);
	                }else if ("desolateRift".equalsIgnoreCase(args[0])){
	                    playZone(Zones.desolateRift);
	                }else if ("desertWastes".equalsIgnoreCase(args[0])){
	                    playZone(Zones.desertWastes);
	                }else if ("nuclearProductionComplex".equalsIgnoreCase(args[0])){
	                    playZone(Zones.nuclearComplex);
	                }else{
	                    playZone(Zones.nuclearComplex);
	                }

	                try{
						net.host(Core.settings.getInt("port"));
					}catch(BindException e){
						state.set(State.menu);
					}catch(IOException e){
						state.set(State.menu);
					}

	                }catch(MapException e){
	                }
			}
		});
		//It can be used normally. :)
	}

	public void playZone(Zone zone){
            logic.reset();
            world.loadGenerator(zone.generator);
            state.rules.zone = zone;
            for(Tile core : state.teams.get(defaultTeam).cores){
                for(ItemStack stack : zone.getStartingItems()){
                    core.entity.items.add(stack.item, stack.amount);
                }
            }
            state.set(State.playing);
            logic.play();
            Events.fire(Trigger.newGame);
    }

}

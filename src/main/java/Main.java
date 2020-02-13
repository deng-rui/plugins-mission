package mission;

import java.io.*;
import java.net.*;
//Java

import arc.*;
import arc.util.*;
//Arc
import mindustry.world.*;
import mindustry.game.EventType.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.core.GameState.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.type.*;
import mindustry.game.*;
import mindustry.game.Team;
import mindustry.game.Difficulty;
import mindustry.game.EventType;
import mindustry.game.EventType.PlayerJoin;
import mindustry.gen.*;
import mindustry.io.*;
import mindustry.maps.Map;
import mindustry.maps.*;
import mindustry.net.Administration.PlayerInfo ;
import mindustry.net.Packets.KickReason;
import mindustry.net.NetConnection;
import mindustry.net.Packets.KickReason ;
import mindustry.plugin.*;
import mindustry.plugin.Plugin;
import mindustry.type.*;
import mindustry.Vars;
import mindustry.content.Zones;
//Mindustry

import static arc.Core.*;
import static mindustry.Vars.*;
import static mindustry.Vars.net;

public class Main extends Plugin{
	@Override
	public void registerServerCommands(CommandHandler handler){

		handler.register("mission","<mapname>","to mission", arg -> {
			if(state.is(State.playing)){
				return;
			}
					netServer.kickAll(KickReason.gameover);
					state.set(State.menu);
				try{
					if("groundZero".equalsIgnoreCase(arg[0])){
						playZone(Zones.groundZero);
					}else if ("craters".equalsIgnoreCase(arg[0])){
						playZone(Zones.craters);
					}else if ("frozenForest".equalsIgnoreCase(arg[0])){
						playZone(Zones.frozenForest);
					}else if ("ruinousShores".equalsIgnoreCase(arg[0])){
						playZone(Zones.ruinousShores);
					}else if ("stainedMountains".equalsIgnoreCase(arg[0])){
						playZone(Zones.stainedMountains);
					}else if ("tarFields".equalsIgnoreCase(arg[0])){
						playZone(Zones.tarFields);
					}else if ("saltFlats".equalsIgnoreCase(arg[0])){
						playZone(Zones.saltFlats);
					}else if ("overgrowth".equalsIgnoreCase(arg[0])){
						playZone(Zones.overgrowth);
					}else if ("desolateRift".equalsIgnoreCase(arg[0])){
						playZone(Zones.desolateRift);
					}else if ("desertWastes".equalsIgnoreCase(arg[0])){
						playZone(Zones.desertWastes);
					}else if ("nuclearProductionComplex".equalsIgnoreCase(arg[0])){
						playZone(Zones.nuclearComplex);
					}else{
						playZone(Zones.nuclearComplex);
					}

					try{
						netServer.openServer();
						System.out.println("mindustry mission to start");
					}catch(Exception e){
						state.set(State.menu);
					}

					}catch(MapException e){
					}
		});
		//It can be used normally. :)
	}

	public void playZone(Zone zone){
			//ui.loadAnd(() -> {
				logic.reset();
				//net.reset();
				world.loadGenerator(zone.generator);
				zone.rules.get(state.rules);
				state.rules.zone = zone;
				for(TileEntity core : state.teams.playerCores()){
					for(ItemStack stack : zone.getStartingItems()){
						core.items.add(stack.item, stack.amount);
					}
				}
				state.set(State.playing);
				state.wavetime = state.rules.waveSpacing;
				//saves.zoneSave();
				logic.play();
				Events.fire(Trigger.newGame);
			//});
	}

}

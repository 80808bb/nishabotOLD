package main;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import entidades.FotoRandom;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import utilidades.*;

public class NishabotMain extends ListenerAdapter {
	private static JDA nishabot;
	private static String rutaNishabot;
	private static Properties config;
	private static FicherosNishabot objFicheros;
	private static Guild objGuild;
	private static Role rolNegro;


	public static void main(String[] args) throws LoginException, InterruptedException {
		rutaNishabot = "/home/sancho/Proyectos/nishabot/test/";
		//rutaNishabot = "/home/sancho/nishabot/";
		config = Utilidades.leerPropiedades(rutaNishabot + "config.xml");
		nishabot = new JDABuilder(AccountType.BOT).setToken(config.getProperty("token")).buildBlocking();
		nishabot.getPresence().setPresence(Game.listening("!nisha"), false);
		nishabot.addEventListener(new NishabotMain());

		objFicheros = new FicherosNishabot(rutaNishabot + "ficheros/");
		objGuild = nishabot.getGuildById(config.getProperty("idguild"));
		rolNegro = objGuild.getRolesByName("campeón del negro", true).get(0);
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if(!e.getMessage().getContentRaw().startsWith("!")) return;
		if(e.getAuthor().isBot()) return;

		String[] arrArgs = e.getMessage().getContentRaw().substring(1).toLowerCase().split(" ");
		String comando = arrArgs[0];

		switch(comando) {
			case "nisha":
				if(comprobarSpam())
					enviarFotoNisha(e);
				else
					e.getMessage().addReaction(e.getGuild().getEmotesByName("thonkang", true).get(0)).queue();
				break;
			case "ntop":
				verRankNegros(e);
				break;
			case "negro":
				verUltimoNegro(e);
				break;
			case "nhelp":
				listarComandos(e);
				break;
			case "vote":
				vote(e.getMessage());
				break;
			case "pole":
				e.getChannel().sendFile(new File(rutaNishabot + "/fotosRandom/primerNegro.jpg")).queue();
				break;
			case "nisa":
				e.getChannel().sendFile(new File(rutaNishabot + "/fotosRandom/nisa.jpg")).queue();
				break;
			case "negrata":
				if(e.getAuthor().equals(objGuild.getMemberById("165202663128956929").getUser())) { // ID Sancho
					cambiarNegro(e.getChannel(), objGuild.getMemberById(arrArgs[1]));
				}
				break;
		}

	}

	private void enviarFotoNisha(MessageReceivedEvent e) {
		FotoRandom fotoNueva = new FotoRandom(rutaNishabot + "nishafotos/", Integer.parseInt(config.getProperty("numFotos")));
		e.getChannel().sendFile(fotoNueva.getFoto()).queue();
		if(fotoNueva.getNumRandom() == Integer.parseInt(config.getProperty("numNegro"))) {
			cambiarNegro(e.getChannel(), e.getMember());
		}
	}

	private static long msMensajeUltimo;
	private boolean comprobarSpam() {
		long msLimite = 2000;
		long msMensajeNuevo = System.currentTimeMillis() - msMensajeUltimo;
		if(msMensajeNuevo >= msLimite) {
			msMensajeUltimo = System.currentTimeMillis();
			return true;
		}
		return false;
	}

	private static long msMensajeUltimo2;
	private boolean comprobarSpam2() {
		long msLimite = 60000;
		long msMensajeNuevo = System.currentTimeMillis() - msMensajeUltimo2;
		if(msMensajeNuevo >= msLimite) {
			msMensajeUltimo2 = System.currentTimeMillis();
			return true;
		}
		return false;
	}

	private void cambiarNegro(MessageChannel mc, Member miembro) {
		if(tieneNegro(miembro)) return;

		List<Member> listaNegros = objGuild.getMembersWithRoles(rolNegro);
		for(Member negro : listaNegros) {
			objGuild.getController().removeRolesFromMember(negro, rolNegro).queue();
		}

		if(new File(objFicheros.getRutaUltimoNegro()).exists()) {
			User negroAnterior = objGuild.getMemberById(objFicheros.leerUltimoNegro()[0]).getUser();
			objFicheros.escribirRankNegros(negroAnterior);
		}
		objFicheros.escribirUltimoNegro(miembro.getUser());
		objGuild.getController().addSingleRoleToMember(miembro, rolNegro).queue();

		String mensajeNuevoNegro = "---" + miembro.getAsMention() + " es ahora el negro---";
		mc.sendMessage(mensajeNuevoNegro).queue();

	}

	private boolean tieneNegro(Member objMember) {
		for(Role rol : objMember.getRoles()) {
			if(rol == rolNegro) {
				return true;
			}
		}
		return false;
	}

	private void verUltimoNegro(MessageReceivedEvent e) {
		String[] arr = objFicheros.leerUltimoNegro();
		if(arr != null) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setColor(java.awt.Color.YELLOW);
			eb.setTitle("EL NEGRO ACTUAL 📦");
			eb.setThumbnail(e.getGuild().getMemberById(arr[0]).getUser().getAvatarUrl());
			eb.addField(e.getGuild().getMemberById(arr[0]).getEffectiveName(),
					Utilidades.msADias(System.currentTimeMillis() - Long.parseLong(arr[1])),
					false);
			e.getChannel().sendMessage(eb.build()).queue();
		}
	}

	private void verRankNegros(MessageReceivedEvent e) {
		String[] arr = objFicheros.leerRankNegros();
		if(arr != null) {
			int longitud = arr.length;
			int posicion = 0;
			EmbedBuilder eb = new EmbedBuilder();
			eb.setColor(java.awt.Color.YELLOW);
			eb.setTitle("TOP NEGROS S2 👑");

			for(int i = 0; i < longitud && i < 20; i += 2) {
				posicion++;
				eb.addField(posicion + ". " + e.getGuild().getMemberById(arr[i]).getEffectiveName(),
						Utilidades.msADias(Long.parseLong(arr[i + 1])),
						false);
			}
			eb.setFooter("Se actualiza cuando le quitan el negro a alguien.", nishabot.getSelfUser().getAvatarUrl());
			e.getChannel().sendMessage(eb.build()).queue();
		}else {
			e.getChannel().sendMessage("Todavía no hay negros en la lista.").queue();
		}
	}

	private void vote(Message m) {
		m.addReaction("⬆").queue();
		m.addReaction("⬇").queue();
		m.addReaction(objGuild.getEmotesByName("esvastica", true).get(0)).queue();
	}
	
	private void listarComandos(MessageReceivedEvent e) {
		EmbedBuilder eb = new EmbedBuilder();
		
		eb.setColor(java.awt.Color.WHITE);
		eb.setTitle("COMANDOS DISPONIBLES 🐶");
		eb.addField("!nisha", "!nisha", false);
		eb.addField("!negro", "Muestra el negro actual y cuánto tiempo lleva siéndolo", false);
		eb.addField("!ntop", "Top negros", false);
		eb.addField("!vote", "Para hacer votaciones", false);

		e.getChannel().sendMessage(eb.build()).queue();
	}
	
}
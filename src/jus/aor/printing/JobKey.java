/**
 * J<i>ava</i> U<i>tilities</i> for S<i>tudents</i>
 */
package jus.aor.printing;

import java.lang.management.ManagementFactory;
import java.util.Date;

/**
 * Représentation d'une identification de Job (travail d'impression), l'identification de la JVM cliente et
 * la date en millisecondes doivent suffire pour être unique.
 * @author Morat
 */
public class JobKey {
	/** l'identification de la jvm */	
	public String serverId;
	/** la date */
	public long date;
	/**
	 * construit un JobKey
	 */
	public JobKey() {
		this(new Date().getTime());
	}
	/**
	 * construit un JobKey ayant la date date fournie.
	 * @param date la date du job
	 */
	public JobKey(long date) {
		this.serverId=ManagementFactory.getRuntimeMXBean().getName();
		this.date = date;
	}
	/**
	 * Construit un JobKey à partir du tableau de bytes représentant la forme textuelle
	 * du JobKey
	 * @param marshal le tableau de bytes de la forme textuelle
	 */
	public JobKey(byte[] marshal) {
		String s = new String(marshal);
		String[] valeur = s.split("�");
		
		this.serverId = valeur[0];
		this.date = Long.parseLong(valeur[1]);	
	}
	/**
	 * restitue le tableau de bytes de la forme textuelle.
	 * @return le tableau de bytes de la forme textuelle
	 */
	public byte[] marshal() {
		return this.toString().getBytes();
	}
	/**
	 * la forme textuelle : "<<b>serverId</b>>¤<<b>date</b>>"
	 * où <<b>X</b>> est la repésentation textuelle de l'attribut correspondant
	 */
	public String toString() { return (serverId+"�"+date);}
	/**
	 * L'équivalence de 2 JobKey, si jobId est vide il n'est pas pris en compte,
	 * si date est 0 elle n'est pas prise en compte.
	 * @param other l'autre opérande
	 * @return vrai si l'équivalence est vérifiée, faux sinon.
	 */
	public boolean equals(Object other){
		if(!(other instanceof JobKey)) return false;
		JobKey o = (JobKey)other;
		if(!serverId.equals(o.serverId)) return false;
		if(o.date==0) return true;
		return date==o.date;
	}
}

package wargame;

import java.util.List;


/**<b>Apatient est l'interface qui gère les processus .</b>
* Elle est caractérisée par :
* <ul>
* <li>Une liste de processus.</li>
* </ul>
* @see AttaqueSoldatCorps
* @author AKIL M., BAYAZID H., AMIROUCHE A.
*/


public abstract class APatient extends Thread {
	/**
	 * La liste des processus
	 * @see DeplacementSoldat
	 */
	protected List<Thread> processusAttendre;
	
	
	/**
	 * Attend la fin des threads
	 * 
	 * @see TourOrdi
	 */
	public void attendre() {
		while(MethodesAuxiliaires.threadVivant(processusAttendre));
	}
}

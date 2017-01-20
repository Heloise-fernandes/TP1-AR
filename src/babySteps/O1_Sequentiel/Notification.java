package babySteps.O1_Sequentiel;
import java.io.Serializable;

/**
 * l'ensemble des notifications pouvant �tre �chang�es entre le client et le serveur.
 * @author Morat 
 */
public enum Notification implements Serializable {
	QUERY_PRINT, REPLY_PRINT_OK;
}


package Controler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Model.Equipe;
import Model.Gymnase;
import Model.Match;
import Model.Poule;

public class GestionBDD {
	private static Connection conn;

	public static void initBDD() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver O.K.");

			String url = "jdbc:mysql://localhost:3306/projetlibre";
			String user = "root";
			String passwd = "";

			conn = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connexion effective !");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ajouterGymnase(String nom, int capacite) {
		try {
			PreparedStatement state = conn.prepareStatement("INSERT INTO gymnase (nom, capacite) VALUES (?, ?);");
			state.setString(1, nom);
			state.setInt(2, capacite);

			state.executeUpdate();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void supprimerGymnase(String nom) {
		try {
			PreparedStatement state = conn.prepareStatement("DELETE FROM gymnase WHERE nom = ?;");
			state.setString(1, nom);

			state.executeUpdate();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Gymnase trouverGymnase(String nom) {
		Gymnase gymnase = null;
		try {
			PreparedStatement state = conn.prepareStatement("SELECT * FROM gymnase WHERE nom = ?;");
			state.setString(1, nom);
			ResultSet result = state.executeQuery();

			if (result.first()) {
				gymnase = new Gymnase(result.getInt("id"), result.getString("nom"), result.getInt("capacite"));
			}

			result.close();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gymnase;
	}

	public static int trouverIdGymnase(String nom) {
		int idGymnase = -1;
		try {
			PreparedStatement state = conn.prepareStatement("SELECT id FROM gymnase WHERE nom = ?;");
			state.setString(1, nom);
			ResultSet result = state.executeQuery();

			if (result.first())
				idGymnase = result.getInt("id");

			result.close();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return idGymnase;
	}

	public static Gymnase trouverGymnaseEquipe(Equipe equipe) {
		Gymnase gymnase = null;
		try {
			PreparedStatement state = conn.prepareStatement(
					"SELECT * FROM gymnase WHERE id IN (SELECT idGymnase FROM equipe WHERE idGymnase = ?)");
			state.setInt(1, equipe.getIdGymnase());
			ResultSet result = state.executeQuery();

			if (result.first()) {
				gymnase = new Gymnase(result.getInt("id"), result.getString("nom"), result.getInt("capacite"));
			}
			result.close();
			state.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return gymnase;
	}

	public static ArrayList<Gymnase> trouverTousGymnases() {
		ArrayList<Gymnase> gymnases = new ArrayList<>();
		try {
			PreparedStatement state = conn.prepareStatement("SELECT * FROM gymnase");
			ResultSet result = state.executeQuery();

			while (result.next()) {
				gymnases.add(new Gymnase(result.getInt("id"), result.getString("nom"), result.getInt("capacite")));
			}

			result.close();
			state.close();
		} catch (Exception e) {
			return null;
		}
		return gymnases;
	}

	public static void ajouterEquipe(String nom, String nomGymnase, String nomPoule) {
		try {
			PreparedStatement state = conn
					.prepareStatement("INSERT INTO equipe (nom, idGymnase, idPoule) VALUES (?, ?, ?);");
			state.setString(1, nom);
			state.setInt(2, trouverIdGymnase(nomGymnase));
			state.setInt(3, trouverIdPoule(nomPoule));

			state.executeUpdate();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void supprimerEquipe(String nom) {
		try {
			PreparedStatement state = conn.prepareStatement("DELETE FROM equipe WHERE nom = ?;");
			state.setString(1, nom);

			state.executeUpdate();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Equipe trouverEquipe(String nom) {
		Equipe equipe = null;
		try {
			PreparedStatement state = conn.prepareStatement("SELECT * FROM equipe WHERE nom = ?;");
			state.setString(1, nom);
			ResultSet result = state.executeQuery();

			if (result.first()) {
				equipe = new Equipe(result.getInt("id"), result.getString("nom"), result.getInt("idGymnase"),
						result.getInt("idPoule"));
			}

			result.close();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return equipe;
	}

	public static Equipe trouverEquipeId(int id) {
		Equipe equipe = null;
		try {
			PreparedStatement state = conn.prepareStatement("SELECT * FROM equipe WHERE id = ?;");
			state.setInt(1, id);
			ResultSet result = state.executeQuery();

			if (result.first()) {
				equipe = new Equipe(result.getInt("id"), result.getString("nom"), result.getInt("idGymnase"),
						result.getInt("idPoule"));
			}

			result.close();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return equipe;
	}

	public static void ajouterMatch(int idEquipeDom, int idEquipeExt, int journee) {
		try {
			PreparedStatement state = conn
					.prepareStatement("INSERT INTO matchs (idEquipeDom, idEquipeExt, journee) VALUES (?, ?, ?);");
			state.setInt(1, idEquipeDom);
			state.setInt(2, idEquipeExt);
			state.setInt(3, journee);

			state.executeUpdate();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void supprimerMatch(int journee, int idEquipe) {
		try {
			PreparedStatement state = conn
					.prepareStatement("DELETE FROM matchs WHERE journee = ? AND (idEquipeDom = ? OR idEquipeExt = ?);");
			state.setInt(1, journee);
			state.setInt(2, idEquipe);
			state.setInt(3, idEquipe);

			state.executeUpdate();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Match trouverMatch(int journee, int idEquipe) {
		Match match = null;
		try {
			PreparedStatement state = conn.prepareStatement(
					"SELECT * FROM matchs WHERE journee = ? AND (idEquipeDom = ? OR idEquipeExt = ?);");
			state.setInt(1, journee);
			state.setInt(2, idEquipe);
			state.setInt(3, idEquipe);
			ResultSet result = state.executeQuery();

			if (result.first()) {
				match = new Match(result.getInt("id"), result.getInt("idEquipeDom"), result.getInt("idEquipeExt"),
						result.getInt("journee"));
			}

			result.close();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return match;
	}

	public static void ajouterPoule(String nom) {
		try {
			PreparedStatement state = conn.prepareStatement("INSERT INTO poule (nom) VALUES (?);");
			state.setString(1, nom);

			state.executeUpdate();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void supprimerPoule(String nom) {
		try {
			PreparedStatement state = conn.prepareStatement("DELETE FROM poule WHERE nom = ?;");
			state.setString(1, nom);

			state.executeUpdate();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Poule trouverPoule(String nom) {
		Poule poule = null;
		try {
			PreparedStatement state = conn.prepareStatement("SELECT * FROM poule WHERE nom = ?;");
			state.setString(1, nom);
			ResultSet result = state.executeQuery();

			if (result.first()) {
				poule = new Poule(result.getInt("id"), result.getString("nom"));
			}

			result.close();
			state.close();
		} catch (Exception e) {
			return poule;
		}
		return poule;
	}

	public static int trouverIdPoule(String nom) {
		int idPoule = -1;
		try {
			PreparedStatement state = conn.prepareStatement("SELECT id FROM poule WHERE nom = ?;");
			state.setString(1, nom);
			ResultSet result = state.executeQuery();

			if (result.first()) {
				idPoule = result.getInt("id");
			}

			result.close();
			state.close();
		} catch (Exception e) {
			return idPoule;
		}
		return idPoule;
	}

	public static ArrayList<Poule> trouverToutesPoules() {
		ArrayList<Poule> poules = new ArrayList<>();
		try {
			PreparedStatement state = conn.prepareStatement("SELECT * FROM poule");
			ResultSet result = state.executeQuery();

			while (result.next()) {
				poules.add(new Poule(result.getInt("id"), result.getString("nom")));
			}

			result.close();
			state.close();
		} catch (Exception e) {
			return null;
		}
		return poules;
	}

	public static ArrayList<Match> trouverMatchJournee(Poule poule, int journee) {
		ArrayList<Match> matchs = new ArrayList<>();
		try {
			PreparedStatement state = conn.prepareStatement(
					"SELECT * FROM matchs WHERE journee = ? AND idEquipeDom IN (SELECT id FROM equipe WHERE idPoule = ?); ");
			state.setInt(1, journee);
			state.setInt(2, poule.getId());
			ResultSet result = state.executeQuery();

			while (result.next())
				matchs.add(new Match(result.getInt("id"), result.getInt("idEquipeDom"), result.getInt("idEquipeExt"),
						journee));

			result.close();
			state.close();
		} catch (Exception e) {
			return null;
		}
		return matchs;
	}

	public static ArrayList<Equipe> trouverEquipesParPoule(String nomPoule) {
		ArrayList<Equipe> equipes = new ArrayList<>();
		try {
			PreparedStatement state = conn.prepareStatement("SELECT * FROM equipe WHERE idPoule = ?;");
			state.setInt(1, trouverIdPoule(nomPoule));
			ResultSet result = state.executeQuery();

			while (result.next()) {
				equipes.add(new Equipe(result.getInt("id"), result.getString("nom"), result.getInt("idGymnase"),
						result.getInt("idPoule")));
			}

			result.close();
			state.close();
		} catch (Exception e) {
			return null;
		}
		return equipes;
	}

	public static Integer trouverNbJournee(Poule poule) {
		int nbJournee = 0;
		try {
			PreparedStatement state = conn.prepareStatement(
					"SELECT MAX(journee) FROM matchs WHERE idEquipeDom IN (SELECT id FROM equipe WHERE idPoule = ?) ;");
			state.setInt(1, poule.getId());
			ResultSet result = state.executeQuery();

			while (result.next())
				nbJournee = result.getInt("MAX(journee)");

			result.close();
			state.close();
		} catch (Exception e) {
			return 0;
		}
		return nbJournee;
	}

	public static void nettoyerBDD() {
		try {
			PreparedStatement state = conn.prepareStatement("DELETE FROM poule;");
			state.executeUpdate();

			state = conn.prepareStatement("DELETE FROM gymnase;");
			state.executeUpdate();

			state = conn.prepareStatement("DELETE FROM equipe;");
			state.executeUpdate();

			state = conn.prepareStatement("DELETE FROM matchs;");
			state.executeUpdate();

			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

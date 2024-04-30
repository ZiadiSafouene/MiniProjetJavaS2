package application;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.beans.binding.IntegerExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.fxml.Initializable;
import javafx.scene.layout.ColumnConstraints;

public class Maincontroller implements Initializable{
	Statement statement;
	Statement statement2;
	Statement statement3;
//	FXMLLoader loader = new FXMLLoader(getClass().getResource("./ListEtudiants.fxml"));
	
	// --------------- DECLARATION DES TABLES ET DES COLONNES -----------------
	
	@FXML
	private TableView<ModelEtudiant> tableView;
	
	
	
	@FXML
    TableColumn<ModelEtudiant, String> cin;
	
	@FXML
    TableColumn<ModelEtudiant, String> nom;
	@FXML
    TableColumn<ModelEtudiant, String> prenom;
	@FXML
    TableColumn<ModelEtudiant, String> branche;
	@FXML
	javafx.collections.ObservableList<ModelEtudiant> data = FXCollections.observableArrayList();
	
	
	
	@FXML
	private TableView<ModelEnseignant> tableView2;
	
	
	@FXML
    TableColumn<ModelEnseignant, String> cin2;
	
	@FXML
    TableColumn<ModelEnseignant, String> nom2;
	@FXML
    TableColumn<ModelEnseignant, String> prenom2;
	@FXML
    TableColumn<ModelEnseignant, String> matiere;
	@FXML
    TableColumn<ModelEnseignant, String> departement;
	@FXML
	javafx.collections.ObservableList<ModelEnseignant> data2 = FXCollections.observableArrayList();
	
	@FXML
	private TableView<ModelPFE> tableView3;
	
	
	@FXML
    TableColumn<ModelPFE, String> id;
	@FXML
    TableColumn<ModelPFE, String> etudiant1;
	@FXML
    TableColumn<ModelPFE, String> etudiant2;
	@FXML
    TableColumn<ModelPFE, String> heure;
	@FXML
    TableColumn<ModelPFE, String> local;
	@FXML
    TableColumn<ModelPFE, String> date;
	@FXML
    TableColumn<ModelPFE, String> idjury;
	@FXML
    TableColumn<ModelPFE, String> etat;
	@FXML
	javafx.collections.ObservableList<ModelPFE> data3 = FXCollections.observableArrayList();
	@FXML
	TableColumn<ModelPFE, Void> editColumn = new TableColumn<>("Edit");
	@FXML
	TableColumn<ModelEnseignant, Void> editColumn1 = new TableColumn<>("Edit");
	@FXML
	TableColumn<ModelEtudiant, Void> editColumn3 = new TableColumn<>("Edit");

	
	
	
	//------------- CONNEXION A LA BASE DE DONNEES--------------
	
	public Maincontroller() {
		try {
		String url = "jdbc:mysql://localhost:3306/mydb";
	    String username = "root";
	    String password = "s.121003.z";
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    // Open a connection
	    Connection connection = DriverManager.getConnection(url, username, password);
	    // Create a statement
	    statement = connection.createStatement();
	    statement2=connection.createStatement();
	    statement3=connection.createStatement();
	    // Close resources
//	    resultSet.close();
//	    statement.close();
//	    connection.close();
		}catch(ClassNotFoundException | SQLException e) {
			System.out.println("error ");
		}
		
		
	}
	
	//---------- INITIALISER COMBOBOX AJOUPFE1 ------
	
	@FXML
    private ComboBox<String> L;
	@FXML
    private ComboBox<String> H;
	@FXML
    private ComboBox<String> E1;
	@FXML
    private DatePicker D = new DatePicker();
	
	@FXML
    private ComboBox<String> Et;
	
	private ObservableList<String> Local = FXCollections.observableArrayList(
            "Amphi K",
            "Amphi B",
            "C11",
            "C01"
    );
	
	private ObservableList<String> Heure = FXCollections.observableArrayList(
            "8:00 AM",
            "9:00 AM",
            "10:00 AM",
            "11:00 AM",
            "12:00 PM",
            "13:00 PM",
            "14:00 PM"
    );
	private ObservableList<String> Etat = FXCollections.observableArrayList(
            "Pas encore passée",
            "Validée",
            "Pas validée"
    );
	
	private ObservableList<String> Etudiants = FXCollections.observableArrayList();
	private ObservableList<String> Enseignants = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
    
        L.setItems(Local);
        H.setItems(Heure);
        if(Et!=null) {
        	Et.setItems(Etat);
        	
        }
    } 
    public void initializeE() {
    	E1.getItems().clear();
    	try {
    		ResultSet resultSet;
			resultSet = statement.executeQuery("SELECT cin FROM etudiant");
			
			 while (resultSet.next()) {
                 Boolean state = Etudiants.add(resultSet.getString("cin"));
             }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(Etudiants);
    	E1.setItems(Etudiants);
    	
    }
    
    //-----SELECT JURY--------
    
    
    @FXML
    private ComboBox<String> R;
	@FXML
    private ComboBox<String> EX;
	@FXML
    private ComboBox<String> P;
	@FXML
    private ComboBox<String> EN ;
	
    public void initializeEN() {
    	ObservableList<String> Enseignants = FXCollections.observableArrayList();
    	EN.getItems().clear();
    	try {
    		ResultSet resultSet;
			resultSet = statement.executeQuery("SELECT cin FROM enseignant");
			
			 while (resultSet.next()) {
                 Boolean state = Enseignants.add(resultSet.getString("cin"));
             }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(Enseignants);
    	EN.setItems(Enseignants);
    	
    }
    public void initializeP() {
    	ObservableList<String> Enseignants = FXCollections.observableArrayList();
    	P.getItems().clear();
    	
    	try {
    		ResultSet resultSet;
			resultSet = statement.executeQuery("SELECT cin FROM enseignant");
			
			 while (resultSet.next()) {
                 Boolean state = Enseignants.add(resultSet.getString("cin"));
             }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(Enseignants);
    	
    	P.setItems(Enseignants);
    	
    	
    }
    public void initializeEX() {
    	ObservableList<String> Enseignants = FXCollections.observableArrayList();
    	EX.getItems().clear();
    	
    	try {
    		ResultSet resultSet;
			resultSet = statement.executeQuery("SELECT cin FROM enseignant");
			
			 while (resultSet.next()) {
                 Boolean state = Enseignants.add(resultSet.getString("cin"));
             }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(Enseignants);

    	EX.setItems(Enseignants);
    	
    }
    public void initializeR() {
    	ObservableList<String> Enseignants = FXCollections.observableArrayList();
    	R.getItems().clear();
    	try {
    		ResultSet resultSet;
			resultSet = statement.executeQuery("SELECT cin FROM enseignant");
			
			 while (resultSet.next()) {
                 Boolean state = Enseignants.add(resultSet.getString("cin"));
             }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(Enseignants);
    	
    	R.setItems(Enseignants);
    	
    }
    public static String idjur;
    boolean t3ada=true;
    public void validerJury() {
    	t3ada =true;
    	ResultSet resultSet;
	    String pr = P.getValue();
	    String ex = EX.getValue();
	    String r = R.getValue();
	    String en = EN.getValue();
	    int generatedId=0;
	    String sql = "INSERT INTO jury(idP,idEX,idR,idEN,ListV) VALUES(?, ?, ?, ?, null)";
        try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql,statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1,pr);
            pstmt.setString(2, ex);
            pstmt.setString(3, r);
            pstmt.setString(4, en);
            System.out.println("heere");
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
                System.out.println(generatedId);
                }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql1 = "UPDATE pfe SET JuryId = ? WHERE idPFE = ?";
        try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql1)) {
            pstmt.setString(1,""+generatedId);
            pstmt.setString(2, ""+idpfeTabdil);
            System.out.println("heere");
            
            pstmt.executeUpdate();
          
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        switchListPFE();
    }
    private int IDpfe=0;
	
	public void ajouterPfe1() {
		ResultSet resultSet;
		ResultSet rs;
	    String etud1 = E1.getValue();
	    String dat = D.getValue().toString();
	    String loca = L.getValue();
	    String heur = H.getValue();
	    idjur=etud1;
	    String sql1 = "SELECT * FROM pfe WHERE date= ? AND heure= ? AND local = ?";
        
        try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql1)) {
            pstmt.setString(1, dat);
            pstmt.setString(2, heur);
            pstmt.setString(3, loca);
          
           rs=pstmt.executeQuery();
           Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
           confirmationAlert.setTitle("Error");
           confirmationAlert.setHeaderText("N'est pas possible d'ajouter cette PFE");
           
           ButtonType buttonTypeYes = new ButtonType("Retour");
           confirmationAlert.getButtonTypes().setAll(buttonTypeYes);
           if (rs.next()) { confirmationAlert.showAndWait().ifPresent(response -> {
               if (response == buttonTypeYes) {
                   System.out.println("User clicked OK");
                   switchToAjoutPFE0();
                   t3ada=false;
                   } 
           });
           
           
           }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        
	    String sql = "INSERT INTO pfe(etudiant1,etudiant2,date,heure,local,etat,JuryId) VALUES(?,null, ?, ?, ?,0,null )";
        try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            pstmt.setString(1, etud1);
            pstmt.setString(2, dat);
            pstmt.setString(3, heur);
            pstmt.setString(4, loca);
            
          
           pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
       if (t3ada) {
        	switchSelectJury();
        }
	    
		
		
	}
	
	//----------- INITIALISER COMBOBOX AJOUTPFE 2
	@FXML
    private ComboBox<String> L1;
	@FXML
    private ComboBox<String> H1;
	@FXML
    private ComboBox<String> E11;
	@FXML
    private ComboBox<String> EN1 ;
	@FXML
	private ComboBox<String> E12;
	@FXML
    private DatePicker D1 = new DatePicker();
	
	private ObservableList<String> Local1 = FXCollections.observableArrayList(
            "Amphi K",
            "Amphi B",
            "C11",
            "C01"
    );
	
	private ObservableList<String> Heure1 = FXCollections.observableArrayList(
			"8:00 AM",
            "9:00 AM",
            "10:00 AM",
            "11:00 AM",
            "12:00 PM",
            "13:00 PM",
            "14:00 PM"
    );
	private ObservableList<String> Etudiants1 = FXCollections.observableArrayList();
	private ObservableList<String> Etudiants2 = FXCollections.observableArrayList();
	private ObservableList<String> Enseignants1 = FXCollections.observableArrayList();

    @FXML
    public void initialize2() {
    
        L1.setItems(Local1);
        H1.setItems(Heure1);
    } 
    public void initializeE2() {
    	E11.getItems().clear();
    	try {
    		ResultSet resultSet;
			resultSet = statement.executeQuery("SELECT cin FROM etudiant");
			
			 while (resultSet.next()) {
                 Boolean state = Etudiants1.add(resultSet.getString("cin"));
             }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(Etudiants1);
    	E11.setItems(Etudiants1);
    	
    }
    public void initializeE12() {
    	E12.getItems().clear();
    	try {
    		ResultSet resultSet;
			resultSet = statement.executeQuery("SELECT cin FROM etudiant");
			
			 while (resultSet.next()) {
                 Boolean state = Etudiants2.add(resultSet.getString("cin"));
             }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(Etudiants2);
    	E12.setItems(Etudiants2);
    	
    }
    public void initializeEN2() {
    	EN1.getItems().clear();
    	try {
    		ResultSet resultSet;
			resultSet = statement.executeQuery("SELECT cin FROM enseignant");
			
			 while (resultSet.next()) {
                 Boolean state = Enseignants1.add(resultSet.getString("cin"));
             }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(Enseignants1);
    	EN1.setItems(Enseignants1);
    	
    }
    boolean t3ada2=true;
    static int idpfeTabdil; 
	public void ajouterPfe2() {
		ResultSet rs;
		ResultSet resultSet;
	    String etud1 = E11.getValue();
	    String dat = D1.getValue().toString();
	    String loca = L1.getValue();
	    String heur = H1.getValue();
	    String etud2 = E12.getValue();
	    idjur=etud1;
	    String sql1 = "SELECT * FROM pfe WHERE date= ? AND heure= ? AND local = ?";
        
        try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql1)) {
            pstmt.setString(1, dat);
            pstmt.setString(2, heur);
            pstmt.setString(3, loca);
          
           rs=pstmt.executeQuery();
           Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
           confirmationAlert.setTitle("Error");
           confirmationAlert.setHeaderText("N'est pas possible d'ajouter cette PFE");
           
           ButtonType buttonTypeYes = new ButtonType("Retour");
           confirmationAlert.getButtonTypes().setAll(buttonTypeYes);
           if (rs.next()) { confirmationAlert.showAndWait().ifPresent(response -> {
               if (response == buttonTypeYes) {
                   System.out.println("User clicked OK");
                   switchToAjoutPFE0();
                   t3ada2=false;
                   } 
           });
           
           
           }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String sql = "INSERT INTO pfe(etudiant1,etudiant2,date,heure,local,etat,JuryId) VALUES(?, ?, ?, ?, ?, 0,null)";
        try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql,statement.RETURN_GENERATED_KEYS)) {
           
            pstmt.setString(1, etud1);
            pstmt.setString(2, etud2);
            pstmt.setString(3, dat);
            pstmt.setString(4, heur);
            pstmt.setString(5, loca);
            
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                System.out.println(generatedId);
                idpfeTabdil=generatedId;
            }
            	
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(t3ada2) {switchSelectJury();}
	      
		
		
		
	}
	public void ajoutEtudiant() {

		String nom = n.getText();
	    String prenom = p.getText();
	    String cin = c.getText();
	    String branche = b.getText();
	    
		StringBuilder whereClause = new StringBuilder("WHERE 1=1");
		
		
		 if (!nom.isEmpty()) {
	            whereClause.append(" AND nom = ?");
	        }
	     if (!prenom.isEmpty()) {
	            whereClause.append(" AND prenom = ?");
	     }
	     if (!cin.isEmpty()) {
	            whereClause.append(" AND cin = ?");
	     }
	     if (!branche.isEmpty()) {
	            whereClause.append(" AND branche = ?");
	     }
		
		try {
			String sql = "INSERT INTO etudiant VALUES (? , ? , ? , ? )";
			PreparedStatement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql);
			

			
			int parameterIndex = 1;
			 if (!cin.isEmpty()) {
	            	statement.setString(parameterIndex++, cin);
	            }
            if (!nom.isEmpty()) {
                statement.setString(parameterIndex++, nom);
            }
            if (!prenom.isEmpty()) {
                statement.setString(parameterIndex++, prenom);
            }
           
            if (!branche.isEmpty()) {
            	statement.setString(parameterIndex++, branche);
            }
            
            
            statement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switchListEtudiants();
	}
	
	//----------- VALIDER RECHERCHE ETUDIANT-------------
	
	public void ok() {
			tableView.getItems().clear();
			ResultSet resultSet;
			nom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
			prenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
			cin.setCellValueFactory(cellData -> cellData.getValue().cinProperty());
			branche.setCellValueFactory(cellData -> cellData.getValue().brancheProperty());
			
			
			String nom = n.getText();
		    String prenom = p.getText();
		    String cin = c.getText();
		    String branche = b.getText();
		    
			StringBuilder whereClause = new StringBuilder("WHERE 1=1");
			
			
			 if (!nom.isEmpty()) {
		            whereClause.append(" AND nom LIKE ?");
		        }
		     if (!prenom.isEmpty()) {
		            whereClause.append(" AND prenom LIKE ?");
		     }
		     if (!cin.isEmpty()) {
		            whereClause.append(" AND cin LIKE ?");
		     }
		     if (!branche.isEmpty()) {
		            whereClause.append(" AND branche LIKE ?");
		     }
			
			try {
				String sql = "SELECT * FROM etudiant " + whereClause.toString();
				PreparedStatement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement("SELECT * FROM etudiant " + whereClause.toString());
				

				
				int parameterIndex = 1;
	            if (!nom.isEmpty()) {
	                statement.setString(parameterIndex++, "%"+nom+"%");
	            }
	            if (!prenom.isEmpty()) {
	                statement.setString(parameterIndex++, "%"+prenom+"%");
	            }
	            if (!cin.isEmpty()) {
	            	statement.setString(parameterIndex++, "%"+cin+"%");
	            }
	            if (!branche.isEmpty()) {
	            	statement.setString(parameterIndex++, "%"+branche+"%");
	            }
	            
	            
	            resultSet = statement.executeQuery();
				 while (resultSet.next()) {
	                 Boolean state = data.add(new ModelEtudiant(resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("cin"), resultSet.getString("branche")));
                 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			tableView.setItems(data);
			System.out.println(data.toString());
		
	}
	
	public void ajoutEnseignant() {
		String nom = n2.getText();
	    String prenom = p2.getText();
	    String cin = c2.getText();
	    String matiere = m.getText();
	    String departement = d.getText();
	    
		StringBuilder whereClause = new StringBuilder("WHERE 1=1");
		
		
		 if (!nom.isEmpty()) {
	            whereClause.append(" AND nom = ?");
	        }
	     if (!prenom.isEmpty()) {
	            whereClause.append(" AND prenom = ?");
	     }
	     if (!cin.isEmpty()) {
	            whereClause.append(" AND cin = ?");
	     }
	     if (!matiere.isEmpty()) {
	            whereClause.append(" AND matiere = ?");
	     }
	     if (!departement.isEmpty()) {
	            whereClause.append(" AND departement = ?");
	     }
		
		try {
			String sql = "INSERT INTO enseignant VALUES (? , ? , ? , ? , ? )" ;
			PreparedStatement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql);
			

			
			int parameterIndex = 1;
            if (!cin.isEmpty()) {
                statement.setString(parameterIndex++, cin);
            }
            if (!nom.isEmpty()) {
                statement.setString(parameterIndex++,nom);
            }
            if (!prenom.isEmpty()) {
            	statement.setString(parameterIndex++,prenom);
            }
            if (!matiere.isEmpty()) {
            	statement.setString(parameterIndex++,matiere);
            }
            if (!departement.isEmpty()) {
            	statement.setString(parameterIndex++,departement);
            }
            
            
            statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switchListEnseignant();
	}
	//----------- VALIDER RECHERCHE ENSEIGNANT -------------
	public void ok2() {
		tableView2.getItems().clear();
		ResultSet resultSet;
		nom2.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		prenom2.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
		cin2.setCellValueFactory(cellData -> cellData.getValue().cinProperty());
		matiere.setCellValueFactory(cellData -> cellData.getValue().matiereProperty());
		departement.setCellValueFactory(cellData -> cellData.getValue().departementProperty());
		
		
		String nom = n2.getText();
	    String prenom = p2.getText();
	    String cin = c2.getText();
	    String matiere = m.getText();
	    String departement = d.getText();
	    
		StringBuilder whereClause = new StringBuilder("WHERE 1=1");
		
		
		 if (!nom.isEmpty()) {
	            whereClause.append(" AND nom LIKE ?");
	        }
	     if (!prenom.isEmpty()) {
	            whereClause.append(" AND prenom LIKE ?");
	     }
	     if (!cin.isEmpty()) {
	            whereClause.append(" AND cin LIKE ?");
	     }
	     if (!matiere.isEmpty()) {
	            whereClause.append(" AND matiere LIKE ?");
	     }
	     if (!departement.isEmpty()) {
	            whereClause.append(" AND departement LIKE ?");
	     }
		
		try {
			String sql = "SELECT * FROM enseignant " + whereClause.toString();
			PreparedStatement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement("SELECT * FROM enseignant " + whereClause.toString());
			

			
			int parameterIndex = 1;
            if (!nom.isEmpty()) {
                statement.setString(parameterIndex++, "%"+nom+"%");
            }
            if (!prenom.isEmpty()) {
                statement.setString(parameterIndex++, "%"+prenom+"%");
            }
            if (!cin.isEmpty()) {
            	statement.setString(parameterIndex++, "%"+cin+"%");
            }
            if (!matiere.isEmpty()) {
            	statement.setString(parameterIndex++, "%"+matiere+"%");
            }
            if (!departement.isEmpty()) {
            	statement.setString(parameterIndex++, "%"+departement+"%");
            }
            
            
            resultSet = statement.executeQuery();
			 while (resultSet.next()) {
                 Boolean state = data2.add(new ModelEnseignant(resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("cin"), resultSet.getString("matiere"), resultSet.getString("departement")));
             }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		tableView2.setItems(data2);
		System.out.println(data2.toString());
	
}
	public void ok3() {
		tableView3.getItems().clear();
		ResultSet resultSet;
		etudiant1.setCellValueFactory(cellData -> cellData.getValue().getEtudiant1());
		etudiant2.setCellValueFactory(cellData -> cellData.getValue().getEtudiant2());
		heure.setCellValueFactory(cellData -> cellData.getValue().getHeure());
		local.setCellValueFactory(cellData -> cellData.getValue().getLocal());
		date.setCellValueFactory(cellData -> cellData.getValue().getDate());
		etat.setCellValueFactory(cellData -> cellData.getValue().getEtat());
		local.setCellValueFactory(cellData -> cellData.getValue().getLocal());
		idjury.setCellValueFactory(cellData -> cellData.getValue().getIdjury());
		
		
		String idj = ij.getText();
	    String etud1 = e1.getText();
	    String etud2 = e2.getText();
	    String dat = da.getText();
	    String loca = l.getText();
	    String heur = h.getText();
	    String eta = et.getText();
	    
		StringBuilder whereClause = new StringBuilder("WHERE 1=1");
		
		
		 if (!idj.isEmpty()) {
	            whereClause.append(" AND juryId LIKE ?");
	        }
	     if (!etud1.isEmpty()) {
	            whereClause.append(" AND etudiant1 LIKE ?");
	     }
	     if (!etud2.isEmpty()) {
	            whereClause.append(" AND etudiant2 LIKE ?");
	     }
	     if (!dat.isEmpty()) {
	            whereClause.append(" AND date LIKE ?");
	     }
	     if (!loca.isEmpty()) {
	            whereClause.append(" AND local LIKE ?");
	     }
	     if (!heur.isEmpty()) {
	            whereClause.append(" AND heure LIKE ?");
	     }
	     if (!eta.isEmpty()) {
	            whereClause.append(" AND etat LIKE ?");
	     }
		
		try {
			String sql = "SELECT * FROM pfe " + whereClause.toString();
			PreparedStatement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement("SELECT * FROM pfe " + whereClause.toString());
			

			
			int parameterIndex = 1;
            if (!idj.isEmpty()) {
                statement.setString(parameterIndex++, "%"+idj+"%");
            }
            if (!etud1.isEmpty()) {
                statement.setString(parameterIndex++, "%"+etud1+"%");
            }
            if (!etud2.isEmpty()) {
            	statement.setString(parameterIndex++, "%"+etud2+"%");
            }
            if (!loca.isEmpty()) {
            	statement.setString(parameterIndex++, "%"+loca+"%");
            }
            if (!heur.isEmpty()) {
            	statement.setString(parameterIndex++, "%"+heur+"%");
            }
            if (!eta.isEmpty()) {
            	statement.setString(parameterIndex++, "%"+eta+"%");
            }
            if (!dat.isEmpty()) {
            	statement.setString(parameterIndex++, "%"+dat+"%");
            }
            
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
				 String etudiantCIN = resultSet.getString("etudiant1");
				 String etudiantCIN2 = resultSet.getString("etudiant2");
				 
			     ResultSet rsEtud1 = statement2.executeQuery("SELECT * FROM etudiant WHERE cin = '" + etudiantCIN + "'");
			     
			     ResultSet rsEtud2 = statement3.executeQuery("SELECT * FROM etudiant WHERE cin = '" + etudiantCIN2 + "'");
			     if (resultSet.getString("etudiant2")==null) {
			    	 if(rsEtud1.next()) {						 
						 Boolean state = data3.add(new ModelPFE(resultSet.getString("idPFE"), rsEtud1.getString("nom")+" "+rsEtud1.getString("prenom"), " ", resultSet.getString("heure"), resultSet.getString("date"), resultSet.getString("local"), resultSet.getString("juryId"), resultSet.getString("etat")));
					 }
				 }
			     else {
			    	 if(rsEtud1.next() && rsEtud2.next() ) {						 
						 Boolean state = data3.add(new ModelPFE(resultSet.getString("idPFE"), rsEtud1.getString("nom")+" "+rsEtud1.getString("prenom"), rsEtud2.getString("nom")+" "+rsEtud2.getString("prenom"), resultSet.getString("heure"), resultSet.getString("date"), resultSet.getString("local"), resultSet.getString("juryId"), resultSet.getString("etat")));
					 }
			     }
				 
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tableView3.setItems(data3);
		System.out.println(data3.toString());
	
}

	
	
	//-------------INITIALISER TABLEAUX----------
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		//afficher Etudiants
		if(location.toString().equals(getClass().getResource("ListEtudiants.fxml").toString())) {
			ResultSet resultSet;
			nom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
			prenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
			cin.setCellValueFactory(cellData -> cellData.getValue().cinProperty());
			branche.setCellValueFactory(cellData -> cellData.getValue().brancheProperty());
			editColumn3.setCellFactory(param -> new TableCellWithButtonEt());
			
			String nom = n.getText();
		    String prenom = p.getText();
		    
			StringBuilder whereClause = new StringBuilder("WHERE 1=1");
			 if (!nom.isEmpty()) {
		            whereClause.append(" AND nom = ?");
		        }
		        if (!prenom.isEmpty()) {
		            whereClause.append(" AND prenom = ?");
		        }
			
			try {
				String sql = "SELECT * FROM etudiant " + whereClause.toString();
				PreparedStatement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql);
				
				
				int parameterIndex = 1;
	            if (!nom.isEmpty()) {
	                statement.setString(parameterIndex++, nom);
	            }
	            if (!prenom.isEmpty()) {
	                statement.setString(parameterIndex++, prenom);
	            }
	            resultSet = statement.executeQuery(sql);
				 while (resultSet.next()) {
	                 Boolean state = data.add(new ModelEtudiant(resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("cin"), resultSet.getString("branche")));
                 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			tableView.setItems(data);
			System.out.println(data.toString());
		}
		
		//afficher Enseignants
		else if (location.toString().equals(getClass().getResource("ListEnseignants.fxml").toString())){
			ResultSet resultSet;
			nom2.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
			prenom2.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
			cin2.setCellValueFactory(cellData -> cellData.getValue().cinProperty());
			matiere.setCellValueFactory(cellData -> cellData.getValue().matiereProperty());
			departement.setCellValueFactory(cellData -> cellData.getValue().departementProperty());
			editColumn1.setCellFactory(param -> new TableCellWithButtonEn());
			try {
				resultSet = statement.executeQuery("SELECT * FROM enseignant");
				
				 while (resultSet.next()) {
	                 Boolean state = data2.add(new ModelEnseignant(resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("cin"), resultSet.getString("matiere"), resultSet.getString("departement")));
                 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			tableView2.setItems(data2);
			System.out.println(data2.toString());
			
		}
		//list pfe
		
		
		
		else if (location.toString().equals(getClass().getResource("ListPFE.fxml").toString())){
			
			ResultSet resultSet;
			
			etudiant1.setCellValueFactory(cellData -> cellData.getValue().getEtudiant1());
			etudiant2.setCellValueFactory(cellData -> cellData.getValue().getEtudiant2());
			heure.setCellValueFactory(cellData -> cellData.getValue().getHeure());
			local.setCellValueFactory(cellData -> cellData.getValue().getLocal());
			date.setCellValueFactory(cellData -> cellData.getValue().getDate());
			etat.setCellValueFactory(cellData -> cellData.getValue().getEtat());
			local.setCellValueFactory(cellData -> cellData.getValue().getLocal());
			idjury.setCellValueFactory(cellData -> cellData.getValue().getIdjury());
		    editColumn.setCellFactory(param -> new TableCellWithButton());

			try {
				resultSet = statement.executeQuery("SELECT * FROM pfe");
				
				 while (resultSet.next()) {
					 String etudiantCIN = resultSet.getString("etudiant1");
					 String etudiantCIN2 = resultSet.getString("etudiant2");
					 
				     ResultSet rsEtud1 = statement2.executeQuery("SELECT * FROM etudiant WHERE cin = '" + etudiantCIN + "'");
				     
				     ResultSet rsEtud2 = statement3.executeQuery("SELECT * FROM etudiant WHERE cin = '" + etudiantCIN2 + "'");
				     if (resultSet.getString("etudiant2")==null) {
				    	 if(rsEtud1.next()) {						 
							 Boolean state = data3.add(new ModelPFE(resultSet.getString("idPFE"), rsEtud1.getString("nom")+" "+rsEtud1.getString("prenom"), " ", resultSet.getString("heure"), resultSet.getString("date"), resultSet.getString("local"), resultSet.getString("juryId"), resultSet.getString("etat")));
						 }
					 }
				     else {
				    	 if(rsEtud1.next() && rsEtud2.next() ) {						 
							 Boolean state = data3.add(new ModelPFE(resultSet.getString("idPFE"), rsEtud1.getString("nom")+" "+rsEtud1.getString("prenom"), rsEtud2.getString("nom")+" "+rsEtud2.getString("prenom"), resultSet.getString("heure"), resultSet.getString("date"), resultSet.getString("local"), resultSet.getString("juryId"), resultSet.getString("etat")));
						 }
				     }
					 
                 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			tableView3.setItems(data3);
			
		}
else if (location.toString().equals(getClass().getResource("JuryPre.fxml").toString())){
			
	 try {
		 ResultSet resultSet;
         Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z");
         PreparedStatement statement = connection.prepareStatement("SELECT idP,idEX,idR,idEN FROM jury WHERE idJ = ?");
         statement.setString(1, idj);
         resultSet= statement.executeQuery();
         System.out.println(idj);
         while (resultSet.next()) {
      	   System.out.println(resultSet.getString("idP"));
             prs.setValue(resultSet.getString("idP"));
             exm.setValue(resultSet.getString("idEX"));
             rapr.setValue(resultSet.getString("idR"));
             enc.setValue(resultSet.getString("idEN"));
      	   //prs.getItems().add(resultSet.getString("idP"));
             
         }
         connection.close();
     } catch (SQLException e) {
         e.printStackTrace();
     }
			
		}
			
			
        //make sure the property value factory should be exactly same as the e.g getStudentId from your model class
    }
	private static String idSearch;
	public void initializeEprs() {
    	ObservableList<String> Enseignants = FXCollections.observableArrayList();
    	prs.getItems().clear();
    	try {
    		ResultSet resultSet;
			resultSet = statement.executeQuery("SELECT cin FROM enseignant");
			
			 while (resultSet.next()) {
                 Boolean state = Enseignants.add(resultSet.getString("cin"));
             }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(Enseignants);
    	prs.setItems(Enseignants);
    	
    }
	public void initializeEexm() {
    	ObservableList<String> Enseignants = FXCollections.observableArrayList();
    	exm.getItems().clear();
    	try {
    		ResultSet resultSet;
			resultSet = statement.executeQuery("SELECT cin FROM enseignant");
			
			 while (resultSet.next()) {
                 Boolean state = Enseignants.add(resultSet.getString("cin"));
             }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(Enseignants);
    	exm.setItems(Enseignants);
    	
    }
	public void initializeErapr() {
    	ObservableList<String> Enseignants = FXCollections.observableArrayList();
    	rapr.getItems().clear();
    	try {
    		ResultSet resultSet;
			resultSet = statement.executeQuery("SELECT cin FROM enseignant");
			
			 while (resultSet.next()) {
                 Boolean state = Enseignants.add(resultSet.getString("cin"));
             }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(Enseignants);
    	rapr.setItems(Enseignants);
    	
    }
	public void initializeEenc() {
    	ObservableList<String> Enseignants = FXCollections.observableArrayList();
    	enc.getItems().clear();
    	try {
    		ResultSet resultSet;
			resultSet = statement.executeQuery("SELECT cin FROM enseignant");
			
			 while (resultSet.next()) {
                 Boolean state = Enseignants.add(resultSet.getString("cin"));
             }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(Enseignants);
    	enc.setItems(Enseignants);
    	
    }
	@FXML
	private Button valide=new Button(); // valider modification de pfe
	
	@FXML
	private ComboBox<String> prs = new ComboBox<String>();
	@FXML
	private ComboBox<String> exm = new ComboBox<String>();
	@FXML
	private ComboBox<String> rapr = new ComboBox<String>();
	@FXML
	private ComboBox<String> enc = new ComboBox<String>();
	
	public static String idj;
	public static String idp;
	public static String idr;
	public static String ide;
	public static String iden;
	
	@FXML
	Tooltip tooltip = new Tooltip();
    public class TableCellWithButton extends TableCell<ModelPFE, Void> {
        private Button editButton = new Button("Edit");
        public static String idPourSearchBd ;
        /*public static String lo ;
        public static String daa ;
        public static String et ;
        public static String he ;*/
        public static String daa ;
        boolean tooltipvisible;
        public TableCellWithButton() {
            editButton.setOnAction(event -> {
            	
                ModelPFE item =getTableView().getItems().get(getIndex());
                openEditPageP(item);
            	
            	
                
               
            });
           
           tableView3.setOnKeyPressed(event -> {
        	   
        	   if(event.getCode()==KeyCode.BACK_SPACE && !tableView3.getSelectionModel().isEmpty()) {
        		   ModelPFE selectedPfe = tableView3.getSelectionModel().getSelectedItem();
                   tableView3.getItems().remove(selectedPfe);
                   try {
                       Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z");
                       PreparedStatement statement = connection.prepareStatement("DELETE FROM pfe WHERE idPFE = ?");
                       statement.setString(1, selectedPfe.getId().getValue());
                       System.out.println(selectedPfe.getId().getValue());
                       statement.executeUpdate();
                       connection.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
        	   }
           });
           tableView3.setOnMouseClicked(event -> {
               if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                  
                   ModelPFE selectedPFE = tableView3.getSelectionModel().getSelectedItem();
                   idj =selectedPFE.getIdjury().getValue();
                   
                   if (selectedPFE != null) {
                	   switchJuryPre();
                       
                       
                   }
               }
           });
           tooltipvisible=false;
           tableView3.setOnKeyPressed(event -> {
        	   if(event.getCode()==KeyCode.SPACE && !tableView3.getSelectionModel().isEmpty()) {
        	   if(!tooltipvisible) {
        	   ResultSet resultset;
        	   String str="";
               ModelPFE selectedPFE = tableView3.getSelectionModel().getSelectedItem();
               if (selectedPFE != null) {
            	   try {
            		   tooltip.setShowDelay(javafx.util.Duration.millis(500));
                       Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z");
                       PreparedStatement statement = connection.prepareStatement("select * FROM jury WHERE idJ = ?");
                       statement.setString(1, selectedPFE.getIdjury().getValue());
                       resultset=statement.executeQuery();
                       if(resultset.next()) {str=resultset.getString("ListV");}
                       
                       connection.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }}
               		double rowWidth = tableView3.getWidth();
               		double rowHeight = tableView3.getHeight();
               		double rowX = rowWidth ; 
               		double rowY = rowHeight / 2; 

               // Convert local coordinates to scene coordinates
               		double sceneX = tableView3.localToScene(rowX, rowY).getX();
               		double sceneY = tableView3.localToScene(rowX, rowY).getY();
                   tooltip.setText(str);
                   tooltip.show(tableView3,sceneX,sceneY);
                   
               }}
        	   else {
        		   tooltip.hide();
        		   tooltipvisible=false;
         }
        	  
                
           });
           
           
        }
        
       
        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(editButton);
            }
        }

        private void openEditPageP(ModelPFE item) {
    
        	idPourSearchBd=item.getId().getValue();
        	System.out.println(idPourSearchBd);
        	/*lo=item.getLocal().toString();
        	daa=item.getDate().toString();
        	et=item.getEtat().toString();
        	he=item.getHeure().toString();*/
        	daa=item.getDate().getValue();
            switchEditPFE();    
        }
        private void openEditPageEN(ModelEnseignant item) {
            
        	idPourSearchBd=item.getCin().getValue();
        	System.out.println(idPourSearchBd);
        	/*lo=item.getLocal().toString();
        	daa=item.getDate().toString();
        	et=item.getEtat().toString();
        	he=item.getHeure().toString();*/
            switchEditPFE();    
        }
        private void openEditPageET(ModelEtudiant item) {
            
        	idPourSearchBd=item.getCin().getValue();
        	System.out.println(idPourSearchBd);
        	/*lo=item.getLocal().toString();
        	daa=item.getDate().toString();
        	et=item.getEtat().toString();
        	he=item.getHeure().toString();*/
            switchEditPFE();    
        }
    }
    public class TableCellWithButtonEn extends TableCell<ModelEnseignant, Void> {
        private Button editButton = new Button("Edit");
        public static String idPourSearchBd ;
        /*public static String lo ;
        public static String daa ;
        public static String et ;
        public static String he ;*/
        public static String daa ;
        public TableCellWithButtonEn() {
            editButton.setOnAction(event -> {
            	if (tableView2!= null) {
                ModelEnseignant item =getTableView().getItems().get(getIndex());
                openEditPageEN(item);}
          });
          
           
           if (tableView2!=null) {
               tableView2.setOnKeyPressed(event -> {
            	   if(event.getCode()==KeyCode.BACK_SPACE && !tableView2.getSelectionModel().isEmpty()) {
            		   ModelEnseignant selectedEns = tableView2.getSelectionModel().getSelectedItem();
                       tableView2.getItems().remove(selectedEns);
                       try {
                           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z");
                           PreparedStatement statement = connection.prepareStatement("DELETE FROM enseignant WHERE cin = ?");
                           statement.setString(1, selectedEns.getCin().getValue());
                           statement.executeUpdate();
                           connection.close();
                       } catch (SQLException e) {
                           e.printStackTrace();
                       }
            	   }
               });
               }
        }
        
       
        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(editButton);
            }
        }

        private void openEditPageEN(ModelEnseignant item) {
            
        	idPourSearchBd=item.getCin().getValue();
        	System.out.println(idPourSearchBd);
        	/*lo=item.getLocal().toString();
        	daa=item.getDate().toString();
        	et=item.getEtat().toString();
        	he=item.getHeure().toString();*/
            switchEditEnseignant();    
        }
    }
    public class TableCellWithButtonEt extends TableCell<ModelEtudiant, Void> {
        private Button editButton = new Button("Edit");
        public static String idPourSearchBd ;
        /*public static String lo ;
        public static String daa ;
        public static String et ;
        public static String he ;*/
        public static String daa ;
        public TableCellWithButtonEt() {
            editButton.setOnAction(event -> {
            	if (tableView!= null) {
                ModelEtudiant item =getTableView().getItems().get(getIndex());
                openEditPageEt(item);}
          });
          
           
           if (tableView!=null) {
               tableView.setOnKeyPressed(event -> {
            	   if(event.getCode()==KeyCode.BACK_SPACE && !tableView.getSelectionModel().isEmpty()) {
            		   ModelEtudiant selectedEt = tableView.getSelectionModel().getSelectedItem();
                       tableView.getItems().remove(selectedEt);
                       try {
                           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z");
                           PreparedStatement statement = connection.prepareStatement("DELETE FROM etudiant WHERE cin = ?");
                           statement.setString(1, selectedEt.getCin().getValue());
                           statement.executeUpdate();
                           connection.close();
                       } catch (SQLException e) {
                           e.printStackTrace();
                       }
            	   }
               });
               }
        }
        
       
        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(editButton);
            }
        }

        private void openEditPageEt(ModelEtudiant item) {
            
        	idPourSearchBd=item.getCin().getValue();
        	System.out.println(idPourSearchBd);
        	/*lo=item.getLocal().toString();
        	daa=item.getDate().toString();
        	et=item.getEtat().toString();
        	he=item.getHeure().toString();*/
            switchEditEtudiant();    
        }
    }
    javafx.collections.ObservableList<ModelPFE> all = FXCollections.observableArrayList();
    javafx.collections.ObservableList<ModelEtudiant> all1 = FXCollections.observableArrayList();
    javafx.collections.ObservableList<ModelEnseignant> all2 = FXCollections.observableArrayList();
    public void supprimerTt() {
    	/*try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM pfe WHERE idPFE = ?");
            statement.setString(1, selectedPfe.getId().getValue());
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    	if (tableView3!=null) {
    	all = tableView3.getItems();
        
        
        for(ModelPFE pfe : all) {
        	supprimer(pfe);
        	}
    	}
    	if (tableView!=null) {
        	all1 = tableView.getItems();
            
            
            for(ModelEtudiant et : all1) {
            	supprimer1(et);
            	}
        	}
    	if (tableView2!=null) {
        	all2 = tableView2.getItems();
            
            
            for(ModelEnseignant en : all2) {
            	supprimer2(en);
            	}
        	}
        
    
    	
    }
    public void supprimer(ModelPFE selectedPfe) {
    	
    	try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM pfe WHERE idPFE = ?");
            PreparedStatement statement1 = connection.prepareStatement("DELETE FROM jury WHERE idJ = ?");
            statement.setString(1, selectedPfe.getId().getValue());
            statement1.setString(1, selectedPfe.getIdjury().getValue());
           
            statement.executeUpdate();
            statement1.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	switchListPFE();
    }
    public void supprimer1(ModelEtudiant selectedEt) {
    	
    	try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM etudiant WHERE cin = ?");
            
            statement.setString(1, selectedEt.getCin().getValue());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	switchListEtudiants();
    }
    public void supprimer2(ModelEnseignant selectedEn) {
    	
    	try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM enseignant WHERE cin = ?");
            statement.setString(1, selectedEn.getCin().getValue());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	switchListEnseignant();
    }
    
    public void ModifJury() {
    	String p=prs.getValue().toString();
    	String ex=exm.getValue();
    	String ec=enc.getValue();
    	String r=rapr.getValue();
    	
    	if (!p.isEmpty()) {
		ResultSet resultSet;
	    String sql = "UPDATE jury SET idP = ? WHERE idJ = ?";
        try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
        	
        	pstmt.setString(1, p);
        	pstmt.setString(2,idj );
        	
           
          
          
           pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }}
    	if (ex!=null) {
    		ResultSet resultSet;
    	    String sql = "UPDATE jury SET idEX = ? WHERE idJ = ?";
            try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            	
            	pstmt.setString(1, ex);
            	pstmt.setString(2,idj );
            	
               
              
              
               pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }}
    	if (ec!=null) {
    		ResultSet resultSet;
    	    String sql = "UPDATE jury SET idEN = ? WHERE idJ = ?";
            try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            	
            	pstmt.setString(1, ec);
            	pstmt.setString(2,idj );
            	
               
              
              
               pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }}
    	if (r!=null) {
    		ResultSet resultSet;
    	    String sql = "UPDATE jury SET idR = ? WHERE idJ = ?";
            try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            	
            	pstmt.setString(1, r);
            	pstmt.setString(2,idj );
            	
               
              
              
               pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }}
    	
    	
    }
    @FXML
    private TextArea v = new TextArea();
    public void visiteur() {
    	String r=v.getText();
    	if (r!=null) {
    		ResultSet resultSet;
    	    String sql = "UPDATE jury SET ListV = ? WHERE idJ = ?";
            try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            	
            	pstmt.setString(1, r);
            	pstmt.setString(2,idj );
            	
               
              
              
               pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }}
    	
    }
    
    @FXML
    private DatePicker D2 = new DatePicker();

    public void ModifPFE() {

    	if (D2.getValue()==null) {
    		D2.setValue(LocalDate.parse(TableCellWithButton.daa, DateTimeFormatter.ISO_LOCAL_DATE));
    		
    	}
    	String date=D2.getValue().toString();
    	String heure=H.getValue();
    	String locale=L.getValue();
    	String etat=Et.getValue();
    	
    	if (!date.isEmpty()) {
		ResultSet resultSet;
	    String sql = "UPDATE pfe SET date = ? WHERE idPFE = ?";
        try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
        	
        	pstmt.setString(1, date);
        	pstmt.setString(2,TableCellWithButton.idPourSearchBd );
        	
           
          
          
           pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }}
    	if (heure!=null) {
    		ResultSet resultSet;
    	    String sql = "UPDATE pfe SET heure = ? WHERE idPFE = ?";
            try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            	
            	pstmt.setString(1, heure);
            	pstmt.setString(2,TableCellWithButton.idPourSearchBd );
            	
               
              
              
               pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }}
    	if (locale!=null) {
    		ResultSet resultSet;
    	    String sql = "UPDATE pfe SET local = ? WHERE idPFE = ?";
            try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            	
            	pstmt.setString(1, locale);
            	pstmt.setString(2,TableCellWithButton.idPourSearchBd );
            	
               
              
              
               pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }}
    	if (etat!=null) {
    		ResultSet resultSet;
    	    String sql = "UPDATE pfe SET etat = ? WHERE idPFE = ?";
            try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            	
            	pstmt.setString(1, etat);
            	pstmt.setString(2,TableCellWithButton.idPourSearchBd );
            	
               
              
              
               pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }}
    	switchListPFE();
    	
    }
    @FXML
    private TextField name = new TextField();
    @FXML
    private TextField ci = new TextField();
    @FXML
    private TextField mat = new TextField();
    @FXML
    private TextField depa = new TextField();
    @FXML
    private TextField surn = new TextField();
    
    public void ModifEN() {

    	String nom=name.getText();
    	String prenom=surn.getText();
    	String cin=ci.getText();
    	String dep=depa.getText();
    	String matiere=mat.getText();
    	
    	if (!nom.isEmpty()) {
		ResultSet resultSet;
	    String sql = "UPDATE enseignant SET nom = ? WHERE cin = ?";
        try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
        	
        	pstmt.setString(1, nom);
        	pstmt.setString(2,TableCellWithButtonEn.idPourSearchBd );
        	
           
          
          
           pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }}
    	if (!prenom.isEmpty()) {
    		System.out.println(prenom +"-------");
    		ResultSet resultSet;
    		 String sql = "UPDATE enseignant SET prenom = ? WHERE cin = ?";
            try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            	
            	pstmt.setString(1, prenom);
            	pstmt.setString(2,TableCellWithButtonEn.idPourSearchBd );
            	
               
              
              
               pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }}
    	if (!matiere.isEmpty()) {
    		ResultSet resultSet;
    		 String sql = "UPDATE enseignant SET matiere = ? WHERE cin = ?";
            try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            	
            	pstmt.setString(1, matiere);
            	pstmt.setString(2,TableCellWithButtonEn.idPourSearchBd );
            	
               
              
              
               pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }}
    	if (!dep.isEmpty()) {
    		ResultSet resultSet;
    		 String sql = "UPDATE enseignant SET departement = ? WHERE cin = ?";
            try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            	
            	pstmt.setString(1, dep);
            	pstmt.setString(2,TableCellWithButtonEn.idPourSearchBd );
            	
               
              
              
               pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }}
    	if (!cin.isEmpty()) {
    		ResultSet resultSet;
    		 String sql = "UPDATE enseignant SET cin = ? WHERE cin = ?";
            try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            	
            	pstmt.setString(1, cin);
            	pstmt.setString(2,TableCellWithButtonEn.idPourSearchBd );
            	
               
              
              
               pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }}
    	switchListEnseignant();
    	
    }
    public void ModifEt() {

    	String nom=name.getText();
    	String prenom=surn.getText();
    	String cin=ci.getText();
    	String matiere=mat.getText();
    	
    	if (!nom.isEmpty()) {
		ResultSet resultSet;
	    String sql = "UPDATE etudiant SET nom = ? WHERE cin = ?";
        try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
        	
        	pstmt.setString(1, nom);
        	pstmt.setString(2,TableCellWithButtonEt.idPourSearchBd );
        	
           
          
          
           pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }}
    	if (!prenom.isEmpty()) {
    		System.out.println(prenom +"-------");
    		ResultSet resultSet;
    		 String sql = "UPDATE etudiant SET prenom = ? WHERE cin = ?";
            try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            	
            	pstmt.setString(1, prenom);
            	pstmt.setString(2,TableCellWithButtonEt.idPourSearchBd );
            	
               
              
              
               pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }}
    	if (!matiere.isEmpty()) {
    		ResultSet resultSet;
    		 String sql = "UPDATE etudiant SET branche = ? WHERE cin = ?";
            try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            	
            	pstmt.setString(1, matiere);
            	pstmt.setString(2,TableCellWithButtonEt.idPourSearchBd );
            	
               
              
              
               pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }}
    	if (!cin.isEmpty()) {
    		ResultSet resultSet;
    		 String sql = "UPDATE etudiant SET cin = ? WHERE cin = ?";
            try (PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "s.121003.z").prepareStatement(sql)) {
            	
            	pstmt.setString(1, cin);
            	pstmt.setString(2,TableCellWithButtonEt.idPourSearchBd );
            	
               
              
              
               pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }}
    	switchListEtudiants();
    	
    }
    
	//---------- INITIALIER LES TEXXTFIELD DE RECHERCHE -----------
	@FXML
	private Button oket3=new Button();
	@FXML
	private TextField ij=new TextField();
	@FXML
	private TextField e1=new TextField();
	@FXML
	private TextField e2=new TextField();
	@FXML
	private TextField h=new TextField();
	@FXML
	private TextField l=new TextField();
	@FXML
	private TextField da=new TextField();
	@FXML
	private TextField et=new TextField();
	
	
	@FXML
	private Button oket=new Button();
	@FXML
	private TextField n=new TextField();
	@FXML
	private TextField p=new TextField();
	@FXML
	private TextField c=new TextField();
	@FXML
	private TextField b=new TextField();
	
	
	private Button oket2=new Button();
	@FXML
	private TextField n2=new TextField();
	@FXML
	private TextField p2=new TextField();
	@FXML
	private TextField c2=new TextField();
	@FXML
	private TextField m=new TextField();
	@FXML
	private TextField d=new TextField();
	
	
	//----------- INITIALISER LES VARIABLE DE RESIZE DANS LA PAGE PRINCIPALE ----------
    @FXML 
	private VBox myVbox=new VBox();
	@FXML 
	private Text title=new Text();
	@FXML 
	private Text description=new Text();
	
	@FXML
	private AnchorPane menuPane;
	boolean animationMenuState = true;

	@FXML
	private Button menuBtn;
	
	
	public void resize(ActionEvent e) {
		final Animation openMenuAnim = new Transition() {
			{
				setCycleDuration(Duration.millis(250));
			}
		
			protected void interpolate(double frac) {
				final int length = 157;
				final int n = Math.round(length * (float) frac);
				// text.setText(content.substring(0, n));
				menuPane.setTranslateX(-157 + n);
			}
		
		};
		final Animation closeMenuAnim = new Transition() {
			{
				setCycleDuration(Duration.millis(250));
			}
		
			protected void interpolate(double frac) {
				final int length = 157;
				final int n = Math.round(length * (float) frac);
				// text.setText(content.substring(0, n));
				menuPane.setTranslateX(-n);
			}
		
		};
		// myVbox.setVisible(!myVbox.isVisible());
		if (animationMenuState) {
			openMenuAnim.play();
			title.setLayoutX(150);
			title.setLayoutY(206);
			description.setLayoutX(239);
			description.setLayoutY(372);
			menuBtn.setText("X");
		}
		else {
			closeMenuAnim.play();
			title.setLayoutX(88);
			title.setLayoutY(209);
			description.setLayoutX(183);
			description.setLayoutY(409);
			menuBtn.setText("|||");
		}
		animationMenuState = !animationMenuState;
		
	}
	// switch entre les pages 
	@FXML
	private StackPane contentPane;
	@FXML
    private void switchListEtudiants() {
        loadPage("ListEtudiants.fxml");        
    }
	@FXML
	private void switchJuryPre() {
        loadPage("JuryPre.fxml");        
    }
	@FXML
    private void switchSelectJury() {
        loadPage("SelectJury.fxml");        
    }
	@FXML
    private void switchEditEnseignant() {
        loadPage("EditEnseignant.fxml");        
    }
	@FXML
    private void switchEditEtudiant() {
        loadPage("EditEtudiant.fxml");        
    }
	@FXML
    private void switchAjoutVisiteur() {
        loadPage("AjoutVisiteurs.fxml");        
    }
	@FXML
    private void switchEditPFE() {
        loadPage("EditPFE.fxml");        
    }
	
	@FXML
    private void switchListPFE() {
        loadPage("ListPFE.fxml");
    }
	
	@FXML
    private void switchListEnseignant() {
        loadPage("ListEnseignants.fxml");
    }
	
	@FXML
    private void switchToMain() {
        loadPage("Main.fxml");
    }
	
    @FXML
    private void switchToAjoutPFE0() {
        loadPage("AjoutPFE0.fxml");
    }

    @FXML
    private void switchToAjoutPFE1e() {
        loadPage("AjoutPFE1.fxml");
    }
    @FXML
    private void switchToAjoutPFE2e() {
        loadPage("AjoutPFE2.fxml");
    }


    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node page = loader.load();
            contentPane.getChildren().setAll(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//------------- INITIALIER LES CLASSE PUR LES UTILISER DANS LE REMPLISAGE DES TABLEAUX ----------------

class ModelEtudiant{
	private SimpleStringProperty cin;
    private SimpleStringProperty nom; 
    private SimpleStringProperty prenom ;
    private SimpleStringProperty branche ;
    
    public ModelEtudiant(String nom,String prenom, String cin, String branche) {
    	this.nom= new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.cin=new SimpleStringProperty(cin);
		this.branche = new SimpleStringProperty(branche);
    }

	public String getNom() {
		return nom.get();
	}

	public String getPrenom() {
        return prenom.get();
    }

    public StringProperty prenomProperty() {
        return prenom;
    }
	
    public StringProperty nomProperty() {
        return nom;
    }
    public StringProperty cinProperty() {
        return getCin();
    }
    public StringProperty brancheProperty() {
        return branche;
    }

	public String getBranche() {
		return branche.get();
	}

	@Override
	public String toString() {
		return "ModelEtudiant [cin=" + getCin().get() + ", nom=" + nom + ", prenom=" + prenom + ", branche=" + branche + "]";
	}

	public SimpleStringProperty getCin() {
		return cin;
	}   
}
class ModelEnseignant{
	private SimpleStringProperty cin;
    private SimpleStringProperty nom; 
    private SimpleStringProperty prenom ;
    private SimpleStringProperty matiere ;
    private SimpleStringProperty departement ;
    
    
    
    public ModelEnseignant(String nom,String prenom, String cin, String matiere,String departement) {
    	this.nom= new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.cin=new SimpleStringProperty(cin);
		this.matiere = new SimpleStringProperty(matiere);
		this.departement = new SimpleStringProperty(departement);
    }

	public String getNom() {
		return nom.get();
	}

	public String getPrenom() {
        return prenom.get();
    }

    public StringProperty prenomProperty() {
        return prenom;
    }
	
    public StringProperty nomProperty() {
        return nom;
    }
    public StringProperty cinProperty() {
        return getCin();
    }
    public StringProperty matiereProperty() {
        return matiere;
    }

	public String getMatiere() {
		return matiere.get();
	}
	public StringProperty departementProperty() {
        return departement;
    }

	@Override
	public String toString() {
		return "ModelEnseignant [cin=" + getCin() + ", nom=" + nom + ", prenom=" + prenom + ", matiere=" + matiere
				+ ", departement=" + departement + "]";
	}

	public SimpleStringProperty getCin() {
		return cin;
	}
	
}
class ModelPFE{
	private SimpleStringProperty id;
    private SimpleStringProperty etudiant1; 
    private SimpleStringProperty etudiant2 ;
    private SimpleStringProperty heure ;
    private SimpleStringProperty date ;
    private SimpleStringProperty etat ;
    private SimpleStringProperty local ;
    private SimpleStringProperty idjury ;
    
    public ModelPFE(String id,String etudiant1, String etudiant2, String heure,String date,String local,String idjury,String etat) {
    	this.id= new SimpleStringProperty(id);
		this.etudiant1 = new SimpleStringProperty(etudiant1);
		this.etudiant2=new SimpleStringProperty(etudiant2);
		this.date = new SimpleStringProperty(date);
		this.heure = new SimpleStringProperty(heure);
		this.local = new SimpleStringProperty(local);
		this.etat = new SimpleStringProperty(etat);
		this.idjury = new SimpleStringProperty(idjury);
    }
    
	public SimpleStringProperty getId() {
		return id;
	}



	public void setId(SimpleStringProperty id) {
		this.id = id;
	}



	public SimpleStringProperty getEtudiant1() {
		return etudiant1;
	}



	public void setEtudiant1(SimpleStringProperty etudiant1) {
		this.etudiant1 = etudiant1;
	}



	public SimpleStringProperty getEtudiant2() {
		return etudiant2;
	}



	public void setEtudiant2(SimpleStringProperty etudiant2) {
		this.etudiant2 = etudiant2;
	}



	public SimpleStringProperty getHeure() {
		return heure;
	}



	public void setHeure(SimpleStringProperty heure) {
		this.heure = heure;
	}



	public SimpleStringProperty getDate() {
		return date;
	}



	public void setDate(SimpleStringProperty date) {
		this.date = date;
	}



	public SimpleStringProperty getEtat() {
		return etat;
	}



	public void setEtat(SimpleStringProperty etat) {
		this.etat = etat;
	}



	public SimpleStringProperty getLocal() {
		return local;
	}



	public void setLocal(SimpleStringProperty local) {
		this.local = local;
	}



	public SimpleStringProperty getIdjury() {
		return idjury;
	}



	public void setIdjury(SimpleStringProperty idjury) {
		this.idjury = idjury;
	}

	@Override
	public String toString() {
		return "ModelPFE [id=" + id + ", etudiant1=" + etudiant1 + ", etudiant2=" + etudiant2 + ", heure=" + heure
				+ ", date=" + date + ", etat=" + etat + ", local=" + local + ", idjury=" + idjury + "]";
	}
	
}
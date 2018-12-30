package com.vel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;
import java.io.BufferedWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

/**
 * Servlet implementation class Compute
 */
public class Compute extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Compute() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				
		//Recupérer la commande saisie
		String commande = request.getParameter("cmd");
		String resultat = "KO";
		String[] param = commande.split(" ");
		int taille = param.length;
		if(taille > 1) {
			String cmd = param[0];
			String nomFichier = param[1];
			
			String separateur = File.separator;			
			String curDir = this.getServletContext().getRealPath(separateur);				
			//Ajouter '/' devant le chemin du fichier s'il n'y en a pas
			if(nomFichier.charAt(0) != '/')				
				nomFichier = curDir+separateur+nomFichier;		
			else
				nomFichier = curDir+separateur+nomFichier.substring(1);	
			
			File fichier = new File(nomFichier);
			
			//Commande touch
			if(cmd.equalsIgnoreCase("touch")) {
				try{
					//Désactivé car on ne crée pas de fichier vide, il faut specifier les 3 paramètres pour créer un fichier et ajouter le contenu  
					/*
					fichier.getParentFile().mkdirs();
					//Création du fichier								
					fichier.createNewFile();
					*/
					//Ajout du contenu du fichier seulement si le 3è paramètre a été spécifié
					if(taille >2) {
						//Création des dossiers parents s'ils n'existent pas
						fichier.getParentFile().mkdirs();
						//Création du fichier								
						fichier.createNewFile();
				        FileWriter fw = new FileWriter(fichier);
				        BufferedWriter bw = new BufferedWriter(fw);
				        //Recupérer le debut de la chaine à ecrire dans le fichier texte 
				        int debut = commande.indexOf('"')+1;
				        if(debut > 0) {
					        int fin = commande.lastIndexOf('"');	
					        if(fin > 0) {					        	
					        	bw.write(commande.substring(debut, fin));
					        }
					        else
					        	bw.write(commande.substring(debut));
				        }
				        else
				        	bw.write(param[2]);
				        
				        bw.close();
				        resultat = "OK";
					}
					//Désactivé : car la création de fichier vide a été désactivée
					//resultat = "OK";
			    }
			    catch(IOException e){		    			    			    	
			        e.printStackTrace();
			    }
				finally {
			    	System.out.println(commande +" : "+resultat);
			    	forwardReponse(request, response, resultat);		    	
				}
		
			}
			
			//Commande rm
			else if(cmd.equalsIgnoreCase("rm")) {																
				try {
					if(fichier.delete())
						resultat = "OK";
				}
				catch(SecurityException e)
				{
					e.printStackTrace();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally {
					System.out.println(commande +" : "+resultat);
			    	forwardReponse(request, response, resultat);		    
				}
			}
			
			//Commande ls
			else if(cmd.equalsIgnoreCase("ls")) {
				//Le map pour renseigner les fichiers ou dossiers et leurs type
				Map<String, Boolean> donnees = new HashMap<String, Boolean>();
				try {
					donnees = new HashMap<String, Boolean>();
					//Vérifier si le chemin correpond bien à un dossier avant de continuer
					if(fichier.isDirectory()) {
						File[] liste = fichier.listFiles();
						for(File fic : liste){
							donnees.put(fic.getName(), fic.isFile());
						}
						resultat = "OK";
					}
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally {
					System.out.println(commande +" : "+resultat);
			    	forwardReponse(request, response, resultat, donnees);		    
				}
			}
		}
			//pour toutes les autres commandes rien à faire
		else
		{
			resultat = "KO";
			forwardReponse(request, response, resultat);
		}
				
		
	}
	
	private void forwardReponse(HttpServletRequest req, HttpServletResponse resp, String resultat)
            throws ServletException, IOException {
        String nextJSP = "/index.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("resultat", resultat);
        dispatcher.forward(req, resp);
    }  
	
	private void forwardReponse(HttpServletRequest req, HttpServletResponse resp, String resultat, Map<String, Boolean> donnees) 
			throws ServletException, IOException {
        String nextJSP = "/index.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("resultat", resultat);
        req.setAttribute("donnees", donnees);
        dispatcher.forward(req, resp);
    } 

}

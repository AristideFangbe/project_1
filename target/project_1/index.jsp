<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="fr">
<head>
 <meta charset="utf-8">
  <title>webShell -- Interprèteur de commandes Web</title>  
  <link rel="stylesheet" href="css/bootstrap.min.css">  
  <link rel="stylesheet" href="css/bootstrap-theme.min.css">         
  <script src="js/jquery.min.js"></script>
  <script src="js/bootstrap.min.js"></script>      
</head>
<body>
<br>
<br>
 <div class="container theme-showcase" role="main">
 <div class="row">
	<div class="col-lg-12">
 		<h1 class="font-weight-bold text-center">Interprèteur de commande "WEBSHELL 0.1"</h1>
 	</div>
 </div>
  <div class="row">
	<div class="col-lg-12 text-center">
 		<img src="img/shell.png" alt="shell" title="shell"/>
 	</div>
 </div>
 <br>
<div class="row">
	<div class="col-lg-12">
		<h3 class="text-center">Saisissez la commande dans la zone de texte et validez</h3>	
		<form action="Compute" method="post">			
			<div class="form-group">
				<label class="control-label" for="cmd">Commande : </label>
				<input type="text" id="cmd" name="cmd" placeholder="Saisir la commande ici" class="form-control" tabindex="5" required/>
			</div>
			<div class="float-right">					
				<button id="btAnnul" type="reset" class="btn btn-danger" tabindex="5">Annuler</button>
				<button id="btValid" type="submit" class="btn btn-primary" tabindex="40">Valider</button>
			</div>
		</form>	
	</div>
</div>
		<br>
	<div class="row">
		<div class="col-lg-12">
				<c:choose>
					<c:when test="${requestScope.resultat == 'OK'}">
						<div class="alert alert-success alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
							<p> <span class="font-weight-bold"> <c:out value="${param.cmd}"></c:out> </span> : <span class="font-italic"> OK </span></p>
						</div>
						<c:if test="${!empty requestScope.donnees}">
							
							<div><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
							<table  class="table table-striped">
	                            <thead>
	                                <tr>
	                                    <td>Nom</td>
	                                    <td>Type</td>                                                               
	                                </tr>
	                            </thead>
	                            <c:forEach var="donnee" items="${requestScope.donnees}">    	                                                      
	                                <tr>
	                                    <td>${donnee.key}</td>
	                                    <td>${donnee.value?'Fichier':'Dossier'}</td>                                                                       
	                                </tr>
	                            </c:forEach>               
	                        </table>  
	                        </div>     
						</c:if>
					</c:when>
					<c:when test="${requestScope.resultat == 'KO'}">
						<div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
							<p> <span class="font-weight-bold"> <c:out value="${param.cmd}"></c:out> </span> : <span class="font-italic"> KO </span> </p>
						</div>
					</c:when>	
								
					<c:otherwise>
						<div></div>	
					</c:otherwise>
	           	</c:choose>	
		</div>
	</div>
</div>
</body>
</html>

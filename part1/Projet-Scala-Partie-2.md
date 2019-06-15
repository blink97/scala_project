# Projet Scala - Partie 2
* objet connecté envoie sur API
* API écrit dans kafka
	* Ecriture dans kafka avec un producer
	* Un seul topic
* 2 consumer kafka 
	* spark:  pour les données
		* pull données
		* écrit dans hdfs (simuler avec son fs)
		* spark-streaming: bibliothèque pour avoir un consumer spark
	* scala: pour les alertes

* Spark il lit dans kafka puis il écrit dans hdfs
* Spark lit dans hdfs, fait la selection des données, collect et renvoie à l’API (pour afficher les utilisateurs par exemple)
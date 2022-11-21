# java-filmorate
Template repository for Filmorate project.
![This is an image](/src/main/resources/filmorate_datebase.jpeg)


Запрос для вывода значений таблицы film с film_id = 1:

SELECT
	*
FROM film 
WHERE film_id = 1

Запрос для вывода всех лайков:

SELECT
	us.user_id
	us.name as user_name,
	fil.name as film_name
FROM user as us
	INNER JOIN likes as like 
		ON us.user_id = like.user_id
	INNER JOIN film as fil 
		ON us.film_id = fil.film_id;
			
			

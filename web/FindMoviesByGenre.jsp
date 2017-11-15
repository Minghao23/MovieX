<%--Created by YafangChen on 03/25/17 --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="model.*" %>
<%@ page import="dal.*" %>
<html>
<head>
	<title>Search for Movies by Genre</title>
	<link href="/FindMoviesByGenre.css" rel="stylesheet" type="text/css">
</head>
<body>
	<form action="findmoviesbygenre" method="get">
		<%-- <h1>Search for movies by genre</h1> --%>
		<div class="genres-list">
			<h2 id="genre">Select a Genre:</h2>
			<%
				List<String> strs = new ArrayList<>();
				String tt = (String)request.getParameter("genre");
				strs.add("Action");
				strs.add("Adventure");
				strs.add("Animation");
				strs.add("Biography");
				strs.add("Crime");
				strs.add("Comedy");
				strs.add("Documentary");
				strs.add("Drama");
				strs.add("Family");
				strs.add("Fantasy");
				strs.add("Film-Noir");
				strs.add("Game-Show");
				strs.add("History");
				strs.add("Horror");
				strs.add("Musical");
				strs.add("Mystery");
				strs.add("News");
				strs.add("Reality-TV");
				strs.add("Romance");
				strs.add("Sci-Fi");
				strs.add("Short");
				strs.add("Sport");
				strs.add("Talk-Show");
				strs.add("Thriller");
				strs.add("War");
				strs.add("Western");
				for (String str: strs) {
					if (str.equals(tt)) {
						out.println("<div class=\"genre\"><input type=\"radio\" name=\"genre\" checked=\"checked\" " +
								"value=\"" + str + "\"> <label>" + str + "</label></div>");
					} else {
						out.println("<div class=\"genre\"><input type=\"radio\" name=\"genre\" value=\"" + str + "\"> " +
								"<label>" + str + "</label></div>");
					}
				}
			%>
			<%--<input type="radio" name="genre" value="Action"> <label>Action</label>
			<input type="radio" name="genre" value="Adventure"> <label>Adventure</label>
			<input type="radio" name="genre" value="Animation"> <label>Animation</label>
			<input type="radio" name="genre" value="Biography"> <label>Biography</label>
			<input type="radio" name="genre" value="Crime"> <label>Crime</label>
			<input type="radio" name="genre" value="Comedy"> <label>Comedy</label>
			<input type="radio" name="genre" value="Documentary"> <label>Documentary</label>
			<input type="radio" name="genre" value="Drama"> <label>Drama</label>
			<input type="radio" name="genre" value="Family"> <label>Family</label>
			<input type="radio" name="genre" value="Fantasy"> <label>Fantasy</label>
			<input type="radio" name="genre" value="Film-Noir"> <label>Film-Noir</label>
			<input type="radio" name="genre" value="Game-Show"> <label>Game-Show</label>
			<input type="radio" name="genre" value="History"> <label>History</label>
			<input type="radio" name="genre" value="Horror"> <label>Horror</label>
			<input type="radio" name="genre" value="Musical"> <label>Musical</label>
			<input type="radio" name="genre" value="Mystery"> <label>Mystery</label>
			<input type="radio" name="genre" value="News"> <label>News</label>
			<input type="radio" name="genre" value="Reality-TV"> <label>Reality-TV</label>
			<input type="radio" name="genre" value="Romance"> <label>Romance</label>
			<input type="radio" name="genre" value="Sci-Fi"> <label>Sci-Fi</label>
			<input type="radio" name="genre" value="Short"> <label>Short</label>
			<input type="radio" name="genre" value="Sport"> <label>Sport</label>
			<input type="radio" name="genre" value="Talk-Show"> <label>Talk-Show</label>
			<input type="radio" name="genre" value="Thriller"> <label>Thriller</label>
			<input type="radio" name="genre" value="War"> <label>War</label>
			<input type="radio" name="genre" value="Western"> <label>Western</label>--%>
		</div>
		<p class="submit-button">
			<input type="submit"> <br />
			<br />
			<br />
			<%-- <span id="successMessage"><b>${messages.success}</b></span>  --%>
		</p>
	</form>
	<%
    	List movieToGenresList = (List)request.getAttribute("movieToGenresList");
		if (!movieToGenresList.isEmpty()) {
			out.println("<table id=\"searchResultTable\">\n" +
					"\t\t<tr>\n" +
					"\t\t\t<td><b>MovieTitle</b></td>\n" +
					"\t\t\t<td><b>Year</b></td>\n" +
					"\t\t\t<td><b>Country</b></td>\n" +
					"\t\t</tr>");
      		MovieToGenres movieToGenres;
      		Movies movie;
			for (int i=0; i< movieToGenresList.size(); i++){
        		movieToGenres = (MovieToGenres) movieToGenresList.get(i);
        		movie = movieToGenres.getMovie();
		  		String title = movie.getMovieTitle();
				out.println("<tr>\n" +
						"\t\t\t<td width=\"40%\"><a href=\"moviedetails?movieID=" + movie.getMovieID() +
						"\">" + title + "</a></td>\n" +
						"<td width=\"20%\">" + movie.getYear() +
						"</td><td width=\"40%\">" + movie.getCountry() + "</td></tr>");
			}
		}// end for
    %>
	</table>
</body>
</html>
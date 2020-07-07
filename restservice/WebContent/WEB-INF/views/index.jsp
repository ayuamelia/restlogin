<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<title>Login Page</title>
</head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<body>

	<div class="container">

		<form action="getUserData" class="form-signin" method="post">
			<h3 class="form-heading">Login Here</h3>
			<hr />

			<div class="form-group ${error != null ? 'has-error' : ''}" style="">
				<input name="username" type="text" class="form-control"
					placeholder="Username" autofocus="true"
					style="margin-bottom: 20px; width: 200px; height: 30px; border-radius: 5px;" />
				<input name="password" type="password" class="form-control"
					placeholder="Password"
					style="margin-bottom: 5px; width: 200px; height: 30px; border-radius: 5px;" />
				<a style="color: red">${error}</a><br> <input type="hidden"
					name="${_csrf.parameterName}" value="${_csrf.token}" />

				<button type="submit"
					style="background-color: #323842; color: white; border-radius: 5px;"
					class="btn btn-primary">Login</button>
			</div>

		</form>

	</div>
</body>
</html>
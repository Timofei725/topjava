<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new meal</title>
</head>

<body>

<div>

        <div>
            <h2>Add meal</h2>
        </div>

        <form method="post">

            <label>Date:
                <input type="text" name="dateTime"/>
                <br />
            </label>
            <label>Description:
                <input type="text" name="description"><br />
            </label>
            <label>Calories:
                <input type="text" name="calories"><br />
            </label>

            <button type="submit">Submit</button>
        </form>
    </div>

<div>
    <button onclick="location.href='/'">Back to main</button>
</div>
</body>
</html>
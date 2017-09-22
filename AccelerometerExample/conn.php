<?php
$dbname = "datatraononggerak";
$servername = "localhost:8000";
$username = "root";
$password = "";

// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
//echo "Connected nih successfully cuy";
?>
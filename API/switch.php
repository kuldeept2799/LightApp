<?php

if($_SERVER['REQUEST_METHOD']=='POST'){

require_once('database.php');

$value = $_POST['value'];

$Sql = "UPDATE Switch SET value=$value ";

if(mysqli_query($con,$Sql)){
	echo 'Done';
	}
	
else{ 
    echo 'Something went wrong';

}
}
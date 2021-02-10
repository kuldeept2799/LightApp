<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
    
require_once('database.php');

$sql="SELECT * FROM  Switch";

$r=mysqli_query($con,$sql);

$result = array();

while($res = mysqli_fetch_array($r)){ 
 array_push($result,array(
"value"=>$res['value']
 )
 );
}

echo json_encode(array("result"=>$result));

 mysqli_close($con);
 
 }
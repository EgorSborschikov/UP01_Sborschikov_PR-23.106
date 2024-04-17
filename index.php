<?php
	$mysqli=new mysqli('localhost','root','','kino');
	if ($mysqli->connect_errno) {
		echo "Неудалось подключиться к MySQL: " . mysqli_connect_error();
		return false;
	};
	$mysqli->set_charset("utf8");
	$method = $_SERVER['REQUEST_METHOD'];
	if ($method == 'GET'){
		$a=array();

		if (isset($_GET['cod']) && $_GET['cod']==1){

			$text="select * from theaters where name like '%".$_GET['name']."%' or address like '%".$_GET['name']."%'"; // вместо theaters должно стоять название таблицы в базе на phpmyadmin

			$result=$mysqli->query($text);
			if ($result === false) {
				echo "Query execution failed: " . $mysqli->error;
				return false;
			}
			while ($row = mysqli_fetch_assoc($result)){
				$b=array("name"=>$row['name'],"address"=>$row['address']);
				$a[]=$b;
			}
			echo json_encode($a);
		}
		
		if (isset($_GET['cod']) && $_GET['cod'] == 2){
			$text = "select theaters.name as tname, theaters.address, films.name as fname from theaters INNER JOIN theatres_films ON theaters.ID = theatres_films.theatreID INNER JOIN films ON theatres_films.filmID = films.ID WHERE films.name LIKE '%".$_GET['name']."%'";
			$result = $mysqli->query($text);
			while ($row = $result->fetch_assoc()){
				$b = array("name" => $row['tname'], "address" => $row['address'], "fname" => $row['fname']);
				$a[] = $b;
			}
			echo json_encode($a);
			return;
		}
		if (isset($_GET['cod']) && $_GET['cod']==3){
			
			$text="select * from theaters";
		
			$result=$mysqli->query($text);
			while ($row = mysqli_fetch_assoc($result)){
				$b=array("name"=>$row['name'],"id"=>$row['ID']);
				$a[]=$b;
			}
			echo json_encode($a);
			return;
		}
		if (isset($_GET['cod']) && $_GET['cod']==4){
			
			$text="select * from theaters where name like '%".$_GET['name']."%' or address like '%".$_GET['name']."%'";
		
			$result=$mysqli->query($text);
			while ($row = mysqli_fetch_assoc($result)){
				$b=array("name"=>$row['name'],"address"=>$row['address'], "id"=>$row['ID']);
				$a[]=$b;
			}
			echo json_encode($a);
			return;
		}
		if (isset($_GET['cod']) && $_GET['cod']==5){
			
			$text="select * from films where name like '%".$_GET['name']."%'";
		
			$result=$mysqli->query($text);
			while ($row = mysqli_fetch_assoc($result)){
				$b=array("name"=>$row['name'],"id"=>$row['ID']);
				$a[]=$b;
			}
			echo json_encode($a);
			return;
		}
	};
	if ($method == 'POST'){
		
		if ($_POST['cod']==1){
			$text="insert into `theaters`(`name`, `address`) values ('".$_POST['name']."','".$_POST['address']."')";
			$result=$mysqli->query($text);
			echo $result;
		}
		if ($_POST['cod']==2){
			$text="insert into `films`(`name`) values ('".$_POST['name']."')";
			$result=$mysqli->query($text);
			$text="select max(`ID`) as id from `films`"; 
			$result=$mysqli->query($text);
			if ($row = mysqli_fetch_assoc($result)){
				echo $row['id'];
				
			}
		}
		if ($_POST['cod']==3){
			$text="insert into `theatres_films`(`theatreID`, `filmID`) values (".$_POST['theatreID'].",".$_POST['filmID'].")";
			$result=$mysqli->query($text);
			echo $result;
		}
	};
	if  ($method=="PUT"){
		$_PUT = array(); 
		
		  $putdata = file_get_contents('php://input'); 
		  $exploded = explode('&', $putdata);  

		  foreach($exploded as $pair) { 

			$item = explode('=', $pair); 
			if(count($item) == 2) { 
			  $_PUT[urldecode($item[0])] = urldecode($item[1]); 
			} 
		  } 
		  if ($_PUT['cod']==1) {
			  $text="update `theaters` set `name`='".$_PUT['name']."', `address`='".$_PUT['address']."' where ID=".$_PUT['id'];
			  $result=$mysqli->query($text);
			  echo $result;
		  }
		  if ($_PUT['cod']==2) {
			  $text="update `films` set `name`='".$_PUT['name']."' where ID=".$_PUT['id'];
			  $result=$mysqli->query($text);
			  echo $result;
		  }
		  return;
	
	};
	if  ($method=="DELETE"){
		$_DELETE = array(); 
		
		  $putdata = file_get_contents('php://input'); 
		  $exploded = explode('&', $putdata);  

		  foreach($exploded as $pair) { 

			$item = explode('=', $pair); 
			if(count($item) == 2) { 
			  $_DELETE[urldecode($item[0])] = urldecode($item[1]); 
			} 
		  } 
		  if ($_DELETE['cod']==1) {
			  $text="delete from `theaters` where ID=".$_DELETE['id'];
			  $result=$mysqli->query($text);
			  echo $result;
		}
		
	}
?>
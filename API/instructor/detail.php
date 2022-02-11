<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id = $_GET['instructor_id'];
	
	//Importing database
	require_once('../connection.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM instructor WHERE InstructorID=$id";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
		"instructorid"=>$row['InstructorID'],
		"instructorname"=>$row['InstructorName'],
		"instructoremail"=>$row['InstructorEmail'],
		"instructorphone"=>$row['InstructorPhone']
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('instructorresult'=>$result));
	
	mysqli_close($con);
?>
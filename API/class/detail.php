<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id = $_GET['class_id'];
	
	//Importing database
	require_once('../connection.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM class c JOIN instructor i ON c.InstructorID = i.InstructorID JOIN subject s ON c.SubjectID = s.SubjectID WHERE ClassID=$id";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
		"classid"=>$row['ClassID'],
		"classstart"=>$row['ClassStart'],
		"classend"=>$row['ClassEnd'],
		"classinstructor"=>$row['InstructorName'],
		"classinstructorid"=>$row['InstructorID'],
		"classsubject"=>$row['SubjectName'],
		"classsubjectid"=>$row['SubjectID']
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('classresult'=>$result));
	
	mysqli_close($con);
?>
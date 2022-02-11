<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id = $_GET['subject_id'];
	
	//Importing database
	require_once('../connection.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM subject WHERE SubjectID=$id";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
		"subjectid"=>$row['SubjectID'],
		"subjectname"=>$row['SubjectName']
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('subjectresult'=>$result));
	
	mysqli_close($con);
?>
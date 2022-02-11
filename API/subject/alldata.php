<?php 
	//Import File Koneksi Database
	require_once('../connection.php');
	
	//Membuat SQL Query
	$sql = "SELECT * FROM subject";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"subjectid"=>$row['SubjectID'],
			"subjectname"=>$row['SubjectName']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('subjectresult'=>$result));
	
	mysqli_close($con);
?>
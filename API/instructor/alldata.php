<?php 
	//Import File Koneksi Database
	require_once('../connection.php');
	
	//Membuat SQL Query
	$sql = "SELECT * FROM instructor";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"instructorid"=>$row['InstructorID'],
			"instructorname"=>$row['InstructorName'],
			"instructoremail"=>$row['InstructorEmail'],
			"instructorphone"=>$row['InstructorPhone']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('instructorresult'=>$result));
	
	mysqli_close($con);
?>
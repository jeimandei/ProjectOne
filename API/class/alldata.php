<?php 
	//Import File Koneksi Database
	require_once('../connection.php');
	
	//Membuat SQL Query
	$sql = "SELECT * FROM class c JOIN instructor i ON c.InstructorID = i.InstructorID JOIN subject s ON c.SubjectID = s.SubjectID ORDER BY c.ClassID ASC";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"classid"=>$row['ClassID'],
			"classinstructor"=>$row['InstructorName'],
			"classsubject"=>$row['SubjectName']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('classresult'=>$result));
	
	mysqli_close($con);
?>